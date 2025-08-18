package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
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
        itemIdTranslation(AMItems.SPELL);
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
        itemIdTranslation(AMItems.ARCANE_COMPOUND);
        itemIdTranslation(AMItems.ARCANE_ASH);
        itemIdTranslation(AMItems.PURIFIED_VINTEUM_DUST);
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
        blockIdTranslation(AMBlocks.AUM);
        blockIdTranslation(AMBlocks.POTTED_AUM);
        blockIdTranslation(AMBlocks.CERUBLOSSOM);
        blockIdTranslation(AMBlocks.POTTED_CERUBLOSSOM);
        blockIdTranslation(AMBlocks.DESERT_NOVA);
        blockIdTranslation(AMBlocks.POTTED_DESERT_NOVA);
        blockIdTranslation(AMBlocks.TARMA_ROOT);
        blockIdTranslation(AMBlocks.POTTED_TARMA_ROOT);
        blockIdTranslation(AMBlocks.WAKEBLOOM);
        blockIdTranslation(AMBlocks.POTTED_WAKEBLOOM);
        addBlock(AMBlocks.VINTEUM_WALL_TORCH, idTranslation(AMBlocks.VINTEUM_TORCH.getId().getPath()));
        add(AMTags.Blocks.ORES_CHIMERITE, "Chimerite Ores");
        add(AMTags.Blocks.ORES_TOPAZ, "Topaz Ores");
        add(AMTags.Blocks.ORES_VINTEUM, "Vinteum Ores");
        add(AMTags.Blocks.ORES_MOONSTONE, "Moonstone Ores");
        add(AMTags.Blocks.ORES_SUNSTONE, "Sunstone Ores");
        add(AMTags.Blocks.STORAGE_BLOCKS_CHIMERITE, "Chimerite Storage Blocks");
        add(AMTags.Blocks.STORAGE_BLOCKS_TOPAZ, "Topaz Storage Blocks");
        add(AMTags.Blocks.STORAGE_BLOCKS_VINTEUM, "Vinteum Storage Blocks");
        add(AMTags.Blocks.STORAGE_BLOCKS_MOONSTONE, "Moonstone Storage Blocks");
        add(AMTags.Blocks.STORAGE_BLOCKS_SUNSTONE, "Sunstone Storage Blocks");
        add(AMTags.Blocks.WITCHWOOD_LOGS, "Witchwood Logs");
        add(AMTags.Blocks.AUM_PLANTABLE_ON, "Soil for Aum");
        add(AMTags.Blocks.CERUBLOSSOM_PLANTABLE_ON, "Soil for Cerublossom");
        add(AMTags.Blocks.DESERT_NOVA_PLANTABLE_ON, "Soil for Desert Nova");
        add(AMTags.Blocks.TARMA_ROOT_PLANTABLE_ON, "Soil for Tarma Root");
        add(AMTags.Items.ORES_CHIMERITE, "Chimerite Ores");
        add(AMTags.Items.ORES_TOPAZ, "Topaz Ores");
        add(AMTags.Items.ORES_VINTEUM, "Vinteum Ores");
        add(AMTags.Items.ORES_MOONSTONE, "Moonstone Ores");
        add(AMTags.Items.ORES_SUNSTONE, "Sunstone Ores");
        add(AMTags.Items.STORAGE_BLOCKS_CHIMERITE, "Chimerite Storage Blocks");
        add(AMTags.Items.STORAGE_BLOCKS_TOPAZ, "Topaz Storage Blocks");
        add(AMTags.Items.STORAGE_BLOCKS_VINTEUM, "Vinteum Storage Blocks");
        add(AMTags.Items.STORAGE_BLOCKS_MOONSTONE, "Moonstone Storage Blocks");
        add(AMTags.Items.STORAGE_BLOCKS_SUNSTONE, "Sunstone Storage Blocks");
        add(AMTags.Items.GEMS_CHIMERITE, "Chimerite Gems");
        add(AMTags.Items.GEMS_TOPAZ, "Topaz Gems");
        add(AMTags.Items.DUSTS_VINTEUM, "Vinteum Dusts");
        add(AMTags.Items.GEMS_MOONSTONE, "Moonstone Gems");
        add(AMTags.Items.GEMS_SUNSTONE, "Sunstone Gems");
        add(AMTags.Items.DUSTS_ARCANE_COMPOUND, "Arcane Compound Dusts");
        add(AMTags.Items.DUSTS_ARCANE_ASH, "Arcane Ash Dusts");
        add(AMTags.Items.DUSTS_PURIFIED_VINTEUM, "Purified Vinteum Dusts");
        add(AMTags.Items.WITCHWOOD_LOGS, "Witchwood Logs");
        advancementTranslation("root", "Ars Magica: Legacy", "A renewed look into Minecraft with a splash of magic...");
        advancementTranslation("spell", "You're a wizard, Harry!", "Get your first spell");
        configTranslation("mana.base", "The base value for mana calculation. Mana is calculated as base + multiplier * (level - 1).");
        configTranslation("mana.multiplier", "The multiplier for mana calculation. Mana is calculated as base + multiplier * (level - 1).");
        configTranslation("mana.regeneration", "The multiplier for mana regeneration. Mana regen is calculated as (base + multiplier * (level - 1)) * regeneration.");
        configTranslation("bars_x", "Horizontal position of the mana, burnout and level bars.");
        configTranslation("bars_y", "Vertical position of the mana, burnout and level bars.");
        configTranslation("bars_anchor_x", "Horizontal anchor of the mana, burnout and level bars.");
        configTranslation("bars_anchor_y", "Vertical anchor of the mana, burnout and level bars.");
        add(AMTranslations.SPELL_CAST_BURNED_OUT, "Spell is malformed and cannot be cast!");
        add(AMTranslations.SPELL_CAST_MALFORMED, "Spell is malformed and cannot be cast!");
        add(AMTranslations.SPELL_CAST_NOT_ENOUGH_MANA, "Not enough mana to cast the spell!");
        add("itemGroup." + ArsMagicaApi.MOD_ID, "Ars Magica: Legacy");
    }

    /**
     * Adds a block translation that matches the block id.
     *
     * @param block The block to generate the translation for.
     */
    private void blockIdTranslation(DeferredBlock<?> block) {
        addBlock(block, idTranslation(block.getId().getPath()));
    }

    /**
     * Adds an item translation that matches the item id.
     *
     * @param item The item to generate the translation for.
     */
    private void itemIdTranslation(DeferredItem<?> item) {
        addItem(item, idTranslation(item.getId().getPath()));
    }

    /**
     * Adds an advancement translation.
     *
     * @param name        The name of the advancement to generate the translation for.
     * @param title       The translation of the advancement's title.
     * @param description The translation of the advancement's description.
     */
    private void advancementTranslation(String name, String title, String description) {
        add("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".title", title);
        add("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".description", description);
    }

    /**
     * Adds a config translation.
     *
     * @param name        The name of the config value to generate the translation for.
     * @param translation The translation of the config value.
     */
    private void configTranslation(String name, String translation) {
        add(AMTranslations.CONFIG + name, translation);
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
