package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color;

import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.function.TriConsumer;
import org.jetbrains.annotations.Nullable;

abstract class ColorPickerWidget extends AbstractWidget {
    private final TriConsumer<Float, Float, Float> onChange;
    protected float hue;
    protected float saturation;
    protected float brightness;

    protected ColorPickerWidget(int x, int y, int width, int height, TriConsumer<Float, Float, Float> onChange) {
        super(x, y, width, height, Component.empty());
        this.onChange = onChange;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        setHovered(mouseX, mouseY);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        if (isHovered) {
            setHovered(mouseX, mouseY);
        }
    }

    protected void setValue(float hue, float saturation, float brightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    protected void setHovered(double mouseX, double mouseY) {
        float[] hovered = getHovered(mouseX, mouseY);
        if (hovered != null) {
            setValue(hovered[0], hovered[1], hovered[2]);
            onChange();
        }
    }

    protected void onChange() {
        onChange.accept(hue, saturation, brightness);
    }

    protected void renderIndicator(GuiGraphicsExtractor guiGraphics, int x, int y) {
        int[] rgb = AMClientUtil.hsbToRgb(hue, saturation, brightness);
        int color = rgb[0] * 0.299 + rgb[1] * 0.587 + rgb[2] * 0.114 > 186 ? 0xff000000 : 0xffffffff;
        guiGraphics.fill(x - 1, y - 1, x + 1, y + 1, color);
    }

    protected abstract float @Nullable [] getHovered(double mouseX, double mouseY);
}
