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

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public record Spell(Optional<Component> name, List<SpellShapeGroup> shapeGroups, int currentShapeGroup, SpellGrammar grammar, PatchedDataComponentMap dataComponents) {
    public static final int MAX_SHAPE_GROUPS = 5;
    public static final Spell EMPTY = new Spell(Optional.empty(), List.of(SpellShapeGroup.EMPTY), 0, SpellGrammar.EMPTY, new PatchedDataComponentMap(DataComponentMap.EMPTY));
    public static final Codec<Spell> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ComponentSerialization.CODEC.optionalFieldOf("name").forGetter(Spell::name),
        SpellShapeGroup.CODEC.listOf(0, MAX_SHAPE_GROUPS).fieldOf("shape_groups").forGetter(Spell::shapeGroups),
        ExtraCodecs.intRange(0, MAX_SHAPE_GROUPS - 1).fieldOf("current_shape_group").forGetter(Spell::currentShapeGroup),
        SpellGrammar.CODEC.fieldOf("grammar").forGetter(Spell::grammar),
        DataComponentPatch.CODEC.fieldOf("data_components").forGetter(Spell::getComponentsPatch)
    ).apply(inst, Spell::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Spell> STREAM_CODEC = StreamCodec.composite(
        ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs::optional), Spell::name,
        SpellShapeGroup.STREAM_CODEC.apply(ByteBufCodecs.list()), Spell::shapeGroups,
        ByteBufCodecs.INT, Spell::currentShapeGroup,
        SpellGrammar.STREAM_CODEC, Spell::grammar,
        DataComponentPatch.STREAM_CODEC, Spell::getComponentsPatch,
        Spell::new);

    private Spell(Optional<Component> name, List<SpellShapeGroup> shapeGroups, int currentShapeGroup, SpellGrammar grammar, DataComponentPatch dataComponents) {
        this(name, shapeGroups, currentShapeGroup, grammar, PatchedDataComponentMap.fromPatch(DataComponentMap.EMPTY, dataComponents));
    }

    private DataComponentPatch getComponentsPatch() {
        return dataComponents.asPatch();
    }
}
