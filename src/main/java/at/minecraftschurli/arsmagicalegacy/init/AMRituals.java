package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualEffect;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualRequirement;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualTrigger;
import at.minecraftschurli.arsmagicalegacy.ritual.LearnSkillRitualEffect;
import at.minecraftschurli.arsmagicalegacy.ritual.SpellGrammarCastRitualTrigger;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMRituals {
    // @formatter:off
    DeferredRegister<MapCodec<? extends RitualEffect>>      RITUAL_EFFECTS      = DeferredRegister.create(AMRegistries.Keys.RITUAL_EFFECT,      ArsMagicaApi.MOD_ID);
    DeferredRegister<MapCodec<? extends RitualRequirement>> RITUAL_REQUIREMENTS = DeferredRegister.create(AMRegistries.Keys.RITUAL_REQUIREMENT, ArsMagicaApi.MOD_ID);
    DeferredRegister<MapCodec<? extends RitualTrigger<?>>>  RITUAL_TRIGGERS     = DeferredRegister.create(AMRegistries.Keys.RITUAL_TRIGGER,     ArsMagicaApi.MOD_ID);

    DeferredHolder<MapCodec<? extends RitualEffect>, MapCodec<LearnSkillRitualEffect>> LEARN_SKILL_EFFECT = RITUAL_EFFECTS.register("learn_skill", () -> LearnSkillRitualEffect.CODEC);

    DeferredHolder<MapCodec<? extends RitualTrigger<?>>, MapCodec<SpellGrammarCastRitualTrigger>> SPELL_GRAMMAR_CAST_TRIGGER = RITUAL_TRIGGERS.register("spell_grammar_cast", () -> SpellGrammarCastRitualTrigger.CODEC);
    // @formatter:on
}
