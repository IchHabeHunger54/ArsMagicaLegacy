package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.color;

import at.minecraftschurli.mods.arsmagicalegacy.client.AMRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderTypes;

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
        float brightness = renderState.brightness();
        VertexConsumer consumer = bufferSource.getBuffer(RenderTypes.debugQuads());
/*
        consumer.addVertex(-0.5f, -0.5f, 0).setUv(brightness, 0);
        consumer.addVertex(-0.5f, 0.5f, 0).setUv(brightness, 0);
        consumer.addVertex(0.5f, 0.5f, 0).setUv(brightness, 0);
        consumer.addVertex(0.5f, -0.5f, 0).setUv(brightness, 0);
*/
        consumer.addVertex(-renderState.scale(), -renderState.scale(), 0).setColor(brightness, 0, 0, 1);
        consumer.addVertex(-renderState.scale(), renderState.scale(), 0).setColor(brightness, 0, 0, 1);
        consumer.addVertex(renderState.scale(), renderState.scale(), 0).setColor(brightness, 0, 0, 1);
        consumer.addVertex(renderState.scale(), -renderState.scale(), 0).setColor(brightness, 0, 0, 1);
    }

    @Override
    protected String getTextureLabel() {
        return "arsmagicalegacy: Color Wheel";
    }

    @Override
    protected float getTranslateY(int height, int guiScale) {
        return height / 2f;
    }
}
