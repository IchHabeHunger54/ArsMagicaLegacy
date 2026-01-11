package at.minecraftschurli.arsmagicalegacy.menu.container;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class ItemStackContainer extends SimpleContainer {
    private final ItemStack stack;

    public ItemStackContainer(ItemStack stack, int size) {
        super(size);
        this.stack = stack;
        ItemContainerContents contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        contents.copyInto(getItems());
    }

    @Override
    public void setChanged() {
        super.setChanged();
        stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(getItems()));
    }
}
