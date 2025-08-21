package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface AMMagic {
    ResourceKey<OcculusTab> OFFENSE                   = occulusTab("offense");
    ResourceKey<OcculusTab> DEFENSE                   = occulusTab("defense");
    ResourceKey<OcculusTab> UTILITY                   = occulusTab("utility");
    ResourceKey<OcculusTab> TALENT                    = occulusTab("talent");
    ResourceKey<OcculusTab> AFFINITY                  = occulusTab("affinity");
    ResourceKey<SkillPoint> BLUE_POINT                = skillPoint("blue");
    ResourceKey<SkillPoint> GREEN_POINT               = skillPoint("green");
    ResourceKey<SkillPoint> RED_POINT                 = skillPoint("red");
    ResourceKey<Skill>      AFFINITY_GAINS_BOOST      = skill("affinity_gains_boost");
    ResourceKey<Skill>      AUGMENTED_CASTING         = skill("augmented_casting");
    ResourceKey<Skill>      MANA_REGENERATION_BOOST_1 = skill("mana_regeneration_boost_1");
    ResourceKey<Skill>      MANA_REGENERATION_BOOST_2 = skill("mana_regeneration_boost_2");
    ResourceKey<Skill>      MANA_REGENERATION_BOOST_3 = skill("mana_regeneration_boost_3");
    ResourceKey<Skill>      SPELL_MOTION              = skill("spell_motion");

    private static ResourceKey<OcculusTab> occulusTab(String name) {
        return key(AMRegistryKeys.OCCULUS_TAB, name);
    }

    private static ResourceKey<SkillPoint> skillPoint(String name) {
        return key(AMRegistryKeys.SKILL_POINT, name);
    }

    private static ResourceKey<Skill> skill(String name) {
        return key(AMRegistryKeys.SKILL, name);
    }

    private static <T> ResourceKey<T> key(ResourceKey<Registry<T>> registryKey, String name) {
        return ResourceKey.create(registryKey, ArsMagicaApi.modLoc(name));
    }
}
