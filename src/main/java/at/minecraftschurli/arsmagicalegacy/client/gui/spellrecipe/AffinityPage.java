package at.minecraftschurli.arsmagicalegacy.client.gui.spellrecipe;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.item.DataComponentNamedItem;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class AffinityPage extends Page<Pair<Holder<Affinity>, Double>> {
    @SuppressWarnings("DataFlowIssue")
    public AffinityPage(Map<Holder<Affinity>, Double> affinities) {
        super(5, 13, 16, 6, 1, affinities.keySet()
            .stream()
            .map(e -> new Pair<>(e, affinities.get(e)))
            .sorted(Comparator.comparing(e -> e.getFirst().getKey()))
            .sorted(Collections.reverseOrder(Comparator.comparing(Pair::getSecond)))
            .toList());
    }

    @Override
    public Component getTitle() {
        return AMTranslations.SPELL_RECIPE_AFFINITIES;
    }

    @Override
    public void renderElement(Pair<Holder<Affinity>, Double> element, int index, GuiGraphics guiGraphics, int x, int y) {
        ItemStack stack = DataComponentNamedItem.set(AMItems.AFFINITY_ESSENCE.toStack(), AMDataComponents.AFFINITY.get(), element.getFirst());
        guiGraphics.renderItem(stack, x, y + index * (size + spacing));
        guiGraphics.renderItemDecorations(AMClientUtil.font(), stack, x, y + index * (size + spacing));
        guiGraphics.drawString(AMClientUtil.font(), "%.3f".formatted(element.getSecond()), x + size + spacing, y + 4 + index * (size + spacing), element.getFirst().value().color(), false);
    }

    @Override
    public List<Component> getElementTooltip(Pair<Holder<Affinity>, Double> element) {
        return List.of(Affinity.getName(element.getFirst()));
    }
}
