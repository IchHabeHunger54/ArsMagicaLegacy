package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color;

import at.minecraftschurli.arsmagicalegacy.client.AMRenderTypes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.phys.Vec2;
import org.apache.commons.lang3.function.TriConsumer;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

class ColorWheel extends ColorPickerWidget {
    private final int radius;

    protected ColorWheel(int centerX, int centerY, int radius, TriConsumer<Float, Float, Float> onChange) {
        super(centerX - radius, centerY - radius, radius * 2, radius * 2, onChange);
        this.radius = radius;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (isFocused()) {
            ColorWheelShader.set(getX() + radius, getY() + radius, radius + 1, -1);
            guiGraphics.fillGradient(AMRenderTypes.COLOR_WHEEL, getX() - 1, getY() - 1, getX() + width + 1, getY() + height + 1, 0xffffffff, 0xffffffff, 0);
        }
        ColorWheelShader.set(getX() + radius, getY() + radius, radius, brightness);
        guiGraphics.fillGradient(AMRenderTypes.COLOR_WHEEL, getX(), getY(), getX() + width, getY() + height, 0xffffffff, 0xffffffff, 0);
        renderIndicator(guiGraphics, (int) (getX() + radius + radius * saturation * Math.cos(hue * Math.TAU)), (int) (getY() + radius + radius * saturation * Math.sin(hue * Math.TAU)));
    }

    @Override
    protected float @Nullable [] getHovered(double mouseX, double mouseY) {
        Vec2 mouseRelative = getMouseRelative(mouseX, mouseY);
        double length = mouseRelative.length();
        double angle = Math.atan2(mouseRelative.y, mouseRelative.x);
        if (angle < 0) {
            angle += Math.TAU;
        }
        return length <= radius ? new float[]{(float) (angle / Math.TAU), (float) (length / radius), brightness} : null;
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        return active && visible && getMouseRelative(mouseX, mouseY).length() <= radius;
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

    private Vec2 getMouseRelative(double mouseX, double mouseY) {
        return new Vec2((float) mouseX - getX() - radius, (float) mouseY - getY() - radius);
    }
}
