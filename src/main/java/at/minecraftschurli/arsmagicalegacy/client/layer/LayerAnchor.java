package at.minecraftschurli.arsmagicalegacy.client.layer;

import net.minecraft.client.Minecraft;

import java.util.function.IntSupplier;

public final class LayerAnchor {
    private LayerAnchor() {}

    public enum X {
        LEFT, CENTER, RIGHT;

        public int getLocation(IntSupplier supplier) {
            return switch (this) {
                case LEFT -> supplier.getAsInt();
                case CENTER -> Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + supplier.getAsInt();
                case RIGHT -> Minecraft.getInstance().getWindow().getGuiScaledWidth() + supplier.getAsInt();
            };
        }
    }

    public enum Y {
        TOP, MIDDLE, BOTTOM;

        public int getLocation(IntSupplier supplier) {
            return switch (this) {
                case TOP -> supplier.getAsInt();
                case MIDDLE -> Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + supplier.getAsInt();
                case BOTTOM -> Minecraft.getInstance().getWindow().getGuiScaledHeight() + supplier.getAsInt();
            };
        }
    }
}
