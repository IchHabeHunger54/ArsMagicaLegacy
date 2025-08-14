package at.minecraftschurli.arsmagicalegacy.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface AMTags {
    interface Blocks {
        TagKey<Block> ORES_CHIMERITE = cTag("ores/chimerite");
        TagKey<Block> ORES_TOPAZ = cTag("ores/topaz");
        TagKey<Block> ORES_VINTEUM = cTag("ores/vinteum");
        TagKey<Block> ORES_MOONSTONE = cTag("ores/moonstone");
        TagKey<Block> ORES_SUNSTONE = cTag("ores/sunstone");
        TagKey<Block> STORAGE_BLOCKS_CHIMERITE = cTag("storage_blocks/chimerite");
        TagKey<Block> STORAGE_BLOCKS_TOPAZ = cTag("storage_blocks/topaz");
        TagKey<Block> STORAGE_BLOCKS_VINTEUM = cTag("storage_blocks/vinteum");
        TagKey<Block> STORAGE_BLOCKS_MOONSTONE = cTag("storage_blocks/moonstone");
        TagKey<Block> STORAGE_BLOCKS_SUNSTONE = cTag("storage_blocks/sunstone");
        TagKey<Block> WITCHWOOD_LOGS = tag("witchwood_logs");

        private static TagKey<Block> cTag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", name));
        }

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, ArsMagicaApi.modLoc(name));
        }
    }

    interface Items {
        TagKey<Item> ORES_CHIMERITE = cTag("ores/chimerite");
        TagKey<Item> ORES_TOPAZ = cTag("ores/topaz");
        TagKey<Item> ORES_VINTEUM = cTag("ores/vinteum");
        TagKey<Item> ORES_MOONSTONE = cTag("ores/moonstone");
        TagKey<Item> ORES_SUNSTONE = cTag("ores/sunstone");
        TagKey<Item> STORAGE_BLOCKS_CHIMERITE = cTag("storage_blocks/chimerite");
        TagKey<Item> STORAGE_BLOCKS_TOPAZ = cTag("storage_blocks/topaz");
        TagKey<Item> STORAGE_BLOCKS_VINTEUM = cTag("storage_blocks/vinteum");
        TagKey<Item> STORAGE_BLOCKS_MOONSTONE = cTag("storage_blocks/moonstone");
        TagKey<Item> STORAGE_BLOCKS_SUNSTONE = cTag("storage_blocks/sunstone");
        TagKey<Item> GEMS_CHIMERITE = cTag("gems/chimerite");
        TagKey<Item> GEMS_TOPAZ = cTag("gems/topaz");
        TagKey<Item> DUSTS_VINTEUM = cTag("dusts/vinteum");
        TagKey<Item> GEMS_MOONSTONE = cTag("gems/moonstone");
        TagKey<Item> GEMS_SUNSTONE = cTag("gems/sunstone");
        TagKey<Item> WITCHWOOD_LOGS = tag("witchwood_logs");

        private static TagKey<Item> cTag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", name));
        }

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ArsMagicaApi.modLoc(name));
        }
    }
}
