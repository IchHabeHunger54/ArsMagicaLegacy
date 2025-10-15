package at.minecraftschurli.arsmagicalegacy.block.obelisk;

import at.minecraftschurli.arsmagicalegacy.init.AMMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ObeliskMenu extends AbstractContainerMenu {
    private final ObeliskBlockEntity blockEntity;

    public ObeliskMenu(int containerId, Inventory inventory, ObeliskBlockEntity blockEntity) {
        super(AMMenus.OBELISK.get(), containerId);
        this.blockEntity = blockEntity;
        addSlot(new ObeliskSlot(blockEntity, 79, 47));
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    public ObeliskMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, ObeliskBlock.getBlockEntity(inventory.player.level(), buf.readBlockPos()));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack originalStack = stack.copy();
        if (index == 0) { // If slot is a BE slot
            // Try moving to the hotbar or inventory
            if (!moveItemStackTo(stack, 1, 37, false))
                return ItemStack.EMPTY;
        } else if (index < 10) { // If slot is a hotbar slot
            // Try moving to the BE
            if (!moveItemStackTo(stack, 0, 1, false))
                return ItemStack.EMPTY;
            // Try moving to the inventory
            if (!moveItemStackTo(stack, 10, 37, false))
                return ItemStack.EMPTY;
        } else if (index < 37) { // If slot is an inventory slot
            // Try moving to the BE
            if (!moveItemStackTo(stack, 0, 1, false))
                return ItemStack.EMPTY;
            // Try moving to the hotbar
            if (!moveItemStackTo(stack, 1, 10, false))
                return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        return originalStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity.stillValid(player);
    }
}
