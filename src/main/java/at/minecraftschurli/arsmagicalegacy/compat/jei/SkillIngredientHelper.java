package at.minecraftschurli.arsmagicalegacy.compat.jei;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

public class SkillIngredientHelper implements IIngredientHelper<Skill> {
    @Override
    public IIngredientType<Skill> getIngredientType() {
        return AMJeiPlugin.SKILL_TYPE;
    }

    @Override
    public String getDisplayName(Skill skill) {
        return Skill.getName(AMRegistries.skills(true).wrapAsHolder(skill)).getString();
    }

    @SuppressWarnings("removal")
    @Override
    public String getUniqueId(Skill skill, UidContext uidContext) {
        return getIdentifier(skill).toString();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Identifier getIdentifier(Skill skill) {
        return AMRegistries.skills(true).getKey(skill);
    }

    @Override
    public Skill copyIngredient(Skill skill) {
        return skill;
    }

    @Override
    public String getErrorInfo(@Nullable Skill skill) {
        return skill == null ? "Unknown skill" : skill.toString();
    }
}
