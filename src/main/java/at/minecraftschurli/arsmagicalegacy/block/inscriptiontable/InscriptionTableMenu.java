package at.minecraftschurli.arsmagicalegacy.block.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.init.AMMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class InscriptionTableMenu extends AbstractContainerMenu {
    private final InscriptionTableBlockEntity blockEntity;

    public InscriptionTableMenu(int containerId, Inventory inventory, InscriptionTableBlockEntity blockEntity) {
        super(AMMenus.INSCRIPTION_TABLE.get(), containerId);
        this.blockEntity = blockEntity;
        blockEntity.startOpen(inventory.player);
        addSlot(new InscriptionTableSlot(blockEntity, inventory.player.isCreative() ? 48 : 102, 74));
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, 30 + i * 18, 228));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, i * 9 + j + 9, 30 + j * 18, 170 + i * 18));
            }
        }
    }

    @SuppressWarnings("DataFlowIssue")
    public InscriptionTableMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, (InscriptionTableBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        Slot tableSlot = slots.getFirst();
        ItemStack stack = slot.getItem();
        ItemStack originalStack = stack.copy();
        if (index == 0) {
            if (!moveItemStackTo(stack, 1, 37, false)) return ItemStack.EMPTY;
        } else if (index > 0 && index < 10) {
            if (tableSlot.getItem() == ItemStack.EMPTY && tableSlot.mayPlace(stack) && !moveItemStackTo(stack, 0, 1, false))
                return ItemStack.EMPTY;
            else if (!moveItemStackTo(stack, 10, 37, false)) return ItemStack.EMPTY;
        } else if (index > 9 && index < 37) {
            if (tableSlot.getItem() == ItemStack.EMPTY && tableSlot.mayPlace(stack) && !moveItemStackTo(stack, 0, 1, false))
                return ItemStack.EMPTY;
            else if (!moveItemStackTo(stack, 1, 10, true)) return ItemStack.EMPTY;
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

    @Override
    public void removed(Player player) {
        super.removed(player);
        blockEntity.stopOpen(player);
    }

    public InscriptionTableBlockEntity getBlockEntity() {
        return blockEntity;
    }

    public int getShapeGroups() {
        return blockEntity.getBlockState().getValue(InscriptionTableBlock.TIER) + 2;
    }
}
