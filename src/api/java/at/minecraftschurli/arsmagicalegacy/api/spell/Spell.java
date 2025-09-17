package at.minecraftschurli.arsmagicalegacy.api.spell;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Represents a spell. All fields except {@link Spell#dataComponents} are immutable by contract.
 *
 * @param name             The name of the spell.
 * @param shapeGroups      The {@link SpellShapeGroup}s of the spell.
 * @param activeShapeGroup The index of the currently active {@link SpellShapeGroup}.
 * @param grammar          The {@link SpellGrammar} of the spell.
 * @param dataComponents   The data components of the spell. To modify, call {@link Spell#updateDataComponents(UnaryOperator)}.
 */
public record Spell(Optional<Component> name, List<SpellShapeGroup> shapeGroups, int activeShapeGroup, SpellGrammar grammar, SpellDataComponentMap dataComponents) {
    public static final int MAX_SHAPE_GROUPS = 5;
    public static final Spell EMPTY = new Spell(Optional.empty(), List.of(SpellShapeGroup.EMPTY), 0, SpellGrammar.EMPTY, SpellDataComponentMap.EMPTY);
    public static final Codec<Spell> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ComponentSerialization.CODEC.optionalFieldOf("name").forGetter(Spell::name),
        SpellShapeGroup.CODEC.listOf(0, MAX_SHAPE_GROUPS).fieldOf("shape_groups").forGetter(Spell::shapeGroups),
        ExtraCodecs.intRange(0, MAX_SHAPE_GROUPS - 1).fieldOf("active_shape_group").forGetter(Spell::activeShapeGroup),
        SpellGrammar.CODEC.fieldOf("grammar").forGetter(Spell::grammar),
        SpellDataComponentMap.CODEC.fieldOf("components").forGetter(Spell::dataComponents)
    ).apply(inst, Spell::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Spell> STREAM_CODEC = StreamCodec.composite(
        ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs::optional), Spell::name,
        SpellShapeGroup.STREAM_CODEC.apply(ByteBufCodecs.list()), Spell::shapeGroups,
        ByteBufCodecs.INT, Spell::activeShapeGroup,
        SpellGrammar.STREAM_CODEC, Spell::grammar,
        SpellDataComponentMap.STREAM_CODEC, Spell::dataComponents,
        Spell::new);

    /**
     * @param operator The modifications to apply to the data components.
     * @return A new spell with the modifications to the data components applied.
     */
    public Spell updateDataComponents(UnaryOperator<SpellDataComponentMap> operator) {
        return new Spell(name, shapeGroups, activeShapeGroup, grammar, operator.apply(dataComponents));
    }

    /**
     * @return The currently active {@link SpellShapeGroup}.
     */
    public SpellShapeGroup currentShapeGroup() {
        return shapeGroups.get(activeShapeGroup);
    }

    /**
     * @return Whether the spell is considered empty.
     */
    public boolean isEmpty() {
        return grammar.isEmpty() || shapeGroups.isEmpty() || shapeGroups.stream().allMatch(SpellShapeGroup::isEmpty);
    }

    /**
     * @return Whether this spell is continuous, i.e., can be cast by holding down the spell.
     */
    public boolean isContinuous() {
        return currentShapeGroup().isContinuous();
    }

    /**
     * @return The combined mana cost of the spell.
     */
    public double getManaCost() {
        return currentShapeGroup().getManaCost() * grammar.getManaCost();
    }
}
