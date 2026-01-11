package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.menu.SpellBookMenu;
import at.minecraftschurli.arsmagicalegacy.util.ItemStackContainer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

public class SpellBookItem extends Item {
    public static final int INVENTORY_SLOTS = 32;
    public static final int HOTBAR_SLOTS = 8;
    public static final int TOTAL_SLOTS = INVENTORY_SLOTS + HOTBAR_SLOTS;

    public SpellBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!player.isSecondaryUseActive()) return getSelectedSpell(stack).use(level, player, usedHand);
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider((id, inventory, $) -> new SpellBookMenu(id, inventory, usedHand), Component.empty()), buf -> buf.writeEnum(usedHand));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return getSelectedSpell(stack).getUseDuration(entity);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        getSelectedSpell(stack).onUseTick(level, livingEntity, remainingUseDuration);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        getSelectedSpell(stack).releaseUsing(level, livingEntity, timeCharged);
    }

    public static IItemHandler getItemHandler(ItemStack stack, Void v) {
        return new InvWrapper(new ItemStackContainer(stack, TOTAL_SLOTS));
    }

    private static ItemStack getSelectedSpell(ItemStack stack) {
        int index = stack.getOrDefault(AMDataComponents.SELECTED_INDEX, -1);
        return index < 0 || index >= HOTBAR_SLOTS ? ItemStack.EMPTY : stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).getStackInSlot(index);
    }
}
