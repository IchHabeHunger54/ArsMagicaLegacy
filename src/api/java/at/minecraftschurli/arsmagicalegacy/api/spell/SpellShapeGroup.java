package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a spell's shape group. One spell may have up to {@link Spell#MAX_SHAPE_GROUPS} different shape groups.
 *
 * @param parts              A {@link List} of all parts. Immutable by contract. Used mainly for serialization, use the other fields for gameplay.
 * @param primaryShape       The {@link PrimarySpellShape} of the shape group.
 * @param primaryModifiers   A {@link List} of {@link SpellModifier}s for the {@link PrimarySpellShape}.
 * @param secondaryShape     The {@link SecondarySpellShape} of the shape group.
 * @param secondaryModifiers A {@link List} of {@link SpellModifier}s for the {@link SecondarySpellShape}.
 */
public record SpellShapeGroup(List<SpellPart> parts, @Nullable PrimarySpellShape primaryShape, List<SpellModifier> primaryModifiers, @Nullable SecondarySpellShape secondaryShape, List<SpellModifier> secondaryModifiers) {
    public static final int MAX_PARTS = 4;
    public static final SpellShapeGroup EMPTY = new SpellShapeGroup(List.of(), null, List.of(), null, List.of());
    public static final Codec<SpellShapeGroup> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ArsMagicaApi.spellPartRegistry().byNameCodec().listOf(0, MAX_PARTS).fieldOf("parts").forGetter(SpellShapeGroup::parts)
    ).apply(inst, SpellShapeGroup::of));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellShapeGroup> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.registry(AMRegistryKeys.SPELL_PART).apply(ByteBufCodecs.list()), SpellShapeGroup::parts,
        SpellShapeGroup::of);

    /**
     * @deprecated Use {@link SpellShapeGroup#of(List)} instead.
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public SpellShapeGroup {
    }

    /**
     * Validates the given {@link List} of {@link SpellPart}s and constructs a {@link SpellShapeGroup} from it.
     *
     * @param parts The {@link List} of {@link SpellPart}s.
     * @return A new {@link SpellShapeGroup}, or {@link SpellShapeGroup#EMPTY} if validation failed.
     */
    public static SpellShapeGroup of(List<SpellPart> parts) {
        if (parts.isEmpty() || !parts.getFirst().isPrimaryShape()) return EMPTY;
        if (parts.size() > MAX_PARTS) {
            parts = parts.subList(0, MAX_PARTS);
        }
        PrimarySpellShape primary = (PrimarySpellShape) parts.getFirst();
        List<SpellModifier> primaryModifiers = new ArrayList<>();
        SecondarySpellShape secondary = null;
        List<SpellModifier> secondaryModifiers = new ArrayList<>();
        for (int i = 1; i < parts.size(); i++) {
            SpellPart part = parts.get(i);
            if (part.isComponent() || part.isPrimaryShape()) continue;
            if (part.isModifier()) {
                if (secondary == null) {
                    primaryModifiers.add((SpellModifier) part);
                } else {
                    secondaryModifiers.add((SpellModifier) part);
                }
            }
            if (part.isSecondaryShape() && secondary == null) {
                secondary = (SecondarySpellShape) part;
            }
        }
        return new SpellShapeGroup(parts, primary, primaryModifiers, secondary, secondaryModifiers);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && getClass() == o.getClass() && parts.equals(((SpellShapeGroup) o).parts);
    }

    @Override
    public int hashCode() {
        return parts.hashCode();
    }

    /**
     * @return The combined mana cost of the spell shape group.
     */
    public double getManaCost() {
        if (primaryShape == null) return 0;
        double cost = primaryShape.getData().mana() * primaryModifiers
            .stream()
            .mapToDouble(e -> e.getData().mana())
            .reduce(1, (a, b) -> a * b);
        return secondaryShape == null ? cost : cost + secondaryShape.getData().mana() * secondaryModifiers
            .stream()
            .mapToDouble(e -> e.getData().mana())
            .reduce(1, (a, b) -> a * b);
    }
}
