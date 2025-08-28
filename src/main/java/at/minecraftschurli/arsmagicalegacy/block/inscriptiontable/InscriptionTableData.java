package at.minecraftschurli.arsmagicalegacy.block.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellGrammar;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellShapeGroup;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;

import java.util.List;
import java.util.Objects;
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

    public static InscriptionTableData fromSpell(Spell spell, RegistryAccess registryAccess) {
        List<List<Holder<Skill>>> groups = spell.shapeGroups()
            .stream()
            .map(e -> skills(e.parts(), registryAccess))
            .toList();
        return new InscriptionTableData(spell.name(), skills(spell.grammar().parts(), registryAccess), groups);
    }

    public Spell toSpell() {
        List<SpellShapeGroup> groups = shapeGroups.stream()
            .map(InscriptionTableData::spellParts)
            .map(SpellShapeGroup::of)
            .toList();
        return new Spell(name, groups, 0, SpellGrammar.of(spellParts(grammar)), new PatchedDataComponentMap(DataComponentMap.EMPTY));
    }

    private static List<SpellPart> spellParts(List<Holder<Skill>> skills) {
        return skills.stream()
            .map(Holder::getKey)
            .filter(Objects::nonNull)
            .map(ResourceKey::location)
            .map(ArsMagicaApi.spellPartRegistry()::get)
            .toList();
    }

    private static List<Holder<Skill>> skills(List<SpellPart> parts, RegistryAccess registryAccess) {
        return parts.stream()
            .map(ArsMagicaApi.spellPartRegistry()::getKey)
            .filter(Objects::nonNull)
            .map(registryAccess.registryOrThrow(AMRegistryKeys.SKILL)::getHolder)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(e -> (Holder<Skill>) e)
            .toList();
    }
}
