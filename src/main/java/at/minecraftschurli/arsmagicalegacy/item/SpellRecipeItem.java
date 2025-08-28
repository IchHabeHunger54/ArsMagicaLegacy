package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SpellRecipeItem extends Item {
    public SpellRecipeItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        if (!stack.has(AMDataComponents.SPELL)) return super.getName(stack);
        Spell spell = stack.get(AMDataComponents.SPELL);
        return spell.name().orElse(super.getName(stack));
    }
}
