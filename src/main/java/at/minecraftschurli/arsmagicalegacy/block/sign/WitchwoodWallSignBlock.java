package at.minecraftschurli.arsmagicalegacy.block.sign;

import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WitchwoodWallSignBlock extends WallSignBlock {
    public WitchwoodWallSignBlock(Properties properties) {
        super(AMBlocks.WITCHWOOD_WOOD_TYPE, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WitchwoodSignBlockEntity(pos, state);
    }
}
