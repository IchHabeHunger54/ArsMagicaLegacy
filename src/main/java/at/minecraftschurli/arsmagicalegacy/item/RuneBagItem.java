package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.menu.RuneBagMenu;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

public class RuneBagItem extends Item {
    public RuneBagItem(Properties properties) {
        super(properties);
    }

    public static IItemHandler getItemHandler(ItemStack stack, Void v) {
        return new InvWrapper(new Container(stack));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (level.isClientSide()) return InteractionResultHolder.success(stack);
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider((id, inventory, $) -> new RuneBagMenu(id, inventory, usedHand), Component.empty()), buf -> buf.writeEnum(usedHand));
        }
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !ItemStack.isSameItem(oldStack, newStack);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    public static class Container extends SimpleContainer {
        private final ItemStack stack;

        public Container(ItemStack stack) {
            super(DyeColor.values().length);
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
}
