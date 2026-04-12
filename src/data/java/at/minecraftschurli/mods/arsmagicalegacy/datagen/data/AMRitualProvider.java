package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.Ritual;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualEffect;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualRequirement;
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
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AMRitualProvider {
    public static void addRituals(BootstrapContext<Ritual<?>> bootstrap) {
        HolderGetter<EntityType<?>> entityTypes = bootstrap.lookup(Registries.ENTITY_TYPE);
        HolderGetter<Item> items = bootstrap.lookup(Registries.ITEM);
        HolderGetter<Affinity> affinities = bootstrap.lookup(AMRegistries.Keys.AFFINITY);
        HolderGetter<Skill> skills = bootstrap.lookup(AMRegistries.Keys.SKILL);
        add(bootstrap, "purification",
            new SpellCastRitualTrigger(List.of(AMSpells.SELF.get(), AMSpells.LIGHT.get())),
            List.of(
                new IngredientRitualRequirement(Ingredient.of(AMItems.MOONSTONE), 4),
                new StructureRitualRequirement(AMMultiblocks.PURIFICATION, BlockPos.ZERO.below(3))),
            List.of(
                new SetBlockRitualEffect(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState().setValue(CelestialPrismBlock.PART, CelestialPrismBlock.Part.LOWER), BlockPos.ZERO.below(3)),
                new SetBlockRitualEffect(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState().setValue(CelestialPrismBlock.PART, CelestialPrismBlock.Part.UPPER), BlockPos.ZERO.below(2)),
                new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO.below(1))));
        add(bootstrap, "corruption",
            new SpellCastRitualTrigger(List.of(AMSpells.FIRE_DAMAGE.get())),
            List.of(
                new IngredientRitualRequirement(Ingredient.of(AMItems.SUNSTONE), 4),
                new StructureRitualRequirement(AMMultiblocks.CORRUPTION, BlockPos.ZERO.below(3))),
            List.of(
                new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO.below(3)),
                new SetBlockRitualEffect(AMBlocks.BLACK_AUREM.get().defaultBlockState(), BlockPos.ZERO.below(2)),
                new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO.below(1))));
        unlock(bootstrap, AMSpells.BLIZZARD, AMSpells.FROST_DAMAGE, AMSpells.FROST, AMSpells.STORM);
        unlock(bootstrap, AMSpells.DAYLIGHT, AMSpells.DIVINE_INTERVENTION, AMSpells.TRUE_SIGHT, AMSpells.SOLAR);
        unlock(bootstrap, AMSpells.DISMEMBERING, AMSpells.PHYSICAL_DAMAGE, AMSpells.DAMAGE, AMSpells.HEALING, AMSpells.PIERCING);
        unlock(bootstrap, AMSpells.EFFECT_POWER, AMSpells.FLIGHT, AMSpells.FURY, AMSpells.REFLECT, AMSpells.SHRINK, AMSpells.SWIFT_SWIM, AMSpells.TEMPORAL_ANCHOR);
        unlock(bootstrap, AMSpells.FALLING_STAR, AMSpells.ASTRAL_DISTORTION, AMSpells.MAGIC_DAMAGE, AMSpells.GRAVITY);
        unlock(bootstrap, AMSpells.FIRE_RAIN, AMSpells.FIRE_DAMAGE, AMSpells.IGNITION, AMSpells.STORM);
        unlock(bootstrap, AMSpells.HEALTH_BOOST, AMSpells.LIFE_TAP, AMSpells.RESISTANCE);
        unlock(bootstrap, AMSpells.MANA_BLAST, AMSpells.EXPLOSION, AMSpells.MANA_DRAIN);
        unlock(bootstrap, AMSpells.MOONRISE, AMSpells.ENDER_INTERVENTION, AMSpells.NIGHT_VISION, AMSpells.LUNAR);
        unlock(bootstrap, AMSpells.PROSPERITY, AMSpells.DIG, AMSpells.PHYSICAL_DAMAGE, AMSpells.MINING_POWER, AMSpells.SILK_TOUCH);
        add(bootstrap, "unlock_shield_overload",
            new SpellCastRitualTrigger(List.of(AMSpells.RESISTANCE.get(), AMSpells.MANA_DRAIN.get())),
            List.of(),
            List.of(new LearnSkillRitualEffect(skills.getOrThrow(AMMagic.SHIELD_OVERLOAD))));
        spawn(bootstrap, AMEntities.WATER_GUARDIAN, AMMultiblocks.WATER_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(Ingredient.of(items.getOrThrow(ItemTags.BOATS)), Ingredient.of(Items.WATER_BUCKET)),
            List.of(
                new DimensionRitualRequirement(Level.OVERWORLD),
                new BiomeTagRitualRequirement(AMTags.Biomes.CAN_SUMMON_WATER_GUARDIAN)));
        spawn(bootstrap, AMEntities.FIRE_GUARDIAN, AMMultiblocks.FIRE_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE)),
            List.of(new EnvironmentAttributeRitualRequirement<>(EnvironmentAttributes.WATER_EVAPORATES, true)));
        spawn(bootstrap, AMEntities.EARTH_GUARDIAN, AMMultiblocks.EARTH_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(Ingredient.of(items.getOrThrow(Tags.Items.GEMS_EMERALD)), Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ))),
            List.of(new DimensionRitualRequirement(Level.OVERWORLD)));
        spawn(bootstrap, AMEntities.AIR_GUARDIAN, AMMultiblocks.AIR_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(Ingredient.of(AMItems.TARMA_ROOT)),
            List.of(
                new DimensionRitualRequirement(Level.OVERWORLD),
                new HeightRitualRequirement(MinMaxBounds.Doubles.atLeast(128))));
        spawn(bootstrap, AMEntities.ICE_GUARDIAN, AMMultiblocks.ICE_GUARDIAN_SPAWN_RITUAL,
            new SetBlockStateRitualTrigger(new BlockMatchTest(Blocks.CARVED_PUMPKIN), new BlockPos(0, 2, 0)),
            List.of(
                new DimensionRitualRequirement(Level.OVERWORLD),
                new BiomeTagRitualRequirement(Tags.Biomes.IS_COLD)),
            List.of(
                new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO),
                new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), new BlockPos(0, 1, 0)),
                new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), new BlockPos(0, 2, 0))));
        spawn(bootstrap, AMEntities.LIGHTNING_GUARDIAN, AMMultiblocks.LIGHTNING_GUARDIAN_SPAWN_RITUAL, new BlockPos(0, -3, 0),
            new GameEventRitualTrigger(GameEvent.LIGHTNING_STRIKE), List.of(), List.of());
        spawn(bootstrap, AMEntities.LIFE_GUARDIAN, AMMultiblocks.LIFE_GUARDIAN_SPAWN_RITUAL,
            new KillEntityRitualTrigger(EntityPredicate.Builder.entity().of(entityTypes, EntityType.VILLAGER).flags(EntityFlagsPredicate.Builder.flags().setIsBaby(true)).build()),
            List.of(
                new DimensionRitualRequirement(Level.OVERWORLD),
                new EnvironmentAttributeRitualRequirement<>(EnvironmentAttributes.MOON_PHASE, MoonPhase.NEW_MOON)));
        spawn(bootstrap, AMEntities.ARCANE_GUARDIAN, AMMultiblocks.ARCANE_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(DataComponentIngredient.of(true, ArsMagicaApi.book())), List.of());
        spawn(bootstrap, AMEntities.ENDER_GUARDIAN, AMMultiblocks.ENDER_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(Ingredient.of(Items.ENDER_EYE)),
            List.of(new DimensionRitualRequirement(Level.END)));
    }

    private static void spawn(BootstrapContext<Ritual<?>> bootstrap, DeferredHolder<EntityType<?>, ?> boss, Identifier structure, RitualTrigger<?> trigger, List<RitualRequirement> requirements) {
        spawn(bootstrap, boss, structure, trigger, requirements, List.of());
    }

    private static void spawn(BootstrapContext<Ritual<?>> bootstrap, DeferredHolder<EntityType<?>, ?> boss, Identifier structure, RitualTrigger<?> trigger, List<RitualRequirement> requirements, List<RitualEffect> effects) {
        spawn(bootstrap, boss, structure, BlockPos.ZERO, trigger, requirements, effects);
    }

    private static void spawn(BootstrapContext<Ritual<?>> bootstrap, DeferredHolder<EntityType<?>, ?> boss, Identifier structure, BlockPos offset, RitualTrigger<?> trigger, List<RitualRequirement> requirements, List<RitualEffect> effects) {
        List<RitualRequirement> newRequirements = new ArrayList<>(requirements);
        newRequirements.addFirst(new StructureRitualRequirement(structure, offset));
        List<RitualEffect> newEffects = new ArrayList<>(effects);
        newEffects.addFirst(new SpawnEntityRitualEffect(boss.get()));
        add(bootstrap, "spawn_" + boss.getId().getPath(), trigger, newRequirements, newEffects);
    }

    @SafeVarargs
    private static void unlock(BootstrapContext<Ritual<?>> bootstrap, DeferredHolder<SpellPart, ?> part, DeferredHolder<SpellPart, ?>... parts) {
        Identifier id = part.getId();
        add(bootstrap, "unlock_" + id.getPath(), new SpellCastRitualTrigger(Arrays.stream(parts)
            .map(DeferredHolder::get)
            .map(e -> (SpellPart) e)
            .toList()), List.of(), List.of(new LearnSkillRitualEffect(bootstrap.lookup(AMRegistries.Keys.SKILL).get(ResourceKey.create(AMRegistries.Keys.SKILL, id)).orElseThrow())));
    }

    private static void add(BootstrapContext<Ritual<?>> bootstrap, String name, RitualTrigger<?> trigger, List<RitualRequirement> requirements, List<RitualEffect> effects) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.RITUAL, ArsMagicaApi.id(name)), new Ritual<>(requirements, trigger, effects));
    }
}
