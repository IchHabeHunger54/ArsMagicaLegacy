package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color;

import net.minecraft.client.renderer.ShaderInstance;

public class ColorWheelShader {
    private static ShaderInstance instance;
    private static float centerX;
    private static float centerY;
    private static float radius;
    private static float brightness;

    public static ShaderInstance getInstance() {
        return instance;
    }

    public static float getCenterX() {
        return centerX;
    }

    public static float getCenterY() {
        return centerY;
    }

    public static float getRadius() {
        return radius;
    }

    public static float getBrightness() {
        return brightness;
    }

    public static void setInstance(ShaderInstance instance) {
        ColorWheelShader.instance = instance;
    }

    public static void set(float centerX, float centerY, float radius, float brightness) {
        ColorWheelShader.centerX = centerX;
        ColorWheelShader.centerY = centerY;
        ColorWheelShader.radius = radius;
        ColorWheelShader.brightness = brightness;
    }
}
