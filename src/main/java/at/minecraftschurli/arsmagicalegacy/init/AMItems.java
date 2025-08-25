package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.item.InfinityOrbItem;
import at.minecraftschurli.arsmagicalegacy.item.InscriptionTableUpgradeItem;
import at.minecraftschurli.arsmagicalegacy.item.SpellItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.neoforged.neoforge.registries.DeferredItem;

public interface AMItems {
    // @formatter:off
    DeferredItem<SpellItem>                   SPELL                            = AMRegistries.ITEMS.registerItem("spell", p -> new SpellItem(p.stacksTo(1).component(AMDataComponents.SPELL, Spell.EMPTY)));
    DeferredItem<BlockItem>                   OCCULUS                          = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.OCCULUS);
    DeferredItem<BlockItem>                   INSCRIPTION_TABLE                = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.INSCRIPTION_TABLE);
    DeferredItem<InscriptionTableUpgradeItem> INSCRIPTION_TABLE_UPGRADE_TIER_1 = AMRegistries.ITEMS.registerItem("inscription_table_upgrade_tier_1", p -> new InscriptionTableUpgradeItem(p, 1));
    DeferredItem<InscriptionTableUpgradeItem> INSCRIPTION_TABLE_UPGRADE_TIER_2 = AMRegistries.ITEMS.registerItem("inscription_table_upgrade_tier_2", p -> new InscriptionTableUpgradeItem(p, 2));
    DeferredItem<InscriptionTableUpgradeItem> INSCRIPTION_TABLE_UPGRADE_TIER_3 = AMRegistries.ITEMS.registerItem("inscription_table_upgrade_tier_3", p -> new InscriptionTableUpgradeItem(p, 3));
    DeferredItem<Item>                        SPELL_PARCHMENT                  = AMRegistries.ITEMS.registerSimpleItem("spell_parchment");
    DeferredItem<InfinityOrbItem>             INFINITY_ORB                     = AMRegistries.ITEMS.registerItem("infinity_orb", InfinityOrbItem::new);
    DeferredItem<BlockItem>                   CHIMERITE_ORE                    = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.CHIMERITE_ORE);
    DeferredItem<BlockItem>                   DEEPSLATE_CHIMERITE_ORE          = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.DEEPSLATE_CHIMERITE_ORE);
    DeferredItem<Item>                        CHIMERITE                        = AMRegistries.ITEMS.registerSimpleItem("chimerite");
    DeferredItem<BlockItem>                   CHIMERITE_BLOCK                  = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.CHIMERITE_BLOCK);
    DeferredItem<BlockItem>                   TOPAZ_ORE                        = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.TOPAZ_ORE);
    DeferredItem<BlockItem>                   DEEPSLATE_TOPAZ_ORE              = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.DEEPSLATE_TOPAZ_ORE);
    DeferredItem<Item>                        TOPAZ                            = AMRegistries.ITEMS.registerSimpleItem("topaz");
    DeferredItem<BlockItem>                   TOPAZ_BLOCK                      = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.TOPAZ_BLOCK);
    DeferredItem<BlockItem>                   VINTEUM_ORE                      = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.VINTEUM_ORE);
    DeferredItem<BlockItem>                   DEEPSLATE_VINTEUM_ORE            = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.DEEPSLATE_VINTEUM_ORE);
    DeferredItem<Item>                        VINTEUM_DUST                     = AMRegistries.ITEMS.registerSimpleItem("vinteum_dust");
    DeferredItem<BlockItem>                   VINTEUM_BLOCK                    = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.VINTEUM_BLOCK);
    DeferredItem<BlockItem>                   MOONSTONE_ORE                    = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.MOONSTONE_ORE);
    DeferredItem<BlockItem>                   DEEPSLATE_MOONSTONE_ORE          = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.DEEPSLATE_MOONSTONE_ORE);
    DeferredItem<Item>                        MOONSTONE                        = AMRegistries.ITEMS.registerSimpleItem("moonstone");
    DeferredItem<BlockItem>                   MOONSTONE_BLOCK                  = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.MOONSTONE_BLOCK);
    DeferredItem<BlockItem>                   SUNSTONE_ORE                     = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.SUNSTONE_ORE);
    DeferredItem<Item>                        SUNSTONE                         = AMRegistries.ITEMS.registerSimpleItem("sunstone");
    DeferredItem<BlockItem>                   SUNSTONE_BLOCK                   = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.SUNSTONE_BLOCK);
    DeferredItem<Item>                        ARCANE_COMPOUND                  = AMRegistries.ITEMS.registerSimpleItem("arcane_compound");
    DeferredItem<Item>                        ARCANE_ASH                       = AMRegistries.ITEMS.registerSimpleItem("arcane_ash");
    DeferredItem<Item>                        PURIFIED_VINTEUM_DUST            = AMRegistries.ITEMS.registerSimpleItem("purified_vinteum_dust");
    DeferredItem<BlockItem>                   WITCHWOOD_LOG                    = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.WITCHWOOD_LOG);
    DeferredItem<BlockItem>                   WITCHWOOD                        = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.WITCHWOOD);
    DeferredItem<BlockItem>                   STRIPPED_WITCHWOOD_LOG           = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.STRIPPED_WITCHWOOD_LOG);
    DeferredItem<BlockItem>                   STRIPPED_WITCHWOOD               = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.STRIPPED_WITCHWOOD);
    DeferredItem<BlockItem>                   WITCHWOOD_LEAVES                 = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.WITCHWOOD_LEAVES);
    DeferredItem<BlockItem>                   WITCHWOOD_SAPLING                = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.WITCHWOOD_SAPLING);
    DeferredItem<BlockItem>                   WITCHWOOD_PLANKS                 = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.WITCHWOOD_PLANKS);
    DeferredItem<BlockItem>                   WITCHWOOD_SLAB                   = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.WITCHWOOD_SLAB);
    DeferredItem<BlockItem>                   WITCHWOOD_STAIRS                 = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.WITCHWOOD_STAIRS);
    DeferredItem<BlockItem>                   WITCHWOOD_FENCE                  = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.WITCHWOOD_FENCE);
    DeferredItem<BlockItem>                   WITCHWOOD_FENCE_GATE             = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.WITCHWOOD_FENCE_GATE);
    DeferredItem<BlockItem>                   WITCHWOOD_DOOR                   = AMRegistries.ITEMS.registerItem("witchwood_door", p -> new DoubleHighBlockItem(AMBlocks.WITCHWOOD_DOOR.get(), p));
    DeferredItem<BlockItem>                   WITCHWOOD_TRAPDOOR               = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.WITCHWOOD_TRAPDOOR);
    DeferredItem<BlockItem>                   WITCHWOOD_BUTTON                 = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.WITCHWOOD_BUTTON);
    DeferredItem<BlockItem>                   WITCHWOOD_PRESSURE_PLATE         = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.WITCHWOOD_PRESSURE_PLATE);
    DeferredItem<BlockItem>                   WITCHWOOD_SIGN                   = AMRegistries.ITEMS.registerItem("witchwood_sign", p -> new SignItem(p, AMBlocks.WITCHWOOD_SIGN.get(), AMBlocks.WITCHWOOD_WALL_SIGN.get()), new Item.Properties().stacksTo(16));
    DeferredItem<BlockItem>                   WITCHWOOD_HANGING_SIGN           = AMRegistries.ITEMS.registerItem("witchwood_hanging_sign", p -> new HangingSignItem(AMBlocks.WITCHWOOD_HANGING_SIGN.get(), AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get(), p), new Item.Properties().stacksTo(16));
    DeferredItem<BlockItem>                   AUM                              = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.AUM);
    DeferredItem<BlockItem>                   CERUBLOSSOM                      = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.CERUBLOSSOM);
    DeferredItem<BlockItem>                   DESERT_NOVA                      = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.DESERT_NOVA);
    DeferredItem<BlockItem>                   TARMA_ROOT                       = AMRegistries.ITEMS.registerSimpleBlockItem(AMBlocks.TARMA_ROOT);
    DeferredItem<BlockItem>                   WAKEBLOOM                        = AMRegistries.ITEMS.registerItem("wakebloom", p -> new PlaceOnWaterBlockItem(AMBlocks.WAKEBLOOM.get(), p));
    DeferredItem<BlockItem>                   VINTEUM_TORCH                    = AMRegistries.ITEMS.registerItem("vinteum_torch", p -> new StandingAndWallBlockItem(AMBlocks.VINTEUM_TORCH.get(), AMBlocks.VINTEUM_WALL_TORCH.get(), p, Direction.DOWN));
    // @formatter:on

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
