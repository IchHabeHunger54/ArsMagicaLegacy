package at.minecraftschurli.arsmagicalegacy.menu.container;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.item.SpellBookItem;
import net.minecraft.world.item.ItemStack;

public class SpellBookContainer extends ItemStackContainer {
    public SpellBookContainer(ItemStack stack) {
        super(stack, SpellBookItem.TOTAL_SLOTS);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        int index = stack.getOrDefault(AMDataComponents.SELECTED_INDEX, -1);
        if (index >= 0 && index < SpellBookItem.HOTBAR_SLOTS) {
            ItemStack item = getItem(index);
            Spell spell = item.get(AMDataComponents.SPELL);
            if (spell != null && !spell.isEmpty()) {
                stack.set(AMDataComponents.SPELL, spell);
                return;
            }
        }
        stack.remove(AMDataComponents.SPELL);
    }
}
