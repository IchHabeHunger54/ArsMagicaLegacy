package at.minecraftschurli.arsmagicalegacy.block.altar;

import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AltarCoreBlockEntity extends BlockEntity {
    public AltarCoreBlockEntity(BlockPos pos, BlockState blockState) {
        super(AMBlockEntities.ALTAR_CORE.get(), pos, blockState);
    }
}
