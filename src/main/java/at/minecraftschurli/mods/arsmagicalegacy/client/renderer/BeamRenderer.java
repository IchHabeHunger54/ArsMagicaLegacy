package at.minecraftschurli.mods.arsmagicalegacy.client.renderer;

import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;

/// Adapted from [BeaconRenderer]
public final class BeamRenderer {
    private static final Identifier BEAM_LOCATION = Identifier.withDefaultNamespace("textures/entity/beacon/beacon_beam.png");

    private BeamRenderer() {}

    public static void submit(PoseStack stack, SubmitNodeCollector collector, int color) {
        Minecraft mc = AMClientUtil.mc();
        Level level = AMClientUtil.level();
        float partialTicks = mc.getDeltaTracker().getGameTimeDeltaTicks();
        float animationTime = level != null ? Math.floorMod(level.getGameTime(), 40) + partialTicks : 0f;
        BeaconRenderer.submitBeaconBeam(stack, collector, BEAM_LOCATION, 1f, animationTime, 0, 2048, color, 0.2f, 0.25f);
    }
}
