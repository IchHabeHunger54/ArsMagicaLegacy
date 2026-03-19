package at.minecraftschurli.arsmagicalegacy.client.gui.spellrecipe;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

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
    public void extractElement(SpellIngredient element, int index, GuiGraphicsExtractor graphics, int x, int y) {
        ItemStack stack = AMUtil.getByTick(element.asItemStacks(), AMClientUtil.player().tickCount / 20).copyWithCount(element.count());
        x = x + index % maxPerLine * (size + spacing);
        y = y + index / maxPerLine * (size + spacing);
        AMClientUtil.renderItem(graphics, stack, x, y);
    }

    @Override
    public List<Component> getElementTooltip(SpellIngredient element) {
        return element.tooltip();
    }
}
