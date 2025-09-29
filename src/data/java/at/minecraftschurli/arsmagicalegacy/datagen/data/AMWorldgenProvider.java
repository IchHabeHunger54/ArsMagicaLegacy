package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMWorldgen;
import at.minecraftschurli.arsmagicalegacy.worldgen.HolderSets;
import at.minecraftschurli.arsmagicalegacy.worldgen.MeteoriteFeature;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

public final class AMWorldgenProvider {
    public static void addConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap) {
        bootstrap.register(
            AMWorldgen.CHIMERITE_ORE_CONFIGURED_FEATURE,
            ore(AMBlocks.CHIMERITE_ORE, AMBlocks.DEEPSLATE_CHIMERITE_ORE, 7, 0f)
        );
        bootstrap.register(
            AMWorldgen.TOPAZ_ORE_CONFIGURED_FEATURE,
            ore(AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0.5f)
        );
        bootstrap.register(
            AMWorldgen.TOPAZ_ORE_EXTRA_CONFIGURED_FEATURE,
            ore(AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0f)
        );
        bootstrap.register(
            AMWorldgen.VINTEUM_ORE_CONFIGURED_FEATURE,
            ore(AMBlocks.VINTEUM_ORE, AMBlocks.DEEPSLATE_VINTEUM_ORE, 10, 0f)
        );
        bootstrap.register(
            AMWorldgen.MOONSTONE_METEORITE_CONFIGURED_FEATURE,
            new ConfiguredFeature<>(AMWorldgen.METEORITE.get(), new MeteoriteFeature.Configuration(
                Blocks.STONE.defaultBlockState(),
                AMBlocks.MOONSTONE_ORE.get().defaultBlockState(),
                Blocks.WATER.defaultBlockState(), //TODO liquid essence
                7,
                5,
                0.1f
            ))
        );
        bootstrap.register(
            AMWorldgen.SUNSTONE_ORE_CONFIGURED_FEATURE,
            new ConfiguredFeature<>(AMWorldgen.SUNSTONE_ORE.get(), new OreConfiguration(
                List.of(OreConfiguration.target(new TagMatchTest(BlockTags.BASE_STONE_NETHER), AMBlocks.SUNSTONE_ORE.get().defaultBlockState())),
                4,
                0f
            ))
        );
        bootstrap.register(
            AMWorldgen.WITCHWOOD_TREE_CONFIGURED_FEATURE,
            new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(AMBlocks.WITCHWOOD_LOG.get()),
                new DarkOakTrunkPlacer(9, 3, 1),
                BlockStateProvider.simple(AMBlocks.WITCHWOOD_LEAVES.get()),
                new DarkOakFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1)),
                new ThreeLayersFeatureSize(1, 2, 1, 1, 2, OptionalInt.empty())
            ).ignoreVines().build())
        );
        bootstrap.register(
            AMWorldgen.AUM_CONFIGURED_FEATURE,
            flower(64, AMBlocks.AUM)
        );
        bootstrap.register(
            AMWorldgen.CERUBLOSSOM_CONFIGURED_FEATURE,
            flower(64, AMBlocks.CERUBLOSSOM)
        );
        bootstrap.register(
            AMWorldgen.DESERT_NOVA_CONFIGURED_FEATURE,
            flower(64, AMBlocks.DESERT_NOVA)
        );
        bootstrap.register(
            AMWorldgen.TARMA_ROOT_CONFIGURED_FEATURE,
            flower(64, AMBlocks.TARMA_ROOT)
        );
        bootstrap.register(
            AMWorldgen.WAKEBLOOM_CONFIGURED_FEATURE,
            flower(64, AMBlocks.WAKEBLOOM)
        );
    }

    public static void addPlacedFeatures(BootstrapContext<PlacedFeature> bootstrap) {
        bootstrap.register(
            AMWorldgen.CHIMERITE_ORE_PLACED_FEATURE,
            ore(bootstrap, AMWorldgen.CHIMERITE_ORE_CONFIGURED_FEATURE, 6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(16)))
        );
        bootstrap.register(
            AMWorldgen.TOPAZ_ORE_PLACED_FEATURE,
            ore(bootstrap, AMWorldgen.TOPAZ_ORE_CONFIGURED_FEATURE, 7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80)))
        );
        bootstrap.register(
            AMWorldgen.TOPAZ_ORE_EXTRA_PLACED_FEATURE,
            ore(bootstrap, AMWorldgen.TOPAZ_ORE_EXTRA_CONFIGURED_FEATURE, 100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480)))
        );
        bootstrap.register(
            AMWorldgen.VINTEUM_ORE_PLACED_FEATURE,
            ore(bootstrap, AMWorldgen.VINTEUM_ORE_CONFIGURED_FEATURE, 8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(80)))
        );
        bootstrap.register(
            AMWorldgen.MOONSTONE_METEORITE_PLACED_FEATURE,
            placedFeature(bootstrap, AMWorldgen.MOONSTONE_METEORITE_CONFIGURED_FEATURE, RarityFilter.onAverageOnceEvery(128), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, HeightRangePlacement.uniform(VerticalAnchor.absolute(56), VerticalAnchor.absolute(180)), BiomeFilter.biome())
        );
        bootstrap.register(
            AMWorldgen.SUNSTONE_ORE_PLACED_FEATURE,
            ore(bootstrap, AMWorldgen.SUNSTONE_ORE_CONFIGURED_FEATURE, 32, HeightRangePlacement.uniform(VerticalAnchor.absolute(31), VerticalAnchor.absolute(33)))
        );
        bootstrap.register(
            AMWorldgen.TREES_WITCHWOOD_PLACED_FEATURE,
            placedFeature(bootstrap, AMWorldgen.WITCHWOOD_TREE_CONFIGURED_FEATURE, ImmutableList.<PlacementModifier>builder().addAll(VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1f, 1), AMBlocks.WITCHWOOD_SAPLING.get())).add(RarityFilter.onAverageOnceEvery(8)).build())
        );
        bootstrap.register(
            AMWorldgen.AUM_PLACED_FEATURE,
            flower(bootstrap, AMWorldgen.AUM_CONFIGURED_FEATURE, 32)
        );
        bootstrap.register(
            AMWorldgen.CERUBLOSSOM_PLACED_FEATURE,
            flower(bootstrap, AMWorldgen.CERUBLOSSOM_CONFIGURED_FEATURE, 32)
        );
        bootstrap.register(
            AMWorldgen.DESERT_NOVA_PLACED_FEATURE,
            flower(bootstrap, AMWorldgen.DESERT_NOVA_CONFIGURED_FEATURE, 32)
        );
        bootstrap.register(
            AMWorldgen.TARMA_ROOT_PLACED_FEATURE,
            flower(bootstrap, AMWorldgen.TARMA_ROOT_CONFIGURED_FEATURE, 32)
        );
        bootstrap.register(
            AMWorldgen.WAKEBLOOM_PLACED_FEATURE,
            flower(bootstrap, AMWorldgen.WAKEBLOOM_CONFIGURED_FEATURE, 32)
        );
    }

    public static void addBiomeModifiers(BootstrapContext<BiomeModifier> bootstrap) {
        bootstrap.register(
            AMWorldgen.OVERWORLD_BIOME_MODIFIER,
            addFeatures(
                bootstrap,
                HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                AMWorldgen.CHIMERITE_ORE_PLACED_FEATURE, AMWorldgen.TOPAZ_ORE_PLACED_FEATURE, AMWorldgen.VINTEUM_ORE_PLACED_FEATURE
            )
        );
        bootstrap.register(
            AMWorldgen.NETHER_BIOME_MODIFIER,
            addFeatures(
                bootstrap,
                HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_NETHER),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                AMWorldgen.SUNSTONE_ORE_PLACED_FEATURE
            )
        );
        bootstrap.register(
            AMWorldgen.NON_OCEAN_BIOME_MODIFIER,
            addFeatures(
                bootstrap,
                HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.not(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OCEAN))),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                AMWorldgen.MOONSTONE_METEORITE_PLACED_FEATURE
            )
        );
        bootstrap.register(
            AMWorldgen.FOREST_BIOME_MODIFIER,
            addFeatures(
                bootstrap,
                HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_FOREST)),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                AMWorldgen.AUM_PLACED_FEATURE
            )
        );
        bootstrap.register(
            AMWorldgen.MOUNTAIN_BIOME_MODIFIER,
            addFeatures(
                bootstrap,
                HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_MOUNTAIN)),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                AMWorldgen.TOPAZ_ORE_EXTRA_PLACED_FEATURE
            )
        );
        bootstrap.register(
            AMWorldgen.SANDY_BIOME_MODIFIER,
            addFeatures(
                bootstrap,
                HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_SANDY)),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                AMWorldgen.DESERT_NOVA_PLACED_FEATURE
            )
        );
        bootstrap.register(
            AMWorldgen.SPOOKY_BIOME_MODIFIER,
            addFeatures(
                bootstrap,
                HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_SPOOKY)),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                AMWorldgen.TREES_WITCHWOOD_PLACED_FEATURE
            )
        );
        bootstrap.register(
            AMWorldgen.JUNGLE_OR_SWAMP_BIOME_MODIFIER,
            addFeatures(
                bootstrap,
                HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.or(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_JUNGLE), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_SWAMP))),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                AMWorldgen.CERUBLOSSOM_PLACED_FEATURE, AMWorldgen.WAKEBLOOM_PLACED_FEATURE
            )
        );
        bootstrap.register(
            AMWorldgen.MOUNTAIN_HILL_OR_UNDERGROUND_BIOME_MODIFIER,
            addFeatures(
                bootstrap,
                HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.or(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_MOUNTAIN), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_HILL), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_UNDERGROUND))),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                AMWorldgen.TARMA_ROOT_PLACED_FEATURE
            )
        );
    }

    /**
     * Creates a {@link ConfiguredFeature} for an ore.
     *
     * @param ore                      The ore block to place.
     * @param deepslateOre             The deepslate ore block to place.
     * @param veinSize                 The ore vein size.
     * @param airExposureDiscardChance The chance that a vein will be discarded if it touches air.
     * @return A {@link ConfiguredFeature}.
     */
    private static ConfiguredFeature<OreConfiguration, ?> ore(DeferredBlock<?> ore, DeferredBlock<?> deepslateOre, int veinSize, float airExposureDiscardChance) {
        return new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
            List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), ore.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), deepslateOre.get().defaultBlockState())),
            veinSize,
            airExposureDiscardChance
        ));
    }

    /**
     * Creates a {@link ConfiguredFeature} for a flower.
     *
     * @param tries  The amount of placement tries.
     * @param flower The flower to place.
     * @return A {@link ConfiguredFeature}.
     */
    @SuppressWarnings("SameParameterValue")
    private static ConfiguredFeature<RandomPatchConfiguration, ?> flower(int tries, DeferredBlock<?> flower) {
        return new ConfiguredFeature<>(Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(
            tries,
            PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(flower.get())))
        ));
    }

    /**
     * Creates a {@link PlacedFeature}.
     *
     * @param bootstrap         The {@link BootstrapContext} to use for lookups.
     * @param configuredFeature The {@link ConfiguredFeature} to use as a base.
     * @param modifiers         The {@link PlacementModifier}s to apply to the {@link PlacedFeature}.
     * @return A {@link PlacedFeature}.
     */
    private static PlacedFeature placedFeature(BootstrapContext<?> bootstrap, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... modifiers) {
        return placedFeature(bootstrap, configuredFeature, Arrays.asList(modifiers));
    }

    /**
     * Creates a {@link PlacedFeature}.
     *
     * @param bootstrap         The {@link BootstrapContext} to use for lookups.
     * @param configuredFeature The {@link ConfiguredFeature} to use as a base.
     * @param modifiers         The {@link PlacementModifier}s to apply to the {@link PlacedFeature}.
     * @return A {@link PlacedFeature}.
     */
    private static PlacedFeature placedFeature(BootstrapContext<?> bootstrap, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        return new PlacedFeature(bootstrap.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configuredFeature), modifiers);
    }

    /**
     * Creates a {@link PlacedFeature} for an ore.
     *
     * @param bootstrap            The {@link BootstrapContext} to use for lookups.
     * @param configuredFeature    The {@link ConfiguredFeature} to use as a base.
     * @param veinCount            How common veins should be.
     * @param heightRangePlacement The height range distribution to use.
     * @return A {@link PlacedFeature}.
     */
    private static PlacedFeature ore(BootstrapContext<?> bootstrap, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, int veinCount, HeightRangePlacement heightRangePlacement) {
        return placedFeature(bootstrap, configuredFeature, CountPlacement.of(veinCount), InSquarePlacement.spread(), heightRangePlacement, BiomeFilter.biome());
    }

    /**
     * Creates a {@link PlacedFeature} for an ore.
     *
     * @param bootstrap         The {@link BootstrapContext} to use for lookups.
     * @param configuredFeature The {@link ConfiguredFeature} to use as a base.
     * @param rarity            How rare patches should be.
     * @return A {@link PlacedFeature}.
     */
    @SuppressWarnings("SameParameterValue")
    private static PlacedFeature flower(BootstrapContext<?> bootstrap, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, int rarity) {
        return placedFeature(bootstrap, configuredFeature, RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

    /**
     * Creates a {@link BiomeModifiers.AddFeaturesBiomeModifier}.
     *
     * @param bootstrap The {@link BootstrapContext} to use for lookups.
     * @param biomes    A {@link HolderSet} of biomes where the features will be added.
     * @param step      The generation step to use.
     * @param features  The keys of the features to generate.
     * @return A {@link BiomeModifiers.AddFeaturesBiomeModifier}.
     */
    @SafeVarargs
    private static BiomeModifiers.AddFeaturesBiomeModifier addFeatures(BootstrapContext<?> bootstrap, HolderSet<Biome> biomes, GenerationStep.Decoration step, ResourceKey<PlacedFeature>... features) {
        return new BiomeModifiers.AddFeaturesBiomeModifier(biomes, HolderSets.direct(bootstrap, Registries.PLACED_FEATURE, features), step);
    }
}
