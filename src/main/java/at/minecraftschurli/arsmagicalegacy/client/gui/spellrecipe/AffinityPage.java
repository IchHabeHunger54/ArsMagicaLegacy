package at.minecraftschurli.arsmagicalegacy.client.gui.spellrecipe;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.item.AffinityEssenceItem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

class AffinityPage extends Page {
    private static final int X_OFFSET = 5;
    private static final int Y_OFFSET = 13;
    private static final int SIZE = 16;
    private static final int SPACING = 6;
    private static final int MAX_PER_LINE = 1;
    private final List<Pair<Holder<Affinity>, Double>> affinities;

    @SuppressWarnings("DataFlowIssue")
    public AffinityPage(Map<Holder<Affinity>, Double> affinities) {
        this.affinities = affinities.keySet()
            .stream()
            .map(e -> new Pair<>(e, affinities.get(e)))
            .sorted(Comparator.comparing(e -> e.getFirst().getKey()))
            .sorted(Comparator.comparing(Pair::getSecond))
            .toList();
    }

    @Override
    public Component getTitle() {
        return AMTranslations.SPELL_RECIPE_AFFINITIES;
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y) {
        for (int i = 0; i < affinities.size(); i++) {
            Pair<Holder<Affinity>, Double> pair = affinities.get(i);
            ItemStack stack = AffinityEssenceItem.set(AMItems.AFFINITY_ESSENCE.toStack(), pair.getFirst());
            graphics.renderItem(stack, x, y);
            graphics.renderItemDecorations(ClientUtil.font(), stack, x, y);
            graphics.drawString(ClientUtil.font(), "%.3f".formatted(pair.getSecond()), x + X_OFFSET + SIZE + SPACING, y + Y_OFFSET + 4 + i * (SIZE + SPACING), pair.getFirst().value().color(), false);
        }
    }

    @Override
    public List<Component> getTooltip(int mouseX, int mouseY) {
        int i = getTooltipIndex(mouseX - X_OFFSET, mouseY - Y_OFFSET, SIZE, SPACING, MAX_PER_LINE, affinities.size());
        return i == -1 ? List.of() : List.of(Affinity.getName(affinities.get(i).getFirst()));
    }
}
