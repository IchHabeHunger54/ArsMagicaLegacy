package at.minecraftschurli.mods.arsmagicalegacy.client.renderer;

import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class BeamRenderer {
    private static final Identifier BEAM_LOCATION = Identifier.withDefaultNamespace("textures/entity/beacon/beacon_beam.png");

    private BeamRenderer() {}

    public static void submit(PoseStack stack, SubmitNodeCollector collector, Vec3 from, Vec3 to, int color, float partialTick) {
        Level level = AMClientUtil.level();
        double dx = to.x - from.x;
        double dy = to.y - from.y;
        double dz = to.z - from.z;
        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(Mth.atan2(dz, dx)) + 90));
        stack.mulPose(Axis.XP.rotationDegrees((float) Math.toDegrees(-Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz))) + 90));
        stack.translate(-0.5, 0, -0.5);
        BeaconRenderer.submitBeaconBeam(stack, collector, BEAM_LOCATION, 1f, level != null ? Math.floorMod(level.getGameTime(), 40) + partialTick : 0f, 0, Mth.ceil(from.distanceTo(to)), color, 0.2f, 0.25f);
        stack.popPose();
    }
}
