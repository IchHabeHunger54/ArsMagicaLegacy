package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InfinityOrbItem extends Item {
    public InfinityOrbItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!stack.has(AMDataComponents.SKILL_POINT)) return super.use(level, player, usedHand);
        ArsMagicaApi.getMagicHelper().addSkillPoint(player, stack.get(AMDataComponents.SKILL_POINT));
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        return InteractionResultHolder.success(stack);
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return stack.has(AMDataComponents.SKILL_POINT) ? Util.makeDescriptionId(super.getDescriptionId(stack), stack.get(AMDataComponents.SKILL_POINT).getKey().location()) : super.getDescriptionId(stack);
    }
}
