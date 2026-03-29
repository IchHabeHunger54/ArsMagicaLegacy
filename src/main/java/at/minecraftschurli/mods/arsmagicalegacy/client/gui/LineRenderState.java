package at.minecraftschurli.mods.arsmagicalegacy.client.gui;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.joml.Matrix3x2f;
import org.jspecify.annotations.Nullable;

public record LineRenderState(
    RenderPipeline pipeline,
    TextureSetup textureSetup,
    Matrix3x2f pose,
    float x0,
    float y0,
    float x1,
    float y1,
    int color,
    int lineWidth,
    @Nullable ScreenRectangle scissorArea,
    @Nullable ScreenRectangle bounds
) implements GuiElementRenderState {
    public LineRenderState(
        RenderPipeline pipeline,
        TextureSetup textureSetup,
        Matrix3x2f pose,
        float x0,
        float y0,
        float x1,
        float y1,
        int color,
        int lineWidth,
        @Nullable ScreenRectangle scissorArea
    ) {
        this(
            pipeline,
            textureSetup,
            pose,
            x0,
            y0,
            x1,
            y1,
            color,
            lineWidth,
            scissorArea,
            getBounds((int)x0, (int)y0, (int)x1, (int)y1, pose, scissorArea));
    }

    @Override
    public void buildVertices(VertexConsumer vertexConsumer) {
        vertexConsumer.addVertexWith2DPose(pose, x0, y0).setNormal(1, 1, 0).setColor(color).setLineWidth(2);
        vertexConsumer.addVertexWith2DPose(pose, x1, y1).setNormal(1, 1, 0).setColor(color).setLineWidth(2);
    }

    private static @Nullable ScreenRectangle getBounds(int x0, int y0, int x1, int y1, Matrix3x2f pose, @Nullable ScreenRectangle scissorArea) {
        ScreenRectangle bounds = new ScreenRectangle(x0, y0, x1 - x0, y1 - y0).transformMaxBounds(pose);
        return scissorArea != null ? scissorArea.intersection(bounds) : bounds;
    }
}
