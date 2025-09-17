package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color;

import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphics;
import org.apache.commons.lang3.function.TriConsumer;
import org.jetbrains.annotations.Nullable;

class BrightnessSlider extends ColorPickerWidget {
    protected BrightnessSlider(int x, int y, int width, int height, TriConsumer<Float, Float, Float> onChange) {
        super(x, y, width, height, onChange);
    }

    @Override
    protected float @Nullable [] getHovered(double mouseX, double mouseY) {
        return new float[]{hue, saturation, (float) (1 - (mouseY - getY()) / height)};
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int[] rgb = AMClientUtil.hsbToRgb(hue, saturation, 1);
        guiGraphics.fillGradient(getX(), getY(), getX() + width, getY() + height, 0xff << 24 | rgb[0] << 16 | rgb[1] << 8 | rgb[2], 0xff000000);
        renderIndicator(guiGraphics, getX() + width / 2, (int) (getY() + Math.clamp(1 - brightness, 0, 1) * height));
    }
}
