package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AffinityEssenceItem extends Item {
    public AffinityEssenceItem(Properties properties) {
        super(properties);
    }

    public static ItemStack set(ItemStack stack, Holder<Affinity> holder) {
        stack.set(AMDataComponents.AFFINITY, holder);
        return stack;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public String getDescriptionId(ItemStack stack) {
        return stack.has(AMDataComponents.AFFINITY) ? Util.makeDescriptionId(super.getDescriptionId(stack), stack.get(AMDataComponents.AFFINITY).getKey().location()) : super.getDescriptionId(stack);
    }
}
