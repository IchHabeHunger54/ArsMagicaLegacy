package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;

import java.util.Optional;

public record SpellPrefab(Optional<Component> name, Identifier icon, Spell spell, SpellDataComponentMap spellData) {
    public static final MapCodec<SpellPrefab> MAP_CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        ComponentSerialization.CODEC.optionalFieldOf("name").forGetter(SpellPrefab::name),
        Identifier.CODEC.fieldOf("icon").forGetter(SpellPrefab::icon),
        Spell.CODEC.fieldOf("spell").forGetter(SpellPrefab::spell),
        SpellDataComponentMap.CODEC.optionalFieldOf("spell_data", SpellDataComponentMap.EMPTY).forGetter(SpellPrefab::spellData)
    ).apply(inst, SpellPrefab::new));
    public static final Codec<SpellPrefab> DIRECT_CODEC = MAP_CODEC.codec();
    public static final Codec<Holder<SpellPrefab>> REFERENCE_CODEC = RegistryFileCodec.create(AMRegistries.Keys.SPELL_PREFAB, DIRECT_CODEC);
    public static final Codec<HolderSet<SpellPrefab>> LIST_CODEC = RegistryCodecs.homogeneousList(AMRegistries.Keys.SPELL_PREFAB, DIRECT_CODEC);
}
