package at.minecraftschurli.arsmagicalegacy.block.obelisk;

import at.minecraftschurli.arsmagicalegacy.api.etherium.ObeliskFuel;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ObeliskSlot extends Slot {
    public ObeliskSlot(Container container, int x, int y) {
        super(container, 0, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return ObeliskFuel.isFuel(stack);
    }
}
