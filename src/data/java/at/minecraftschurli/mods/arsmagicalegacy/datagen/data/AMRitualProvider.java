package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.data.RitualBuilder;
import at.minecraftschurli.mods.arsmagicalegacy.api.data.RitualProvider;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.block.CelestialPrismBlock;
import at.minecraftschurli.mods.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.effect.LearnSkillRitualEffect;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.effect.SetBlockRitualEffect;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.effect.SpawnEntityRitualEffect;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.BiomeTagRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.DimensionRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.EnvironmentAttributeRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.HeightRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.IngredientRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.MoonPhaseRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.StructureRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.trigger.DroppedItemRitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.trigger.GameEventRitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.trigger.KillEntityRitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.trigger.SetBlockStateRitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.trigger.SpellCastRitualTrigger;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class AMRitualProvider extends RitualProvider {
    public AMRitualProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        HolderLookup.RegistryLookup<EntityType<?>> entityTypes = provider.lookupOrThrow(Registries.ENTITY_TYPE);
        HolderLookup.RegistryLookup<Item> items = provider.lookupOrThrow(Registries.ITEM);
        builder("purification", new SpellCastRitualTrigger(List.of(AMSpells.SELF.get(), AMSpells.LIGHT.get())))
            .addRequirement(new IngredientRitualRequirement(Ingredient.of(AMItems.MOONSTONE), 4))
            .addRequirement(new StructureRitualRequirement(AMMultiblocks.PURIFICATION, BlockPos.ZERO.below(3)))
            .addEffect(new SetBlockRitualEffect(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState().setValue(CelestialPrismBlock.PART, CelestialPrismBlock.Part.LOWER), BlockPos.ZERO.below(3)))
            .addEffect(new SetBlockRitualEffect(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState().setValue(CelestialPrismBlock.PART, CelestialPrismBlock.Part.UPPER), BlockPos.ZERO.below(2)))
            .addEffect(new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO.below(1)));
        builder("corruption", new SpellCastRitualTrigger(List.of(AMSpells.FIRE_DAMAGE.get())))
            .addRequirement(new IngredientRitualRequirement(Ingredient.of(AMItems.SUNSTONE), 4))
            .addRequirement(new StructureRitualRequirement(AMMultiblocks.CORRUPTION, BlockPos.ZERO.below(3)))
            .addEffect(new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO.below(3)))
            .addEffect(new SetBlockRitualEffect(AMBlocks.BLACK_AUREM.get().defaultBlockState(), BlockPos.ZERO.below(2)))
            .addEffect(new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO.below(1)));
        HolderLookup.RegistryLookup<Skill> skills = provider.lookupOrThrow(AMRegistries.Keys.SKILL);
        unlock(skills, AMSpells.BLIZZARD, AMSpells.FROST_DAMAGE, AMSpells.FROST, AMSpells.STORM);
        unlock(skills, AMSpells.DAYLIGHT, AMSpells.DIVINE_INTERVENTION, AMSpells.TRUE_SIGHT, AMSpells.SOLAR);
        unlock(skills, AMSpells.DISMEMBERING, AMSpells.PHYSICAL_DAMAGE, AMSpells.DAMAGE, AMSpells.HEALING, AMSpells.PIERCING);
        unlock(skills, AMSpells.EFFECT_POWER, AMSpells.FLIGHT, AMSpells.FURY, AMSpells.REFLECT, AMSpells.SHRINK, AMSpells.SWIFT_SWIM, AMSpells.TEMPORAL_ANCHOR);
        unlock(skills, AMSpells.FALLING_STAR, AMSpells.ASTRAL_DISTORTION, AMSpells.MAGIC_DAMAGE, AMSpells.GRAVITY);
        unlock(skills, AMSpells.FIRE_RAIN, AMSpells.FIRE_DAMAGE, AMSpells.IGNITION, AMSpells.STORM);
        unlock(skills, AMSpells.HEALTH_BOOST, AMSpells.LIFE_TAP, AMSpells.RESISTANCE);
        unlock(skills, AMSpells.MANA_BLAST, AMSpells.EXPLOSION, AMSpells.MANA_DRAIN);
        unlock(skills, AMSpells.MOONRISE, AMSpells.ENDER_INTERVENTION, AMSpells.NIGHT_VISION, AMSpells.LUNAR);
        unlock(skills, AMSpells.PROSPERITY, AMSpells.DIG, AMSpells.PHYSICAL_DAMAGE, AMSpells.MINING_POWER, AMSpells.SILK_TOUCH);
        builder("unlock_shield_overload", new SpellCastRitualTrigger(List.of(AMSpells.RESISTANCE.get(), AMSpells.MANA_DRAIN.get())))
            .addEffect(new LearnSkillRitualEffect(skills.getOrThrow(AMMagic.SHIELD_OVERLOAD)));
        HolderLookup.RegistryLookup<Affinity> affinities = provider.lookupOrThrow(AMRegistries.Keys.AFFINITY);
        // TODO guardians
        spawn("water_guardian", AMEntities.MANA_CREEPER, AMMultiblocks.WATER_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(Ingredient.of(items.getOrThrow(ItemTags.BOATS)), Ingredient.of(Items.WATER_BUCKET)))
            .addRequirement(new DimensionRitualRequirement(Level.OVERWORLD))
            .addRequirement(new BiomeTagRitualRequirement(AMTags.Biomes.CAN_SUMMON_WATER_GUARDIAN));
        spawn("fire_guardian", AMEntities.MANA_CREEPER, AMMultiblocks.FIRE_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE)))
            .addRequirement(new EnvironmentAttributeRitualRequirement<>(EnvironmentAttributes.WATER_EVAPORATES, true));
        spawn("earth_guardian", AMEntities.MANA_CREEPER, AMMultiblocks.EARTH_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(Ingredient.of(items.getOrThrow(Tags.Items.GEMS_EMERALD)), Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ))))
            .addRequirement(new DimensionRitualRequirement(Level.OVERWORLD));
        spawn("air_guardian", AMEntities.MANA_CREEPER, AMMultiblocks.AIR_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(Ingredient.of(AMItems.TARMA_ROOT)))
            .addRequirement(new DimensionRitualRequirement(Level.OVERWORLD))
            .addRequirement(new HeightRitualRequirement(MinMaxBounds.Doubles.atLeast(128)));
        spawn("ice_guardian", AMEntities.MANA_CREEPER, AMMultiblocks.ICE_GUARDIAN_SPAWN_RITUAL,
            new SetBlockStateRitualTrigger(new BlockMatchTest(Blocks.CARVED_PUMPKIN), new BlockPos(0, 2, 0)))
            .addRequirement(new DimensionRitualRequirement(Level.OVERWORLD))
            .addRequirement(new BiomeTagRitualRequirement(Tags.Biomes.IS_COLD))
            .addEffect(new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO))
            .addEffect(new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), new BlockPos(0, 1, 0)))
            .addEffect(new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), new BlockPos(0, 2, 0)));
        spawn("lightning_guardian", AMEntities.MANA_CREEPER, AMMultiblocks.LIGHTNING_GUARDIAN_SPAWN_RITUAL, new BlockPos(0, -3, 0),
            new GameEventRitualTrigger(GameEvent.LIGHTNING_STRIKE));
        spawn("life_guardian", AMEntities.MANA_CREEPER, AMMultiblocks.LIFE_GUARDIAN_SPAWN_RITUAL,
            new KillEntityRitualTrigger(EntityPredicate.Builder.entity().of(entityTypes, EntityType.VILLAGER).flags(EntityFlagsPredicate.Builder.flags().setIsBaby(true)).build()))
            .addRequirement(new DimensionRitualRequirement(Level.OVERWORLD))
            .addRequirement(new MoonPhaseRitualRequirement(MoonPhase.NEW_MOON));
        spawn("arcane_guardian", AMEntities.MANA_CREEPER, AMMultiblocks.ARCANE_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(DataComponentIngredient.of(true, ArsMagicaApi.book())));
        spawn("ender_guardian", AMEntities.MANA_CREEPER, AMMultiblocks.ENDER_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(Ingredient.of(Items.ENDER_EYE)))
            .addRequirement(new DimensionRitualRequirement(Level.END));
    }

    private RitualBuilder spawn(String name, DeferredHolder<EntityType<?>, ? extends EntityType<?>> boss, Identifier structure, RitualTrigger<?> trigger) {
        return spawn(name, boss, structure, BlockPos.ZERO, trigger);
    }

    private RitualBuilder spawn(String name, DeferredHolder<EntityType<?>, ? extends EntityType<?>> boss, Identifier structure, BlockPos offset, RitualTrigger<?> trigger) {
        return builder("spawn_" + name/*TODO boss.getId().getPath()*/, trigger)
            .addRequirement(new StructureRitualRequirement(structure, offset))
            .addEffect(new SpawnEntityRitualEffect(boss.get()));
    }

    @SafeVarargs
    private void unlock(HolderLookup.RegistryLookup<Skill> skills, DeferredHolder<SpellPart, ?> part, DeferredHolder<SpellPart, ?>... parts) {
        Identifier id = part.getId();
        builder("unlock_" + id.getPath(), new SpellCastRitualTrigger(Arrays.stream(parts)
            .map(DeferredHolder::get)
            .map(e -> (SpellPart) e)
            .toList()))
            .addEffect(new LearnSkillRitualEffect(skills.getOrThrow(ResourceKey.create(AMRegistries.Keys.SKILL, id))));
    }
}
