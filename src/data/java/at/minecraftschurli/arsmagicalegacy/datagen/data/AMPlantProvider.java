package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.data.PlantProvider;
import at.minecraftschurli.arsmagicalegacy.plant.CropGrowthType;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.concurrent.CompletableFuture;

public class AMPlantProvider extends PlantProvider {
    public AMPlantProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        builder("wheat", new CropGrowthType(), Items.WHEAT_SEEDS, Items.WHEAT, new BlockMatchTest(Blocks.FARMLAND), new BlockMatchTest(Blocks.WHEAT), Direction.DOWN)
            .harvest(Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7), Blocks.WHEAT.defaultBlockState());
        builder("carrots", new CropGrowthType(), Items.CARROT, Items.CARROT, new BlockMatchTest(Blocks.FARMLAND), new BlockMatchTest(Blocks.CARROTS), Direction.DOWN)
            .harvest(Blocks.CARROTS.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7), Blocks.CARROTS.defaultBlockState());
        builder("potatoes", new CropGrowthType(), Items.POTATO, Items.POTATO, new BlockMatchTest(Blocks.FARMLAND), new BlockMatchTest(Blocks.POTATOES), Direction.DOWN)
            .harvest(Blocks.POTATOES.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7), Blocks.POTATOES.defaultBlockState());
        builder("beetroots", new CropGrowthType(), Items.BEETROOT_SEEDS, Items.BEETROOT, new BlockMatchTest(Blocks.FARMLAND), new BlockMatchTest(Blocks.BEETROOTS), Direction.DOWN)
            .harvest(Blocks.BEETROOTS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), Blocks.BEETROOTS.defaultBlockState());
    }
}
