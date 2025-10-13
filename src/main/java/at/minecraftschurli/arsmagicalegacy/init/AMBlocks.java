package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.block.AMFlowerBlock;
import at.minecraftschurli.arsmagicalegacy.block.OcculusBlock;
import at.minecraftschurli.arsmagicalegacy.block.WakebloomBlock;
import at.minecraftschurli.arsmagicalegacy.block.WizardsChalkBlock;
import at.minecraftschurli.arsmagicalegacy.block.altar.AltarCoreBlock;
import at.minecraftschurli.arsmagicalegacy.block.inscriptiontable.InscriptionTableBlock;
import at.minecraftschurli.arsmagicalegacy.block.obelisk.ObeliskBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.BlockFamily;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;
import java.util.function.Supplier;

public interface AMBlocks {
    BlockSetType WITCHWOOD_BLOCK_SET_TYPE = BlockSetType.register(new BlockSetType(ArsMagicaApi.MOD_ID + ":witchwood"));
    WoodType WITCHWOOD_WOOD_TYPE = WoodType.register(new WoodType(ArsMagicaApi.MOD_ID + ":witchwood", WITCHWOOD_BLOCK_SET_TYPE));
    Lazy<BlockFamily> WITCHWOOD_BLOCK_FAMILY = Lazy.of(() -> new BlockFamily.Builder(AMBlocks.WITCHWOOD_PLANKS.get())
        .slab(AMBlocks.WITCHWOOD_SLAB.get())
        .stairs(AMBlocks.WITCHWOOD_STAIRS.get())
        .fence(AMBlocks.WITCHWOOD_FENCE.get())
        .fenceGate(AMBlocks.WITCHWOOD_FENCE_GATE.get())
        .door(AMBlocks.WITCHWOOD_DOOR.get())
        .trapdoor(AMBlocks.WITCHWOOD_TRAPDOOR.get())
        .button(AMBlocks.WITCHWOOD_BUTTON.get())
        .pressurePlate(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get())
        .sign(AMBlocks.WITCHWOOD_SIGN.get(), AMBlocks.WITCHWOOD_WALL_SIGN.get())
        .getFamily());

    // @formatter:off
    DeferredBlock<AirBlock>                SPELL_LIGHT                 = register("spell_light",                 AirBlock::new, copyProperties(Blocks.AIR).lightLevel($ -> 15));
    DeferredBlock<OcculusBlock>            OCCULUS                     = register("occulus",                     OcculusBlock::new, properties().strength(3, 5));
    DeferredBlock<InscriptionTableBlock>   INSCRIPTION_TABLE           = register("inscription_table",           InscriptionTableBlock::new, properties().strength(2).lightLevel($ -> 1).noOcclusion());
    DeferredBlock<AltarCoreBlock>          ALTAR_CORE                  = register("altar_core",                  AltarCoreBlock::new, properties().mapColor(MapColor.METAL).strength(3));
    DeferredBlock<TransparentBlock>        MAGIC_WALL                  = register("magic_wall",                  TransparentBlock::new, properties().strength(3).noOcclusion().isValidSpawn(Blocks::never).isRedstoneConductor((state, level, pos) -> false).isSuffocating((state, level, pos) -> false).isViewBlocking((state, level, pos) -> false));
    DeferredBlock<ObeliskBlock>            OBELISK                     = register("obelisk",                     ObeliskBlock::new, copyProperties(Blocks.STONE).noOcclusion().lightLevel(state -> state.getValue(ObeliskBlock.LIT) ? 11 : 1));
    DeferredBlock<WizardsChalkBlock>       WIZARDS_CHALK               = register("wizards_chalk",               WizardsChalkBlock::new, properties().instabreak().noCollission().sound(SoundType.GRAVEL));
    DeferredBlock<TorchBlock>              VINTEUM_TORCH               = register("vinteum_torch",               p -> new TorchBlock(ParticleTypes.SMOKE, p), copyProperties(Blocks.TORCH));
    DeferredBlock<WallTorchBlock>          VINTEUM_WALL_TORCH          = register("vinteum_wall_torch",          p -> new WallTorchBlock(ParticleTypes.SMOKE, p), copyProperties(Blocks.WALL_TORCH).lootFrom(VINTEUM_TORCH));
    DeferredBlock<DropExperienceBlock>     CHIMERITE_ORE               = register("chimerite_ore",               p -> new DropExperienceBlock(UniformInt.of(0, 2), p), properties().requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock>     DEEPSLATE_CHIMERITE_ORE     = register("deepslate_chimerite_ore",     p -> new DropExperienceBlock(UniformInt.of(0, 2), p), properties().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 3f).sound(SoundType.DEEPSLATE));
    DeferredBlock<Block>                   CHIMERITE_BLOCK             = register("chimerite_block",             properties().mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock>     TOPAZ_ORE                   = register("topaz_ore",                   p -> new DropExperienceBlock(UniformInt.of(0, 2), p), properties().requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock>     DEEPSLATE_TOPAZ_ORE         = register("deepslate_topaz_ore",         p -> new DropExperienceBlock(UniformInt.of(0, 2), p), properties().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 3f).sound(SoundType.DEEPSLATE));
    DeferredBlock<Block>                   TOPAZ_BLOCK                 = register("topaz_block",                 properties().mapColor(MapColor.DIAMOND).requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock>     VINTEUM_ORE                 = register("vinteum_ore",                 p -> new DropExperienceBlock(UniformInt.of(1, 3), p), properties().requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock>     DEEPSLATE_VINTEUM_ORE       = register("deepslate_vinteum_ore",       p -> new DropExperienceBlock(UniformInt.of(1, 3), p), properties().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 3f).sound(SoundType.DEEPSLATE));
    DeferredBlock<Block>                   VINTEUM_BLOCK               = register("vinteum_block",               properties().mapColor(MapColor.LAPIS).requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock>     MOONSTONE_ORE               = register("moonstone_ore",               p -> new DropExperienceBlock(UniformInt.of(3, 7), p), properties().requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock>     DEEPSLATE_MOONSTONE_ORE     = register("deepslate_moonstone_ore",     p -> new DropExperienceBlock(UniformInt.of(3, 7), p), properties().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 3f).sound(SoundType.DEEPSLATE));
    DeferredBlock<Block>                   MOONSTONE_BLOCK             = register("moonstone_block",             properties().mapColor(MapColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<DropExperienceBlock>     SUNSTONE_ORE                = register("sunstone_ore",                p -> new DropExperienceBlock(UniformInt.of(0, 1), p), properties().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50f, 1200f));
    DeferredBlock<Block>                   SUNSTONE_BLOCK              = register("sunstone_block",              properties().mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(3f, 3f));
    DeferredBlock<RotatedPillarBlock>      WITCHWOOD_LOG               = register("witchwood_log",               RotatedPillarBlock::new, copyProperties(Blocks.OAK_LOG).mapColor(s -> s.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_LIGHT_BLUE : MapColor.TERRACOTTA_BLUE));
    DeferredBlock<RotatedPillarBlock>      WITCHWOOD                   = register("witchwood",                   RotatedPillarBlock::new, copyProperties(Blocks.OAK_WOOD).mapColor(MapColor.TERRACOTTA_BLUE));
    DeferredBlock<RotatedPillarBlock>      STRIPPED_WITCHWOOD_LOG      = register("stripped_witchwood_log",      RotatedPillarBlock::new, copyProperties(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE));
    DeferredBlock<RotatedPillarBlock>      STRIPPED_WITCHWOOD          = register("stripped_witchwood",          RotatedPillarBlock::new, copyProperties(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE));
    DeferredBlock<LeavesBlock>             WITCHWOOD_LEAVES            = register("witchwood_leaves",            LeavesBlock::new, copyProperties(Blocks.OAK_LEAVES).mapColor(MapColor.QUARTZ));
    DeferredBlock<SaplingBlock>            WITCHWOOD_SAPLING           = register("witchwood_sapling",           p -> new SaplingBlock(AMWorldgen.WITCHWOOD_TREE_GROWER, p), copyProperties(Blocks.OAK_SAPLING));
    DeferredBlock<FlowerPotBlock>          POTTED_WITCHWOOD_SAPLING    = register("potted_witchwood_sapling",    p -> flowerPot(WITCHWOOD_SAPLING, p).get(), copyProperties(Blocks.FLOWER_POT));
    DeferredBlock<Block>                   WITCHWOOD_PLANKS            = register("witchwood_planks",            copyProperties(Blocks.OAK_PLANKS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE));
    DeferredBlock<SlabBlock>               WITCHWOOD_SLAB              = register("witchwood_slab",              SlabBlock::new, copyProperties(Blocks.OAK_SLAB).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE));
    DeferredBlock<StairBlock>              WITCHWOOD_STAIRS            = register("witchwood_stairs",            p -> new StairBlock(WITCHWOOD_PLANKS.get().defaultBlockState(), p), copyProperties(Blocks.OAK_STAIRS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE));
    DeferredBlock<FenceBlock>              WITCHWOOD_FENCE             = register("witchwood_fence",             FenceBlock::new, copyProperties(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE));
    DeferredBlock<FenceGateBlock>          WITCHWOOD_FENCE_GATE        = register("witchwood_fence_gate",        p -> new FenceGateBlock(WITCHWOOD_WOOD_TYPE, p), copyProperties(Blocks.OAK_FENCE_GATE).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE));
    DeferredBlock<DoorBlock>               WITCHWOOD_DOOR              = register("witchwood_door",              p -> new DoorBlock(WITCHWOOD_BLOCK_SET_TYPE, p), copyProperties(Blocks.OAK_DOOR).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE));
    DeferredBlock<TrapDoorBlock>           WITCHWOOD_TRAPDOOR          = register("witchwood_trapdoor",          p -> new TrapDoorBlock(WITCHWOOD_BLOCK_SET_TYPE, p), copyProperties(Blocks.OAK_TRAPDOOR).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE));
    DeferredBlock<ButtonBlock>             WITCHWOOD_BUTTON            = register("witchwood_button",            p -> new ButtonBlock(WITCHWOOD_BLOCK_SET_TYPE, 30, p), copyProperties(Blocks.OAK_BUTTON));
    DeferredBlock<PressurePlateBlock>      WITCHWOOD_PRESSURE_PLATE    = register("witchwood_pressure_plate",    p -> new PressurePlateBlock(WITCHWOOD_BLOCK_SET_TYPE, p), copyProperties(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE));
    DeferredBlock<StandingSignBlock>       WITCHWOOD_SIGN              = register("witchwood_sign",              p -> new StandingSignBlock(WITCHWOOD_WOOD_TYPE, p), copyProperties(Blocks.OAK_SIGN).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE));
    DeferredBlock<WallSignBlock>           WITCHWOOD_WALL_SIGN         = register("witchwood_wall_sign",         p -> new WallSignBlock(WITCHWOOD_WOOD_TYPE, p), copyProperties(Blocks.OAK_WALL_SIGN).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).lootFrom(WITCHWOOD_SIGN));
    DeferredBlock<CeilingHangingSignBlock> WITCHWOOD_HANGING_SIGN      = register("witchwood_hanging_sign",      p -> new CeilingHangingSignBlock(WITCHWOOD_WOOD_TYPE, p), copyProperties(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE));
    DeferredBlock<WallHangingSignBlock>    WITCHWOOD_WALL_HANGING_SIGN = register("witchwood_wall_hanging_sign", p -> new WallHangingSignBlock(WITCHWOOD_WOOD_TYPE, p), copyProperties(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).lootFrom(WITCHWOOD_HANGING_SIGN));
    DeferredBlock<AMFlowerBlock>           AUM                         = register("aum",                         p -> new AMFlowerBlock(AMMobEffects.MANA_REGENERATION, 7, AMTags.Blocks.AUM_PLANTABLE_ON, p), copyProperties(Blocks.POPPY));
    DeferredBlock<FlowerPotBlock>          POTTED_AUM                  = register("potted_aum",                  p -> flowerPot(AUM, p).get(), copyProperties(Blocks.FLOWER_POT));
    DeferredBlock<AMFlowerBlock>           CERUBLOSSOM                 = register("cerublossom",                 p -> new AMFlowerBlock(MobEffects.LEVITATION, 7, AMTags.Blocks.CERUBLOSSOM_PLANTABLE_ON, p), copyProperties(Blocks.POPPY));
    DeferredBlock<FlowerPotBlock>          POTTED_CERUBLOSSOM          = register("potted_cerublossom",          p -> flowerPot(CERUBLOSSOM, p).get(), copyProperties(Blocks.FLOWER_POT));
    DeferredBlock<AMFlowerBlock>           DESERT_NOVA                 = register("desert_nova",                 p -> new AMFlowerBlock(MobEffects.FIRE_RESISTANCE, 7, AMTags.Blocks.DESERT_NOVA_PLANTABLE_ON, p), copyProperties(Blocks.POPPY));
    DeferredBlock<FlowerPotBlock>          POTTED_DESERT_NOVA          = register("potted_desert_nova",          p -> flowerPot(DESERT_NOVA, p).get(), copyProperties(Blocks.FLOWER_POT));
    DeferredBlock<AMFlowerBlock>           TARMA_ROOT                  = register("tarma_root",                  p -> new AMFlowerBlock(MobEffects.DIG_SLOWDOWN, 7, AMTags.Blocks.TARMA_ROOT_PLANTABLE_ON, p), copyProperties(Blocks.POPPY));
    DeferredBlock<FlowerPotBlock>          POTTED_TARMA_ROOT           = register("potted_tarma_root",           p -> flowerPot(TARMA_ROOT, p).get(), copyProperties(Blocks.FLOWER_POT));
    DeferredBlock<WakebloomBlock>          WAKEBLOOM                   = register("wakebloom",                   WakebloomBlock::new, copyProperties(Blocks.POPPY));
    DeferredBlock<FlowerPotBlock>          POTTED_WAKEBLOOM            = register("potted_wakebloom",            p -> flowerPot(WAKEBLOOM, p).get(), copyProperties(Blocks.FLOWER_POT));
    // @formatter:on

    private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, B> function, BlockBehaviour.Properties properties) {
        return AMRegistries.BLOCKS.registerBlock(name, function, properties);
    }

    private static DeferredBlock<Block> register(String name, BlockBehaviour.Properties properties) {
        return AMRegistries.BLOCKS.registerSimpleBlock(name, properties);
    }

    private static BlockBehaviour.Properties properties() {
        return BlockBehaviour.Properties.of();
    }

    private static BlockBehaviour.Properties copyProperties(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block);
    }

    /**
     * Creates a {@link FlowerPotBlock} and registers it to the flower pot conversion map.
     *
     * @param flower     The flower to use.
     * @param properties The {@link Block.Properties} to use.
     * @return The created {@link FlowerPotBlock}.
     */
    private static Supplier<FlowerPotBlock> flowerPot(DeferredBlock<?> flower, BlockBehaviour.Properties properties) {
        Supplier<FlowerPotBlock> flowerPot = () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, flower, properties);
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(flower.getId(), flowerPot);
        return flowerPot;
    }

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
