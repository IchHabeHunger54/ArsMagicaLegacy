package at.minecraftschurli.mods.arsmagicalegacy.client.renderer;

import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Options;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

/// Adapted from [BeaconRenderer]
public final class BeamRenderer {
    private static final Identifier BEAM_LOCATION = Identifier.withDefaultNamespace("textures/entity/beacon/beacon_beam.png");
    private static final float BEAM_RADIUS = 0.02f;
    private static final float GLOW_RADIUS = 0.07f;

    private BeamRenderer() {}

    public static void submit(PoseStack stack, SubmitNodeCollector collector, boolean caster, Entity entity, Vec3 target, int color, float partialTick) {
        Player player = Objects.requireNonNull(AMClientUtil.player());
        Level level = AMClientUtil.level();
        Options options = AMClientUtil.mc().options;
        boolean firstPerson = caster && entity.getUUID().equals(player.getUUID()) && options.getCameraType().isFirstPerson();
        Vec3 origin = firstPerson ? entity.getPosition(partialTick).add(0, entity.getBbHeight() / 2, 0) : entity.getEyePosition(partialTick);
        double xd = target.x - origin.x;
        double zd = target.z - origin.z;
        float xRot = firstPerson ? entity.getViewXRot(partialTick) + 90 : Mth.wrapDegrees((float) Math.toDegrees(-Math.atan2(target.y - origin.y, Math.sqrt(xd * xd + zd * zd))) + 90);
        float yRot = firstPerson ? entity.getViewYRot(partialTick) : Mth.wrapDegrees((float) Math.toDegrees(Math.atan2(zd, xd)) - 90);
        float x = 0;
        float y = 0;
        float z = 0;
        if (firstPerson) {
            float fov = (options.fov().get() - 30) / 80f;
        } else if (caster) {

        }
        float height = (float) target.distanceTo(origin);
        float animationTime = level != null ? -Math.floorMod(level.getGameTime(), 40) - partialTick : 0f;
        float vOffset = Mth.frac(animationTime * 0.2f - Mth.floor(animationTime * 0.1f)) - 1;
        stack.pushPose();
        stack.translate(origin);
        stack.mulPose(Axis.YP.rotationDegrees(-yRot));
        stack.mulPose(Axis.XP.rotationDegrees(xRot));
        stack.translate(x, y, z);
        submit(stack, collector, true, GLOW_RADIUS, height, vOffset, height + vOffset, ARGB.color(32, color));
        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(animationTime * 2.25f - 45f));
        submit(stack, collector, false, BEAM_RADIUS, height, vOffset, height * (0.5f / BEAM_RADIUS) + vOffset, color);
        stack.popPose();
        stack.popPose();
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private static void submit(PoseStack stack, SubmitNodeCollector collector, boolean translucent, float radius, float height, float v0, float v1, int color) {
        float wnx = -radius;
        float wnz = -radius;
        float enx = -radius;
        float enz = radius;
        float wsx = radius;
        float wsz = -radius;
        float esx = radius;
        float esz = radius;
        collector.submitCustomGeometry(stack, RenderTypes.beaconBeam(BEAM_LOCATION, translucent), (pose, builder) -> {
            renderQuad(pose, builder, color, height, wnx, wnz, enx, enz, v0, v1);
            renderQuad(pose, builder, color, height, esx, esz, wsx, wsz, v0, v1);
            renderQuad(pose, builder, color, height, enx, enz, esx, esz, v0, v1);
            renderQuad(pose, builder, color, height, wsx, wsz, wnx, wnz, v0, v1);
        });
    }

    private static void renderQuad(PoseStack.Pose pose, VertexConsumer builder, int color, float height, float x0, float z0, float x1, float z1, float v0, float v1) {
        addVertex(pose, builder, color, x0, height, z0, 1f, v0);
        addVertex(pose, builder, color, x0, 0f, z0, 1f, v1);
        addVertex(pose, builder, color, x1, 0f, z1, 0f, v1);
        addVertex(pose, builder, color, x1, height, z1, 0f, v0);
    }

    private static void addVertex(PoseStack.Pose pose, VertexConsumer builder, int color, float x, float y, float z, float u, float v) {
        builder.addVertex(pose, x, y, z)
            .setColor(color)
            .setUv(u, v)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(LightCoordsUtil.FULL_BRIGHT)
            .setNormal(pose, 0f, 1f, 0f);
    }
}
