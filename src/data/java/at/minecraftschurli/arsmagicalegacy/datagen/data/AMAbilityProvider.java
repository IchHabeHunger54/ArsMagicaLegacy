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

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class AMAbilityProvider {
    public static void addAbilities(BootstrapContext<Ability> bootstrap) {
        HolderGetter<Affinity> affinities = bootstrap.lookup(AMRegistries.AFFINITY);
        HolderGetter<DamageType> damageTypes = bootstrap.lookup(Registries.DAMAGE_TYPE);
        HolderGetter<EntityType<?>> entityTypes = bootstrap.lookup(Registries.ENTITY_TYPE);
        // @formatter:off
        bootstrap.register(AMAbilities.SWIM_SPEED,             new Ability(affinities.getOrThrow(AMMagic.WATER),     MinMaxBounds.Doubles.between(0.01, 1), new AttributeAbilityEffect(Map.of(NeoForgeMod.SWIM_SPEED, new LinearAttributeModifier(AMAbilities.SWIM_SPEED.location(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)))));
        bootstrap.register(AMAbilities.ENDER_THORNS,           new Ability(affinities.getOrThrow(AMMagic.WATER),     MinMaxBounds.Doubles.atLeast(1),       new ThornsAbilityEffect(1, 1, Optional.of(entityTypes.getOrThrow(AMTags.EntityTypes.AFFECTED_BY_ENDER_THORNS_ABILITY)))));
        bootstrap.register(AMAbilities.NETHER_DAMAGE_WATER,    new Ability(affinities.getOrThrow(AMMagic.WATER),     MinMaxBounds.Doubles.between(0.5, 1),  true, new NetherDamageAbilityEffect(0, 0.25)));
        bootstrap.register(AMAbilities.FIRE_RESISTANCE,        new Ability(affinities.getOrThrow(AMMagic.FIRE),      MinMaxBounds.Doubles.between(0.01, 1), new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_FIRE_RESISTANCE_ABILITY), 1, 0.5)));
        bootstrap.register(AMAbilities.FIRE_PUNCH,             new Ability(affinities.getOrThrow(AMMagic.FIRE),      MinMaxBounds.Doubles.atLeast(1),       new FirePunchAbilityEffect(100, 100)));
        bootstrap.register(AMAbilities.WATER_DAMAGE_FIRE,      new Ability(affinities.getOrThrow(AMMagic.FIRE),      MinMaxBounds.Doubles.between(0.5, 1),  true, new WaterDamageAbilityEffect(0, 0.25)));
        bootstrap.register(AMAbilities.RESISTANCE,             new Ability(affinities.getOrThrow(AMMagic.EARTH),     MinMaxBounds.Doubles.between(0.01, 1), new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_RESISTANCE_ABILITY), 1, 0.5)));
        bootstrap.register(AMAbilities.HASTE,                  new Ability(affinities.getOrThrow(AMMagic.EARTH),     MinMaxBounds.Doubles.between(0.01, 1), new AttributeAbilityEffect(Map.of(Attributes.ATTACK_SPEED, new LinearAttributeModifier(AMAbilities.HASTE.location(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), Attributes.BLOCK_BREAK_SPEED, new LinearAttributeModifier(AMAbilities.HASTE.location(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)))));
        bootstrap.register(AMAbilities.FALL_DAMAGE,            new Ability(affinities.getOrThrow(AMMagic.EARTH),     MinMaxBounds.Doubles.between(0.5, 1),  true, new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_FALL_DAMAGE_ABILITY), 1, 1.5)));
        bootstrap.register(AMAbilities.JUMP_BOOST,             new Ability(affinities.getOrThrow(AMMagic.AIR),       MinMaxBounds.Doubles.between(0.01, 1), new JumpBoostAbilityEffect(0, 0.5)));
        bootstrap.register(AMAbilities.FEATHER_FALLING,        new Ability(affinities.getOrThrow(AMMagic.AIR),       MinMaxBounds.Doubles.between(0.01, 1), new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_FEATHER_FALLING_ABILITY), 1, 0.5)));
        bootstrap.register(AMAbilities.GRAVITY,                new Ability(affinities.getOrThrow(AMMagic.AIR),       MinMaxBounds.Doubles.between(0.5, 1),  true, new AttributeAbilityEffect(Map.of(Attributes.GRAVITY, new LinearAttributeModifier(AMAbilities.GRAVITY.location(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)))));
        bootstrap.register(AMAbilities.FROST_PUNCH,            new Ability(affinities.getOrThrow(AMMagic.ICE),       MinMaxBounds.Doubles.between(0.01, 1), new FrostPunchAbilityEffect(0, 100)));
        bootstrap.register(AMAbilities.FROST_WALKER,           new Ability(affinities.getOrThrow(AMMagic.ICE),       MinMaxBounds.Doubles.atLeast(1),       new FrostWalkerAbilityEffect(1, 1, new ReplaceDisk(
            new LevelBasedValue.Clamped(LevelBasedValue.perLevel(3, 1), 0, 16),
            LevelBasedValue.constant(1),
            new Vec3i(0, -1, 0),
            Optional.of(BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, 1, 0), BlockTags.AIR), BlockPredicate.matchesBlocks(Blocks.WATER), BlockPredicate.matchesFluids(Fluids.WATER), BlockPredicate.unobstructed())),
            BlockStateProvider.simple(Blocks.FROSTED_ICE),
            Optional.of(GameEvent.BLOCK_PLACE)))));
        bootstrap.register(AMAbilities.SLOWNESS,               new Ability(affinities.getOrThrow(AMMagic.ICE),       MinMaxBounds.Doubles.between(0.5, 1),  true, new AttributeAbilityEffect(Map.of(Attributes.MOVEMENT_SPEED, new LinearAttributeModifier(AMAbilities.SLOWNESS.location(), 0, -0.05, AttributeModifier.Operation.ADD_VALUE)))));
        bootstrap.register(AMAbilities.SPEED,                  new Ability(affinities.getOrThrow(AMMagic.LIGHTNING), MinMaxBounds.Doubles.between(0.01, 1), new AttributeAbilityEffect(Map.of(Attributes.MOVEMENT_SPEED, new LinearAttributeModifier(AMAbilities.SPEED.location(), 0, 0.05, AttributeModifier.Operation.ADD_VALUE)))));
        bootstrap.register(AMAbilities.STEP_ASSIST,            new Ability(affinities.getOrThrow(AMMagic.LIGHTNING), MinMaxBounds.Doubles.atLeast(1),       new AttributeAbilityEffect(Map.of(Attributes.STEP_HEIGHT, new LinearAttributeModifier(AMAbilities.STEP_ASSIST.location(), 0, 0.4, AttributeModifier.Operation.ADD_VALUE)))));
        bootstrap.register(AMAbilities.WATER_DAMAGE_LIGHTNING, new Ability(affinities.getOrThrow(AMMagic.LIGHTNING), MinMaxBounds.Doubles.between(0.5, 1),  true, new WaterDamageAbilityEffect(0, 0.25)));
        bootstrap.register(AMAbilities.THORNS,                 new Ability(affinities.getOrThrow(AMMagic.NATURE),    MinMaxBounds.Doubles.between(0.01, 1), new ThornsAbilityEffect(0, 0.5, Optional.empty())));
        bootstrap.register(AMAbilities.SATURATION,             new Ability(affinities.getOrThrow(AMMagic.NATURE),    MinMaxBounds.Doubles.atLeast(1),       new EffectAbilityEffect(MobEffects.SATURATION, 10, 0, false)));
        bootstrap.register(AMAbilities.NETHER_DAMAGE_NATURE,   new Ability(affinities.getOrThrow(AMMagic.NATURE),    MinMaxBounds.Doubles.between(0.5, 1),  true, new NetherDamageAbilityEffect(0, 0.25)));
        bootstrap.register(AMAbilities.SMITE,                  new Ability(affinities.getOrThrow(AMMagic.LIFE),      MinMaxBounds.Doubles.between(0.01, 1), new ExtraDamageAbilityEffect(0, 4, Optional.of(entityTypes.getOrThrow(AMTags.EntityTypes.AFFECTED_BY_SMITE_ABILITY)))));
        bootstrap.register(AMAbilities.REGENERATION,           new Ability(affinities.getOrThrow(AMMagic.LIFE),      MinMaxBounds.Doubles.atLeast(1),       new EffectAbilityEffect(MobEffects.REGENERATION, 10, 0, false)));
        bootstrap.register(AMAbilities.NAUSEA,                 new Ability(affinities.getOrThrow(AMMagic.LIFE),      MinMaxBounds.Doubles.between(0.5, 1),  true, new KillEffectAbilityEffect(MobEffects.CONFUSION, 0, 600, 0, false, Optional.of(entityTypes.getOrThrow(AMTags.EntityTypes.AFFECTED_BY_NAUSEA_ABILITY)))));
        bootstrap.register(AMAbilities.MANA_REDUCTION,         new Ability(affinities.getOrThrow(AMMagic.ARCANE),    MinMaxBounds.Doubles.between(0.01, 1), new ManaCostModifierAbilityEffect(1, 0.5)));
        bootstrap.register(AMAbilities.CLARITY,                new Ability(affinities.getOrThrow(AMMagic.ARCANE),    MinMaxBounds.Doubles.atLeast(1),       new SpellCastEffectAbilityEffect(AMMobEffects.CLARITY, 1200, 0, true, 0.5)));
        bootstrap.register(AMAbilities.MAGIC_DAMAGE,           new Ability(affinities.getOrThrow(AMMagic.ARCANE),    MinMaxBounds.Doubles.between(0.5, 1),  true, new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_MAGIC_DAMAGE_ABILITY), 1, 1.5)));
        bootstrap.register(AMAbilities.POISON_RESISTANCE,      new Ability(affinities.getOrThrow(AMMagic.ENDER),     MinMaxBounds.Doubles.between(0.5, 1),  new EffectResistanceAbilityEffect(List.of(MobEffects.POISON))));
        bootstrap.register(AMAbilities.NIGHT_VISION,           new Ability(affinities.getOrThrow(AMMagic.ENDER),     MinMaxBounds.Doubles.between(0.5, 1),  new EffectAbilityEffect(MobEffects.NIGHT_VISION, 210, 0, false)));
        bootstrap.register(AMAbilities.ENDERMAN_PUMPKIN,       new Ability(affinities.getOrThrow(AMMagic.ENDER),     MinMaxBounds.Doubles.atLeast(1),       EndermanPumpkinAbilityEffect.INSTANCE));
        bootstrap.register(AMAbilities.LIGHT_HEALTH_REDUCTION, new Ability(affinities.getOrThrow(AMMagic.ENDER),     MinMaxBounds.Doubles.between(0.5, 0.99), true, new LightHealthModifierAbilityEffect(0, -0.2, 10)));
        bootstrap.register(AMAbilities.WATER_HEALTH_REDUCTION, new Ability(affinities.getOrThrow(AMMagic.ENDER),     MinMaxBounds.Doubles.between(0.5, 0.99), true, new WaterHealthModifierAbilityEffect(0, -0.2)));
        // @formatter:on
    }
}
