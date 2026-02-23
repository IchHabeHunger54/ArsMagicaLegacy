package at.minecraftschurli.arsmagicalegacy;

import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class AMEnumExtensions {
    public static Object witchwoodBoatType(int index, Class<?> type) {
        if (index == 5) return false;
        return type.cast(switch (index) {
            case 0 -> (Supplier<Block>) AMBlocks.WITCHWOOD_PLANKS;
            case 1 -> "arsmagicalegacy:witchwood";
            case 2 -> (Supplier<Item>) AMItems.WITCHWOOD_BOAT::get;
            case 3 -> (Supplier<Item>) AMItems.WITCHWOOD_CHEST_BOAT::get;
            case 4 -> (Supplier<Item>) () -> Items.STICK;
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + index);
        });
    }
}
