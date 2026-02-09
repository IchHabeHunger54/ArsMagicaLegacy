package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.data.PlantProvider;
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
        builder("beetroots", new CropGrowthType(), new ItemStack(Items.BEETROOT_SEEDS), new ItemStack(Items.BEETROOT), new BlockMatchTest(Blocks.FARMLAND), new BlockMatchTest(Blocks.BEETROOTS), Direction.DOWN)
            .harvest(Blocks.BEETROOTS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), Blocks.BEETROOTS.defaultBlockState());
        //TODO cactus (26.1 cactus flower)
        builder("carrots", new CropGrowthType(), new ItemStack(Items.CARROT), new ItemStack(Items.CARROT), new BlockMatchTest(Blocks.FARMLAND), new BlockMatchTest(Blocks.CARROTS), Direction.DOWN)
            .harvest(Blocks.CARROTS.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7), Blocks.CARROTS.defaultBlockState());
        //TODO chorus
        //TODO cocoa beans
        //TODO glow berries
        //TODO kelp
        //TODO melon
        //TODO pitcher plant
        builder("potatoes", new CropGrowthType(), new ItemStack(Items.POTATO), new ItemStack(Items.POTATO), new BlockMatchTest(Blocks.FARMLAND), new BlockMatchTest(Blocks.POTATOES), Direction.DOWN)
            .harvest(Blocks.POTATOES.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7), Blocks.POTATOES.defaultBlockState());
        //TODO pumpkin
        //TODO sweet berries
        builder("torchflower", new CropGrowthType(), new ItemStack(Items.TORCHFLOWER_SEEDS), new ItemStack(Items.TORCHFLOWER), new BlockMatchTest(Blocks.FARMLAND), new BlockMatchTest(Blocks.TORCHFLOWER_CROP))
            .harvest(Blocks.TORCHFLOWER.defaultBlockState(), Blocks.AIR.defaultBlockState());
        //TODO vines
        builder("wheat", new CropGrowthType(), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT), new BlockMatchTest(Blocks.FARMLAND), new BlockMatchTest(Blocks.WHEAT), Direction.DOWN)
            .harvest(Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7), Blocks.WHEAT.defaultBlockState());
    }
}
