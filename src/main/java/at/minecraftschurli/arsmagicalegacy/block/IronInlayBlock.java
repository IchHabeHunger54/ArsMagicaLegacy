package at.minecraftschurli.arsmagicalegacy.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class IronInlayBlock extends InlayBlock {
    public IronInlayBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
        Vec3 deltaMovement = cart.getDeltaMovement();
        cart.setDeltaMovement(-deltaMovement.x(), deltaMovement.y(), -deltaMovement.z());
        return super.getRailMaxSpeed(state, level, pos, cart);
    }
}
