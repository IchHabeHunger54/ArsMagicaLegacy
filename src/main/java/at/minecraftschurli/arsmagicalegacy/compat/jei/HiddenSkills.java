package at.minecraftschurli.arsmagicalegacy.compat.jei;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class HiddenSkills {
    private static final Set<SkillCategory.Recipe> HIDDEN_RECIPES = new HashSet<>();

    private HiddenSkills() {
    }

    @SuppressWarnings("DataFlowIssue")
    public static void update() {
        IJeiRuntime runtime = AMJeiPlugin.getRuntime();
        if (runtime == null) return;
        MagicHelper helper = ArsMagicaApi.magicHelper();
        LocalPlayer player = AMClientUtil.player();
        IFocusFactory focusFactory = runtime.getJeiHelpers().getFocusFactory();
        List<? extends Holder<Skill>> list = AMRegistries.skills(true)
            .holders()
            .filter(e -> AMRegistries.SPELL_PARTS.containsKey(e.getKey().location()))
            .sorted(Comparator.comparing(e -> Skill.getName(e).getString()))
            .toList();
        IIngredientManager ingredientManager = runtime.getIngredientManager();
        ingredientManager.removeIngredientsAtRuntime(AMJeiPlugin.SKILL_TYPE, new ArrayList<>(ingredientManager.getAllIngredients(AMJeiPlugin.SKILL_TYPE)));
        List<Skill> skills = list.stream()
            .filter(e -> !e.value().hidden() || helper.knows(player, e))
            .map(Holder::value)
            .toList(); // fixme
        ingredientManager.addIngredientsAtRuntime(AMJeiPlugin.SKILL_TYPE, skills);
        IRecipeManager recipeManager = runtime.getRecipeManager();
        recipeManager.unhideRecipes(SkillCategory.RECIPE_TYPE, HIDDEN_RECIPES);
        HIDDEN_RECIPES.clear();
        recipeManager.createRecipeLookup(SkillCategory.RECIPE_TYPE)
            .includeHidden()
            .limitFocus(list.stream()
                .filter(e -> e.value().hidden() && !helper.knows(player, e))
                .map(e -> focusFactory.createFocus(RecipeIngredientRole.OUTPUT, AMJeiPlugin.SKILL_TYPE, e.value()))
                .toList())
            .get()
            .forEach(HIDDEN_RECIPES::add);
        recipeManager.hideRecipes(SkillCategory.RECIPE_TYPE, HIDDEN_RECIPES);
    }

    static void clear() {
        HIDDEN_RECIPES.clear();
    }
}
