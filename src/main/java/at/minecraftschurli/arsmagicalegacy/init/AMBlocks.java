package at.minecraftschurli.arsmagicalegacy.init;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface AMBlocks {
    // @formatter:off
    DeferredBlock<DropExperienceBlock> CHIMERITE_ORE           = register("chimerite_ore",           p -> new DropExperienceBlock(UniformInt.of(0, 2), p), p -> p.requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock> DEEPSLATE_CHIMERITE_ORE = register("deepslate_chimerite_ore", p -> new DropExperienceBlock(UniformInt.of(0, 2), p), p -> p.mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 3f).sound(SoundType.DEEPSLATE));
    DeferredBlock<Block>               CHIMERITE_BLOCK         = register("chimerite_block",         p -> p.mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock> TOPAZ_ORE               = register("topaz_ore",               p -> new DropExperienceBlock(UniformInt.of(0, 2), p), p -> p.requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock> DEEPSLATE_TOPAZ_ORE     = register("deepslate_topaz_ore",     p -> new DropExperienceBlock(UniformInt.of(0, 2), p), p -> p.mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 3f).sound(SoundType.DEEPSLATE));
    DeferredBlock<Block>               TOPAZ_BLOCK             = register("topaz_block",             p -> p.mapColor(MapColor.DIAMOND).requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock> VINTEUM_ORE             = register("vinteum_ore",             p -> new DropExperienceBlock(UniformInt.of(1, 3), p), p -> p.requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock> DEEPSLATE_VINTEUM_ORE   = register("deepslate_vinteum_ore",   p -> new DropExperienceBlock(UniformInt.of(1, 3), p), p -> p.mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 3f).sound(SoundType.DEEPSLATE));
    DeferredBlock<Block>               VINTEUM_BLOCK           = register("vinteum_block",           p -> p.mapColor(MapColor.LAPIS).requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock> MOONSTONE_ORE           = register("moonstone_ore",           p -> new DropExperienceBlock(UniformInt.of(3, 7), p), p -> p.requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock> DEEPSLATE_MOONSTONE_ORE = register("deepslate_moonstone_ore", p -> new DropExperienceBlock(UniformInt.of(3, 7), p), p -> p.mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 3f).sound(SoundType.DEEPSLATE));
    DeferredBlock<Block>               MOONSTONE_BLOCK         = register("moonstone_block",         p -> p.mapColor(MapColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock> SUNSTONE_ORE            = register("sunstone_ore",            p -> new DropExperienceBlock(UniformInt.of(0, 1), p), p -> p.mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50f, 1200f));
    DeferredBlock<Block>               SUNSTONE_BLOCK          = register("sunstone_block",          p -> p.mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(3f, 3f));
    // @formatter:on

    static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, B> function, UnaryOperator<BlockBehaviour.Properties> properties) {
        return AMRegistries.BLOCKS.registerBlock(name, function, properties.apply(BlockBehaviour.Properties.of()));
    }

    static DeferredBlock<Block> register(String name, UnaryOperator<BlockBehaviour.Properties> properties) {
        return AMRegistries.BLOCKS.registerSimpleBlock(name, properties.apply(BlockBehaviour.Properties.of()));
    }

    static void init() {
    }
}
