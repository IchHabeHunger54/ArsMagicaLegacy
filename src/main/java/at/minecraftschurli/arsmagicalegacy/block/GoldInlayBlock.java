package at.minecraftschurli.arsmagicalegacy.block;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GoldInlayBlock extends InlayBlock {
    public GoldInlayBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
        if (cart.getDeltaMovement().horizontalDistance() > 0.01) {
            Vec3i normal = cart.getMotionDirection().getNormal();
            int range = AMServerConfig.GOLD_INLAY_RANGE.get();
            for (int i = 0; i < range; i++) {
                pos = pos.offset(normal);
                if (level.getBlockState(pos).is(this)) {
                    cart.setPos(cart.position().add(Vec3.atLowerCornerOf(normal.multiply(i + 1))));
                    break;
                }
            }
        }
        return super.getRailMaxSpeed(state, level, pos, cart);
    }
}
