package at.minecraftschurli.arsmagicalegacy.compat.jei;

import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.item.CrystalPhylacteryItem;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

public final class CrystalPhylacterySubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
    public static final CrystalPhylacterySubtypeInterpreter INSTANCE = new CrystalPhylacterySubtypeInterpreter();

    private CrystalPhylacterySubtypeInterpreter() {
    }

    @Override
    public Object getSubtypeData(ItemStack ingredient, UidContext context) {
        CrystalPhylacteryItem.Contents contents = ingredient.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        return contents != null && contents.amount() != 0 ? contents.type() : null;
    }

    @Override
    public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
        CrystalPhylacteryItem.Contents contents = ingredient.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        return contents != null && contents.amount() != 0 ? BuiltInRegistries.ENTITY_TYPE.getKey(contents.type()).toString() : "";
    }
}
