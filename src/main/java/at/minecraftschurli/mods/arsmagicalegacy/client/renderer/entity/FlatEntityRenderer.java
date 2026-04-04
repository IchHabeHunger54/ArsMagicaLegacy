package at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity;

import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.TextureAtlasSpriteRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.jspecify.annotations.NullUnmarked;

public abstract class FlatEntityRenderer<T extends Entity, S extends FlatEntityRenderer.State> extends EntityRenderer<T, S> {
    private static final float RAD = (float) (Math.PI / 180);
    private static final Vector3f FORWARDS = new Vector3f(0, 0, -1);
    private static final Vector3f UP = new Vector3f(0, 1, 0);
    private static final Vector3f LEFT = new Vector3f(-1, 0, 0);

    protected FlatEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void extractRenderState(T entity, S state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        Minecraft mc = AMClientUtil.mc();
        // Still need to go through the main camera because CameraRenderState doesn't give us what we need
        Camera gameCamera = mc.gameRenderer.getMainCamera();
        state.quaternion.rotationYXZ(-gameCamera.yRot() * RAD, gameCamera.xRot() * RAD, -gameCamera.getRoll() * RAD);
        FORWARDS.rotate(state.quaternion, new Vector3f(gameCamera.forwardVector()));
        UP.rotate(state.quaternion, new Vector3f(gameCamera.upVector()));
        LEFT.rotate(state.quaternion, new Vector3f(gameCamera.leftVector()));
        state.sprite = mc.getAtlasManager().get(Sheets.BLOCK_ENTITIES_MAPPER.apply(getTextureLocation()));
    }

    @Override
    public void submit(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(state.quaternion);
        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.translucentMovingBlock(), new TextureAtlasSpriteRenderer(state.sprite, state.lightCoords));
        poseStack.popPose();
    }

    public abstract Identifier getTextureLocation();

    @NullUnmarked
    public static class State extends EntityRenderState {
        public Quaternionf quaternion = new Quaternionf();
        public TextureAtlasSprite sprite;
    }
}
