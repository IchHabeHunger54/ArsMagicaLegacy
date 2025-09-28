package at.minecraftschurli.arsmagicalegacy.item.runebag;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RuneBagSlot extends Slot {
    private final Item item;

    public RuneBagSlot(Container container, int index, int x, int y, Item item) {
        super(container, index, x, y);
        this.item = item;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(item);
    }
}
