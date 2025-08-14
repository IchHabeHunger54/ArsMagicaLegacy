package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMWorldgen;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class AMDatapackRegistryProvider extends DatapackBuiltinEntriesProvider {
    public AMDatapackRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, bootstrap -> {
                    bootstrap.register(AMWorldgen.WITCHWOOD_TREE_CONFIGURED_FEATURE, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                            BlockStateProvider.simple(AMBlocks.WITCHWOOD_LOG.get()),
                            new DarkOakTrunkPlacer(9, 3, 1),
                            BlockStateProvider.simple(AMBlocks.WITCHWOOD_LEAVES.get()),
                            new DarkOakFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1)),
                            new ThreeLayersFeatureSize(1, 2, 1, 1, 2, OptionalInt.empty())
                    ).ignoreVines().build()));
                }),
                Set.of(ArsMagicaApi.MOD_ID));
    }
}
