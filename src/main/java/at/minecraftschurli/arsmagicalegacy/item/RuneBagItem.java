package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.menu.RuneBagMenu;
import at.minecraftschurli.arsmagicalegacy.menu.container.ItemStackContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

public class RuneBagItem extends Item {
    public RuneBagItem(Properties properties) {
        super(properties);
    }

    public static IItemHandler getItemHandler(ItemStack stack, Void v) {
        return new InvWrapper(new ItemStackContainer(stack, DyeColor.values().length));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider((id, inventory, _) -> new RuneBagMenu(id, inventory, hand), Component.empty()), buf -> buf.writeEnum(hand));
        }
        return InteractionResult.SUCCESS.heldItemTransformedTo(player.getItemInHand(hand));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !ItemStack.isSameItem(oldStack, newStack);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }
}
