package at.minecraftschurli.arsmagicalegacy.client.gui.spellrecipe;

import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;

class IngredientsPage extends Page {
    private static final int X_OFFSET = 5;
    private static final int Y_OFFSET = 13;
    private static final int SIZE = 16;
    private static final int SPACING = 6;
    private static final int MAX_PER_LINE = 5;
    private final List<SpellIngredient> ingredients;

    public IngredientsPage(List<SpellIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public Component getTitle() {
        return AMTranslations.SPELL_RECIPE_INGREDIENTS;
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y) {
        for (int i = 0; i < ingredients.size(); i++) {
            SpellIngredient ingredient = ingredients.get(i);
            ArsMagicaClientApi.spellIngredientRenderer(ingredient).renderInGui(ingredient, graphics, x + X_OFFSET + i % MAX_PER_LINE * (SIZE + SPACING), y + Y_OFFSET + i / MAX_PER_LINE * (SIZE + SPACING), 0, 0);
        }
    }

    @Override
    public List<Component> getTooltip(int mouseX, int mouseY) {
        int i = getTooltipIndex(mouseX - X_OFFSET, mouseY - Y_OFFSET, SIZE, SPACING, MAX_PER_LINE, ingredients.size());
        return i == -1 ? List.of() : ingredients.get(i).tooltip();
    }
}
