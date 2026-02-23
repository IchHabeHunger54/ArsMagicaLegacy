package at.minecraftschurli.arsmagicalegacy.util;

import at.minecraftschurli.arsmagicalegacy.entity.WitchwoodBoat;
import at.minecraftschurli.arsmagicalegacy.entity.WitchwoodChestBoat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.phys.Vec3;

public class DispenseWitchwoodBoatBehavior extends DefaultDispenseItemBehavior {
    private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
    private final boolean isChestBoat;

    public DispenseWitchwoodBoatBehavior(boolean isChestBoat) {
        this.isChestBoat = isChestBoat;
    }

    @Override
    public ItemStack execute(BlockSource blockSource, ItemStack item) {
        Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
        ServerLevel level = blockSource.level();
        Vec3 vec3 = blockSource.center();
        double x = vec3.x() + direction.getStepX() * 1.25;
        double y = vec3.y() + direction.getStepY() * 1.125;
        double z = vec3.z() + direction.getStepZ() * 1.25;
        BlockPos pos = blockSource.pos().relative(direction);
        Boat boat = isChestBoat ? new WitchwoodChestBoat(level, x, y, z) : new WitchwoodBoat(level, x, y, z);
        EntityType.<Boat>createDefaultStackConfig(level, item, null).accept(boat);
        boat.setYRot(direction.toYRot());
        double yOffset = 0;
        if (boat.canBoatInFluid(level.getFluidState(pos))) {
            yOffset = 1;
        } else {
            if (!level.getBlockState(pos).isAir() || !boat.canBoatInFluid(level.getFluidState(pos.below()))) return defaultDispenseItemBehavior.dispense(blockSource, item);
        }
        boat.setPos(x, y + yOffset, z);
        level.addFreshEntity(boat);
        item.shrink(1);
        return item;
    }

    @Override
    protected void playSound(BlockSource blockSource) {
        blockSource.level().levelEvent(LevelEvent.SOUND_DISPENSER_DISPENSE, blockSource.pos(), 0);
    }
}
