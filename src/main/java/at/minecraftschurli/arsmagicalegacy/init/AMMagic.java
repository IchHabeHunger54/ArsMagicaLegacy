package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Ability;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.List;

public interface AMMagic {
    // @formatter:off
    ResourceKey<Ability>    SWIM_SPEED                = ability("swim_speed");
    ResourceKey<Ability>    ENDERMAN_THORNS           = ability("enderman_thorns");
    ResourceKey<Ability>    NETHER_DAMAGE_WATER       = ability("nether_damage_water");
    ResourceKey<Ability>    FIRE_RESISTANCE           = ability("fire_resistance");
    ResourceKey<Ability>    FIRE_PUNCH                = ability("fire_punch");
    ResourceKey<Ability>    WATER_DAMAGE_FIRE         = ability("water_damage_fire");
    ResourceKey<Ability>    RESISTANCE                = ability("resistance");
    ResourceKey<Ability>    HASTE                     = ability("haste");
    ResourceKey<Ability>    FALL_DAMAGE               = ability("fall_damage");
    ResourceKey<Ability>    JUMP_BOOST                = ability("jump_boost");
    ResourceKey<Ability>    FEATHER_FALLING           = ability("feather_falling");
    ResourceKey<Ability>    GRAVITY                   = ability("gravity");
    ResourceKey<Ability>    FROST_PUNCH               = ability("frost_punch");
    ResourceKey<Ability>    FROST_WALKER              = ability("frost_walker");
    ResourceKey<Ability>    SLOWNESS                  = ability("slowness");
    ResourceKey<Ability>    SPEED                     = ability("speed");
    ResourceKey<Ability>    STEP_ASSIST               = ability("step_assist");
    ResourceKey<Ability>    WATER_DAMAGE_LIGHTNING    = ability("water_damage_lightning");
    ResourceKey<Ability>    THORNS                    = ability("thorns");
    ResourceKey<Ability>    SATURATION                = ability("saturation");
    ResourceKey<Ability>    NETHER_DAMAGE_NATURE      = ability("nether_damage_nature");
    ResourceKey<Ability>    SMITE                     = ability("smite");
    ResourceKey<Ability>    REGENERATION              = ability("regeneration");
    ResourceKey<Ability>    NAUSEA                    = ability("nausea");
    ResourceKey<Ability>    MANA_REDUCTION            = ability("mana_reduction");
    ResourceKey<Ability>    CLARITY                   = ability("clarity");
    ResourceKey<Ability>    MAGIC_DAMAGE              = ability("magic_damage");
    ResourceKey<Ability>    POISON_RESISTANCE         = ability("poison_resistance");
    ResourceKey<Ability>    NIGHT_VISION              = ability("night_vision");
    ResourceKey<Ability>    ENDERMAN_PUMPKIN          = ability("enderman_pumpkin");
    ResourceKey<Ability>    LIGHT_HEALTH_REDUCTION    = ability("light_health_reduction");
    ResourceKey<Ability>    WATER_HEALTH_REDUCTION    = ability("water_health_reduction");
    ResourceKey<Affinity>   WATER                     = affinity("water");
    ResourceKey<Affinity>   FIRE                      = affinity("fire");
    ResourceKey<Affinity>   EARTH                     = affinity("earth");
    ResourceKey<Affinity>   AIR                       = affinity("air");
    ResourceKey<Affinity>   ICE                       = affinity("ice");
    ResourceKey<Affinity>   LIGHTNING                 = affinity("lightning");
    ResourceKey<Affinity>   NATURE                    = affinity("nature");
    ResourceKey<Affinity>   LIFE                      = affinity("life");
    ResourceKey<Affinity>   ARCANE                    = affinity("arcane");
    ResourceKey<Affinity>   ENDER                     = affinity("ender");
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
    ResourceKey<Skill>      EXTRA_SUMMONS             = skill("extra_summons");
    ResourceKey<Skill>      MAGE_BAND_1               = skill("mage_band_1");
    ResourceKey<Skill>      MAGE_BAND_2               = skill("mage_band_2");
    ResourceKey<Skill>      MANA_REGENERATION_BOOST_1 = skill("mana_regeneration_boost_1");
    ResourceKey<Skill>      MANA_REGENERATION_BOOST_2 = skill("mana_regeneration_boost_2");
    ResourceKey<Skill>      MANA_REGENERATION_BOOST_3 = skill("mana_regeneration_boost_3");
    ResourceKey<Skill>      SHIELD_OVERLOAD           = skill("shield_overload");
    ResourceKey<Skill>      SPELL_MOTION              = skill("spell_motion");
    // @formatter:on
    List<ResourceKey<Affinity>> AFFINITIES = List.of(WATER, FIRE, EARTH, AIR, ICE, LIGHTNING, NATURE, LIFE, ARCANE, ENDER);
    List<ResourceKey<SkillPoint>> SKILL_POINTS = List.of(BLUE_POINT, GREEN_POINT, RED_POINT);

    private static ResourceKey<Ability> ability(String name) {
        return key(AMRegistryKeys.ABILITY, name);
    }

    private static ResourceKey<Affinity> affinity(String name) {
        return key(AMRegistryKeys.AFFINITY, name);
    }

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
