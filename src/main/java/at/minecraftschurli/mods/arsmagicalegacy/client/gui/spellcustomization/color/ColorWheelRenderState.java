package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.color;

import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
import org.joml.Vector2fc;
import org.jspecify.annotations.Nullable;

public record ColorWheelRenderState(int x0, int x1, int y0, int y1, float scale, @Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds, Vector2fc center, float radius, float brightness) implements PictureInPictureRenderState {
    public ColorWheelRenderState(int x, int y, int width, int height, @Nullable ScreenRectangle scissorArea, Vector2fc center, float radius, float brightness) {
        this(x, x + width, y, y + height, 1, scissorArea, PictureInPictureRenderState.getBounds(x, y, x + width, y + height, scissorArea), center, radius, brightness);
    }
}