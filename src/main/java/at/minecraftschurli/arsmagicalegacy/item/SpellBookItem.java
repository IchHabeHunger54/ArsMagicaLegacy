package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.menu.SpellBookMenu;
import at.minecraftschurli.arsmagicalegacy.menu.container.SpellBookContainer;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

import java.util.List;

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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        ItemStack spell = getSelectedSpell(stack);
        if (spell.isEmpty()) {
            tooltipComponents.add(AMTranslations.SPELL_BOOK_NO_SPELL_SELECTED);
        } else {
            tooltipComponents.add(Component.translatable(AMTranslations.SPELL_BOOK_SELECTED_SPELL_KEY, spell.getHoverName()));
            spell.getItem().appendHoverText(spell, context, tooltipComponents, tooltipFlag);
        }
    }

    public static IItemHandler getItemHandler(ItemStack stack, Void v) {
        return new InvWrapper(new SpellBookContainer(stack));
    }

    public static void scroll(ItemStack stack, boolean backwards) {
        int index = stack.getOrDefault(AMDataComponents.SELECTED_INDEX, 0);
        index += backwards ? HOTBAR_SLOTS - 1 : 1;
        index %= HOTBAR_SLOTS;
        stack.set(AMDataComponents.SELECTED_INDEX, index);
        updateSpell(stack);
    }

    public static void updateSpell(ItemStack stack) {
        int index = stack.getOrDefault(AMDataComponents.SELECTED_INDEX, -1);
        ItemContainerContents container = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        if (index >= 0 && index < Math.min(container.getSlots(), SpellBookItem.HOTBAR_SLOTS)) {
            ItemStack item = container.getStackInSlot(index);
            Spell spell = item.get(AMDataComponents.SPELL);
            if (spell != null && !spell.isEmpty()) {
                stack.set(AMDataComponents.SPELL, spell);
                return;
            }
        }
        stack.remove(AMDataComponents.SPELL);
    }

    private static ItemStack getSelectedSpell(ItemStack stack) {
        int index = stack.getOrDefault(AMDataComponents.SELECTED_INDEX, -1);
        if (index < 0 || index >= HOTBAR_SLOTS) return ItemStack.EMPTY;
        ItemContainerContents container = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        return index < container.getSlots() ? container.getStackInSlot(index) : ItemStack.EMPTY;
    }
}
