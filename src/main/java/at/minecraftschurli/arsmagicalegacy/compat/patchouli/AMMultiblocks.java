package at.minecraftschurli.arsmagicalegacy.compat.patchouli;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

public abstract class AMMultiblocks {
    public static final ResourceLocation ALTAR_NAME = ArsMagicaApi.modLoc("altar");
    public static final String[][] ALTAR_STRUCTURE = new String[][]{
        {" C2C ", " 3B1 ", " 3O1 ", " 3B1 ", " C4C "},
        {" BMB ", " 6 6 ", "     ", " 5 5 ", " BMB "},
        {" BMBI", "     ", "     ", "     ", " BMB "},
        {" BMB ", "     ", "     ", "     ", " BMBL"},
        {"BBBBB", "BBBBB", "BB0BB", "BBBBB", "BBBBB"}};
    public static IMultiblock ALTAR;

    public static void init() {
        PatchouliAPI.IPatchouliAPI api = PatchouliAPI.get();
        ALTAR = api.registerMultiblock(ALTAR_NAME, api.makeMultiblock(ALTAR_STRUCTURE,
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
    }
}
