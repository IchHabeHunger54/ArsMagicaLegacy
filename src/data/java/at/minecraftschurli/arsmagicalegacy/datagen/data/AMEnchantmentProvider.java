package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.init.AMEnchantments;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;

public final class AMEnchantmentProvider {
    public static void addEnchantments(BootstrapContext<Enchantment> bootstrap) {
        bootstrap.register(AMEnchantments.DISMEMBERING, Enchantment.enchantment(
            Enchantment.definition(bootstrap.lookup(Registries.ITEM).getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE), 2, 3, Enchantment.dynamicCost(15, 9), Enchantment.dynamicCost(65, 9), 4, EquipmentSlotGroup.MAINHAND)
        ).build(AMEnchantments.DISMEMBERING.location()));
    }
}
