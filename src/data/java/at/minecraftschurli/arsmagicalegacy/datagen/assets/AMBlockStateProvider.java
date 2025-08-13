package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class AMBlockStateProvider extends BlockStateProvider {
    public AMBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ArsMagicaApi.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(AMBlocks.CHIMERITE_ORE);
        simpleBlock(AMBlocks.DEEPSLATE_CHIMERITE_ORE);
        simpleBlock(AMBlocks.CHIMERITE_BLOCK);
        simpleBlock(AMBlocks.TOPAZ_ORE);
        simpleBlock(AMBlocks.DEEPSLATE_TOPAZ_ORE);
        simpleBlock(AMBlocks.TOPAZ_BLOCK);
        simpleBlock(AMBlocks.VINTEUM_ORE);
        simpleBlock(AMBlocks.DEEPSLATE_VINTEUM_ORE);
        simpleBlock(AMBlocks.VINTEUM_BLOCK);
        simpleBlock(AMBlocks.MOONSTONE_ORE);
        simpleBlock(AMBlocks.DEEPSLATE_MOONSTONE_ORE);
        simpleBlock(AMBlocks.MOONSTONE_BLOCK);
        simpleBlock(AMBlocks.SUNSTONE_ORE);
        simpleBlock(AMBlocks.SUNSTONE_BLOCK);
    }

    private void simpleBlock(DeferredBlock<?> block) {
        simpleBlock(block.get());
    }
}
