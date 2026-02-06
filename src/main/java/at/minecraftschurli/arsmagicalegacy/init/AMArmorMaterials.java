package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public interface AMArmorMaterials {
    DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, ArsMagicaApi.MOD_ID);
    DeferredHolder<ArmorMaterial, ArmorMaterial> MAGITECH_GOGGLES = register("magitech_goggles", Map.of(ArmorItem.Type.HELMET, 0), 0, SoundEvents.ARMOR_EQUIP_LEATHER, AMTags.Items.MAGITECH_GOGGLES_REPAIR_ITEMS, 0);
    DeferredHolder<ArmorMaterial, ArmorMaterial> MAGE = register("mage", defenseMap(2, 6, 4, 2), 15, SoundEvents.ARMOR_EQUIP_LEATHER, AMTags.Items.MAGE_ARMOR_REPAIR_ITEMS, 0.5f);
    DeferredHolder<ArmorMaterial, ArmorMaterial> BATTLEMAGE = register("battlemage", defenseMap(3, 8, 6, 3), 10, SoundEvents.ARMOR_EQUIP_NETHERITE, AMTags.Items.BATTLEMAGE_ARMOR_REPAIR_ITEMS, 1);

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, Map<ArmorItem.Type, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, TagKey<Item> repairItems, float toughness) {
        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(defense, enchantmentValue, equipSound, () -> Ingredient.of(repairItems), List.of(new ArmorMaterial.Layer(ArsMagicaApi.id(name))), toughness, 0));
    }

    private static EnumMap<ArmorItem.Type, Integer> defenseMap(int helmet, int chestplate, int leggings, int boots) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.HELMET, helmet);
        map.put(ArmorItem.Type.CHESTPLATE, chestplate);
        map.put(ArmorItem.Type.LEGGINGS, leggings);
        map.put(ArmorItem.Type.BOOTS, boots);
        return map;
    }
}
