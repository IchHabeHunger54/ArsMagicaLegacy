package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.item.CrystalWrenchItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

public final class AMItemModelProvider extends ItemModelProvider {
    public AMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ArsMagicaApi.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture("arcane_compendium", mcLoc("item/generated"), "layer0", modLoc("item/arcane_compendium"));
        basicItem(AMItems.SPELL);
        basicItemWithVariants(AMItems.SPELL, AMMagic.AFFINITIES_WITH_NONE);
        withExistingParent(AMItems.SPELL_RECIPE.getId().getPath(), mcLoc("item/written_book"));
        basicItem(AMItems.ETHERIUM_PLACEHOLDER);
        blockItem(AMItems.OCCULUS);
        basicItem(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1);
        basicItem(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2);
        basicItem(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3);
        blockItem(AMItems.ALTAR_CORE);
        blockItem(AMItems.MAGIC_WALL);
        singleTexture("black_aurem", mcLoc("item/generated"), "layer0", modLoc("block/black_aurem"));
        getBuilder("crystal_wrench")
            .override()
            .predicate(CrystalWrenchItem.ACTIVE, 1)
            .model(singleTexture("crystal_wrench_active", mcLoc("item/generated"), "layer0", modLoc("item/crystal_wrench_active")))
            .end()
            .texture("layer0", modLoc("item/crystal_wrench"));
        basicItem(AMItems.CRYSTAL_WRENCH);
        basicItem(AMItems.WIZARDS_CHALK);
        basicBlockItem(AMItems.VINTEUM_TORCH);
        basicItem(AMItems.SPELL_PARCHMENT);
        withExistingParent(AMItems.SPELL_BOOK.getId().getPath(), mcLoc("item/generated"))
            .texture("layer0", modLoc("item/spell_book"))
            .texture("layer1", modLoc("item/spell_book_overlay"));
        basicItem(AMItems.MAGITECH_GOGGLES);
        basicItem(AMItems.MAGE_HELMET);
        basicItem(AMItems.MAGE_CHESTPLATE);
        basicItem(AMItems.MAGE_LEGGINGS);
        basicItem(AMItems.MAGE_BOOTS);
        basicItem(AMItems.BATTLEMAGE_HELMET);
        basicItem(AMItems.BATTLEMAGE_CHESTPLATE);
        basicItem(AMItems.BATTLEMAGE_LEGGINGS);
        basicItem(AMItems.BATTLEMAGE_BOOTS);
        basicItem(AMItems.MANA_CAKE);
        basicItem(AMItems.MANA_MARTINI);
        basicItem(AMItems.INFINITY_ORB);
        basicItemWithVariants(AMItems.INFINITY_ORB, AMMagic.SKILL_POINTS);
        basicItemWithVariants(AMItems.AFFINITY_ESSENCE, AMMagic.AFFINITIES);
        basicItemWithVariants(AMItems.AFFINITY_TOME, AMMagic.AFFINITIES_WITH_NONE);
        basicItem(AMItems.BLANK_RUNE);
        basicItem(AMItems.WHITE_RUNE);
        basicItem(AMItems.ORANGE_RUNE);
        basicItem(AMItems.MAGENTA_RUNE);
        basicItem(AMItems.LIGHT_BLUE_RUNE);
        basicItem(AMItems.YELLOW_RUNE);
        basicItem(AMItems.LIME_RUNE);
        basicItem(AMItems.PINK_RUNE);
        basicItem(AMItems.GRAY_RUNE);
        basicItem(AMItems.LIGHT_GRAY_RUNE);
        basicItem(AMItems.CYAN_RUNE);
        basicItem(AMItems.PURPLE_RUNE);
        basicItem(AMItems.BLUE_RUNE);
        basicItem(AMItems.BROWN_RUNE);
        basicItem(AMItems.GREEN_RUNE);
        basicItem(AMItems.RED_RUNE);
        basicItem(AMItems.BLACK_RUNE);
        basicItem(AMItems.RUNE_BAG);
        blockItem(AMItems.CHIMERITE_ORE);
        blockItem(AMItems.DEEPSLATE_CHIMERITE_ORE);
        basicItem(AMItems.CHIMERITE);
        blockItem(AMItems.CHIMERITE_BLOCK);
        blockItem(AMItems.TOPAZ_ORE);
        blockItem(AMItems.DEEPSLATE_TOPAZ_ORE);
        basicItem(AMItems.TOPAZ);
        blockItem(AMItems.TOPAZ_BLOCK);
        blockItem(AMItems.VINTEUM_ORE);
        blockItem(AMItems.DEEPSLATE_VINTEUM_ORE);
        basicItem(AMItems.VINTEUM_DUST);
        blockItem(AMItems.VINTEUM_BLOCK);
        blockItem(AMItems.MOONSTONE_ORE);
        blockItem(AMItems.DEEPSLATE_MOONSTONE_ORE);
        basicItem(AMItems.MOONSTONE);
        blockItem(AMItems.MOONSTONE_BLOCK);
        blockItem(AMItems.SUNSTONE_ORE);
        basicItem(AMItems.SUNSTONE);
        blockItem(AMItems.SUNSTONE_BLOCK);
        basicItem(AMItems.ARCANE_COMPOUND);
        basicItem(AMItems.ARCANE_ASH);
        basicItem(AMItems.PURIFIED_VINTEUM_DUST);
        blockItem(AMItems.WITCHWOOD_LOG);
        blockItem(AMItems.WITCHWOOD);
        blockItem(AMItems.STRIPPED_WITCHWOOD_LOG);
        blockItem(AMItems.STRIPPED_WITCHWOOD);
        blockItem(AMItems.WITCHWOOD_LEAVES);
        basicBlockItem(AMItems.WITCHWOOD_SAPLING);
        blockItem(AMItems.WITCHWOOD_PLANKS);
        blockItem(AMItems.WITCHWOOD_SLAB);
        blockItem(AMItems.WITCHWOOD_STAIRS);
        withExistingParent(AMItems.WITCHWOOD_FENCE.getId().getPath(), modLoc("block/witchwood_fence_inventory"));
        blockItem(AMItems.WITCHWOOD_FENCE_GATE);
        basicItem(AMItems.WITCHWOOD_DOOR);
        withExistingParent(AMItems.WITCHWOOD_TRAPDOOR.getId().getPath(), modLoc("block/witchwood_trapdoor_bottom"));
        withExistingParent(AMItems.WITCHWOOD_BUTTON.getId().getPath(), modLoc("block/witchwood_button_inventory"));
        blockItem(AMItems.WITCHWOOD_PRESSURE_PLATE);
        basicItem(AMItems.WITCHWOOD_SIGN);
        basicItem(AMItems.WITCHWOOD_HANGING_SIGN);
        basicBlockItem(AMItems.AUM);
        basicBlockItem(AMItems.CERUBLOSSOM);
        basicBlockItem(AMItems.DESERT_NOVA);
        basicBlockItem(AMItems.TARMA_ROOT);
        basicBlockItem(AMItems.WAKEBLOOM);
    }

    /**
     * Adds a block item model.
     *
     * @param item The block item to add the model for.
     */
    private void blockItem(DeferredItem<? extends BlockItem> item) {
        simpleBlockItem(item.get().getBlock());
    }

    /**
     * Adds a flat item model.
     *
     * @param item The item to add the model for.
     */
    private void basicItem(DeferredItem<?> item) {
        basicItem(item.get());
    }

    /**
     * Adds a flat item model and flat variant item models for an item with variants.
     *
     * @param item     The item to add the models for.
     * @param variants The variants to add models for.
     */
    private void basicItemWithVariants(DeferredItem<?> item, List<? extends ResourceKey<?>> variants) {
        for (ResourceKey<?> variant : variants) {
            ResourceLocation location = variant.location().withPrefix(item.getId().getPath() + "_");
            singleTexture(location.getPath(), mcLoc("item/generated"), "layer0", location.withPrefix("item/"));
        }
    }

    /**
     * Adds a flat block item model.
     *
     * @param item The block item to add the model for.
     */
    private void basicBlockItem(DeferredItem<?> item) {
        withExistingParent(item.getId().getPath(), mcLoc("item/generated")).texture("layer0", modLoc("block/" + item.getId().getPath()));
    }
}
