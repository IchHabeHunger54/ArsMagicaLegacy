package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public final class AMBlockTagsProvider extends BlockTagsProvider {
    public AMBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(AMTags.Blocks.ORES_CHIMERITE).add(AMBlocks.CHIMERITE_ORE.get(), AMBlocks.DEEPSLATE_CHIMERITE_ORE.get());
        tag(AMTags.Blocks.ORES_TOPAZ).add(AMBlocks.TOPAZ_ORE.get(), AMBlocks.DEEPSLATE_TOPAZ_ORE.get());
        tag(AMTags.Blocks.ORES_VINTEUM).add(AMBlocks.VINTEUM_ORE.get(), AMBlocks.DEEPSLATE_VINTEUM_ORE.get());
        tag(AMTags.Blocks.ORES_MOONSTONE).add(AMBlocks.MOONSTONE_ORE.get(), AMBlocks.DEEPSLATE_MOONSTONE_ORE.get());
        tag(AMTags.Blocks.ORES_SUNSTONE).add(AMBlocks.SUNSTONE_ORE.get());
        tag(AMTags.Blocks.STORAGE_BLOCKS_CHIMERITE).add(AMBlocks.CHIMERITE_BLOCK.get());
        tag(AMTags.Blocks.STORAGE_BLOCKS_TOPAZ).add(AMBlocks.TOPAZ_BLOCK.get());
        tag(AMTags.Blocks.STORAGE_BLOCKS_VINTEUM).add(AMBlocks.VINTEUM_BLOCK.get());
        tag(AMTags.Blocks.STORAGE_BLOCKS_MOONSTONE).add(AMBlocks.MOONSTONE_BLOCK.get());
        tag(AMTags.Blocks.STORAGE_BLOCKS_SUNSTONE).add(AMBlocks.SUNSTONE_BLOCK.get());
        tag(Tags.Blocks.ORES).addTags(AMTags.Blocks.ORES_CHIMERITE, AMTags.Blocks.ORES_TOPAZ, AMTags.Blocks.ORES_VINTEUM, AMTags.Blocks.ORES_MOONSTONE, AMTags.Blocks.ORES_SUNSTONE);
        tag(Tags.Blocks.STORAGE_BLOCKS).addTags(AMTags.Blocks.STORAGE_BLOCKS_CHIMERITE, AMTags.Blocks.STORAGE_BLOCKS_TOPAZ, AMTags.Blocks.STORAGE_BLOCKS_VINTEUM, AMTags.Blocks.STORAGE_BLOCKS_MOONSTONE, AMTags.Blocks.STORAGE_BLOCKS_SUNSTONE);
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(AMBlocks.CHIMERITE_ORE.get(), AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), AMBlocks.CHIMERITE_BLOCK.get(), AMBlocks.TOPAZ_ORE.get(), AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), AMBlocks.TOPAZ_BLOCK.get(), AMBlocks.VINTEUM_ORE.get(), AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), AMBlocks.VINTEUM_BLOCK.get(), AMBlocks.MOONSTONE_ORE.get(), AMBlocks.DEEPSLATE_MOONSTONE_ORE.get(), AMBlocks.MOONSTONE_BLOCK.get(), AMBlocks.SUNSTONE_ORE.get(), AMBlocks.SUNSTONE_BLOCK.get());
    }
}
