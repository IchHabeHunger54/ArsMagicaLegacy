package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.color;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.MultiBufferSource;

public class ColorWheelPictureInPictureRenderer extends PictureInPictureRenderer<ColorWheelRenderState> {
    public ColorWheelPictureInPictureRenderer(MultiBufferSource.BufferSource bufferSource) {
        super(bufferSource);
    }

    @Override
    public Class<ColorWheelRenderState> getRenderStateClass() {
        return ColorWheelRenderState.class;
    }

    @Override
    protected void renderToTexture(ColorWheelRenderState renderState, PoseStack poseStack) {
        // TODO
    }

    @Override
    protected String getTextureLabel() {
        return "arsmagicalegacy: Color Wheel";
    }
}
