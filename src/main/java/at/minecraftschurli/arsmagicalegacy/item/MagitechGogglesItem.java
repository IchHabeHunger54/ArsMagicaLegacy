package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

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
        return player.getInventory().getArmor(3).is(AMItems.MAGITECH_GOGGLES);
    }
}
