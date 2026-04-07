package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Represents the data components of a {@link Spell}. All fields are immutable by contract. To change the values of a field, call {@link SpellDataComponentMap#updateShapeGroup(int, Consumer)} or {@link SpellDataComponentMap#updateGrammar(Consumer)}.
 *
 * @param shapeGroups The {@link SpellShapeGroup}-specific {@link PatchedDataComponentMap}s.
 * @param grammar     The {@link SpellGrammar}-specific {@link PatchedDataComponentMap}.
 */
public record SpellDataComponentMap(List<PatchedDataComponentMap> shapeGroups, PatchedDataComponentMap grammar) {
    private static final PatchedDataComponentMap EMPTY_COMPONENT_MAP = new PatchedDataComponentMap(DataComponentMap.EMPTY);
    private static final List<PatchedDataComponentMap> EMPTY_LIST = IntStream.range(0, Spell.MAX_SHAPE_GROUPS).mapToObj(_ -> EMPTY_COMPONENT_MAP).toList();
    private static final Codec<PatchedDataComponentMap> COMPONENT_MAP_CODEC = DataComponentPatch.CODEC.xmap(patch -> PatchedDataComponentMap.fromPatch(DataComponentMap.EMPTY, patch), PatchedDataComponentMap::asPatch);
    private static final StreamCodec<RegistryFriendlyByteBuf, PatchedDataComponentMap> COMPONENT_MAP_STREAM_CODEC = DataComponentPatch.STREAM_CODEC.map(patch -> PatchedDataComponentMap.fromPatch(DataComponentMap.EMPTY, patch), PatchedDataComponentMap::asPatch);
    public static final MapCodec<SpellDataComponentMap> MAP_CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        COMPONENT_MAP_CODEC.listOf(Spell.MAX_SHAPE_GROUPS, Spell.MAX_SHAPE_GROUPS).optionalFieldOf("shape_groups", EMPTY_LIST).forGetter(SpellDataComponentMap::shapeGroups),
        COMPONENT_MAP_CODEC.optionalFieldOf("grammar", EMPTY_COMPONENT_MAP).forGetter(SpellDataComponentMap::grammar)
    ).apply(inst, SpellDataComponentMap::new));
    public static final Codec<SpellDataComponentMap> CODEC = MAP_CODEC.codec();
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellDataComponentMap> STREAM_CODEC = StreamCodec.composite(
        COMPONENT_MAP_STREAM_CODEC.apply(ByteBufCodecs.list()), SpellDataComponentMap::shapeGroups,
        COMPONENT_MAP_STREAM_CODEC, SpellDataComponentMap::grammar,
        SpellDataComponentMap::new);
    public static final SpellDataComponentMap EMPTY = new SpellDataComponentMap(List.of(EMPTY_COMPONENT_MAP, EMPTY_COMPONENT_MAP, EMPTY_COMPONENT_MAP, EMPTY_COMPONENT_MAP, EMPTY_COMPONENT_MAP), EMPTY_COMPONENT_MAP);

    /**
     * @param index The index of the data components to return. If positive, will return the corresponding shape group. If negative, will return the grammar.
     * @return The {@link PatchedDataComponentMap} for the specified index.
     */
    public PatchedDataComponentMap get(int index) {
        return index < 0 ? grammar : shapeGroups.get(index);
    }

    /**
     * @param index    The index of the data components to modify. If positive, will modify the corresponding shape group. If negative, will modify the grammar.
     * @param consumer The modifications to apply.
     * @return A new spell data component map with the modifications to the data components applied.
     */
    public SpellDataComponentMap update(int index, Consumer<PatchedDataComponentMap> consumer) {
        return index < 0 ? updateGrammar(consumer) : updateShapeGroup(index, consumer);
    }

    /**
     * @param index    The index of the data components to modify.
     * @param consumer The modifications to apply.
     * @return A new spell data component map with the modifications to the data components applied.
     */
    public SpellDataComponentMap updateShapeGroup(int index, Consumer<PatchedDataComponentMap> consumer) {
        List<PatchedDataComponentMap> shapeGroups = new ArrayList<>(this.shapeGroups);
        PatchedDataComponentMap shapeGroup = shapeGroups.get(index).copy();
        consumer.accept(shapeGroup);
        shapeGroups.set(index, shapeGroup);
        return new SpellDataComponentMap(shapeGroups, grammar);
    }

    /**
     * @param consumer The modifications to apply.
     * @return A new spell data component map with the modifications to the data components applied.
     */
    public SpellDataComponentMap updateGrammar(Consumer<PatchedDataComponentMap> consumer) {
        PatchedDataComponentMap grammar = this.grammar.copy();
        consumer.accept(grammar);
        return new SpellDataComponentMap(shapeGroups, grammar);
    }
}
