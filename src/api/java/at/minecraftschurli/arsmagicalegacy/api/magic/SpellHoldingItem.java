package at.minecraftschurli.arsmagicalegacy.api.magic;

import net.minecraft.world.item.ItemStack;

public interface SpellHoldingItem {
    Spell getActiveSpell(ItemStack stack);

    void setActiveSpell(ItemStack stack, Spell spell);
}
