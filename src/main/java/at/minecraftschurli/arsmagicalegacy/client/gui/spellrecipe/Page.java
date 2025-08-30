package at.minecraftschurli.arsmagicalegacy.client.gui.spellrecipe;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;

abstract class Page {
    public abstract Component getTitle();

    public abstract void render(GuiGraphics graphics, int x, int y);

    public abstract List<Component> getTooltip(int mouseX, int mouseY);

    protected int getTooltipIndex(int x, int y, int size, int spacing, int maxPerLine, int maxTotal) {
        int resX = -1, resY = -1;
        for (int i = 0; i < maxPerLine; i++) {
            int min = i * size + i * spacing;
            int max = size + i * size + i * spacing;
            if (x >= min && x < max) {
                resX = i;
            }
            if (y >= min && y < max) {
                resY = i;
            }
        }
        if (resX == -1 || resY == -1) return -1;
        int result = resX + resY * maxPerLine;
        return result >= maxTotal ? -1 : result;
    }
}
