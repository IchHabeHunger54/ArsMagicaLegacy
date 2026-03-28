package at.minecraftschurli.mods.arsmagicalegacy.compat.jei;

import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

record DataComponentSubtypeInterpreter<T>(DataComponentType<T> type) implements ISubtypeInterpreter<ItemStack> {
    public static final DataComponentSubtypeInterpreter<Holder<Affinity>> AFFINITY = new DataComponentSubtypeInterpreter<>(AMDataComponents.AFFINITY.get());
    public static final DataComponentSubtypeInterpreter<Holder<SkillPoint>> SKILL_POINT = new DataComponentSubtypeInterpreter<>(AMDataComponents.SKILL_POINT.get());

    @Override
    @Nullable
    public Object getSubtypeData(ItemStack ingredient, UidContext context) {
        return ingredient.has(type) ? ingredient.get(type) : null;
    }
}
