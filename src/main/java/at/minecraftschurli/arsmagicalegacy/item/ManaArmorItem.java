package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ManaArmorItem extends ArmorItem {
    public ManaArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties, int durability, double manaCost) {
        super(material, type, properties.stacksTo(1).durability(type.getDurability(durability)).component(AMDataComponents.MANA_REPAIR_COST, manaCost));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof LivingEntity living && stack.isDamaged()) {
            ManaHelper helper = ArsMagicaApi.manaHelper();
            double cost = stack.getOrDefault(AMDataComponents.MANA_REPAIR_COST, 1.);
            if (helper.getMana(living) > cost) {
                stack.setDamageValue(stack.getDamageValue() - 1);
                helper.decreaseMana(living, cost);
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}
