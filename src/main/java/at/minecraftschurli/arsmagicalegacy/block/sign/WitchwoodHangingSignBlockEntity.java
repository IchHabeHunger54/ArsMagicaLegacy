package at.minecraftschurli.arsmagicalegacy.block.sign;

import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WitchwoodHangingSignBlockEntity extends HangingSignBlockEntity {
    public WitchwoodHangingSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return AMBlockEntities.WITCHWOOD_HANGING_SIGN.get();
    }
}
