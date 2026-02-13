package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.data.PlantProvider;
import at.minecraftschurli.arsmagicalegacy.api.plant.HarvestState;
import at.minecraftschurli.arsmagicalegacy.api.plant.TallHarvestState;
import at.minecraftschurli.arsmagicalegacy.plant.BushGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.CropGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.StemGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.TallCropGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.UpwardsGrowthType;
import at.minecraftschurli.arsmagicalegacy.worldgen.BlockStatePropertyMatchTest;
import at.minecraftschurli.arsmagicalegacy.worldgen.CompositeMatchTest;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
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
        builder("bamboo", new UpwardsGrowthType(1, 16), new ItemStack(Items.BAMBOO), new ItemStack(Items.BAMBOO), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.BAMBOO),
            new BlockMatchTest(Blocks.BAMBOO_SAPLING))));
        builder("beetroots", new CropGrowthType(List.of(new HarvestState(
            Blocks.BEETROOTS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3),
            Blocks.BEETROOTS.defaultBlockState()
        ))), new ItemStack(Items.BEETROOT_SEEDS), new ItemStack(Items.BEETROOT), new BlockMatchTest(Blocks.BEETROOTS));
        // TODO 26.1 cactus flower
        builder("cactus", new UpwardsGrowthType(1, 3), new ItemStack(Items.CACTUS), new ItemStack(Items.CACTUS), new BlockMatchTest(Blocks.CACTUS));
        builder("carrots", new CropGrowthType(List.of(new HarvestState(
            Blocks.CARROTS.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7),
            Blocks.CARROTS.defaultBlockState()
        ))), new ItemStack(Items.CARROT), new ItemStack(Items.CARROT), new BlockMatchTest(Blocks.CARROTS));
        //TODO chorus
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
        )), new ItemStack(Items.COCOA_BEANS), new ItemStack(Items.COCOA_BEANS), new BlockMatchTest(Blocks.COCOA));
        //TODO glow berries
        builder("kelp", new UpwardsGrowthType(1, 26, Blocks.KELP.defaultBlockState().setValue(BlockStateProperties.AGE_25, 25)), new ItemStack(Items.KELP), new ItemStack(Items.KELP), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.KELP),
            new BlockMatchTest(Blocks.KELP_PLANT))));
        builder("melon", new StemGrowthType(
            new BlockMatchTest(Blocks.MELON_STEM),
            Blocks.ATTACHED_MELON_STEM,
            Blocks.MELON.defaultBlockState(),
            "age",
            7), new ItemStack(Items.MELON_SEEDS), new ItemStack(Items.MELON), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.MELON_STEM),
            new BlockMatchTest(Blocks.ATTACHED_MELON_STEM),
            new BlockMatchTest(Blocks.MELON))));
        builder("nether_wart", new CropGrowthType(List.of(new HarvestState(
            Blocks.NETHER_WART.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3),
            Blocks.NETHER_WART.defaultBlockState()
        ))), new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART), new BlockMatchTest(Blocks.NETHER_WART));
        builder("pitcher_crop", new TallCropGrowthType(
            List.of(new TallHarvestState(
                Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER).setValue(BlockStateProperties.AGE_4, 4),
                Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER).setValue(BlockStateProperties.AGE_4, 4),
                Blocks.AIR.defaultBlockState(),
                Blocks.AIR.defaultBlockState())),
            new BlockStatePropertyMatchTest(Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), List.of(BlockStateProperties.AGE_4)),
            new BlockStatePropertyMatchTest(Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), List.of(BlockStateProperties.AGE_4))
        ), ItemStack.EMPTY, ItemStack.EMPTY, new BlockMatchTest(Blocks.PITCHER_CROP));
        builder("potatoes", new CropGrowthType(List.of(new HarvestState(
            Blocks.POTATOES.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7),
            Blocks.POTATOES.defaultBlockState()
        ))), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new BlockMatchTest(Blocks.POTATOES));
        builder("pumpkin", new StemGrowthType(
            new BlockMatchTest(Blocks.PUMPKIN_STEM),
            Blocks.ATTACHED_PUMPKIN_STEM,
            Blocks.PUMPKIN.defaultBlockState(),
            "age",
            7), new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(Items.PUMPKIN), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.PUMPKIN_STEM),
            new BlockMatchTest(Blocks.ATTACHED_PUMPKIN_STEM),
            new BlockMatchTest(Blocks.PUMPKIN))));
        builder("sugar_cane", new UpwardsGrowthType(1, 3), new ItemStack(Items.SUGAR_CANE), new ItemStack(Items.SUGAR_CANE), new BlockMatchTest(Blocks.SUGAR_CANE));
        builder("sweet_berry_bush", new BushGrowthType(List.of(
            Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2),
            Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3)
        )), new ItemStack(Items.SWEET_BERRIES), new ItemStack(Items.SWEET_BERRIES), new BlockMatchTest(Blocks.SWEET_BERRY_BUSH));
        builder("torchflower", new CropGrowthType(List.of(new HarvestState(
            Blocks.TORCHFLOWER.defaultBlockState(),
            Blocks.AIR.defaultBlockState()
        ))), new ItemStack(Items.TORCHFLOWER_SEEDS), new ItemStack(Items.TORCHFLOWER), new BlockMatchTest(Blocks.TORCHFLOWER_CROP));
        //TODO vines
        builder("wheat", new CropGrowthType(List.of(new HarvestState(
            Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7),
            Blocks.WHEAT.defaultBlockState()
        ))), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT), new BlockMatchTest(Blocks.WHEAT));
    }
}
