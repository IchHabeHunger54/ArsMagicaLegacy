package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
        logBlock(AMBlocks.WITCHWOOD_LOG.get());
        woodBlock(AMBlocks.WITCHWOOD, AMBlocks.WITCHWOOD_LOG);
        logBlock(AMBlocks.STRIPPED_WITCHWOOD_LOG.get());
        woodBlock(AMBlocks.STRIPPED_WITCHWOOD, AMBlocks.STRIPPED_WITCHWOOD_LOG);
        simpleBlock(AMBlocks.WITCHWOOD_LEAVES);
        crossBlock(AMBlocks.WITCHWOOD_SAPLING);
        flowerPotBlock(AMBlocks.POTTED_WITCHWOOD_SAPLING, AMBlocks.WITCHWOOD_SAPLING);
        simpleBlock(AMBlocks.WITCHWOOD_PLANKS);
        ResourceLocation planksTexture = blockTexture(AMBlocks.WITCHWOOD_PLANKS.get());
        slabBlock(AMBlocks.WITCHWOOD_SLAB.get(), cubeAll(AMBlocks.WITCHWOOD_PLANKS.get()).getLocation(), planksTexture);
        stairsBlock(AMBlocks.WITCHWOOD_STAIRS.get(), planksTexture);
        fenceBlock(AMBlocks.WITCHWOOD_FENCE.get(), planksTexture);
        models().fenceInventory(AMBlocks.WITCHWOOD_FENCE.getId().getPath() + "_inventory", planksTexture);
        fenceGateBlock(AMBlocks.WITCHWOOD_FENCE_GATE.get(), planksTexture);
        doorBlockWithRenderType(AMBlocks.WITCHWOOD_DOOR.get(), "witchwood", modLoc("block/witchwood_door_bottom"), modLoc("block/witchwood_door_top"), "cutout");
        trapdoorBlockWithRenderType(AMBlocks.WITCHWOOD_TRAPDOOR.get(), planksTexture, true, "cutout");
        buttonBlock(AMBlocks.WITCHWOOD_BUTTON.get(), planksTexture);
        models().withExistingParent(AMBlocks.WITCHWOOD_BUTTON.getId().getPath() + "_inventory", "block/button_inventory").texture("texture", planksTexture);
        pressurePlateBlock(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get(), planksTexture);
        signBlock(AMBlocks.WITCHWOOD_SIGN.get(), AMBlocks.WITCHWOOD_WALL_SIGN.get(), planksTexture);
        hangingSignBlock(AMBlocks.WITCHWOOD_HANGING_SIGN.get(), AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get(), blockTexture(AMBlocks.STRIPPED_WITCHWOOD_LOG.get()));
        crossBlock(AMBlocks.AUM);
        flowerPotBlock(AMBlocks.POTTED_AUM, AMBlocks.AUM);
        crossBlock(AMBlocks.CERUBLOSSOM);
        flowerPotBlock(AMBlocks.POTTED_CERUBLOSSOM, AMBlocks.CERUBLOSSOM);
        crossBlock(AMBlocks.DESERT_NOVA);
        flowerPotBlock(AMBlocks.POTTED_DESERT_NOVA, AMBlocks.DESERT_NOVA);
        crossBlock(AMBlocks.TARMA_ROOT);
        flowerPotBlock(AMBlocks.POTTED_TARMA_ROOT, AMBlocks.TARMA_ROOT);
        crossBlock(AMBlocks.WAKEBLOOM);
        flowerPotBlock(AMBlocks.POTTED_WAKEBLOOM, AMBlocks.WAKEBLOOM);
        torchBlock(AMBlocks.VINTEUM_TORCH, AMBlocks.VINTEUM_WALL_TORCH);
    }

    /**
     * Adds a simple block model that uses its block id as the texture name on all six sides.
     *
     * @param block The block to generate the model for.
     */
    private void simpleBlock(DeferredBlock<?> block) {
        simpleBlock(block.get());
    }

    /**
     * Adds a block model that uses its block id as the texture name on all six sides. Rotates accordingly.
     *
     * @param block The block to generate the model for.
     * @param log   The corresponding log block.
     */
    private void woodBlock(DeferredBlock<? extends RotatedPillarBlock> block, DeferredBlock<?> log) {
        axisBlock(block.get(),
                models().cubeColumn(block.getId().getPath(), blockTexture(log.get()), blockTexture(log.get())),
                models().cubeColumnHorizontal(block.getId().getPath(), blockTexture(log.get()), blockTexture(log.get())));
    }

    /**
     * Adds a cross block model, as seen on flowers and saplings. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void crossBlock(DeferredBlock<?> block) {
        simpleBlock(block.get(), models().cross(block.getId().getPath(), blockTexture(block.get())).renderType("cutout"));
    }

    /**
     * Adds a flower pot model with a plant inside.
     *
     * @param pot   The flower pot block to generate the model for.
     * @param plant The plant to place inside the flower pot.
     */
    private void flowerPotBlock(DeferredBlock<?> pot, DeferredBlock<?> plant) {
        simpleBlock(pot.get(), models().withExistingParent(pot.getId().getPath(), "block/flower_pot_cross").texture("plant", blockTexture(plant.get())).renderType("cutout"));
    }

    /**
     * Adds a torch/wall torch model. Uses the normal torch block id as the texture name.
     *
     * @param torch     The torch block to generate the model for.
     * @param wallTorch The wall torch block to generate the model for.
     */
    private void torchBlock(DeferredBlock<?> torch, DeferredBlock<?> wallTorch) {
        ModelFile file = models().withExistingParent(torch.getId().getPath(), "block/template_torch").texture("torch", modLoc("block/" + torch.getId().getPath())).renderType("cutout");
        ModelFile wallFile = models().withExistingParent(wallTorch.getId().getPath(), "block/template_torch_wall").texture("torch", modLoc("block/" + torch.getId().getPath())).renderType("cutout");
        getVariantBuilder(torch.get()).partialState().setModels(ConfiguredModel.builder().modelFile(file).build());
        getVariantBuilder(wallTorch.get()).forAllStates(state -> switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case EAST -> ConfiguredModel.builder().modelFile(wallFile).build();
            case SOUTH -> ConfiguredModel.builder().modelFile(wallFile).rotationY(90).build();
            case WEST -> ConfiguredModel.builder().modelFile(wallFile).rotationY(180).build();
            case NORTH -> ConfiguredModel.builder().modelFile(wallFile).rotationY(270).build();
            default -> new ConfiguredModel[0];
        });
    }
}
