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
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

@SuppressWarnings("DataFlowIssue")
@JeiPlugin
public final class AMJeiPlugin implements IModPlugin {
    public static final IIngredientType<Skill> SKILL_TYPE = () -> Skill.class;
    private static final Identifier ID = ArsMagicaApi.id(ArsMagicaApi.MOD_ID);
    private static IJeiRuntime runtime = null;

    @Override
    public Identifier getPluginUid() {
        return ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.INFINITY_ORB.get(), DataComponentSubtypeInterpreter.SKILL_POINT);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.AFFINITY_ESSENCE.get(), DataComponentSubtypeInterpreter.AFFINITY);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.AFFINITY_TOME.get(), DataComponentSubtypeInterpreter.AFFINITY);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.CRYSTAL_PHYLACTERY.get(), CrystalPhylacterySubtypeInterpreter.INSTANCE);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        registration.register(SKILL_TYPE, AMRegistries.skills(true)
            .listElements()
            .filter(e -> AMRegistries.SPELL_PARTS.containsKey(e.getKey().identifier()))
            .sorted(Comparator.comparing(e -> Skill.getName(e).getString()))
            .map(Holder::value)
            .toList(), new SkillIngredientHelper(), new SkillIngredientRenderer(), Skill.CODEC.xmap(Holder::value, AMRegistries.skills(true)::wrapAsHolder));
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
        runtime = jeiRuntime;
        HiddenSkills.update();
    }

    @Override
    public void onRuntimeUnavailable() {
        runtime = null;
        HiddenSkills.clear();
    }

    @Nullable
    static IJeiRuntime getRuntime() {
        return runtime;
    }
}
