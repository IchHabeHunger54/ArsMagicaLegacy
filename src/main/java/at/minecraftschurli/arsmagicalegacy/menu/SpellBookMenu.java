package at.minecraftschurli.arsmagicalegacy.menu;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMenus;
import at.minecraftschurli.arsmagicalegacy.item.SpellBookItem;
import at.minecraftschurli.arsmagicalegacy.menu.container.ItemStackContainer;
import at.minecraftschurli.arsmagicalegacy.menu.slot.PlacePredicateSlot;
import at.minecraftschurli.arsmagicalegacy.menu.slot.ViewSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class SpellBookMenu extends AbstractContainerMenu {
    private static final Predicate<ItemStack> PREDICATE = stack -> stack.is(AMItems.SPELL);

    public SpellBookMenu(int containerId, Inventory inventory, InteractionHand hand) {
        super(AMMenus.SPELL_BOOK.get(), containerId);
        Container container = new ItemStackContainer(inventory.player.getItemInHand(hand), SpellBookItem.TOTAL_SLOTS);
        for (int i = 0; i < SpellBookItem.HOTBAR_SLOTS; i++) {
            addSlot(new PlacePredicateSlot(container, i, 18, 5 + (i * 18), PREDICATE));
        }
        for (int i = 0; i < SpellBookItem.INVENTORY_SLOTS / SpellBookItem.HOTBAR_SLOTS; i++) {
            for (int j = 0; j < SpellBookItem.HOTBAR_SLOTS; j++) {
                addSlot(new PlacePredicateSlot(container, (i + 1) * SpellBookItem.HOTBAR_SLOTS + j, 138 + (i * 26), 5 + (j * 18), PREDICATE));
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, i * 9 + j + 9, 48 + j * 18, 171 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlot(i == inventory.selected ? new ViewSlot(inventory, i, 48 + i * 18, 229) : new Slot(inventory, i, 48 + i * 18, 229));
        }
    }

    public SpellBookMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readEnum(InteractionHand.class));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack stackCopy = stack.copy();
        if (index < SpellBookItem.TOTAL_SLOTS) {
            if (!moveItemStackTo(stack, SpellBookItem.TOTAL_SLOTS, SpellBookItem.TOTAL_SLOTS + 36, true)) return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, 0, SpellBookItem.TOTAL_SLOTS, false)) return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        if (stack.getCount() == stackCopy.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return stackCopy;
    }

    @Override
    public boolean stillValid(Player player) {
        return ArsMagicaApi.magicHelper().knowsMagic(player);
    }
}
