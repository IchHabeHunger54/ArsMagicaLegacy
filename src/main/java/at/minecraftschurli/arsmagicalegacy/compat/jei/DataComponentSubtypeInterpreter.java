package at.minecraftschurli.arsmagicalegacy.compat.jei;

import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@SuppressWarnings("DataFlowIssue")
public class DataComponentSubtypeInterpreter<T> implements ISubtypeInterpreter<ItemStack> {
    public static final DataComponentSubtypeInterpreter<Holder<Affinity>> AFFINITY = new DataComponentSubtypeInterpreter<>(AMDataComponents.AFFINITY.get(), holder -> holder.getKey().location().toString());
    public static final DataComponentSubtypeInterpreter<Holder<SkillPoint>> SKILL_POINT = new DataComponentSubtypeInterpreter<>(AMDataComponents.SKILL_POINT.get(), holder -> holder.getKey().location().toString());
    private final DataComponentType<T> type;
    private final Function<T, String> legacySubtype;

    private DataComponentSubtypeInterpreter(DataComponentType<T> type, Function<T, String> legacySubtype) {
        this.type = type;
        this.legacySubtype = legacySubtype;
    }

    @Override
    @Nullable
    public Object getSubtypeData(ItemStack ingredient, UidContext context) {
        return ingredient.has(type) ? ingredient.get(type) : null;
    }

    @Override
    public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
        return ingredient.has(type) ? legacySubtype.apply(ingredient.get(type)) : "";
    }
}
