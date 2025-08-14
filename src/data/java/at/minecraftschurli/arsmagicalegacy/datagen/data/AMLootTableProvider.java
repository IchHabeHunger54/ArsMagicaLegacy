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
            dropSelf(AMBlocks.WITCHWOOD_LOG.get());
            dropSelf(AMBlocks.WITCHWOOD.get());
            dropSelf(AMBlocks.STRIPPED_WITCHWOOD_LOG.get());
            dropSelf(AMBlocks.STRIPPED_WITCHWOOD.get());
            add(AMBlocks.WITCHWOOD_LEAVES.get(), p -> createLeavesDrops(p, AMBlocks.WITCHWOOD_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f));
            dropSelf(AMBlocks.WITCHWOOD_SAPLING.get());
            dropPottedContents(AMBlocks.POTTED_WITCHWOOD_SAPLING.get());
            dropSelf(AMBlocks.WITCHWOOD_PLANKS.get());
            dropSelf(AMBlocks.WITCHWOOD_SLAB.get());
            dropSelf(AMBlocks.WITCHWOOD_STAIRS.get());
            dropSelf(AMBlocks.WITCHWOOD_FENCE.get());
            dropSelf(AMBlocks.WITCHWOOD_FENCE_GATE.get());
            add(AMBlocks.WITCHWOOD_DOOR.get(), this::createDoorTable);
            dropSelf(AMBlocks.WITCHWOOD_TRAPDOOR.get());
            dropSelf(AMBlocks.WITCHWOOD_BUTTON.get());
            dropSelf(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get());
            dropSelf(AMBlocks.WITCHWOOD_SIGN.get());
            dropOther(AMBlocks.WITCHWOOD_WALL_SIGN.get(), AMBlocks.WITCHWOOD_SIGN.get());
            dropSelf(AMBlocks.WITCHWOOD_HANGING_SIGN.get());
            dropOther(AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get(), AMBlocks.WITCHWOOD_HANGING_SIGN.get());
            dropSelf(AMBlocks.AUM.get());
            dropPottedContents(AMBlocks.POTTED_AUM.get());
            dropSelf(AMBlocks.CERUBLOSSOM.get());
            dropPottedContents(AMBlocks.POTTED_CERUBLOSSOM.get());
            dropSelf(AMBlocks.DESERT_NOVA.get());
            dropPottedContents(AMBlocks.POTTED_DESERT_NOVA.get());
            dropSelf(AMBlocks.TARMA_ROOT.get());
            dropPottedContents(AMBlocks.POTTED_TARMA_ROOT.get());
            dropSelf(AMBlocks.WAKEBLOOM.get());
            dropPottedContents(AMBlocks.POTTED_WAKEBLOOM.get());
            dropSelf(AMBlocks.VINTEUM_TORCH.get());
            dropOther(AMBlocks.VINTEUM_WALL_TORCH.get(), AMBlocks.VINTEUM_TORCH.get());
        }
    }
}
