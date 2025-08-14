package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public interface AMWorldgen {
    ResourceKey<ConfiguredFeature<?, ?>> WITCHWOOD_TREE_CONFIGURED_FEATURE = ResourceKey.create(Registries.CONFIGURED_FEATURE, ArsMagicaApi.modLoc("witchwood_tree"));
    TreeGrower WITCHWOOD_TREE_GROWER = new TreeGrower(ArsMagicaApi.MOD_ID + ":witchwood", Optional.of(WITCHWOOD_TREE_CONFIGURED_FEATURE), Optional.empty(), Optional.empty());
}
