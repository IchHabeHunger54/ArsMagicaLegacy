package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
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
        copy(AMTags.Blocks.ORES_CHIMERITE, AMTags.Items.ORES_CHIMERITE);
        copy(AMTags.Blocks.ORES_TOPAZ, AMTags.Items.ORES_TOPAZ);
        copy(AMTags.Blocks.ORES_VINTEUM, AMTags.Items.ORES_VINTEUM);
        copy(AMTags.Blocks.ORES_MOONSTONE, AMTags.Items.ORES_MOONSTONE);
        copy(AMTags.Blocks.ORES_SUNSTONE, AMTags.Items.ORES_SUNSTONE);
        copy(AMTags.Blocks.STORAGE_BLOCKS_CHIMERITE, AMTags.Items.STORAGE_BLOCKS_CHIMERITE);
        copy(AMTags.Blocks.STORAGE_BLOCKS_TOPAZ, AMTags.Items.STORAGE_BLOCKS_TOPAZ);
        copy(AMTags.Blocks.STORAGE_BLOCKS_VINTEUM, AMTags.Items.STORAGE_BLOCKS_VINTEUM);
        copy(AMTags.Blocks.STORAGE_BLOCKS_MOONSTONE, AMTags.Items.STORAGE_BLOCKS_MOONSTONE);
        copy(AMTags.Blocks.STORAGE_BLOCKS_SUNSTONE, AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
        tag(AMTags.Items.GEMS_CHIMERITE).add(AMItems.CHIMERITE.get());
        tag(AMTags.Items.GEMS_TOPAZ).add(AMItems.TOPAZ.get());
        tag(AMTags.Items.DUSTS_VINTEUM).add(AMItems.VINTEUM_DUST.get());
        tag(AMTags.Items.GEMS_MOONSTONE).add(AMItems.MOONSTONE.get());
        tag(AMTags.Items.GEMS_SUNSTONE).add(AMItems.SUNSTONE.get());
        tag(AMTags.Items.DUSTS_ARCANE_COMPOUND).add(AMItems.ARCANE_COMPOUND.get());
        tag(AMTags.Items.DUSTS_ARCANE_ASH).add(AMItems.ARCANE_ASH.get());
        tag(AMTags.Items.DUSTS_PURIFIED_VINTEUM).add(AMItems.PURIFIED_VINTEUM_DUST.get());
        tag(Tags.Items.ORES).addTags(AMTags.Items.ORES_CHIMERITE, AMTags.Items.ORES_TOPAZ, AMTags.Items.ORES_VINTEUM, AMTags.Items.ORES_MOONSTONE, AMTags.Items.ORES_SUNSTONE);
        tag(Tags.Items.STORAGE_BLOCKS).addTags(AMTags.Items.STORAGE_BLOCKS_CHIMERITE, AMTags.Items.STORAGE_BLOCKS_TOPAZ, AMTags.Items.STORAGE_BLOCKS_VINTEUM, AMTags.Items.STORAGE_BLOCKS_MOONSTONE, AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
        tag(Tags.Items.GEMS).addTags(AMTags.Items.GEMS_CHIMERITE, AMTags.Items.GEMS_TOPAZ, AMTags.Items.GEMS_MOONSTONE, AMTags.Items.GEMS_SUNSTONE);
        tag(Tags.Items.DUSTS).addTags(AMTags.Items.DUSTS_VINTEUM, AMTags.Items.DUSTS_ARCANE_COMPOUND, AMTags.Items.DUSTS_ARCANE_ASH, AMTags.Items.DUSTS_PURIFIED_VINTEUM);
        copy(AMTags.Blocks.WITCHWOOD_LOGS, AMTags.Items.WITCHWOOD_LOGS);
        tag(ItemTags.LOGS).addTag(AMTags.Items.WITCHWOOD_LOGS);
        tag(ItemTags.LEAVES).add(AMItems.WITCHWOOD_LEAVES.get());
        tag(ItemTags.SAPLINGS).add(AMItems.WITCHWOOD_SAPLING.get());
        tag(ItemTags.PLANKS).add(AMItems.WITCHWOOD_PLANKS.get());
        tag(ItemTags.WOODEN_SLABS).add(AMItems.WITCHWOOD_SLAB.get());
        tag(ItemTags.WOODEN_STAIRS).add(AMItems.WITCHWOOD_STAIRS.get());
        tag(ItemTags.WOODEN_FENCES).add(AMItems.WITCHWOOD_FENCE.get());
        tag(Tags.Items.FENCES_WOODEN).add(AMItems.WITCHWOOD_FENCE.get());
        tag(ItemTags.FENCE_GATES).add(AMItems.WITCHWOOD_FENCE_GATE.get());
        tag(Tags.Items.FENCE_GATES_WOODEN).add(AMItems.WITCHWOOD_FENCE_GATE.get());
        tag(ItemTags.WOODEN_DOORS).add(AMItems.WITCHWOOD_DOOR.get());
        tag(ItemTags.WOODEN_TRAPDOORS).add(AMItems.WITCHWOOD_TRAPDOOR.get());
        tag(ItemTags.WOODEN_BUTTONS).add(AMItems.WITCHWOOD_BUTTON.get());
        tag(ItemTags.WOODEN_PRESSURE_PLATES).add(AMItems.WITCHWOOD_PRESSURE_PLATE.get());
        tag(ItemTags.SIGNS).add(AMItems.WITCHWOOD_SIGN.get());
        tag(ItemTags.HANGING_SIGNS).add(AMItems.WITCHWOOD_HANGING_SIGN.get());
        tag(ItemTags.SMALL_FLOWERS).add(AMItems.AUM.get(), AMItems.CERUBLOSSOM.get(), AMItems.DESERT_NOVA.get(), AMItems.TARMA_ROOT.get(), AMItems.WAKEBLOOM.get());
        tag(AMTags.Items.INSCRIPTION_TABLE_BOOKS).add(Items.WRITABLE_BOOK, AMItems.SPELL_RECIPE.get());
        tag(AMTags.Items.OCCULUS_FORGET_ALL).addTag(AMTags.Items.STORAGE_BLOCKS_VINTEUM);
        tag(AMTags.Items.RUNES).add(AMItems.BLANK_RUNE.get(), AMItems.WHITE_RUNE.get(), AMItems.ORANGE_RUNE.get(), AMItems.MAGENTA_RUNE.get(), AMItems.LIGHT_BLUE_RUNE.get(), AMItems.YELLOW_RUNE.get(), AMItems.LIME_RUNE.get(), AMItems.PINK_RUNE.get(), AMItems.GRAY_RUNE.get(), AMItems.LIGHT_GRAY_RUNE.get(), AMItems.CYAN_RUNE.get(), AMItems.PURPLE_RUNE.get(), AMItems.BLUE_RUNE.get(), AMItems.BROWN_RUNE.get(), AMItems.GREEN_RUNE.get(), AMItems.RED_RUNE.get(), AMItems.BLACK_RUNE.get());
        tag(AMTags.Items.SPELLCRAFTING_START).add(AMItems.BLANK_RUNE.get());
        tag(AMTags.Items.SPELLCRAFTING_END).add(AMItems.SPELL_PARCHMENT.get());
    }
}
