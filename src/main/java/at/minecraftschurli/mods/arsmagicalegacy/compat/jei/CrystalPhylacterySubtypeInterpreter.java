package at.minecraftschurli.mods.arsmagicalegacy.compat.jei;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.item.CrystalPhylacteryItem;
import at.minecraftschurli.mods.arsmagicalegacy.util.CrystalPhylacteryContentsSize;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

final class CrystalPhylacterySubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
    public static final CrystalPhylacterySubtypeInterpreter INSTANCE = new CrystalPhylacterySubtypeInterpreter();

    private CrystalPhylacterySubtypeInterpreter() {
    }

    @Override
    public Object getSubtypeData(ItemStack ingredient, UidContext context) {
        CrystalPhylacteryItem.Contents contents = ingredient.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        if (contents == null || contents.amount() == 0) return null;
        EntityType<?> type = contents.type();
        return CrystalPhylacteryContentsSize.get(type) > 0 ? type : null;
    }
}
