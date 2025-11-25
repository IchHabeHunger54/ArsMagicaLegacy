package at.minecraftschurli.arsmagicalegacy.compat.jei;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartData;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.item.DataComponentNamedItem;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("DataFlowIssue")
public class SkillCategory implements IRecipeCategory<SkillCategory.Recipe> {
    public static final RecipeType<Recipe> RECIPE_TYPE = RecipeType.create(ArsMagicaApi.MOD_ID, "skill", Recipe.class);
    private static final ResourceLocation BACKGROUND = ArsMagicaApi.modLoc("textures/gui/skill_category.png");
    private static final Comparator<Holder<Affinity>> COMPARATOR = Comparator.comparing(Holder::getKey);
    private static final int INGREDIENT_COLUMNS = 7;
    private static final int SLOT_SIZE = 18;
    private static final int WIDTH = INGREDIENT_COLUMNS * SLOT_SIZE;
    private static final int HEIGHT = 192;
    private static final int TEXT_BOTTOM_PADDING = 2;
    private final IDrawable icon;

    public SkillCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableItemStack(AMItems.ALTAR_CORE.toStack());
    }

    @Override
    public RecipeType<Recipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return AMTranslations.JEI_SKILL_TITLE;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Recipe recipe, IFocusGroup focuses) {
        List<SpellIngredient> ingredients = recipe.recipe;
        Map<Holder<Affinity>, Double> affinityShifts = recipe.affinityShifts;
        List<Skill> modifiers = recipe.modifiers;
        int x = 0;
        int y = AMClientUtil.font().lineHeight + TEXT_BOTTOM_PADDING;
        builder.addSlot(RecipeIngredientRole.OUTPUT, (WIDTH - SLOT_SIZE) / 2, y).addIngredient(AMJeiPlugin.SKILL_TYPE, recipe.skill);
        y += SLOT_SIZE + TEXT_BOTTOM_PADDING;
        for (int i = 0; i < ingredients.size(); i++) {
            if (i % INGREDIENT_COLUMNS != 0) {
                x += SLOT_SIZE;
            } else {
                x = (WIDTH - Math.min(ingredients.size() - i * INGREDIENT_COLUMNS, INGREDIENT_COLUMNS) * SLOT_SIZE) / 2;
                y += SLOT_SIZE;
            }
            builder.addSlot(RecipeIngredientRole.INPUT, x, y).addItemStacks(ingredients.get(i).asItemStacks());
        }
        y += SLOT_SIZE;
        if (!affinityShifts.isEmpty()) {
            x = getAffinityValueAnchor(affinityShifts) - 9;
            y += SLOT_SIZE + TEXT_BOTTOM_PADDING;
            for (Holder<Affinity> affinity : affinityShifts.keySet().stream().sorted(COMPARATOR).toList()) {
                builder.addSlot(RecipeIngredientRole.RENDER_ONLY, x, y)
                    .addItemStack(DataComponentNamedItem.set(AMItems.AFFINITY_ESSENCE.toStack(), AMDataComponents.AFFINITY.get(), affinity))
                    .addRichTooltipCallback((slot, tooltip) -> {
                        tooltip.clear();
                        tooltip.add(Affinity.getName(affinity));
                    });
                y += SLOT_SIZE - TEXT_BOTTOM_PADDING;
            }
            y += TEXT_BOTTOM_PADDING;
        }
        if (!modifiers.isEmpty()) {
            y += TEXT_BOTTOM_PADDING;
            for (int i = 0; i < modifiers.size(); i++) {
                if (i % INGREDIENT_COLUMNS != 0) {
                    x += SLOT_SIZE;
                } else {
                    x = (WIDTH - Math.min(modifiers.size() - i * INGREDIENT_COLUMNS, INGREDIENT_COLUMNS) * SLOT_SIZE) / 2;
                    y += SLOT_SIZE;
                }
                builder.addSlot(RecipeIngredientRole.CATALYST, x, y).addIngredient(AMJeiPlugin.SKILL_TYPE, modifiers.get(i));
            }
        }
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(BACKGROUND, 0, 0, 0, 0, WIDTH, HEIGHT);
        Font font = AMClientUtil.font();
        drawCentered(guiGraphics, font, Skill.getName(AMUtil.registryAccess().registryOrThrow(AMRegistryKeys.SKILL).wrapAsHolder(recipe.skill)), 0);
        int y = SLOT_SIZE * 2 + TEXT_BOTTOM_PADDING;
        drawCentered(guiGraphics, font, AMTranslations.JEI_SKILL_INGREDIENTS, y);
        y += (recipe.recipe.size() / INGREDIENT_COLUMNS + 1) * SLOT_SIZE + font.lineHeight + TEXT_BOTTOM_PADDING;
        if (!recipe.affinityShifts.isEmpty()) {
            y += SLOT_SIZE - font.lineHeight;
            drawCentered(guiGraphics, font, AMTranslations.JEI_SKILL_AFFINITY_BREAKDOWN, y);
            y += font.lineHeight + font.lineHeight / 2 + TEXT_BOTTOM_PADDING;
            int x = getAffinityValueAnchor(recipe.affinityShifts) + 9;
            for (Holder<Affinity> affinity : recipe.affinityShifts.keySet().stream().sorted(COMPARATOR).toList()) {
                guiGraphics.drawString(font, String.valueOf(Math.round(recipe.affinityShifts.get(affinity) * 1000) / 1000.), x, y, affinity.value().color(), false);
                y += SLOT_SIZE - 2;
            }
            y += 2 - font.lineHeight / 2 + TEXT_BOTTOM_PADDING;
        }
        if (!recipe.modifiers.isEmpty()) {
            y += SLOT_SIZE - font.lineHeight;
            drawCentered(guiGraphics, font, AMTranslations.JEI_SKILL_MODIFIED_BY, y);
        }
    }

    private static void drawCentered(GuiGraphics graphics, Font font, Component component, int y) {
        graphics.drawString(font, component, (int) ((WIDTH - font.getSplitter().stringWidth(component.getString())) / 2), y, 0x404040, false);
    }

    private static int getAffinityValueAnchor(Map<Holder<Affinity>, Double> affinityShifts) {
        return (int) (WIDTH - AMClientUtil.font().getSplitter().stringWidth(String.valueOf(Math.round(affinityShifts.values().stream().min(Double::compareTo).orElse(0.) * 1000) / 1000.))) / 2;
    }

    public record Recipe(Skill skill, List<SpellIngredient> recipe, Map<Holder<Affinity>, Double> affinityShifts, List<Skill> modifiers) {
        @SuppressWarnings("DataFlowIssue")
        public static Recipe of(Skill skill) {
            Registry<Skill> skills = AMUtil.registryAccess().registryOrThrow(AMRegistryKeys.SKILL);
            Registry<SpellPart> spellParts = ArsMagicaApi.spellPartRegistry();
            SpellPart part = spellParts.get(skills.getKey(skill));
            SpellPartData data = part.getData();
            return new Recipe(skill, data.recipe(), data.affinityShifts(), ArsMagicaApi.spellHelper()
                .getModifiers(part)
                .stream()
                .map(e -> skills.get(spellParts.getKey(e)))
                .toList());
        }
    }
}
