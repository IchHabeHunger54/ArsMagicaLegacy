package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record SpellShapeGroup(List<SpellPart> parts, List<Pair<SpellShape, List<SpellModifier>>> shapes) {
    public static final int MAX_PARTS = 4;
    public static final SpellShapeGroup EMPTY = new SpellShapeGroup(List.of(), List.of());
    public static final Codec<SpellShapeGroup> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ArsMagicaApi.getSpellPartRegistry().byNameCodec().listOf().fieldOf("parts").forGetter(SpellShapeGroup::parts)
    ).apply(inst, SpellShapeGroup::of));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellShapeGroup> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.registry(AMRegistryKeys.SPELL_PART).apply(ByteBufCodecs.list()), SpellShapeGroup::parts,
        SpellShapeGroup::of);

    public static SpellShapeGroup of(List<SpellPart> parts) {
        if (parts.isEmpty() || !parts.getFirst().isShape()) return EMPTY;
        if (parts.size() > MAX_PARTS) {
            parts = parts.subList(0, MAX_PARTS);
        }
        List<Pair<SpellShape, List<SpellModifier>>> shapes = new ArrayList<>();
        SpellShape currentShape = null;
        List<SpellModifier> currentModifiers = new ArrayList<>();
        boolean hasFirstShape = false;
        boolean hasLastShape = false;
        for (SpellPart part : parts) {
            if (part.isComponent()) return EMPTY;
            if (part.isModifier()) {
                currentModifiers.add((SpellModifier) part);
            } else if (part.isShape()) {
                if (currentShape != null) {
                    shapes.add(Pair.of(currentShape, Collections.unmodifiableList(currentModifiers)));
                    currentModifiers = new ArrayList<>();
                }
                SpellShape shape = (SpellShape) part;
                if (hasLastShape) return EMPTY;
                if (hasFirstShape && shape.mustBeFirst()) return EMPTY;
                if (!hasFirstShape && shape.mustNotBeFirst()) return EMPTY;
                currentShape = shape;
                hasFirstShape = true;
                if (shape.mustBeLast()) {
                    hasLastShape = true;
                }
            }
        }
        return new SpellShapeGroup(parts, Collections.unmodifiableList(shapes));
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
}
