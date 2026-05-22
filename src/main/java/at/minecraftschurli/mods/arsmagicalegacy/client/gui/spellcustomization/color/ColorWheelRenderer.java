package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.color;

import at.minecraftschurli.mods.arsmagicalegacy.client.AMRenderTypes;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.MappableRingBuffer;
import org.joml.Vector2fc;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public final class ColorWheelRenderer {
    public static final ColorWheelRenderer INSTANCE = new ColorWheelRenderer();
    private static final int UBO_SIZE = new Std140SizeCalculator().putVec2().putFloat().putFloat().get();
    private final MappableRingBuffer ubo = new MappableRingBuffer(() -> "Color Wheel UBO", GpuBuffer.USAGE_UNIFORM | GpuBuffer.USAGE_MAP_WRITE, UBO_SIZE);

    private ColorWheelRenderer() {}

    public void render(Vector2fc center, float radius, float brightness) {
        try (GpuBuffer.MappedView view = RenderSystem.getDevice()
            .createCommandEncoder()
            .mapBuffer(ubo.currentBuffer(), false, true)) {
            Std140Builder.intoBuffer(view.data())
                .putVec2(center)
                .putFloat(radius)
                .putFloat(brightness);
        }
        RenderTarget renderTarget = AMClientUtil.mc().getMainRenderTarget();
        try (RenderPass renderPass = RenderSystem.getDevice()
            .createCommandEncoder()
            .createRenderPass(() -> "Color Wheel", renderTarget.getColorTextureView(), OptionalInt.empty(), renderTarget.getDepthTextureView(), OptionalDouble.empty())) {
            renderPass.setPipeline(AMRenderTypes.COLOR_WHEEL_PIPELINE);
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("ColorWheelInfo", this.ubo.currentBuffer());
            renderPass.draw(0, 0);
        }
    }
}
