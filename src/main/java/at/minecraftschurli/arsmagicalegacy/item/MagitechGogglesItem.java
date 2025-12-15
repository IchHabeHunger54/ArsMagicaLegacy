package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MagitechGogglesItem extends ArmorItem {
    public static final Supplier<ArmorMaterial> ARMOR_MATERIAL = () -> new ArmorMaterial(
        Map.of(Type.HELMET, 0),
        0,
        SoundEvents.ARMOR_EQUIP_LEATHER,
        () -> Ingredient.EMPTY,
        List.of(new ArmorMaterial.Layer(ArsMagicaApi.modLoc("magitech_goggles"))),
        0,
        0
    );

    public MagitechGogglesItem(Properties properties) {
        super(AMItems.MAGITECH_GOGGLES_MATERIAL, Type.HELMET, properties.stacksTo(1));
    }

    public static boolean shouldRender(Player player) {
        return player.getInventory().getArmor(3).is(AMItems.MAGITECH_GOGGLES) || AMUtil.ifModLoaded("curios", () -> CuriosApi.getCuriosInventory(player)
                .map(ICuriosItemHandler::getCurios)
                .map(map -> map.values()
                    .stream()
                    .map(ICurioStacksHandler::getStacks)
                    .anyMatch(items -> {
                        for (int i = 0; i < items.getSlots(); i++) {
                            if (items.getStackInSlot(i).is(AMItems.MAGITECH_GOGGLES)) return true;
                        }
                        return false;
                    }))
                .orElse(false))
            .orElse(false);
    }
}
