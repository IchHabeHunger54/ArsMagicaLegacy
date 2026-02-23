package at.minecraftschurli.arsmagicalegacy.compat.jei;

import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.item.CrystalPhylacteryItem;
import at.minecraftschurli.arsmagicalegacy.util.CrystalPhylacteryContentsSize;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public final class CrystalPhylacterySubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
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

    @Override
    public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
        CrystalPhylacteryItem.Contents contents = ingredient.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        if (contents == null || contents.amount() == 0) return "";
        EntityType<?> type = contents.type();
        return CrystalPhylacteryContentsSize.get(type) > 0 ? BuiltInRegistries.ENTITY_TYPE.getKey(type).toString() : "";
    }
}
