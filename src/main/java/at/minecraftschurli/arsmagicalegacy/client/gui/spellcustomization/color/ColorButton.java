package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import java.util.function.IntConsumer;

class ColorButton extends Button {
    private static final int SIZE = 10;
    private final int color;

    protected ColorButton(int x, int y, int color, IntConsumer onPress, Component tooltip) {
        super(x, y, SIZE, SIZE, Component.empty(), $ -> onPress.accept(color), DEFAULT_NARRATION);
        this.color = color;
        setTooltip(Tooltip.create(tooltip));
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), color | 0xff000000);
    }
}
