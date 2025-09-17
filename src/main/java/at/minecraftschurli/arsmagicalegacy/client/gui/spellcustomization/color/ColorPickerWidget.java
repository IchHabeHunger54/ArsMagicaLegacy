package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color;

import at.minecraftschurli.arsmagicalegacy.client.AMRenderTypes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import org.apache.commons.lang3.function.TriConsumer;
import org.lwjgl.glfw.GLFW;

public class ColorPickerWidget extends AbstractWidget {
    private final int radius;
    private final TriConsumer<Float, Float, Float> onChange;
    private float hue;
    private float saturation;
    private float brightness;

    public ColorPickerWidget(int centerX, int centerY, int radius, Component message, TriConsumer<Float, Float, Float> onChange) {
        super(centerX - radius, centerY - radius, radius * 2, radius * 2, message);
        this.radius = radius;
        this.onChange = onChange;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ColorWheelShader.set(getX() + radius, getY() + radius, radius, brightness);
        guiGraphics.fillGradient(AMRenderTypes.COLOR_WHEEL, getX(), getY(), getX() + width, getY() + height, 0xffffffff, 0xffffffff, 0);
        int x = (int) (getX() + radius + radius * saturation * Math.cos(hue * Math.TAU));
        int y = (int) (getY() + radius + radius * saturation * Math.sin(hue * Math.TAU));
        guiGraphics.fill(x - 1, y - 1, x + 1, y + 1, ColorCustomizationScreen.blackOrWhite(hue, saturation, brightness));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        return active && visible && getMouseRelative(mouseX, mouseY).length() <= radius;
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

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return switch (keyCode) {
            case GLFW.GLFW_KEY_LEFT -> {
                hue = (hue - 1 / 256f + 1) % 1;
                onChange();
                yield true;
            }
            case GLFW.GLFW_KEY_RIGHT -> {
                hue = (hue + 1 / 256f) % 1;
                onChange();
                yield true;
            }
            case GLFW.GLFW_KEY_UP -> {
                saturation = (saturation + 1 / 256f) % 1;
                onChange();
                yield true;
            }
            case GLFW.GLFW_KEY_DOWN -> {
                saturation = (saturation - 1 / 256f + 1) % 1;
                onChange();
                yield true;
            }
            default -> super.keyPressed(keyCode, scanCode, modifiers);
        };
    }

    public void setValue(float hue, float saturation, float brightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    private void onChange() {
        onChange.accept(hue, saturation, brightness);
    }

    private Vec2 getMouseRelative(double mouseX, double mouseY) {
        return new Vec2((float) mouseX - getX() - radius, (float) mouseY - getY() - radius);
    }

    private void setHovered(double mouseX, double mouseY) {
        Vec2 mouseRelative = getMouseRelative(mouseX, mouseY);
        double length = mouseRelative.length();
        double angle = Math.atan2(mouseRelative.y, mouseRelative.x);
        if (angle < 0) {
            angle += Math.TAU;
        }
        if (length <= radius) {
            hue = (float) (angle / Math.TAU);
            saturation = (float) (length / radius);
            onChange();
        }
    }
}
