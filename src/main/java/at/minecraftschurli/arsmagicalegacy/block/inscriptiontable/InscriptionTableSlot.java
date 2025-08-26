package at.minecraftschurli.arsmagicalegacy.block.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class InscriptionTableSlot extends Slot {
    public InscriptionTableSlot(Container container, int x, int y) {
        super(container, 0, x, y);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(AMTags.Items.INSCRIPTION_TABLE_BOOKS);
    }
}
