package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;

public interface AMArmorMaterials {
    DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, ArsMagicaApi.MOD_ID);
    DeferredHolder<ArmorMaterial, ArmorMaterial> MAGITECH_GOGGLES = ARMOR_MATERIALS.register("magitech_goggles", () -> new ArmorMaterial(
        Map.of(ArmorItem.Type.HELMET, 0),
        0,
        SoundEvents.ARMOR_EQUIP_LEATHER,
        () -> Ingredient.EMPTY,
        List.of(new ArmorMaterial.Layer(ArsMagicaApi.modLoc("magitech_goggles"))),
        0,
        0
    ));
}
