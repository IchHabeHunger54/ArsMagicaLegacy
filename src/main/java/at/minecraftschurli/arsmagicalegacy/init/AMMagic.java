package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface AMMagic {
    ResourceKey<OcculusTab> OFFENSE     = occulusTab("offense");
    ResourceKey<OcculusTab> DEFENSE     = occulusTab("defense");
    ResourceKey<OcculusTab> UTILITY     = occulusTab("utility");
    ResourceKey<OcculusTab> TALENT      = occulusTab("talent");
    ResourceKey<OcculusTab> AFFINITY    = occulusTab("affinity");
    ResourceKey<SkillPoint> BLUE_POINT  = skillPoint("blue");
    ResourceKey<SkillPoint> GREEN_POINT = skillPoint("green");
    ResourceKey<SkillPoint> RED_POINT   = skillPoint("red");

    private static ResourceKey<OcculusTab> occulusTab(String name) {
        return key(AMRegistryKeys.OCCULUS_TAB, name);
    }

    private static ResourceKey<SkillPoint> skillPoint(String name) {
        return key(AMRegistryKeys.SKILL_POINT, name);
    }

    private static ResourceKey<Skill> skill(String name) {
        return key(AMRegistryKeys.SKILL, name);
    }

    private static ResourceKey<Skill> skillForSpellPart(SpellPart part) {
        return skill(ArsMagicaApi.getSpellPartRegistry().getKey(part).getNamespace());
    }
    
    private static <T> ResourceKey<T> key(ResourceKey<Registry<T>> registryKey, String name) {
        return ResourceKey.create(registryKey, ArsMagicaApi.modLoc(name));
    }
}
