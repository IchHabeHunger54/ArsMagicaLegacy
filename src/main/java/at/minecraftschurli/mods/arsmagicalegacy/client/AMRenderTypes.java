/* TODO render pipeline
package at.minecraftschurli.arsmagicalegacy.client;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SpellIconAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.blaze3d.opengl.Uniform;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderType;

import java.util.Optional;

public final class AMRenderTypes {
    public static final RenderPipeline COLOR_WHEEL_PIPELINE = RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
        .withLocation(ArsMagicaApi.id("pipeline/color_wheel"))
        .withFragmentShader(ArsMagicaApi.id("core/color_wheel"))
        .withVertexShader(ArsMagicaApi.id("core/color_wheel"))
        .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS)
        .build();
    public static final RenderType COLOR_WHEEL = RenderType.create(
        ArsMagicaApi.id("color_wheel").toString().replace(":", "_"),
        DefaultVertexFormat.POSITION_COLOR,
        VertexFormat.Mode.QUADS,
        256,
        false,
        false,
        RenderType.CompositeState.builder()
            .setShaderState(COLOR_WHEEL_SHADER)
            .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
            .setLayeringState(new RenderStateShard.LayeringStateShard("set_uniforms", () -> {
                getUniform("center").ifPresent(uniform -> uniform.set(ColorWheelShader.getCenterX(), ColorWheelShader.getCenterY()));
                getUniform("radius").ifPresent(uniform -> uniform.set(ColorWheelShader.getRadius()));
                getUniform("brightness").ifPresent(uniform -> uniform.set(ColorWheelShader.getBrightness()));
            }, () -> {}))
            .createCompositeState(false));
    public static final RenderType SPELL_ICON = RenderType.itemEntityTranslucentCull(SpellIconAtlasHolder.ATLAS);
    public static final RenderType SPELL_ICON_FABULOUS = RenderType.entityTranslucentCull(SpellIconAtlasHolder.ATLAS);
    public static final RenderType OUTLINE = RenderType.create(
        ArsMagicaApi.id("outline").toString().replace(":", "_"),
        DefaultVertexFormat.POSITION_TEX_COLOR,
        VertexFormat.Mode.QUADS,
        8192,
        false,
        false,
        RenderType.CompositeState.builder()
            .setShaderState(RenderStateShard.RENDERTYPE_OUTLINE_SHADER)
            .setTextureState(new RenderStateShard.EmptyTextureStateShard(() -> RenderSystem.setShaderTexture(0, AMUtil.MISSINGNO), () -> {}))
            .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
            .setOutputState(RenderStateShard.OUTLINE_TARGET)
            .createCompositeState(RenderType.OutlineProperty.IS_OUTLINE));

    private static Optional<Uniform> getUniform(String name) {
        return Optional.ofNullable(RenderSystem.getShader()).map(shader -> shader.getUniform(name));
    }
}
*/
