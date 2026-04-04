package at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public abstract class ModelEntityRenderer<T extends Entity, S extends ModelEntityRenderState, M extends EntityModel<S>> extends EntityRenderer<T, S> {
    private final M model;
    private final RenderLayer<S, M> layer;

    public ModelEntityRenderer(EntityRendererProvider.Context context, M model, RenderLayer<S, M> layer) {
        super(context);
        this.model = model;
        this.layer = layer;
    }

    @Override
    public void extractRenderState(T entity, S state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.bodyRot = getBodyRot(entity, partialTicks);
        state.yRot = Mth.wrapDegrees(getHeadRot(entity, partialTicks) - state.bodyRot);
        state.xRot = entity.getXRot(partialTicks);
    }

    @Override
    public void submit(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(180 - state.bodyRot));
        poseStack.scale(-1, -1, 1);
        poseStack.translate(0, -1.501f, 0);
        RenderType renderType = model.renderType(getTexture(state));
        submitNodeCollector.submitModel(model, state, poseStack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, null, state.outlineColor, null);
        model.setupAnim(state);
        layer.submit(poseStack, submitNodeCollector, state.lightCoords, state, state.yRot, state.xRot);
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    protected abstract Identifier getTexture(S state);

    protected float getBodyRot(T entity, float partialTick) {
        return entity.getYRot(partialTick);
    }

    protected float getHeadRot(T entity, float partialTick) {
        return entity.getYRot(partialTick);
    }
}
