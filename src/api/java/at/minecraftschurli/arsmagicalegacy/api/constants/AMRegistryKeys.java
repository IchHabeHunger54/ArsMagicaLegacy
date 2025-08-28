package at.minecraftschurli.arsmagicalegacy.api.constants;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;

/**
 * Holds the registry keys of all registries added by Ars Magica: Legacy.
 */
public interface AMRegistryKeys {
    // @formatter:off
    // Static registries
    ResourceKey<Registry<SpellPart>>            SPELL_PART           = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("spell_part"));
    ResourceKey<Registry<DataComponentType<?>>> SPELL_DATA_COMPONENT = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("spell_data_component"));
    // Datapack registries
    ResourceKey<Registry<Affinity>>   AFFINITY    = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("affinity"));
    ResourceKey<Registry<OcculusTab>> OCCULUS_TAB = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("occulus_tab"));
    ResourceKey<Registry<Skill>>      SKILL       = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("skill"));
    ResourceKey<Registry<SkillPoint>> SKILL_POINT = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("skill_point"));
    // @formatter:on
}
