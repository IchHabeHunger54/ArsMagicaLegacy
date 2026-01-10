package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.advancement.SkillChangeTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMCriterionTriggers {
    DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES = DeferredRegister.create(Registries.TRIGGER_TYPE, ArsMagicaApi.MOD_ID);

    DeferredHolder<CriterionTrigger<?>, SkillChangeTrigger> SKILL_CHANGE = TRIGGER_TYPES.register("skill_change", SkillChangeTrigger::new);
}
