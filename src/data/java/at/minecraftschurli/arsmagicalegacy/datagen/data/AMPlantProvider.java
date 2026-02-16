package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.data.PlantProvider;
import at.minecraftschurli.arsmagicalegacy.api.plant.HarvestState;
import at.minecraftschurli.arsmagicalegacy.api.plant.TallHarvestState;
import at.minecraftschurli.arsmagicalegacy.plant.BushGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.ChorusGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.CropGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.HangingBushGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.HangingGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.StemGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.TallCropGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.UpwardsGrowthType;
import at.minecraftschurli.arsmagicalegacy.worldgen.BlockStatePropertyMatchTest;
import at.minecraftschurli.arsmagicalegacy.worldgen.CompositeMatchTest;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class AMPlantProvider extends PlantProvider {
    public AMPlantProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        builder("bamboo", new UpwardsGrowthType(1, 16, Blocks.BAMBOO, Blocks.BAMBOO), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.BAMBOO),
            new BlockMatchTest(Blocks.BAMBOO_SAPLING))))
            .seed(Items.BAMBOO)
            .crop(Items.BAMBOO);
        builder("beetroots", new CropGrowthType(List.of(new HarvestState(
            Blocks.BEETROOTS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3),
            Blocks.BEETROOTS.defaultBlockState()
        ))), new BlockMatchTest(Blocks.BEETROOTS))
            .seed(Items.BEETROOT_SEEDS)
            .crop(Items.BEETROOT);
        // TODO 26.1 cactus flower
        builder("cactus", new UpwardsGrowthType(1, 3, Blocks.CACTUS, Blocks.CACTUS), new BlockMatchTest(Blocks.CACTUS))
            .seed(Items.CACTUS)
            .crop(Items.CACTUS);
        builder("carrots", new CropGrowthType(List.of(new HarvestState(
            Blocks.CARROTS.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7),
            Blocks.CARROTS.defaultBlockState()
        ))), new BlockMatchTest(Blocks.CARROTS))
            .seed(Items.CARROT)
            .crop(Items.CARROT);
        //TODO chorus
        builder("chorus", new ChorusGrowthType(), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.CHORUS_FLOWER),
            new BlockMatchTest(Blocks.CHORUS_PLANT))))
            .seed(Items.CHORUS_FLOWER)
            .crop(Items.CHORUS_FRUIT);
        builder("cocoa", new CropGrowthType(List.of(
            new HarvestState(
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(BlockStateProperties.AGE_2, 2),
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)),
            new HarvestState(
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST).setValue(BlockStateProperties.AGE_2, 2),
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)),
            new HarvestState(
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).setValue(BlockStateProperties.AGE_2, 2),
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)),
            new HarvestState(
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).setValue(BlockStateProperties.AGE_2, 2),
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST))
        )), new BlockMatchTest(Blocks.COCOA))
            .seed(Items.COCOA_BEANS)
            .crop(Items.COCOA_BEANS);
        builder("cave_vines", new HangingBushGrowthType(List.of(
            Blocks.CAVE_VINES_PLANT.defaultBlockState().setValue(BlockStateProperties.BERRIES, true),
            Blocks.CAVE_VINES.defaultBlockState().setValue(BlockStateProperties.BERRIES, true)
        ), 1, 0, Blocks.CAVE_VINES, Blocks.CAVE_VINES_PLANT), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.CAVE_VINES),
            new BlockMatchTest(Blocks.CAVE_VINES_PLANT))))
            .seed(Items.GLOW_BERRIES)
            .crop(Items.GLOW_BERRIES);
        builder("kelp", new UpwardsGrowthType(1, 26, Blocks.KELP, Blocks.KELP_PLANT), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.KELP),
            new BlockMatchTest(Blocks.KELP_PLANT))))
            .seed(Items.KELP)
            .crop(Items.KELP);
        builder("melon", new StemGrowthType(new BlockMatchTest(Blocks.MELON_STEM), Blocks.ATTACHED_MELON_STEM, Blocks.MELON.defaultBlockState(), "age", 7), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.MELON_STEM),
            new BlockMatchTest(Blocks.ATTACHED_MELON_STEM),
            new BlockMatchTest(Blocks.MELON))))
            .seed(Items.MELON_SEEDS)
            .crop(Items.MELON_SLICE);
        builder("nether_wart", new CropGrowthType(List.of(new HarvestState(Blocks.NETHER_WART.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), Blocks.NETHER_WART.defaultBlockState()))), new BlockMatchTest(Blocks.NETHER_WART))
            .seed(Items.NETHER_WART)
            .crop(Items.NETHER_WART);
        builder("pitcher_crop", new TallCropGrowthType(List.of(new TallHarvestState(
            Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER).setValue(BlockStateProperties.AGE_4, 4),
            Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER).setValue(BlockStateProperties.AGE_4, 4),
            Blocks.AIR.defaultBlockState(),
            Blocks.AIR.defaultBlockState())),
            new BlockStatePropertyMatchTest(Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), List.of(BlockStateProperties.AGE_4)),
            new BlockStatePropertyMatchTest(Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), List.of(BlockStateProperties.AGE_4))
        ), new BlockMatchTest(Blocks.PITCHER_CROP));
        builder("potatoes", new CropGrowthType(List.of(new HarvestState(
            Blocks.POTATOES.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7),
            Blocks.POTATOES.defaultBlockState()
        ))), new BlockMatchTest(Blocks.POTATOES))
            .seed(Items.POTATO)
            .crop(Items.POTATO);
        builder("pumpkin", new StemGrowthType(new BlockMatchTest(Blocks.PUMPKIN_STEM), Blocks.ATTACHED_PUMPKIN_STEM, Blocks.PUMPKIN.defaultBlockState(), "age", 7), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.PUMPKIN_STEM),
            new BlockMatchTest(Blocks.ATTACHED_PUMPKIN_STEM),
            new BlockMatchTest(Blocks.PUMPKIN))))
            .seed(Items.PUMPKIN_SEEDS)
            .crop(Items.PUMPKIN);
        builder("sugar_cane", new UpwardsGrowthType(1, 3, Blocks.SUGAR_CANE, Blocks.SUGAR_CANE), new BlockMatchTest(Blocks.SUGAR_CANE))
            .seed(Items.SUGAR_CANE)
            .crop(Items.SUGAR_CANE);
        builder("sweet_berry_bush", new BushGrowthType(List.of(
            Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2),
            Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3)
        )), new BlockMatchTest(Blocks.SWEET_BERRY_BUSH))
            .seed(Items.SWEET_BERRIES)
            .crop(Items.SWEET_BERRIES);
        builder("torchflower", new CropGrowthType(List.of(new HarvestState(
            Blocks.TORCHFLOWER.defaultBlockState(),
            Blocks.AIR.defaultBlockState()
        ))), new BlockMatchTest(Blocks.TORCHFLOWER_CROP));
        builder("vine", new HangingGrowthType(1, 0, Blocks.VINE, Blocks.VINE), new BlockMatchTest(Blocks.VINE))
            .seed(Items.VINE)
            .crop(Items.VINE)
            .tool(Items.SHEARS);
        builder("wheat", new CropGrowthType(List.of(new HarvestState(
            Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7),
            Blocks.WHEAT.defaultBlockState()
        ))), new BlockMatchTest(Blocks.WHEAT))
            .seed(Items.WHEAT_SEEDS)
            .crop(Items.WHEAT);
    }
}
