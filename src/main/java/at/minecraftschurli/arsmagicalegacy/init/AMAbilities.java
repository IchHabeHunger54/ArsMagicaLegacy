package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.ability.AttributeAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.DamageModifierAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.EffectAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.EffectResistanceAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.EndermanPumpkinAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.ExtraDamageAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.FirePunchAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.FrostPunchAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.FrostWalkerAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.JumpBoostAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.KillEffectAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.LightHealthModifierAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.ManaCostModifierAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.NetherDamageAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.SpellCastEffectAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.ThornsAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.WaterDamageAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.WaterHealthModifierAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import net.minecraft.resources.ResourceKey;

public interface AMAbilities {
    // @formatter:off
    ResourceKey<Ability> SWIM_SPEED             = key("swim_speed");
    ResourceKey<Ability> ENDER_THORNS           = key("ender_thorns");
    ResourceKey<Ability> NETHER_DAMAGE_WATER    = key("nether_damage_water");
    ResourceKey<Ability> FIRE_RESISTANCE        = key("fire_resistance");
    ResourceKey<Ability> FIRE_PUNCH             = key("fire_punch");
    ResourceKey<Ability> WATER_DAMAGE_FIRE      = key("water_damage_fire");
    ResourceKey<Ability> RESISTANCE             = key("resistance");
    ResourceKey<Ability> HASTE                  = key("haste");
    ResourceKey<Ability> FALL_DAMAGE            = key("fall_damage");
    ResourceKey<Ability> JUMP_BOOST             = key("jump_boost");
    ResourceKey<Ability> FEATHER_FALLING        = key("feather_falling");
    ResourceKey<Ability> GRAVITY                = key("gravity");
    ResourceKey<Ability> FROST_PUNCH            = key("frost_punch");
    ResourceKey<Ability> FROST_WALKER           = key("frost_walker");
    ResourceKey<Ability> SLOWNESS               = key("slowness");
    ResourceKey<Ability> SPEED                  = key("speed");
    ResourceKey<Ability> STEP_ASSIST            = key("step_assist");
    ResourceKey<Ability> WATER_DAMAGE_LIGHTNING = key("water_damage_lightning");
    ResourceKey<Ability> THORNS                 = key("thorns");
    ResourceKey<Ability> SATURATION             = key("saturation");
    ResourceKey<Ability> NETHER_DAMAGE_NATURE   = key("nether_damage_nature");
    ResourceKey<Ability> SMITE                  = key("smite");
    ResourceKey<Ability> REGENERATION           = key("regeneration");
    ResourceKey<Ability> NAUSEA                 = key("nausea");
    ResourceKey<Ability> MANA_REDUCTION         = key("mana_reduction");
    ResourceKey<Ability> CLARITY                = key("clarity");
    ResourceKey<Ability> MAGIC_DAMAGE           = key("magic_damage");
    ResourceKey<Ability> POISON_RESISTANCE      = key("poison_resistance");
    ResourceKey<Ability> NIGHT_VISION           = key("night_vision");
    ResourceKey<Ability> ENDERMAN_PUMPKIN       = key("enderman_pumpkin");
    ResourceKey<Ability> LIGHT_HEALTH_REDUCTION = key("light_health_reduction");
    ResourceKey<Ability> WATER_HEALTH_REDUCTION = key("water_health_reduction");
    // @formatter:on

    private static ResourceKey<Ability> key(String name) {
        return ResourceKey.create(AMRegistryKeys.ABILITY, ArsMagicaApi.modLoc(name));
    }

    static void init() {
        // @formatter:off
        AMRegistries.ABILITY_EFFECTS.register("attribute",             () -> AttributeAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("damage_modifier",       () -> DamageModifierAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("enderman_pumpkin",      () -> EndermanPumpkinAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("effect",                () -> EffectAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("effect_resistance",     () -> EffectResistanceAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("extra_damage",          () -> ExtraDamageAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("fire_punch",            () -> FirePunchAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("frost_punch",           () -> FrostPunchAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("frost_walker",          () -> FrostWalkerAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("jump_boost",            () -> JumpBoostAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("kill_effect",           () -> KillEffectAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("light_health_modifier", () -> LightHealthModifierAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("mana_cost_modifier",    () -> ManaCostModifierAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("nether_damage",         () -> NetherDamageAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("spell_cast_effect",     () -> SpellCastEffectAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("thorns",                () -> ThornsAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("water_damage",          () -> WaterDamageAbilityEffect.CODEC);
        AMRegistries.ABILITY_EFFECTS.register("water_health_modifier", () -> WaterHealthModifierAbilityEffect.CODEC);
        // @formatter:on
    }
}
