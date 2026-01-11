package at.minecraftschurli.arsmagicalegacy.menu;

import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMenus;
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
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

public class RuneBagMenu extends AbstractContainerMenu {
    private static final List<DeferredItem<?>> RUNES = List.of(
        AMItems.BLACK_RUNE,
        AMItems.GRAY_RUNE,
        AMItems.LIGHT_GRAY_RUNE,
        AMItems.WHITE_RUNE,
        AMItems.BROWN_RUNE,
        AMItems.RED_RUNE,
        AMItems.ORANGE_RUNE,
        AMItems.YELLOW_RUNE,
        AMItems.LIME_RUNE,
        AMItems.GREEN_RUNE,
        AMItems.CYAN_RUNE,
        AMItems.LIGHT_BLUE_RUNE,
        AMItems.BLUE_RUNE,
        AMItems.PURPLE_RUNE,
        AMItems.MAGENTA_RUNE,
        AMItems.PINK_RUNE);
    private final InteractionHand hand;

    public RuneBagMenu(int containerId, Inventory inventory, InteractionHand hand) {
        super(AMMenus.RUNE_BAG.get(), containerId);
        this.hand = hand;
        Container container = new ItemStackContainer(inventory.player.getItemInHand(hand), RUNES.size());
        for (int i = 0; i < 16; i++) {
            final int j = i;
            addSlot(new PlacePredicateSlot(container, i, 8 + i % 8 * 18, 8 + i / 8 * 18, stack -> stack.is(RUNES.get(j))));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, i * 9 + j + 9, 8 + j * 18, 68 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlot(i == inventory.selected ? new ViewSlot(inventory, i, 8 + i * 18, 126) : new Slot(inventory, i, 8 + i * 18, 126));
        }
    }

    public RuneBagMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readEnum(InteractionHand.class));
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getItemInHand(hand).is(AMItems.RUNE_BAG);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack stackCopy = stack.copy();
        if (index < RUNES.size()) {
            if (!moveItemStackTo(stack, RUNES.size(), RUNES.size() + 36, true)) return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, 0, RUNES.size(), false)) return ItemStack.EMPTY;
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
}
