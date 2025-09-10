package at.minecraftschurli.arsmagicalegacy.api.constants;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;

/**
 * Holds the registry keys of all registries added by Ars Magica: Legacy.
 */
public interface AMRegistryKeys {
    // @formatter:off
    // Static registries
    ResourceKey<Registry<SpellPart>>                           SPELL_PART           = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("spell_part"));
    ResourceKey<Registry<DataComponentType<?>>>                SPELL_DATA_COMPONENT = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("spell_data_component"));
    ResourceKey<Registry<MapCodec<? extends SpellIngredient>>> SPELL_INGREDIENT     = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("spell_ingredient"));
    ResourceKey<Registry<MapCodec<? extends AbilityEffect>>>   ABILITY_EFFECT       = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("ability_effect"));
    // Datapack registries
    ResourceKey<Registry<Ability>>          ABILITY            = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("ability"));
    ResourceKey<Registry<Affinity>>         AFFINITY           = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("affinity"));
    ResourceKey<Registry<AltarCapMaterial>> ALTAR_CAP_MATERIAL = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("altar_cap_material"));
    ResourceKey<Registry<AltarMaterial>>    ALTAR_MATERIAL     = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("altar_material"));
    ResourceKey<Registry<OcculusTab>>       OCCULUS_TAB        = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("occulus_tab"));
    ResourceKey<Registry<Skill>>            SKILL              = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("skill"));
    ResourceKey<Registry<SkillPoint>>       SKILL_POINT        = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("skill_point"));
    // @formatter:on
}
