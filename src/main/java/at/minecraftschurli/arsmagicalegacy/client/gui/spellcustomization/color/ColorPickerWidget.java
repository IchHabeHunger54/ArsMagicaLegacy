package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color;

import at.minecraftschurli.arsmagicalegacy.client.AMRenderTypes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class ColorPickerWidget extends AbstractWidget {
    private final int radius;

    public ColorPickerWidget(int centerX, int centerY, int radius, Component message) {
        super(centerX - radius, centerY - radius, radius * 2, radius * 2, message);
        this.radius = radius;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ColorWheelShader.set(getX() + radius, getY() + radius, radius, 1);
        guiGraphics.fillGradient(AMRenderTypes.COLOR_WHEEL, getX(), getY(), getX() + width, getY() + height, 0xffffffff, 0xffffffff, 0);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }
}
