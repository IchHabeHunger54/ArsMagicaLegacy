package at.minecraftschurli.arsmagicalegacy.block.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.init.AMMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class InscriptionTableMenu extends AbstractContainerMenu {
    private final InscriptionTableBlockEntity blockEntity;

    public InscriptionTableMenu(int containerId, Inventory inventory, InscriptionTableBlockEntity blockEntity) {
        super(AMMenus.INSCRIPTION_TABLE.get(), containerId);
        this.blockEntity = blockEntity;
    }

    public InscriptionTableMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, (InscriptionTableBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

   @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        BlockPos pos = blockEntity.getBlockPos();
        return player.level().getBlockEntity(pos) == blockEntity && player.distanceToSqr(Vec3.atCenterOf(pos)) <= 64D;
    }
}
