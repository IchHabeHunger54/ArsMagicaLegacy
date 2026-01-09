package at.minecraftschurli.arsmagicalegacy.datagen.data;

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
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.init.AMAbilities;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMMobEffects;
import at.minecraftschurli.arsmagicalegacy.util.LinearAttributeModifier;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.ReplaceDisk;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class AMAbilityProvider {
    public static final Map<ResourceKey<Ability>, PatchouliAbilityData> PATCHOULI_ABILITY_DATA = new HashMap<>();

    public static void addAbilities(BootstrapContext<Ability> bootstrap) {
        HolderGetter<DamageType> damageTypes = bootstrap.lookup(Registries.DAMAGE_TYPE);
        HolderGetter<EntityType<?>> entityTypes = bootstrap.lookup(Registries.ENTITY_TYPE);
        // @formatter:off
        register(bootstrap, AMAbilities.SWIM_SPEED,             AMMagic.WATER,     MinMaxBounds.Doubles.between(0.01, 1), new AttributeAbilityEffect(Map.of(NeoForgeMod.SWIM_SPEED, new LinearAttributeModifier(AMAbilities.SWIM_SPEED.location(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))));
        register(bootstrap, AMAbilities.ENDER_THORNS,           AMMagic.WATER,     MinMaxBounds.Doubles.atLeast(1),       new ThornsAbilityEffect(1, 1, Optional.of(entityTypes.getOrThrow(AMTags.EntityTypes.AFFECTED_BY_ENDER_THORNS_ABILITY))));
        register(bootstrap, AMAbilities.NETHER_DAMAGE_WATER,    AMMagic.WATER,     MinMaxBounds.Doubles.between(0.5, 1),  true, new NetherDamageAbilityEffect(0, 0.25));
        register(bootstrap, AMAbilities.FIRE_RESISTANCE,        AMMagic.FIRE,      MinMaxBounds.Doubles.between(0.01, 1), new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_FIRE_RESISTANCE_ABILITY), 1, 0.5));
        register(bootstrap, AMAbilities.FIRE_PUNCH,             AMMagic.FIRE,      MinMaxBounds.Doubles.atLeast(1),       new FirePunchAbilityEffect(100, 100));
        register(bootstrap, AMAbilities.WATER_DAMAGE_FIRE,      AMMagic.FIRE,      MinMaxBounds.Doubles.between(0.5, 1),  true, new WaterDamageAbilityEffect(0, 0.25));
        register(bootstrap, AMAbilities.RESISTANCE,             AMMagic.EARTH,     MinMaxBounds.Doubles.between(0.01, 1), new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_RESISTANCE_ABILITY), 1, 0.5));
        register(bootstrap, AMAbilities.HASTE,                  AMMagic.EARTH,     MinMaxBounds.Doubles.between(0.01, 1), new AttributeAbilityEffect(Map.of(Attributes.ATTACK_SPEED, new LinearAttributeModifier(AMAbilities.HASTE.location(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), Attributes.BLOCK_BREAK_SPEED, new LinearAttributeModifier(AMAbilities.HASTE.location(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))));
        register(bootstrap, AMAbilities.FALL_DAMAGE,            AMMagic.EARTH,     MinMaxBounds.Doubles.between(0.5, 1),  true, new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_FALL_DAMAGE_ABILITY), 1, 1.5));
        register(bootstrap, AMAbilities.JUMP_BOOST,             AMMagic.AIR,       MinMaxBounds.Doubles.between(0.01, 1), new JumpBoostAbilityEffect(0, 0.5));
        register(bootstrap, AMAbilities.FEATHER_FALLING,        AMMagic.AIR,       MinMaxBounds.Doubles.between(0.01, 1), new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_FEATHER_FALLING_ABILITY), 1, 0.5));
        register(bootstrap, AMAbilities.GRAVITY,                AMMagic.AIR,       MinMaxBounds.Doubles.between(0.5, 1),  true, new AttributeAbilityEffect(Map.of(Attributes.GRAVITY, new LinearAttributeModifier(AMAbilities.GRAVITY.location(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))));
        register(bootstrap, AMAbilities.FROST_PUNCH,            AMMagic.ICE,       MinMaxBounds.Doubles.between(0.01, 1), new FrostPunchAbilityEffect(0, 100));
        register(bootstrap, AMAbilities.FROST_WALKER,           AMMagic.ICE,       MinMaxBounds.Doubles.atLeast(1),       new FrostWalkerAbilityEffect(1, 1, new ReplaceDisk(
            new LevelBasedValue.Clamped(LevelBasedValue.perLevel(3, 1), 0, 16),
            LevelBasedValue.constant(1),
            new Vec3i(0, -1, 0),
            Optional.of(BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, 1, 0), BlockTags.AIR), BlockPredicate.matchesBlocks(Blocks.WATER), BlockPredicate.matchesFluids(Fluids.WATER), BlockPredicate.unobstructed())),
            BlockStateProvider.simple(Blocks.FROSTED_ICE),
            Optional.of(GameEvent.BLOCK_PLACE))));
        register(bootstrap, AMAbilities.SLOWNESS,               AMMagic.ICE,       MinMaxBounds.Doubles.between(0.5, 1),  true, new AttributeAbilityEffect(Map.of(Attributes.MOVEMENT_SPEED, new LinearAttributeModifier(AMAbilities.SLOWNESS.location(), 0, -0.05, AttributeModifier.Operation.ADD_VALUE))));
        register(bootstrap, AMAbilities.SPEED,                  AMMagic.LIGHTNING, MinMaxBounds.Doubles.between(0.01, 1), new AttributeAbilityEffect(Map.of(Attributes.MOVEMENT_SPEED, new LinearAttributeModifier(AMAbilities.SPEED.location(), 0, 0.05, AttributeModifier.Operation.ADD_VALUE))));
        register(bootstrap, AMAbilities.STEP_ASSIST,            AMMagic.LIGHTNING, MinMaxBounds.Doubles.atLeast(1),       new AttributeAbilityEffect(Map.of(Attributes.STEP_HEIGHT, new LinearAttributeModifier(AMAbilities.STEP_ASSIST.location(), 0, 0.4, AttributeModifier.Operation.ADD_VALUE))));
        register(bootstrap, AMAbilities.WATER_DAMAGE_LIGHTNING, AMMagic.LIGHTNING, MinMaxBounds.Doubles.between(0.5, 1),  true, new WaterDamageAbilityEffect(0, 0.25));
        register(bootstrap, AMAbilities.THORNS,                 AMMagic.NATURE,    MinMaxBounds.Doubles.between(0.01, 1), new ThornsAbilityEffect(0, 0.5, Optional.empty()));
        register(bootstrap, AMAbilities.SATURATION,             AMMagic.NATURE,    MinMaxBounds.Doubles.atLeast(1),       new EffectAbilityEffect(MobEffects.SATURATION, 10, 0, false));
        register(bootstrap, AMAbilities.NETHER_DAMAGE_NATURE,   AMMagic.NATURE,    MinMaxBounds.Doubles.between(0.5, 1),  true, new NetherDamageAbilityEffect(0, 0.25));
        register(bootstrap, AMAbilities.SMITE,                  AMMagic.LIFE,      MinMaxBounds.Doubles.between(0.01, 1), new ExtraDamageAbilityEffect(0, 4, Optional.of(entityTypes.getOrThrow(AMTags.EntityTypes.AFFECTED_BY_SMITE_ABILITY))));
        register(bootstrap, AMAbilities.REGENERATION,           AMMagic.LIFE,      MinMaxBounds.Doubles.atLeast(1),       new EffectAbilityEffect(MobEffects.REGENERATION, 10, 0, false));
        register(bootstrap, AMAbilities.NAUSEA,                 AMMagic.LIFE,      MinMaxBounds.Doubles.between(0.5, 1),  true, new KillEffectAbilityEffect(MobEffects.CONFUSION, 0, 600, 0, false, Optional.of(entityTypes.getOrThrow(AMTags.EntityTypes.AFFECTED_BY_NAUSEA_ABILITY))));
        register(bootstrap, AMAbilities.MANA_REDUCTION,         AMMagic.ARCANE,    MinMaxBounds.Doubles.between(0.01, 1), new ManaCostModifierAbilityEffect(1, 0.5));
        register(bootstrap, AMAbilities.CLARITY,                AMMagic.ARCANE,    MinMaxBounds.Doubles.atLeast(1),       new SpellCastEffectAbilityEffect(AMMobEffects.CLARITY, 1200, 0, true, 0.5));
        register(bootstrap, AMAbilities.MAGIC_DAMAGE,           AMMagic.ARCANE,    MinMaxBounds.Doubles.between(0.5, 1),  true, new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_MAGIC_DAMAGE_ABILITY), 1, 1.5));
        register(bootstrap, AMAbilities.POISON_RESISTANCE,      AMMagic.ENDER,     MinMaxBounds.Doubles.between(0.5, 1),  new EffectResistanceAbilityEffect(List.of(MobEffects.POISON)));
        register(bootstrap, AMAbilities.NIGHT_VISION,           AMMagic.ENDER,     MinMaxBounds.Doubles.between(0.5, 1),  new EffectAbilityEffect(MobEffects.NIGHT_VISION, 210, 0, false));
        register(bootstrap, AMAbilities.ENDERMAN_PUMPKIN,       AMMagic.ENDER,     MinMaxBounds.Doubles.atLeast(1),       EndermanPumpkinAbilityEffect.INSTANCE);
        register(bootstrap, AMAbilities.LIGHT_HEALTH_REDUCTION, AMMagic.ENDER,     MinMaxBounds.Doubles.between(0.5, 0.99), true, new LightHealthModifierAbilityEffect(0, -0.2, 10));
        register(bootstrap, AMAbilities.WATER_HEALTH_REDUCTION, AMMagic.ENDER,     MinMaxBounds.Doubles.between(0.5, 0.99), true, new WaterHealthModifierAbilityEffect(0, -0.2));
        // @formatter:on
    }

    private static void register(BootstrapContext<Ability> bootstrap, ResourceKey<Ability> ability, ResourceKey<Affinity> affinity, MinMaxBounds.Doubles bounds, AbilityEffect effect) {
        register(bootstrap, ability, affinity, bounds, false, effect);
    }

    private static void register(BootstrapContext<Ability> bootstrap, ResourceKey<Ability> ability, ResourceKey<Affinity> affinity, MinMaxBounds.Doubles bounds, boolean negative, AbilityEffect effect) {
        bootstrap.register(ability, new Ability(bootstrap.lookup(AMRegistries.AFFINITY).getOrThrow(affinity), bounds, negative, effect));
        PATCHOULI_ABILITY_DATA.put(ability, new PatchouliAbilityData(affinity, bounds));
    }

    public record PatchouliAbilityData(ResourceKey<Affinity> affinity, MinMaxBounds.Doubles bounds) {
    }
}
