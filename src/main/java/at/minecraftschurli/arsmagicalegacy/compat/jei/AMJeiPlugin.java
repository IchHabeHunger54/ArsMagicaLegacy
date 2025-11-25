package at.minecraftschurli.arsmagicalegacy.compat.jei;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import java.util.Comparator;
import java.util.List;

@JeiPlugin
public final class AMJeiPlugin implements IModPlugin {
    public static final IIngredientType<Skill> SKILL_TYPE = () -> Skill.class;
    private static final ResourceLocation ID = ArsMagicaApi.modLoc(ArsMagicaApi.MOD_ID);

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.INFINITY_ORB.get(), DataComponentSubtypeInterpreter.SKILL_POINT);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.AFFINITY_ESSENCE.get(), DataComponentSubtypeInterpreter.AFFINITY);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.AFFINITY_TOME.get(), DataComponentSubtypeInterpreter.AFFINITY);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        registration.register(SKILL_TYPE, List.of(), new SkillIngredientHelper(), new SkillIngredientRenderer(), Skill.CODEC.xmap(Holder::value, AMRegistries.skills()::wrapAsHolder));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SkillCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(VanillaTypes.ITEM_STACK, AMItems.OCCULUS.toStack(), SkillCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(VanillaTypes.ITEM_STACK, AMItems.INSCRIPTION_TABLE.toStack(), SkillCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(VanillaTypes.ITEM_STACK, AMItems.ALTAR_CORE.toStack(), SkillCategory.RECIPE_TYPE);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        List<Skill> list = AMRegistries.skills()
            .holders()
            .filter(e -> ArsMagicaApi.spellPartRegistry().containsKey(e.getKey().location()))
            .sorted(Comparator.comparing(e -> Skill.getName(e).getString()))
            .map(Holder::value)
            .toList();
        jeiRuntime.getIngredientManager().addIngredientsAtRuntime(SKILL_TYPE, list);
        jeiRuntime.getRecipeManager().addRecipes(SkillCategory.RECIPE_TYPE, list.stream()
            .map(SkillCategory.Recipe::of)
            .toList());
    }
}
