package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.ability.AttributeAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.init.AMAbilities;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.util.LinearAttributeModifier;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.List;
import java.util.Map;

public final class AMAbilityProvider {
    public static void addAbilities(BootstrapContext<Ability> bootstrap) {
        HolderGetter<Affinity> affinities = bootstrap.lookup(AMRegistryKeys.AFFINITY);
        // @formatter:off
        bootstrap.register(AMAbilities.SWIM_SPEED,             new Ability(affinities.getOrThrow(AMMagic.WATER),     MinMaxBounds.Doubles.between(0.01, 1), new AttributeAbilityEffect(Map.of(NeoForgeMod.SWIM_SPEED, new LinearAttributeModifier(AMAbilities.SWIM_SPEED.location(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)))));
        bootstrap.register(AMAbilities.ENDERMAN_THORNS,        new Ability(affinities.getOrThrow(AMMagic.WATER),     MinMaxBounds.Doubles.atLeast(1),       List.of()));
        bootstrap.register(AMAbilities.NETHER_DAMAGE_WATER,    new Ability(affinities.getOrThrow(AMMagic.WATER),     MinMaxBounds.Doubles.between(0.5, 1),  true, List.of()));
        bootstrap.register(AMAbilities.FIRE_RESISTANCE,        new Ability(affinities.getOrThrow(AMMagic.FIRE),      MinMaxBounds.Doubles.between(0.01, 1), List.of()));
        bootstrap.register(AMAbilities.FIRE_PUNCH,             new Ability(affinities.getOrThrow(AMMagic.FIRE),      MinMaxBounds.Doubles.atLeast(1),       List.of()));
        bootstrap.register(AMAbilities.WATER_DAMAGE_FIRE,      new Ability(affinities.getOrThrow(AMMagic.FIRE),      MinMaxBounds.Doubles.between(0.5, 1),  true, List.of()));
        bootstrap.register(AMAbilities.RESISTANCE,             new Ability(affinities.getOrThrow(AMMagic.EARTH),     MinMaxBounds.Doubles.between(0.01, 1), List.of()));
        bootstrap.register(AMAbilities.HASTE,                  new Ability(affinities.getOrThrow(AMMagic.EARTH),     MinMaxBounds.Doubles.between(0.01, 1), new AttributeAbilityEffect(Map.of(Attributes.ATTACK_SPEED, new LinearAttributeModifier(AMAbilities.HASTE.location(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)))));
        bootstrap.register(AMAbilities.FALL_DAMAGE,            new Ability(affinities.getOrThrow(AMMagic.EARTH),     MinMaxBounds.Doubles.between(0.5, 1),  true, List.of()));
        bootstrap.register(AMAbilities.JUMP_BOOST,             new Ability(affinities.getOrThrow(AMMagic.AIR),       MinMaxBounds.Doubles.between(0.01, 1), List.of()));
        bootstrap.register(AMAbilities.FEATHER_FALLING,        new Ability(affinities.getOrThrow(AMMagic.AIR),       MinMaxBounds.Doubles.between(0.01, 1), List.of()));
        bootstrap.register(AMAbilities.GRAVITY,                new Ability(affinities.getOrThrow(AMMagic.AIR),       MinMaxBounds.Doubles.between(0.5, 1),  true, new AttributeAbilityEffect(Map.of(Attributes.GRAVITY, new LinearAttributeModifier(AMAbilities.GRAVITY.location(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)))));
        bootstrap.register(AMAbilities.FROST_PUNCH,            new Ability(affinities.getOrThrow(AMMagic.ICE),       MinMaxBounds.Doubles.between(0.01, 1), List.of()));
        bootstrap.register(AMAbilities.FROST_WALKER,           new Ability(affinities.getOrThrow(AMMagic.ICE),       MinMaxBounds.Doubles.atLeast(1),       List.of()));
        bootstrap.register(AMAbilities.SLOWNESS,               new Ability(affinities.getOrThrow(AMMagic.ICE),       MinMaxBounds.Doubles.between(0.5, 1),  true, new AttributeAbilityEffect(Map.of(Attributes.MOVEMENT_SPEED, new LinearAttributeModifier(AMAbilities.SLOWNESS.location(), 0, -0.05, AttributeModifier.Operation.ADD_VALUE)))));
        bootstrap.register(AMAbilities.SPEED,                  new Ability(affinities.getOrThrow(AMMagic.LIGHTNING), MinMaxBounds.Doubles.between(0.01, 1), new AttributeAbilityEffect(Map.of(Attributes.MOVEMENT_SPEED, new LinearAttributeModifier(AMAbilities.SPEED.location(), 0, 0.05, AttributeModifier.Operation.ADD_VALUE)))));
        bootstrap.register(AMAbilities.STEP_ASSIST,            new Ability(affinities.getOrThrow(AMMagic.LIGHTNING), MinMaxBounds.Doubles.atLeast(1),       new AttributeAbilityEffect(Map.of(Attributes.STEP_HEIGHT, new LinearAttributeModifier(AMAbilities.STEP_ASSIST.location(), 0, 0.4, AttributeModifier.Operation.ADD_VALUE)))));
        bootstrap.register(AMAbilities.WATER_DAMAGE_LIGHTNING, new Ability(affinities.getOrThrow(AMMagic.LIGHTNING), MinMaxBounds.Doubles.between(0.5, 1),  true, List.of()));
        bootstrap.register(AMAbilities.THORNS,                 new Ability(affinities.getOrThrow(AMMagic.NATURE),    MinMaxBounds.Doubles.between(0.01, 1), List.of()));
        bootstrap.register(AMAbilities.SATURATION,             new Ability(affinities.getOrThrow(AMMagic.NATURE),    MinMaxBounds.Doubles.atLeast(1),       List.of()));
        bootstrap.register(AMAbilities.NETHER_DAMAGE_NATURE,   new Ability(affinities.getOrThrow(AMMagic.NATURE),    MinMaxBounds.Doubles.between(0.5, 1),  true, List.of()));
        bootstrap.register(AMAbilities.SMITE,                  new Ability(affinities.getOrThrow(AMMagic.LIFE),      MinMaxBounds.Doubles.between(0.01, 1), List.of()));
        bootstrap.register(AMAbilities.REGENERATION,           new Ability(affinities.getOrThrow(AMMagic.LIFE),      MinMaxBounds.Doubles.atLeast(1),       List.of()));
        bootstrap.register(AMAbilities.NAUSEA,                 new Ability(affinities.getOrThrow(AMMagic.LIFE),      MinMaxBounds.Doubles.between(0.5, 1),  true, List.of()));
        bootstrap.register(AMAbilities.MANA_REDUCTION,         new Ability(affinities.getOrThrow(AMMagic.ARCANE),    MinMaxBounds.Doubles.between(0.01, 1), List.of()));
        bootstrap.register(AMAbilities.CLARITY,                new Ability(affinities.getOrThrow(AMMagic.ARCANE),    MinMaxBounds.Doubles.atLeast(1),       List.of()));
        bootstrap.register(AMAbilities.MAGIC_DAMAGE,           new Ability(affinities.getOrThrow(AMMagic.ARCANE),    MinMaxBounds.Doubles.between(0.5, 1),  true, List.of()));
        bootstrap.register(AMAbilities.POISON_RESISTANCE,      new Ability(affinities.getOrThrow(AMMagic.ENDER),     MinMaxBounds.Doubles.between(0.5, 1),  List.of()));
        bootstrap.register(AMAbilities.NIGHT_VISION,           new Ability(affinities.getOrThrow(AMMagic.ENDER),     MinMaxBounds.Doubles.between(0.5, 1),  List.of()));
        bootstrap.register(AMAbilities.ENDERMAN_PUMPKIN,       new Ability(affinities.getOrThrow(AMMagic.ENDER),     MinMaxBounds.Doubles.atLeast(1),       List.of()));
        bootstrap.register(AMAbilities.LIGHT_HEALTH_REDUCTION, new Ability(affinities.getOrThrow(AMMagic.ENDER),     MinMaxBounds.Doubles.between(0.5, 0.99), true, List.of()));
        bootstrap.register(AMAbilities.WATER_HEALTH_REDUCTION, new Ability(affinities.getOrThrow(AMMagic.ENDER),     MinMaxBounds.Doubles.between(0.5, 0.99), true, List.of()));
        // @formatter:on
    }
}
