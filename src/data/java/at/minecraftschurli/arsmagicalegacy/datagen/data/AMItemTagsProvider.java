package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public final class AMItemTagsProvider extends ItemTagsProvider {
    public AMItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, ArsMagicaApi.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(AMTags.Items.ORES_CHIMERITE).add(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get());
        tag(AMTags.Items.ORES_TOPAZ).add(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get());
        tag(AMTags.Items.ORES_VINTEUM).add(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get());
        tag(AMTags.Items.ORES_MOONSTONE).add(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get());
        tag(AMTags.Items.ORES_SUNSTONE).add(AMItems.SUNSTONE_ORE.get());
        tag(AMTags.Items.STORAGE_BLOCKS_CHIMERITE).add(AMItems.CHIMERITE_BLOCK.get());
        tag(AMTags.Items.STORAGE_BLOCKS_TOPAZ).add(AMItems.TOPAZ_BLOCK.get());
        tag(AMTags.Items.STORAGE_BLOCKS_VINTEUM).add(AMItems.VINTEUM_BLOCK.get());
        tag(AMTags.Items.STORAGE_BLOCKS_MOONSTONE).add(AMItems.MOONSTONE_BLOCK.get());
        tag(AMTags.Items.STORAGE_BLOCKS_SUNSTONE).add(AMItems.SUNSTONE_BLOCK.get());
        tag(AMTags.Items.GEMS_CHIMERITE).add(AMItems.CHIMERITE.get());
        tag(AMTags.Items.GEMS_TOPAZ).add(AMItems.TOPAZ.get());
        tag(AMTags.Items.DUSTS_VINTEUM).add(AMItems.VINTEUM_DUST.get());
        tag(AMTags.Items.GEMS_MOONSTONE).add(AMItems.MOONSTONE.get());
        tag(AMTags.Items.GEMS_SUNSTONE).add(AMItems.SUNSTONE.get());
        tag(Tags.Items.ORES).addTags(AMTags.Items.ORES_CHIMERITE, AMTags.Items.ORES_TOPAZ, AMTags.Items.ORES_VINTEUM, AMTags.Items.ORES_MOONSTONE, AMTags.Items.ORES_SUNSTONE);
        tag(Tags.Items.STORAGE_BLOCKS).addTags(AMTags.Items.STORAGE_BLOCKS_CHIMERITE, AMTags.Items.STORAGE_BLOCKS_TOPAZ, AMTags.Items.STORAGE_BLOCKS_VINTEUM, AMTags.Items.STORAGE_BLOCKS_MOONSTONE, AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
        tag(Tags.Items.GEMS).addTags(AMTags.Items.GEMS_CHIMERITE, AMTags.Items.GEMS_TOPAZ, AMTags.Items.GEMS_MOONSTONE, AMTags.Items.GEMS_SUNSTONE);
        tag(Tags.Items.DUSTS).addTag(AMTags.Items.DUSTS_VINTEUM);
    }
}
