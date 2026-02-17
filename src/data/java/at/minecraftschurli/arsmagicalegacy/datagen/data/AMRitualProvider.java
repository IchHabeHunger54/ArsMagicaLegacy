package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.data.RitualProvider;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.ritual.LearnSkillRitualEffect;
import at.minecraftschurli.arsmagicalegacy.ritual.SpellGrammarCastRitualTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class AMRitualProvider extends RitualProvider {
    public AMRitualProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        HolderLookup.RegistryLookup<Skill> skills = provider.lookupOrThrow(AMRegistries.Keys.SKILL);
        unlock(skills, AMSpells.BLIZZARD, AMSpells.FROST_DAMAGE, AMSpells.FROST, AMSpells.STORM);
        unlock(skills, AMSpells.DAYLIGHT, AMSpells.DIVINE_INTERVENTION, AMSpells.TRUE_SIGHT, AMSpells.SOLAR);
        unlock(skills, AMSpells.DISMEMBERING, AMSpells.PHYSICAL_DAMAGE, AMSpells.DAMAGE, AMSpells.HEALING, AMSpells.PIERCING);
        unlock(skills, AMSpells.EFFECT_POWER, AMSpells.FLIGHT, AMSpells.FURY, AMSpells.REFLECT, AMSpells.SHRINK, AMSpells.SWIFT_SWIM, AMSpells.TEMPORAL_ANCHOR);
        unlock(skills, AMSpells.FALLING_STAR, AMSpells.ASTRAL_DISTORTION, AMSpells.MAGIC_DAMAGE, AMSpells.GRAVITY);
        unlock(skills, AMSpells.FIRE_RAIN, AMSpells.FIRE_DAMAGE, AMSpells.IGNITION, AMSpells.STORM);
        unlock(skills, AMSpells.HEALTH_BOOST, AMSpells.LIFE_TAP, AMSpells.RESISTANCE);
        unlock(skills, AMSpells.MANA_BLAST, AMSpells.EXPLOSION, AMSpells.MANA_DRAIN);
        unlock(skills, AMSpells.MOONRISE, AMSpells.ENDER_INTERVENTION, AMSpells.NIGHT_VISION, AMSpells.LUNAR);
        unlock(skills, AMSpells.PROSPERITY, AMSpells.DIG, AMSpells.PHYSICAL_DAMAGE, AMSpells.MINING_POWER, AMSpells.SILK_TOUCH);
        builder("unlock_shield_overload", new SpellGrammarCastRitualTrigger(List.of(AMSpells.RESISTANCE.get(), AMSpells.MANA_DRAIN.get())))
            .addEffect(new LearnSkillRitualEffect(skills.getOrThrow(AMMagic.SHIELD_OVERLOAD)));
    }

    @SafeVarargs
    private void unlock(HolderLookup.RegistryLookup<Skill> skills, DeferredHolder<SpellPart, ?> part, DeferredHolder<SpellPart, ?>... parts) {
        ResourceLocation id = part.getId();
        builder("unlock_" + id.getPath(), new SpellGrammarCastRitualTrigger(Arrays.stream(parts)
            .map(DeferredHolder::get)
            .map(e -> (SpellPart) e)
            .toList()))
            .addEffect(new LearnSkillRitualEffect(skills.getOrThrow(ResourceKey.create(AMRegistries.Keys.SKILL, id))));
    }
}
