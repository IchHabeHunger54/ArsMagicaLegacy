package at.minecraftschurli.mods.arsmagicalegacy.compat.curios;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public final class AMCuriosHelper {
    private AMCuriosHelper() {}

    public static boolean hasItemEquipped(Player player, Item item) {
        return CuriosApi.getCuriosInventory(player)
            .map(ICuriosItemHandler::getCurios)
            .map(map -> map.values()
                .stream()
                .map(ICurioStacksHandler::getStacks)
                .anyMatch(items -> {
                    for (int i = 0; i < items.getSlots(); i++) {
                        if (items.getStackInSlot(i).is(item)) return true;
                    }
                    return false;
                }))
            .orElse(false);
    }
}
