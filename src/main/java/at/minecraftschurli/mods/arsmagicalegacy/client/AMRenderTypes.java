package at.minecraftschurli.mods.arsmagicalegacy.client;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public final class AMRenderTypes {
    private static final Identifier MAGITECH_GOGGLES_ID = ArsMagicaApi.id("magitech_goggles");
    public static final RenderPipeline MAGITECH_GOGGLES_PIPELINE = RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
        .withLocation(MAGITECH_GOGGLES_ID)
        .withDepthStencilState(Optional.empty())
        .build();
    public static final RenderType MAGITECH_GOGGLES = RenderType.create(MAGITECH_GOGGLES_ID.toString(), RenderSetup.builder(MAGITECH_GOGGLES_PIPELINE)
        .sortOnUpload()
        .createRenderSetup());

    private AMRenderTypes() {}
}
