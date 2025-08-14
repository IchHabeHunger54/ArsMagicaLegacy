package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
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
        tag(AMTags.Blocks.WITCHWOOD_LOGS).add(AMBlocks.WITCHWOOD_LOG.get(), AMBlocks.WITCHWOOD.get(), AMBlocks.STRIPPED_WITCHWOOD_LOG.get(), AMBlocks.STRIPPED_WITCHWOOD.get());
        tag(BlockTags.LOGS).addTag(AMTags.Blocks.WITCHWOOD_LOGS);
        tag(BlockTags.LEAVES).add(AMBlocks.WITCHWOOD_LEAVES.get());
        tag(BlockTags.SAPLINGS).add(AMBlocks.WITCHWOOD_SAPLING.get());
        tag(BlockTags.PLANKS).add(AMBlocks.WITCHWOOD_PLANKS.get());
        tag(BlockTags.WOODEN_SLABS).add(AMBlocks.WITCHWOOD_SLAB.get());
        tag(BlockTags.WOODEN_STAIRS).add(AMBlocks.WITCHWOOD_STAIRS.get());
        tag(BlockTags.WOODEN_FENCES).add(AMBlocks.WITCHWOOD_FENCE.get());
        tag(Tags.Blocks.FENCES_WOODEN).add(AMBlocks.WITCHWOOD_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(AMBlocks.WITCHWOOD_FENCE_GATE.get());
        tag(Tags.Blocks.FENCE_GATES_WOODEN).add(AMBlocks.WITCHWOOD_FENCE_GATE.get());
        tag(BlockTags.WOODEN_DOORS).add(AMBlocks.WITCHWOOD_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(AMBlocks.WITCHWOOD_TRAPDOOR.get());
        tag(BlockTags.WOODEN_BUTTONS).add(AMBlocks.WITCHWOOD_BUTTON.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get());
        tag(BlockTags.STANDING_SIGNS).add(AMBlocks.WITCHWOOD_SIGN.get());
        tag(BlockTags.WALL_SIGNS).add(AMBlocks.WITCHWOOD_WALL_SIGN.get());
        tag(BlockTags.CEILING_HANGING_SIGNS).add(AMBlocks.WITCHWOOD_HANGING_SIGN.get());
        tag(BlockTags.WALL_HANGING_SIGNS).add(AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get());
        tag(AMTags.Blocks.AUM_PLANTABLE_ON).addTag(BlockTags.DIRT);
        tag(AMTags.Blocks.CERUBLOSSOM_PLANTABLE_ON).addTag(BlockTags.DIRT);
        tag(AMTags.Blocks.DESERT_NOVA_PLANTABLE_ON).addTag(BlockTags.SAND);
        tag(AMTags.Blocks.TARMA_ROOT_PLANTABLE_ON).add(Blocks.CLAY, Blocks.GRAVEL).addTags(BlockTags.DIRT, BlockTags.SAND, BlockTags.STONE_ORE_REPLACEABLES, BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        tag(BlockTags.SMALL_FLOWERS).add(AMBlocks.AUM.get(), AMBlocks.CERUBLOSSOM.get(), AMBlocks.DESERT_NOVA.get(), AMBlocks.TARMA_ROOT.get(), AMBlocks.WAKEBLOOM.get());
        tag(BlockTags.FLOWER_POTS).add(AMBlocks.POTTED_WITCHWOOD_SAPLING.get(), AMBlocks.POTTED_AUM.get(), AMBlocks.POTTED_CERUBLOSSOM.get(), AMBlocks.POTTED_DESERT_NOVA.get(), AMBlocks.POTTED_TARMA_ROOT.get(), AMBlocks.POTTED_WAKEBLOOM.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(AMBlocks.CHIMERITE_ORE.get(), AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), AMBlocks.CHIMERITE_BLOCK.get(), AMBlocks.TOPAZ_ORE.get(), AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), AMBlocks.TOPAZ_BLOCK.get(), AMBlocks.VINTEUM_ORE.get(), AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), AMBlocks.VINTEUM_BLOCK.get(), AMBlocks.MOONSTONE_ORE.get(), AMBlocks.DEEPSLATE_MOONSTONE_ORE.get(), AMBlocks.MOONSTONE_BLOCK.get(), AMBlocks.SUNSTONE_ORE.get(), AMBlocks.SUNSTONE_BLOCK.get());
        tag(BlockTags.NEEDS_STONE_TOOL).add(AMBlocks.CHIMERITE_BLOCK.get(), AMBlocks.CHIMERITE_ORE.get(), AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), AMBlocks.TOPAZ_BLOCK.get(), AMBlocks.TOPAZ_ORE.get(), AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), AMBlocks.VINTEUM_BLOCK.get(), AMBlocks.VINTEUM_ORE.get(), AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), AMBlocks.MOONSTONE_BLOCK.get(), AMBlocks.SUNSTONE_BLOCK.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(AMBlocks.MOONSTONE_ORE.get(), AMBlocks.DEEPSLATE_MOONSTONE_ORE.get());
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(AMBlocks.SUNSTONE_ORE.get());
    }
}
