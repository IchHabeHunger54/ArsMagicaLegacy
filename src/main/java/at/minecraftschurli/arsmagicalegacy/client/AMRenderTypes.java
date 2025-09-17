package at.minecraftschurli.arsmagicalegacy.client;

import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color.ColorWheelShader;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;

public final class AMRenderTypes {
    private static final RenderStateShard.ShaderStateShard COLOR_WHEEL_SHADER = new RenderStateShard.ShaderStateShard(ColorWheelShader::getInstance);
    public static final RenderType COLOR_WHEEL = RenderType.create(
        "color_wheel",
        DefaultVertexFormat.POSITION_COLOR,
        VertexFormat.Mode.QUADS,
        256,
        false,
        false,
        RenderType.CompositeState.builder()
            .setShaderState(COLOR_WHEEL_SHADER)
            .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
            .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
            .setLayeringState(new RenderStateShard.LayeringStateShard("set_uniforms", () -> {
                setUniform("center", ColorWheelShader.getCenterX(), ColorWheelShader.getCenterY());
                setUniform("radius", ColorWheelShader.getRadius());
                setUniform("brightness", ColorWheelShader.getBrightness());
            }, () -> {}))
            .createCompositeState(false)
    );

    private static void setUniform(String name, float value) {
        ShaderInstance shader = RenderSystem.getShader();
        if (shader == null) return;
        Uniform uniform = shader.getUniform(name);
        if (uniform == null) return;
        uniform.set(value);
    }

    private static void setUniform(String name, float value1, float value2) {
        ShaderInstance shader = RenderSystem.getShader();
        if (shader == null) return;
        Uniform uniform = shader.getUniform(name);
        if (uniform == null) return;
        uniform.set(value1, value2);
    }
}
