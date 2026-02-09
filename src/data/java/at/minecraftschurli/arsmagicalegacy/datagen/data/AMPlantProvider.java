package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.data.PlantProvider;
import at.minecraftschurli.arsmagicalegacy.plant.BushGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.CropGrowthType;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.concurrent.CompletableFuture;

public final class AMPlantProvider extends PlantProvider {
    public AMPlantProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        //TODO bamboo
        builder("beetroots", new CropGrowthType(), new ItemStack(Items.BEETROOT_SEEDS), new ItemStack(Items.BEETROOT), new BlockMatchTest(Blocks.BEETROOTS))
            .harvest(Blocks.BEETROOTS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), Blocks.BEETROOTS.defaultBlockState());
        //TODO cactus (26.1 cactus flower)
        builder("carrots", new CropGrowthType(), new ItemStack(Items.CARROT), new ItemStack(Items.CARROT), new BlockMatchTest(Blocks.CARROTS))
            .harvest(Blocks.CARROTS.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7), Blocks.CARROTS.defaultBlockState());
        //TODO chorus
        builder("cocoa", new CropGrowthType(), new ItemStack(Items.COCOA_BEANS), new ItemStack(Items.COCOA_BEANS), new BlockMatchTest(Blocks.COCOA))
            .harvest(Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(BlockStateProperties.AGE_2, 2), Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(BlockStateProperties.AGE_2, 0))
            .harvest(Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST).setValue(BlockStateProperties.AGE_2, 2), Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST).setValue(BlockStateProperties.AGE_2, 0))
            .harvest(Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).setValue(BlockStateProperties.AGE_2, 2), Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).setValue(BlockStateProperties.AGE_2, 0))
            .harvest(Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).setValue(BlockStateProperties.AGE_2, 2), Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).setValue(BlockStateProperties.AGE_2, 0));
        //TODO glow berries
        //TODO kelp
        //TODO melon
        //TODO pitcher plant
        builder("potatoes", new CropGrowthType(), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new BlockMatchTest(Blocks.POTATOES))
            .harvest(Blocks.POTATOES.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7), Blocks.POTATOES.defaultBlockState());
        //TODO pumpkin
        builder("sweet_berry_bush", new BushGrowthType(), new ItemStack(Items.SWEET_BERRIES), new ItemStack(Items.SWEET_BERRIES), new BlockMatchTest(Blocks.SWEET_BERRY_BUSH))
            .harvest(Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1))
            .harvest(Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1));
        builder("torchflower", new CropGrowthType(), new ItemStack(Items.TORCHFLOWER_SEEDS), new ItemStack(Items.TORCHFLOWER), new BlockMatchTest(Blocks.TORCHFLOWER_CROP))
            .harvest(Blocks.TORCHFLOWER.defaultBlockState(), Blocks.AIR.defaultBlockState());
        //TODO vines
        builder("wheat", new CropGrowthType(), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT), new BlockMatchTest(Blocks.WHEAT))
            .harvest(Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7), Blocks.WHEAT.defaultBlockState());
    }
}
