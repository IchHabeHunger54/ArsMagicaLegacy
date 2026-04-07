package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

/**
 * Represents a spell.
 *
 * @param shapeGroups      The {@link SpellShapeGroup}s of the spell. Immutable by contract.
 * @param grammar          The {@link SpellGrammar} of the spell. Immutable by contract.
 */
public record Spell(List<SpellShapeGroup> shapeGroups, SpellGrammar grammar) {
    public static final int MAX_SHAPE_GROUPS = 5;
    public static final Spell EMPTY = new Spell(List.of(), SpellGrammar.EMPTY);
    public static final Codec<Spell> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        SpellShapeGroup.CODEC.listOf(0, MAX_SHAPE_GROUPS).optionalFieldOf("shape_groups", List.of()).forGetter(Spell::shapeGroups),
        SpellGrammar.CODEC.fieldOf("grammar").forGetter(Spell::grammar)
    ).apply(inst, Spell::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Spell> STREAM_CODEC = StreamCodec.composite(
        SpellShapeGroup.STREAM_CODEC.apply(ByteBufCodecs.list()), Spell::shapeGroups,
        SpellGrammar.STREAM_CODEC, Spell::grammar,
        Spell::new);

    public Spell {
        Preconditions.checkNotNull(shapeGroups, "Shape groups cannot be null");
        Preconditions.checkNotNull(grammar, "Grammar cannot be null");
        Preconditions.checkArgument(shapeGroups.size() <= MAX_SHAPE_GROUPS, "Shape groups cannot exceed " + MAX_SHAPE_GROUPS);
        shapeGroups = List.copyOf(shapeGroups);
    }

    /// @return Whether the spell is considered empty.
    public boolean isEmpty() {
        return grammar.isEmpty() && (shapeGroups.isEmpty() || shapeGroups.stream().allMatch(SpellShapeGroup::isEmpty));
    }
}
