package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.HarvestState;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.TallHarvestState;
import at.minecraftschurli.mods.arsmagicalegacy.plant.BushGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.ChorusGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.CropGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.HangingBushGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.HangingGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.StemGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.TallCropGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.UpwardsGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.worldgen.BlockStatePropertyMatchTest;
import at.minecraftschurli.mods.arsmagicalegacy.worldgen.CompositeMatchTest;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;
import java.util.Optional;

public final class AMPlantProvider {
    public static void addPlants(BootstrapContext<Plant> bootstrap) {
        add(bootstrap, "bamboo", new UpwardsGrowthType(1, 16, Blocks.BAMBOO), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.BAMBOO),
            new BlockMatchTest(Blocks.BAMBOO_SAPLING)
        )), Items.BAMBOO, Items.BAMBOO);
        add(bootstrap, "beetroots", new CropGrowthType(List.of(new HarvestState(
            Blocks.BEETROOTS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3),
            Blocks.BEETROOTS.defaultBlockState()
        ))), new BlockMatchTest(Blocks.BEETROOTS), Items.BEETROOT_SEEDS, Items.BEETROOT);
        add(bootstrap, "cactus", new UpwardsGrowthType(1, 4, Blocks.CACTUS_FLOWER, Blocks.CACTUS, false), new BlockMatchTest(Blocks.CACTUS), Items.CACTUS, Items.CACTUS);
        add(bootstrap, "carrots", new CropGrowthType(List.of(new HarvestState(
            Blocks.CARROTS.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7),
            Blocks.CARROTS.defaultBlockState()
        ))), new BlockMatchTest(Blocks.CARROTS), Items.CARROT, Items.CARROT);
        add(bootstrap, "chorus", new ChorusGrowthType(), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.CHORUS_FLOWER),
            new BlockMatchTest(Blocks.CHORUS_PLANT)
        )), Items.CHORUS_FLOWER, Items.CHORUS_FRUIT);
        add(bootstrap, "cocoa", new CropGrowthType(List.of(
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
        )), new BlockMatchTest(Blocks.COCOA), Items.COCOA_BEANS, Items.COCOA_BEANS);
        add(bootstrap, "cave_vines", new HangingBushGrowthType(List.of(
            Blocks.CAVE_VINES_PLANT.defaultBlockState().setValue(BlockStateProperties.BERRIES, true),
            Blocks.CAVE_VINES.defaultBlockState().setValue(BlockStateProperties.BERRIES, true)
        ), 1, 0, Blocks.CAVE_VINES, Blocks.CAVE_VINES_PLANT), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.CAVE_VINES),
            new BlockMatchTest(Blocks.CAVE_VINES_PLANT)
        )), Items.GLOW_BERRIES, Items.GLOW_BERRIES);
        add(bootstrap, "kelp", new UpwardsGrowthType(1, 26, Blocks.KELP, Blocks.KELP_PLANT), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.KELP),
            new BlockMatchTest(Blocks.KELP_PLANT)
        )), Items.KELP, Items.KELP);
        add(bootstrap, "melon", new StemGrowthType(new BlockMatchTest(Blocks.MELON_STEM), Blocks.ATTACHED_MELON_STEM, Blocks.MELON.defaultBlockState(), "age", 7), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.MELON_STEM),
            new BlockMatchTest(Blocks.ATTACHED_MELON_STEM),
            new BlockMatchTest(Blocks.MELON)
        )), Items.MELON_SEEDS, Items.MELON_SLICE);
        add(bootstrap, "nether_wart", new CropGrowthType(List.of(
            new HarvestState(Blocks.NETHER_WART.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), Blocks.NETHER_WART.defaultBlockState())
        )), new BlockMatchTest(Blocks.NETHER_WART), Items.NETHER_WART, Items.NETHER_WART);
        add(bootstrap, "pitcher_crop", new TallCropGrowthType(List.of(new TallHarvestState(
            Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER).setValue(BlockStateProperties.AGE_4, 4),
            Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER).setValue(BlockStateProperties.AGE_4, 4),
            Blocks.AIR.defaultBlockState(),
            Blocks.AIR.defaultBlockState())),
            new BlockStatePropertyMatchTest(Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), List.of(BlockStateProperties.AGE_4)),
            new BlockStatePropertyMatchTest(Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), List.of(BlockStateProperties.AGE_4))
        ), new BlockMatchTest(Blocks.PITCHER_CROP));
        add(bootstrap, "potatoes", new CropGrowthType(List.of(new HarvestState(
            Blocks.POTATOES.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7),
            Blocks.POTATOES.defaultBlockState()
        ))), new BlockMatchTest(Blocks.POTATOES), Items.POTATO, Items.POTATO);
        add(bootstrap, "pumpkin", new StemGrowthType(new BlockMatchTest(Blocks.PUMPKIN_STEM), Blocks.ATTACHED_PUMPKIN_STEM, Blocks.PUMPKIN.defaultBlockState(), "age", 7), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.PUMPKIN_STEM),
            new BlockMatchTest(Blocks.ATTACHED_PUMPKIN_STEM),
            new BlockMatchTest(Blocks.PUMPKIN)
        )), Items.PUMPKIN_SEEDS, Items.PUMPKIN);
        add(bootstrap, "sugar_cane", new UpwardsGrowthType(1, 3, Blocks.SUGAR_CANE), new BlockMatchTest(Blocks.SUGAR_CANE), Items.SUGAR_CANE, Items.SUGAR_CANE);
        add(bootstrap, "sweet_berry_bush", new BushGrowthType(List.of(
            Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2),
            Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3)
        )), new BlockMatchTest(Blocks.SWEET_BERRY_BUSH),
            Items.SWEET_BERRIES,
            Items.SWEET_BERRIES);
        add(bootstrap, "torchflower", new CropGrowthType(List.of(new HarvestState(
            Blocks.TORCHFLOWER.defaultBlockState(),
            Blocks.AIR.defaultBlockState()
        ))), new BlockMatchTest(Blocks.TORCHFLOWER_CROP));
        add(bootstrap, "vine", new HangingGrowthType(1, 0, Blocks.VINE, Blocks.VINE), new BlockMatchTest(Blocks.VINE),
            Items.VINE,
            Items.VINE,
            Items.SHEARS);
        add(bootstrap, "wheat", new CropGrowthType(List.of(new HarvestState(
            Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7),
            Blocks.WHEAT.defaultBlockState()
        ))), new BlockMatchTest(Blocks.WHEAT),
            Items.WHEAT_SEEDS,
            Items.WHEAT);
    }

    private static void add(BootstrapContext<Plant> bootstrap, String name, GrowthType growthType, RuleTest allStates) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.PLANT, ArsMagicaApi.id(name)), new Plant(growthType, allStates, Optional.empty(), Optional.empty(), Optional.empty()));
    }

    private static void add(BootstrapContext<Plant> bootstrap, String name, GrowthType growthType, RuleTest allStates, Item seed, Item crop) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.PLANT, ArsMagicaApi.id(name)), new Plant(growthType, allStates, Optional.of(new ItemStackTemplate(seed)), Optional.of(new ItemStackTemplate(crop)), Optional.empty()));
    }

    @SuppressWarnings("SameParameterValue")
    private static void add(BootstrapContext<Plant> bootstrap, String name, GrowthType growthType, RuleTest allStates, Item seed, Item crop, Item tool) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.PLANT, ArsMagicaApi.id(name)), new Plant(growthType, allStates, Optional.of(new ItemStackTemplate(seed)), Optional.of(new ItemStackTemplate(crop)), Optional.of(new ItemStackTemplate(tool))));
    }
}
