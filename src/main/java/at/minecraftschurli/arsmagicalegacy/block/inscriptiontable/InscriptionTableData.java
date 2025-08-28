package at.minecraftschurli.arsmagicalegacy.block.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellGrammar;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellShapeGroup;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;
import java.util.Optional;

public record InscriptionTableData(Optional<Component> name, List<Holder<Skill>> grammar, List<List<Holder<Skill>>> shapeGroups) {
    public static final Codec<InscriptionTableData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ComponentSerialization.CODEC.optionalFieldOf("name").forGetter(InscriptionTableData::name),
        Skill.CODEC.listOf(0, SpellGrammar.MAX_PARTS).fieldOf("grammar").forGetter(InscriptionTableData::grammar),
        Skill.CODEC.listOf(0, SpellShapeGroup.MAX_PARTS).listOf(0, Spell.MAX_SHAPE_GROUPS).fieldOf("shape_groups").forGetter(InscriptionTableData::shapeGroups)
    ).apply(inst, InscriptionTableData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, InscriptionTableData> STREAM_CODEC = StreamCodec.composite(
        ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs::optional), InscriptionTableData::name,
        ByteBufCodecs.holderRegistry(AMRegistryKeys.SKILL).apply(ByteBufCodecs.list()), InscriptionTableData::grammar,
        ByteBufCodecs.holderRegistry(AMRegistryKeys.SKILL).apply(ByteBufCodecs.list()).apply(ByteBufCodecs.list()), InscriptionTableData::shapeGroups,
        InscriptionTableData::new);
    public static final InscriptionTableData EMPTY = new InscriptionTableData(Optional.empty(), List.of(), List.of());
}
