package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.SpellPartDataManager;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public record SpellShapeGroup(List<SpellPart> parts, @Nullable PrimarySpellShape primaryShape, List<SpellModifier> primaryModifiers, @Nullable SecondarySpellShape secondaryShape, List<SpellModifier> secondaryModifiers) {
    public static final int MAX_PARTS = 4;
    public static final SpellShapeGroup EMPTY = new SpellShapeGroup(List.of(), null, List.of(), null, List.of());
    public static final Codec<SpellShapeGroup> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ArsMagicaApi.getSpellPartRegistry().byNameCodec().listOf(0, MAX_PARTS).fieldOf("parts").forGetter(SpellShapeGroup::parts)
    ).apply(inst, SpellShapeGroup::of));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellShapeGroup> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.registry(AMRegistryKeys.SPELL_PART).apply(ByteBufCodecs.list()), SpellShapeGroup::parts,
        SpellShapeGroup::of);

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

    public static SpellShapeGroup of(SpellPart... parts) {
        return of(List.of(parts));
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && getClass() == o.getClass() && parts.equals(((SpellShapeGroup) o).parts);
    }

    @Override
    public int hashCode() {
        return parts.hashCode();
    }

    public double getManaCost() {
        if (primaryShape == null) return 0;
        SpellPartDataManager manager = ArsMagicaApi.getSpellPartDataManager();
        double cost = manager.get(primaryShape).mana() * primaryModifiers
            .stream()
            .mapToDouble(e -> manager.get(e).mana())
            .reduce(1, (a, b) -> a * b);
        return secondaryShape == null ? cost : cost + manager.get(secondaryShape).mana() * secondaryModifiers
            .stream()
            .mapToDouble(e -> manager.get(e).mana())
            .reduce(1, (a, b) -> a * b);
    }
}
