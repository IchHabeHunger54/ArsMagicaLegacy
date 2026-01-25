package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.block.AltarCoreBlock;
import at.minecraftschurli.arsmagicalegacy.block.CelestialPrismBlock;
import at.minecraftschurli.arsmagicalegacy.block.ObeliskBlock;
import at.minecraftschurli.arsmagicalegacy.block.WizardsChalkBlock;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.loaders.ObjModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

public final class AMBlockStateProvider extends BlockStateProvider {
    private static final ToIntFunction<Direction> ROTATE_90 = direction -> switch (direction) {
        case SOUTH -> 90;
        case WEST -> 180;
        case NORTH -> 270;
        default -> 0;
    };
    private static final ToIntFunction<Direction> ROTATE_180 = direction -> switch (direction) {
        case WEST -> 90;
        case NORTH -> 180;
        case EAST -> 270;
        default -> 0;
    };

    public AMBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ArsMagicaApi.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(AMBlocks.SPELL_LIGHT.get(), models().getExistingFile(mcLoc("block/air")));
        simpleBlock(AMBlocks.LIQUID_ETHERIUM.get(), particleModel(AMBlocks.LIQUID_ETHERIUM.getId().getPath(), AMBlocks.LIQUID_ETHERIUM.getId().withPrefix("block/").withSuffix("_still")));
        simpleBlock(AMBlocks.LIQUID_ETHERIUM_CAULDRON.get(), models().withExistingParent("liquid_etherium_cauldron", mcLoc("block/template_cauldron_full"))
            .texture("bottom", mcLoc("block/cauldron_bottom"))
            .texture("content", modLoc("block/liquid_etherium_still"))
            .texture("inside", mcLoc("block/cauldron_inner"))
            .texture("particle", mcLoc("block/cauldron_side"))
            .texture("side", mcLoc("block/cauldron_side"))
            .texture("top", mcLoc("block/cauldron_top")));
        horizontalBlock(AMBlocks.OCCULUS.get(), models().getExistingFile(modLoc("block/occulus")));
        getVariantBuilder(AMBlocks.ALTAR_CORE.get())
            .partialState().with(AltarCoreBlock.FORMED, false).modelForState().modelFile(cubeAll(AMBlocks.ALTAR_CORE.get())).addModel()
            .partialState().with(AltarCoreBlock.FORMED, true).modelForState().modelFile(models().getExistingFile(modLoc("block/altar_core_overlay"))).addModel();
        simpleBlock(AMBlocks.MAGIC_WALL.get(), modelBuilder(models().cubeAll(AMBlocks.MAGIC_WALL.getId().getPath(), AMBlocks.MAGIC_WALL.getId().withPrefix("block/")).renderType("translucent")).build());
        ResourceLocation obelisk = modLoc("block/obelisk.obj");
        ResourceLocation stoneBricks = mcLoc("block/stone_bricks");
        rotatedBlock(AMBlocks.OBELISK, List.of(
            Pair.of(state -> state.with(ObeliskBlock.PART, ObeliskBlock.Part.LOWER).with(ObeliskBlock.LIT, true), modelBuilder(objModel("obelisk_lit", obelisk).texture("tex", modLoc("block/obelisk_lit")).texture("particle", stoneBricks))),
            Pair.of(state -> state.with(ObeliskBlock.PART, ObeliskBlock.Part.LOWER).with(ObeliskBlock.LIT, false), modelBuilder(objModel("obelisk", obelisk).texture("tex", modLoc("block/obelisk")).texture("particle", stoneBricks))),
            Pair.of(state -> state.with(ObeliskBlock.PART, ObeliskBlock.Part.MIDDLE), modelBuilder(particleModel("obelisk_particle", stoneBricks))),
            Pair.of(state -> state.with(ObeliskBlock.PART, ObeliskBlock.Part.UPPER), modelBuilder(particleModel("obelisk_particle", stoneBricks)))
        ), ROTATE_180);
        getVariantBuilder(AMBlocks.CELESTIAL_PRISM.get())
            .partialState().with(CelestialPrismBlock.PART, CelestialPrismBlock.Part.LOWER).setModels(modelBuilder(objModel("celestial_prism", modLoc("block/celestial_prism.obj")).texture("tex", modLoc("block/celestial_prism")).texture("particle", modLoc("block/celestial_prism"))).build())
            .partialState().with(CelestialPrismBlock.PART, CelestialPrismBlock.Part.UPPER).setModels(modelBuilder(particleModel("celestial_prism_particle", modLoc("block/celestial_prism"))).build());
        getVariantBuilder(AMBlocks.BLACK_AUREM.get()).partialState().setModels(modelBuilder(particleModel(AMBlocks.BLACK_AUREM.getId().getPath(), modLoc("block/black_aurem"))).build());
        rotatedBlock(AMBlocks.WIZARDS_CHALK, IntStream.range(0, 16)
            .mapToObj(i -> Pair.<UnaryOperator<VariantBlockStateBuilder.PartialBlockstate>, ConfiguredModel.Builder<?>>of(
                state -> state.with(WizardsChalkBlock.VARIANT, i),
                modelBuilder(models().withExistingParent("wizards_chalk_" + i, "block/rail_flat").texture("rail", modLoc("block/wizards_chalk_" + i)).renderType("translucent")))
            )
            .toList(), ROTATE_180);
        getVariantBuilder(AMBlocks.VINTEUM_TORCH.get()).partialState().setModels(modelBuilder(models().withExistingParent("vinteum_torch", "block/template_torch").texture("torch", modLoc("block/vinteum_torch")).renderType("cutout")).build());
        rotatedBlock(AMBlocks.VINTEUM_WALL_TORCH, List.of(Pair.of(UnaryOperator.identity(), modelBuilder(models().withExistingParent("vinteum_wall_torch", "block/template_torch_wall").texture("torch", modLoc("block/vinteum_torch")).renderType("cutout")))), ROTATE_90);
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

    private BlockModelBuilder particleModel(String name, ResourceLocation particle) {
        return models().getBuilder(name).texture("particle", particle);
    }

    private BlockModelBuilder objModel(String name, ResourceLocation location) {
        return models().getBuilder(name)
            .customLoader(ObjModelBuilder::begin)
            .modelLocation(location.withPrefix("models/"))
            .emissiveAmbient(false)
            .automaticCulling(false)
            .shadeQuads(false)
            .end();
    }

    private ConfiguredModel.Builder<?> modelBuilder(ModelFile file) {
        return ConfiguredModel.builder().modelFile(file);
    }

    private void rotatedBlock(DeferredBlock<?> block, List<Pair<UnaryOperator<VariantBlockStateBuilder.PartialBlockstate>, ConfiguredModel.Builder<?>>> list, ToIntFunction<Direction> rotation) {
        VariantBlockStateBuilder builder = getVariantBuilder(block.get());
        for (Direction direction : Direction.values()) {
            if (direction.getAxis().isVertical()) continue;
            for (Pair<UnaryOperator<VariantBlockStateBuilder.PartialBlockstate>, ConfiguredModel.Builder<?>> pair : list) {
                pair.getFirst().apply(builder.partialState().with(BlockStateProperties.HORIZONTAL_FACING, direction)).setModels(pair.getSecond().rotationY(rotation.applyAsInt(direction)).build());
            }
        }
    }
}
