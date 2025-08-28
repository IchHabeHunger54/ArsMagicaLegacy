package at.minecraftschurli.arsmagicalegacy.compat.jei;

import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SkillPointSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
    public static final SkillPointSubtypeInterpreter INSTANCE = new SkillPointSubtypeInterpreter();

    private SkillPointSubtypeInterpreter() {
    }

    @Override
    @Nullable
    public Object getSubtypeData(ItemStack ingredient, UidContext context) {
        return ingredient.has(AMDataComponents.SKILL_POINT) ? ingredient.get(AMDataComponents.SKILL_POINT) : null;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
        return ingredient.has(AMDataComponents.SKILL_POINT) ? ingredient.get(AMDataComponents.SKILL_POINT).getKey().location().toString() : "";
    }
}
