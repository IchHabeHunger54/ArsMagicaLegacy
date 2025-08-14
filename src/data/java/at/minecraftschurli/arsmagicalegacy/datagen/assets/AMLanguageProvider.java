package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public final class AMLanguageProvider extends LanguageProvider {
    public AMLanguageProvider(PackOutput output) {
        super(output, ArsMagicaApi.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        blockIdTranslation(AMBlocks.CHIMERITE_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_CHIMERITE_ORE);
        itemIdTranslation(AMItems.CHIMERITE);
        addBlock(AMBlocks.CHIMERITE_BLOCK, "Block of Chimerite");
        blockIdTranslation(AMBlocks.TOPAZ_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_TOPAZ_ORE);
        itemIdTranslation(AMItems.TOPAZ);
        addBlock(AMBlocks.TOPAZ_BLOCK, "Block of Topaz");
        blockIdTranslation(AMBlocks.VINTEUM_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_VINTEUM_ORE);
        itemIdTranslation(AMItems.VINTEUM_DUST);
        addBlock(AMBlocks.VINTEUM_BLOCK, "Block of Vinteum");
        blockIdTranslation(AMBlocks.MOONSTONE_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_MOONSTONE_ORE);
        itemIdTranslation(AMItems.MOONSTONE);
        addBlock(AMBlocks.MOONSTONE_BLOCK, "Block of Moonstone");
        blockIdTranslation(AMBlocks.SUNSTONE_ORE);
        itemIdTranslation(AMItems.SUNSTONE);
        addBlock(AMBlocks.SUNSTONE_BLOCK, "Block of Sunstone");
        blockIdTranslation(AMBlocks.WITCHWOOD_LOG);
        blockIdTranslation(AMBlocks.WITCHWOOD);
        blockIdTranslation(AMBlocks.STRIPPED_WITCHWOOD_LOG);
        blockIdTranslation(AMBlocks.STRIPPED_WITCHWOOD);
        blockIdTranslation(AMBlocks.WITCHWOOD_LEAVES);
        blockIdTranslation(AMBlocks.WITCHWOOD_SAPLING);
        blockIdTranslation(AMBlocks.POTTED_WITCHWOOD_SAPLING);
        blockIdTranslation(AMBlocks.WITCHWOOD_PLANKS);
        blockIdTranslation(AMBlocks.WITCHWOOD_SLAB);
        blockIdTranslation(AMBlocks.WITCHWOOD_STAIRS);
        blockIdTranslation(AMBlocks.WITCHWOOD_FENCE);
        blockIdTranslation(AMBlocks.WITCHWOOD_FENCE_GATE);
        blockIdTranslation(AMBlocks.WITCHWOOD_DOOR);
        blockIdTranslation(AMBlocks.WITCHWOOD_TRAPDOOR);
        blockIdTranslation(AMBlocks.WITCHWOOD_BUTTON);
        blockIdTranslation(AMBlocks.WITCHWOOD_PRESSURE_PLATE);
        addBlock(AMBlocks.WITCHWOOD_WALL_SIGN, idTranslation(AMBlocks.WITCHWOOD_SIGN.getId().getPath()));
        addBlock(AMBlocks.WITCHWOOD_WALL_HANGING_SIGN, idTranslation(AMBlocks.WITCHWOOD_HANGING_SIGN.getId().getPath()));
        add("itemGroup." + ArsMagicaApi.MOD_ID, "Ars Magica: Legacy");
    }

    private void blockIdTranslation(DeferredBlock<?> block) {
        addBlock(block, idTranslation(block.getId().getPath()));
    }

    private void itemIdTranslation(DeferredItem<?> item) {
        addItem(item, idTranslation(item.getId().getPath()));
    }

    /**
     * @param id A string of format "word_word_word".
     * @return A string of format "Word Word Word".
     */
    private static String idTranslation(String id) {
        StringBuilder result = new StringBuilder();
        for (String string : id.split("_")) {
            result.append(string.substring(0, 1).toUpperCase()).append(string.substring(1)).append(" ");
        }
        return result.substring(0, result.length() - 1);
    }
}
