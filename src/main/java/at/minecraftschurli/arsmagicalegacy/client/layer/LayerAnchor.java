package at.minecraftschurli.arsmagicalegacy.client.layer;

import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import net.minecraft.client.Minecraft;

import java.util.function.IntSupplier;

public final class LayerAnchor {
    private LayerAnchor() {}

    public enum X {
        LEFT, CENTER, RIGHT;

        public int getLocation(IntSupplier supplier) {
            return switch (this) {
                case LEFT -> supplier.getAsInt();
                case CENTER -> ClientUtil.mc().getWindow().getGuiScaledWidth() / 2 + supplier.getAsInt();
                case RIGHT -> ClientUtil.mc().getWindow().getGuiScaledWidth() + supplier.getAsInt();
            };
        }
    }

    public enum Y {
        TOP, MIDDLE, BOTTOM;

        public int getLocation(IntSupplier supplier) {
            return switch (this) {
                case TOP -> supplier.getAsInt();
                case MIDDLE -> ClientUtil.mc().getWindow().getGuiScaledHeight() / 2 + supplier.getAsInt();
                case BOTTOM -> ClientUtil.mc().getWindow().getGuiScaledHeight() + supplier.getAsInt();
            };
        }
    }
}
