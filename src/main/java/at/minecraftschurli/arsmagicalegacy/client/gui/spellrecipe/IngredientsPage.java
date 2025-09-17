package at.minecraftschurli.arsmagicalegacy.client.gui.spellrecipe;

import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.arsmagicalegacy.api.client.SpellIngredientRenderer;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;

class IngredientsPage extends Page<SpellIngredient> {
    public IngredientsPage(List<SpellIngredient> ingredients) {
        super(5, 13, 16, 6, 5, ingredients);
    }

    @Override
    public Component getTitle() {
        return AMTranslations.SPELL_RECIPE_INGREDIENTS;
    }

    @Override
    public void renderElement(SpellIngredient element, int index, GuiGraphics guiGraphics, int x, int y) {
        SpellIngredientRenderer<SpellIngredient> renderer = ArsMagicaClientApi.spellIngredientRenderer(element);
        if (renderer != null) {
            renderer.renderInGui(element, guiGraphics, x + index % maxPerLine * (size + spacing), y + index / maxPerLine * (size + spacing), 0, 0);
        }
    }

    @Override
    public List<Component> getElementTooltip(SpellIngredient element) {
        return element.tooltip();
    }
}
