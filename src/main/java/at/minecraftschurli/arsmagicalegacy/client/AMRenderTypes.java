package at.minecraftschurli.arsmagicalegacy.client;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SpellIconAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color.ColorWheelShader;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

import java.util.Optional;

public final class AMRenderTypes {
    private static final RenderStateShard.ShaderStateShard COLOR_WHEEL_SHADER = new RenderStateShard.ShaderStateShard(ColorWheelShader::getInstance);
    public static final RenderType COLOR_WHEEL = RenderType.create(
        ArsMagicaApi.modLoc("color_wheel").toString().replace(":", "_"),
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
    public static final RenderType LINES_WITH_WIDTH = RenderType.create(
        ArsMagicaApi.modLoc("lines_with_width").toString().replace(":", "_"),
        DefaultVertexFormat.POSITION_COLOR,
        VertexFormat.Mode.QUADS,
        8192,
        false,
        false,
        RenderType.CompositeState.builder()
            .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
            .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
            .createCompositeState(false));

    private static Optional<Uniform> getUniform(String name) {
        return Optional.ofNullable(RenderSystem.getShader()).map(shader -> shader.getUniform(name));
    }
}
