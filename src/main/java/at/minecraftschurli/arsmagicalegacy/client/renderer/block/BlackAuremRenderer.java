package at.minecraftschurli.arsmagicalegacy.client.renderer.block;

import at.minecraftschurli.arsmagicalegacy.blockentity.BlackAuremBlockEntity;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class BlackAuremRenderer implements BlockEntityRenderer<BlackAuremBlockEntity> {
    private static final float RAD = (float) (Math.PI / 180);
    private static final Vector3f FORWARDS = new Vector3f(0, 0, -1);
    private static final Vector3f UP = new Vector3f(0, 1, 0);
    private static final Vector3f LEFT = new Vector3f(-1, 0, 0);
    private final Quaternionf quaternion = new Quaternionf();

    @SuppressWarnings("unused")
    public BlackAuremRenderer(BlockEntityRendererProvider.Context context) {
    }

    @SuppressWarnings("deprecation")
    @Override
    public void render(BlackAuremBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Minecraft mc = AMClientUtil.mc();
        Camera camera = mc.gameRenderer.getMainCamera();
        rotate(camera.getXRot(), camera.getYRot(), camera.getRoll(), camera.getLookVector(), camera.getUpVector(), camera.getLeftVector());
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(quaternion);
        poseStack.mulPose(Axis.ZP.rotation(AMClientUtil.player().tickCount / 10f % 360));
        TextureAtlasSprite sprite = mc.getBlockRenderer().getBlockModelShaper().getParticleIcon(blockEntity.getBlockState());
        VertexConsumer buffer = bufferSource.getBuffer(RenderType.translucent());
        buffer.addVertex(poseStack.last().pose(), -1, -1, 0).setColor(1f, 1f, 1f, 1f).setUv(sprite.getU1(), sprite.getV1()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack.last(), 0, 1, 0);
        buffer.addVertex(poseStack.last().pose(), -1, 1, 0).setColor(1f, 1f, 1f, 1f).setUv(sprite.getU1(), sprite.getV0()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack.last(), 0, 1, 0);
        buffer.addVertex(poseStack.last().pose(), 1, 1, 0).setColor(1f, 1f, 1f, 1f).setUv(sprite.getU0(), sprite.getV0()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack.last(), 0, 1, 0);
        buffer.addVertex(poseStack.last().pose(), 1, -1, 0).setColor(1f, 1f, 1f, 1f).setUv(sprite.getU0(), sprite.getV1()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack.last(), 0, 1, 0);
        poseStack.popPose();
    }

    // Adapted from Camera#setRotation
    private void rotate(float xRot, float yRot, float roll, Vector3f forwards, Vector3f up, Vector3f left) {
        quaternion.rotationYXZ(-yRot * RAD, xRot * RAD, -roll * RAD);
        FORWARDS.rotate(quaternion, forwards);
        UP.rotate(quaternion, up);
        LEFT.rotate(quaternion, left);
    }
}
