package at.minecraftschurli.arsmagicalegacy.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public interface AMItems {
    DeferredItem<BlockItem> CHIMERITE_ORE           = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.CHIMERITE_ORE);
    DeferredItem<BlockItem> DEEPSLATE_CHIMERITE_ORE = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.DEEPSLATE_CHIMERITE_ORE);
    DeferredItem<Item>      CHIMERITE               = AMRegistries.ITEMS.registerSimpleItem("chimerite");
    DeferredItem<BlockItem> CHIMERITE_BLOCK         = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.CHIMERITE_BLOCK);
    DeferredItem<BlockItem> TOPAZ_ORE               = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.TOPAZ_ORE);
    DeferredItem<BlockItem> DEEPSLATE_TOPAZ_ORE     = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.DEEPSLATE_TOPAZ_ORE);
    DeferredItem<Item>      TOPAZ                   = AMRegistries.ITEMS.registerSimpleItem("topaz");
    DeferredItem<BlockItem> TOPAZ_BLOCK             = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.TOPAZ_BLOCK);
    DeferredItem<BlockItem> VINTEUM_ORE             = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.VINTEUM_ORE);
    DeferredItem<BlockItem> DEEPSLATE_VINTEUM_ORE   = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.DEEPSLATE_VINTEUM_ORE);
    DeferredItem<Item>      VINTEUM_DUST            = AMRegistries.ITEMS.registerSimpleItem("vinteum_dust");
    DeferredItem<BlockItem> VINTEUM_BLOCK           = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.VINTEUM_BLOCK);
    DeferredItem<BlockItem> MOONSTONE_ORE           = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.MOONSTONE_ORE);
    DeferredItem<BlockItem> DEEPSLATE_MOONSTONE_ORE = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.DEEPSLATE_MOONSTONE_ORE);
    DeferredItem<Item>      MOONSTONE               = AMRegistries.ITEMS.registerSimpleItem("moonstone");
    DeferredItem<BlockItem> MOONSTONE_BLOCK         = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.MOONSTONE_BLOCK);
    DeferredItem<BlockItem> SUNSTONE_ORE            = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.SUNSTONE_ORE);
    DeferredItem<Item>      SUNSTONE                = AMRegistries.ITEMS.registerSimpleItem("sunstone");
    DeferredItem<BlockItem> SUNSTONE_BLOCK          = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.SUNSTONE_BLOCK);

    static void init() {
    }
}
