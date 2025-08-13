package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public final class AMItemModelProvider extends ItemModelProvider {
    public AMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ArsMagicaApi.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleBlockItem(AMItems.CHIMERITE_ORE);
        simpleBlockItem(AMItems.DEEPSLATE_CHIMERITE_ORE);
        basicItem(AMItems.CHIMERITE);
        simpleBlockItem(AMItems.CHIMERITE_BLOCK);
        simpleBlockItem(AMItems.TOPAZ_ORE);
        simpleBlockItem(AMItems.DEEPSLATE_TOPAZ_ORE);
        basicItem(AMItems.TOPAZ);
        simpleBlockItem(AMItems.TOPAZ_BLOCK);
        simpleBlockItem(AMItems.VINTEUM_ORE);
        simpleBlockItem(AMItems.DEEPSLATE_VINTEUM_ORE);
        basicItem(AMItems.VINTEUM_DUST);
        simpleBlockItem(AMItems.VINTEUM_BLOCK);
        simpleBlockItem(AMItems.MOONSTONE_ORE);
        simpleBlockItem(AMItems.DEEPSLATE_MOONSTONE_ORE);
        basicItem(AMItems.MOONSTONE);
        simpleBlockItem(AMItems.MOONSTONE_BLOCK);
        simpleBlockItem(AMItems.SUNSTONE_ORE);
        basicItem(AMItems.SUNSTONE);
        simpleBlockItem(AMItems.SUNSTONE_BLOCK);
    }

    private void simpleBlockItem(DeferredItem<? extends BlockItem> item) {
        simpleBlockItem(item.get().getBlock());
    }

    private void basicItem(DeferredItem<?> item) {
        basicItem(item.get());
    }
}
