package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class AMLootTableProvider extends LootTableProvider {
    public AMLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(new SubProviderEntry(AMBlockLootSubProvider::new, LootContextParamSets.BLOCK)), registries);
    }

    private static class AMBlockLootSubProvider extends BlockLootSubProvider {
        private AMBlockLootSubProvider(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return AMRegistries.BLOCKS.getEntries().stream().map(Holder::value).toList();
        }

        @Override
        protected void generate() {
            add(AMBlocks.CHIMERITE_ORE.get(), b -> createOreDrop(b, AMItems.CHIMERITE.get()));
            add(AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), b -> createOreDrop(b, AMItems.CHIMERITE.get()));
            dropSelf(AMBlocks.CHIMERITE_BLOCK.get());
            add(AMBlocks.TOPAZ_ORE.get(), b -> createOreDrop(b, AMItems.TOPAZ.get()));
            add(AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), b -> createOreDrop(b, AMItems.TOPAZ.get()));
            dropSelf(AMBlocks.TOPAZ_BLOCK.get());
            add(AMBlocks.VINTEUM_ORE.get(), b -> createOreDrop(b, AMItems.VINTEUM_DUST.get()));
            add(AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), b -> createOreDrop(b, AMItems.VINTEUM_DUST.get()));
            dropSelf(AMBlocks.VINTEUM_BLOCK.get());
            add(AMBlocks.MOONSTONE_ORE.get(), b -> createOreDrop(b, AMItems.MOONSTONE.get()));
            add(AMBlocks.DEEPSLATE_MOONSTONE_ORE.get(), b -> createOreDrop(b, AMItems.MOONSTONE.get()));
            dropSelf(AMBlocks.MOONSTONE_BLOCK.get());
            add(AMBlocks.SUNSTONE_ORE.get(), b -> createOreDrop(b, AMItems.SUNSTONE.get()));
            dropSelf(AMBlocks.SUNSTONE_BLOCK.get());
        }
    }
}
