package at.minecraftschurli.arsmagicalegacy.client.util;

public final class ColorUtil {
    private ColorUtil() {
    }

    /**
     * @param color The color to get the blue value for.
     * @return The blue value of the given color.
     */
    public static float getBlue(int color) {
        return (0xFF & color) / 255f;
    }

    /**
     * @param color The color to get the green value for.
     * @return The green value of the given color.
     */
    public static float getGreen(int color) {
        return (0xFF & (color >> 8)) / 255f;
    }

    /**
     * @param color The color to get the red value for.
     * @return The red value of the given color.
     */
    public static float getRed(int color) {
        return (0xFF & (color >> 16)) / 255f;
    }

    /**
     * @param color The color to get the alpha value for.
     * @return The alpha value of the given color.
     */
    public static float getAlpha(int color) {
        return (0xFF & (color >> 24)) / 255f;
    }
}
