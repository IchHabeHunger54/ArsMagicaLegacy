package at.minecraftschurli.arsmagicalegacy.block.sign;

import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WitchwoodSignBlockEntity extends SignBlockEntity {
    public WitchwoodSignBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return AMBlockEntities.WITCHWOOD_SIGN.get();
    }
}
