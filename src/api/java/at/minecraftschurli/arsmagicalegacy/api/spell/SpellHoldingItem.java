package at.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.world.item.ItemStack;

public interface SpellHoldingItem {
    Spell getActiveSpell(ItemStack stack);

    void setActiveSpell(ItemStack stack, Spell spell);
}
