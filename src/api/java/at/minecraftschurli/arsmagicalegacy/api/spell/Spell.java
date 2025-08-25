package at.minecraftschurli.arsmagicalegacy.api.spell;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.List;
import java.util.Optional;

/**
 * Represents a spell.
 *
 * @param name             The name of the spell.
 * @param shapeGroups      The {@link SpellShapeGroup}s of the spell.
 * @param activeShapeGroup The index of the currently active {@link SpellShapeGroup}.
 * @param grammar          The {@link SpellGrammar} of the spell.
 * @param dataComponents   The data components of the spell.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public record Spell(Optional<Component> name, List<SpellShapeGroup> shapeGroups, int activeShapeGroup, SpellGrammar grammar, PatchedDataComponentMap dataComponents) {
    public static final int MAX_SHAPE_GROUPS = 5;
    public static final Spell EMPTY = new Spell(Optional.empty(), List.of(SpellShapeGroup.EMPTY), 0, SpellGrammar.EMPTY, new PatchedDataComponentMap(DataComponentMap.EMPTY));
    public static final Codec<Spell> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ComponentSerialization.CODEC.optionalFieldOf("name").forGetter(Spell::name),
        SpellShapeGroup.CODEC.listOf(0, MAX_SHAPE_GROUPS).fieldOf("shape_groups").forGetter(Spell::shapeGroups),
        ExtraCodecs.intRange(0, MAX_SHAPE_GROUPS - 1).fieldOf("active_shape_group").forGetter(Spell::activeShapeGroup),
        SpellGrammar.CODEC.fieldOf("grammar").forGetter(Spell::grammar),
        DataComponentPatch.CODEC.fieldOf("data_components").forGetter(Spell::getComponentsPatch)
    ).apply(inst, Spell::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Spell> STREAM_CODEC = StreamCodec.composite(
        ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs::optional), Spell::name,
        SpellShapeGroup.STREAM_CODEC.apply(ByteBufCodecs.list()), Spell::shapeGroups,
        ByteBufCodecs.INT, Spell::activeShapeGroup,
        SpellGrammar.STREAM_CODEC, Spell::grammar,
        DataComponentPatch.STREAM_CODEC, Spell::getComponentsPatch,
        Spell::new);

    private Spell(Optional<Component> name, List<SpellShapeGroup> shapeGroups, int activeShapeGroup, SpellGrammar grammar, DataComponentPatch dataComponents) {
        this(name, shapeGroups, activeShapeGroup, grammar, PatchedDataComponentMap.fromPatch(DataComponentMap.EMPTY, dataComponents));
    }

    private DataComponentPatch getComponentsPatch() {
        return dataComponents.asPatch();
    }

    /**
     * @return The currently active {@link SpellShapeGroup}.
     */
    public SpellShapeGroup currentShapeGroup() {
        return shapeGroups.get(activeShapeGroup);
    }

    /**
     * @return The combined mana cost of the spell.
     */
    public double getManaCost() {
        return currentShapeGroup().getManaCost() * grammar.getManaCost();
    }
}
