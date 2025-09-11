package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.ability.AttributeAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface AMAbilities {
    ResourceKey<Ability> SWIM_SPEED             = key("swim_speed");
    ResourceKey<Ability> ENDERMAN_THORNS        = key("enderman_thorns");
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

    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<AttributeAbilityEffect>> ATTRIBUTE_EFFECT = AMRegistries.ABILITY_EFFECTS.register("attribute", () -> AttributeAbilityEffect.CODEC);

    private static ResourceKey<Ability> key(String name) {
        return ResourceKey.create(AMRegistryKeys.ABILITY, ArsMagicaApi.modLoc(name));
    }

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
