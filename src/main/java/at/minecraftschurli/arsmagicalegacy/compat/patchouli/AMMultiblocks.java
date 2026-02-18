package at.minecraftschurli.arsmagicalegacy.compat.patchouli;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.block.CelestialPrismBlock;
import at.minecraftschurli.arsmagicalegacy.block.ObeliskBlock;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.PatchouliAPI;

public final class AMMultiblocks {
    public static final ResourceLocation ALTAR = ArsMagicaApi.id("altar");
    public static final ResourceLocation OBELISK_CHALK = ArsMagicaApi.id("obelisk_chalk");
    public static final ResourceLocation OBELISK_PILLARS = ArsMagicaApi.id("obelisk_pillars");
    public static final ResourceLocation CELESTIAL_PRISM_CHALK = ArsMagicaApi.id("celestial_prism_chalk");
    public static final ResourceLocation CELESTIAL_PRISM_PILLARS_1 = ArsMagicaApi.id("celestial_prism_pillars_1");
    public static final ResourceLocation CELESTIAL_PRISM_PILLARS_2 = ArsMagicaApi.id("celestial_prism_pillars_2");
    public static final ResourceLocation CELESTIAL_PRISM_PILLARS_3 = ArsMagicaApi.id("celestial_prism_pillars_3");
    public static final ResourceLocation CELESTIAL_PRISM_PILLARS_4 = ArsMagicaApi.id("celestial_prism_pillars_4");
    public static final ResourceLocation BLACK_AUREM_CHALK = ArsMagicaApi.id("black_aurem_chalk");
    public static final ResourceLocation BLACK_AUREM_PILLARS_1 = ArsMagicaApi.id("black_aurem_pillars_1");
    public static final ResourceLocation BLACK_AUREM_PILLARS_2 = ArsMagicaApi.id("black_aurem_pillars_2");
    public static final ResourceLocation BLACK_AUREM_PILLARS_3 = ArsMagicaApi.id("black_aurem_pillars_3");
    public static final ResourceLocation BLACK_AUREM_PILLARS_4 = ArsMagicaApi.id("black_aurem_pillars_4");
    public static final ResourceLocation PURIFICATION = ArsMagicaApi.id("purification");
    public static final ResourceLocation CORRUPTION = ArsMagicaApi.id("corruption");
    private static final String[][] ALTAR_STRUCTURE = new String[][]{
        {" C2C ", " 3B1 ", " 3O1 ", " 3B1 ", " C4C "},
        {" BMB ", " 6 6 ", "     ", " 5 5 ", " BMB "},
        {" BMBI", "     ", "     ", "     ", " BMB "},
        {" BMB ", "     ", "     ", "     ", " BMBL"},
        {"BBBBB", "BBBBB", "BB0BB", "BBBBB", "BBBBB"}};
    private static final String[][] OBELISK_CHALK_STRUCTURE = new String[][]{
        {"   ", " 2 ", "   "},
        {"   ", " 1 ", "   "},
        {"WWW", "W0W", "WWW"}};
    private static final String[][] CELESTIAL_PRISM_CHALK_STRUCTURE = new String[][]{
        {"   ", " 1 ", "   "},
        {"WWW", "W0W", "WWW"}};
    private static final String[][] BLACK_AUREM_CHALK_STRUCTURE = new String[][]{
        {"WWW", "W0W", "WWW"}};
    private static final String[][] PILLARS_STRUCTURE = new String[][]{
        {"T   T", "     ", "  2  ", "     ", "T   T"},
        {"P   P", "     ", "  1  ", "     ", "P   P"},
        {"P   P", " WWW ", " W0W ", " WWW ", "P   P"}};
    private static final String[][] PURIFICATION_STRUCTURE = new String[][]{
        {"       ", "       ", "       ", "   2   ", "       ", "       ", "       "},
        {"       ", "       ", "       ", "   1   ", "       ", "       ", "       "},
        {"  WWW  ", " CW WC ", "WWW WWW", "W  0  W", "WWW WWW", " CW WC ", "  WWW  "}};
    private static final String[][] CORRUPTION_STRUCTURE = new String[][]{
        {"     ", "     ", "     ", "  2  ", "     ", "     ", "     "},
        {"     ", "     ", "     ", "  1  ", "     ", "     ", "     "},
        {" W W ", "WCWCW", "W   W", " W0W ", "W   W", "WCWCW", " W W "}};

    private AMMultiblocks() {
    }

    public static void init() {
        PatchouliAPI.IPatchouliAPI api = PatchouliAPI.get();
        IStateMatcher air = api.airMatcher();
        IStateMatcher chalk = api.looseBlockMatcher(AMBlocks.WIZARDS_CHALK.get());
        IStateMatcher candle = api.propertyMatcher(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true), CandleBlock.LIT);
        IStateMatcher obeliskLower = api.propertyMatcher(AMBlocks.OBELISK.get().defaultBlockState(), ObeliskBlock.PART);
        IStateMatcher obeliskMiddle = api.propertyMatcher(AMBlocks.OBELISK.get().defaultBlockState().setValue(ObeliskBlock.PART, ObeliskBlock.Part.MIDDLE), ObeliskBlock.PART);
        IStateMatcher obeliskUpper = api.propertyMatcher(AMBlocks.OBELISK.get().defaultBlockState().setValue(ObeliskBlock.PART, ObeliskBlock.Part.UPPER), ObeliskBlock.PART);
        IStateMatcher celestialPrismLower = api.stateMatcher(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState());
        IStateMatcher celestialPrismUpper = api.stateMatcher(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState().setValue(CelestialPrismBlock.PART, CelestialPrismBlock.Part.UPPER));
        IStateMatcher blackAurem = api.strictBlockMatcher(AMBlocks.BLACK_AUREM.get());
        IStateMatcher quartzPillar = api.strictBlockMatcher(Blocks.QUARTZ_PILLAR);
        IStateMatcher netherBricks = api.strictBlockMatcher(Blocks.NETHER_BRICKS);
        api.registerMultiblock(ALTAR, api.makeMultiblock(ALTAR_STRUCTURE,
            'L', api.predicateMatcher(Blocks.LECTERN.defaultBlockState().setValue(LecternBlock.FACING, Direction.SOUTH), state -> state.is(Blocks.LECTERN) && state.getValue(LecternBlock.FACING) == Direction.SOUTH),
            'I', api.predicateMatcher(Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACING, Direction.SOUTH), state -> state.is(Blocks.LEVER) && state.getValue(LeverBlock.FACE) == AttachFace.WALL && state.getValue(LeverBlock.FACING) == Direction.SOUTH),
            'O', api.looseBlockMatcher(AMBlocks.ALTAR_CORE.get()),
            'M', api.strictBlockMatcher(AMBlocks.MAGIC_WALL.get()),
            'B', new AltarStateMatcher(),
            'C', new AltarCapStateMatcher(),
            '0', new AltarCapStateMatcher(),
            '1', new AltarStairStateMatcher(Direction.NORTH, Half.BOTTOM),
            '2', new AltarStairStateMatcher(Direction.EAST, Half.BOTTOM),
            '3', new AltarStairStateMatcher(Direction.SOUTH, Half.BOTTOM),
            '4', new AltarStairStateMatcher(Direction.WEST, Half.BOTTOM),
            '5', new AltarStairStateMatcher(Direction.EAST, Half.TOP),
            '6', new AltarStairStateMatcher(Direction.WEST, Half.TOP)
        ));
        api.registerMultiblock(OBELISK_CHALK, api.makeMultiblock(OBELISK_CHALK_STRUCTURE,
            'W', chalk,
            '0', obeliskLower,
            '1', obeliskMiddle,
            '2', obeliskUpper
        ).setSymmetrical(true));
        api.registerMultiblock(OBELISK_PILLARS, makePillarsMultiblock(api, obeliskLower, obeliskMiddle, obeliskUpper, chalk, api.strictBlockMatcher(Blocks.STONE_BRICKS), api.strictBlockMatcher(Blocks.CHISELED_STONE_BRICKS)));
        api.registerMultiblock(CELESTIAL_PRISM_CHALK, api.makeMultiblock(CELESTIAL_PRISM_CHALK_STRUCTURE,
            'W', chalk,
            '0', celestialPrismLower,
            '1', celestialPrismUpper
        ).setSymmetrical(true));
        api.registerMultiblock(CELESTIAL_PRISM_PILLARS_1, makePillarsMultiblock(api, celestialPrismLower, celestialPrismUpper, air, chalk, quartzPillar, api.strictBlockMatcher(AMBlocks.CHIMERITE_BLOCK.get())));
        api.registerMultiblock(CELESTIAL_PRISM_PILLARS_2, makePillarsMultiblock(api, celestialPrismLower, celestialPrismUpper, air, chalk, quartzPillar, api.strictBlockMatcher(Blocks.GOLD_BLOCK)));
        api.registerMultiblock(CELESTIAL_PRISM_PILLARS_3, makePillarsMultiblock(api, celestialPrismLower, celestialPrismUpper, air, chalk, quartzPillar, api.strictBlockMatcher(Blocks.DIAMOND_BLOCK)));
        api.registerMultiblock(CELESTIAL_PRISM_PILLARS_4, makePillarsMultiblock(api, celestialPrismLower, celestialPrismUpper, air, chalk, quartzPillar, api.strictBlockMatcher(AMBlocks.SUNSTONE_BLOCK.get())));
        api.registerMultiblock(BLACK_AUREM_CHALK, api.makeMultiblock(BLACK_AUREM_CHALK_STRUCTURE,
            'W', chalk,
            '0', blackAurem
        ).setSymmetrical(true));
        api.registerMultiblock(BLACK_AUREM_PILLARS_1, makePillarsMultiblock(api, blackAurem, air, air, chalk, netherBricks, api.strictBlockMatcher(AMBlocks.CHIMERITE_BLOCK.get())));
        api.registerMultiblock(BLACK_AUREM_PILLARS_2, makePillarsMultiblock(api, blackAurem, air, air, chalk, netherBricks, api.strictBlockMatcher(Blocks.GOLD_BLOCK)));
        api.registerMultiblock(BLACK_AUREM_PILLARS_3, makePillarsMultiblock(api, blackAurem, air, air, chalk, netherBricks, api.strictBlockMatcher(Blocks.DIAMOND_BLOCK)));
        api.registerMultiblock(BLACK_AUREM_PILLARS_4, makePillarsMultiblock(api, blackAurem, air, air, chalk, netherBricks, api.strictBlockMatcher(AMBlocks.SUNSTONE_BLOCK.get())));
        api.registerMultiblock(PURIFICATION, api.makeMultiblock(PURIFICATION_STRUCTURE,
            'W', chalk,
            'C', candle,
            '0', obeliskLower,
            '1', obeliskMiddle,
            '2', obeliskUpper));
        api.registerMultiblock(CORRUPTION, api.makeMultiblock(CORRUPTION_STRUCTURE,
            'W', chalk,
            'C', candle,
            '0', obeliskLower,
            '1', obeliskMiddle,
            '2', obeliskUpper));
    }

    private static IMultiblock makePillarsMultiblock(PatchouliAPI.IPatchouliAPI api, IStateMatcher lower, IStateMatcher middle, IStateMatcher upper, IStateMatcher chalk, IStateMatcher pillar, IStateMatcher top) {
        return api.makeMultiblock(PILLARS_STRUCTURE,
            '0', lower,
            '1', middle,
            '2', upper,
            'W', chalk,
            'P', pillar,
            'T', top
        ).setSymmetrical(true);
    }
}
