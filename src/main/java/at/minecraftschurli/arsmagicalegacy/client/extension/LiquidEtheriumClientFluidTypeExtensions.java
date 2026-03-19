package at.minecraftschurli.arsmagicalegacy.client.extension;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.joml.Vector4f;

public class LiquidEtheriumClientFluidTypeExtensions implements IClientFluidTypeExtensions {
    public static final LiquidEtheriumClientFluidTypeExtensions INSTANCE = new LiquidEtheriumClientFluidTypeExtensions();
    private static final Identifier STILL_TEXTURE = ArsMagicaApi.id("block/liquid_etherium_still");
    private static final Identifier FLOWING_TEXTURE = ArsMagicaApi.id("block/liquid_etherium_flowing");
    private static final Identifier UNDERWATER_TEXTURE = ArsMagicaApi.id("textures/misc/in_liquid_etherium.png");

    private LiquidEtheriumClientFluidTypeExtensions() {
    }

    @Override
    public Identifier getStillTexture() {
        return STILL_TEXTURE;
    }

    @Override
    public Identifier getFlowingTexture() {
        return FLOWING_TEXTURE;
    }

    @Override
    public Identifier getRenderOverlayTexture(Minecraft mc) {
        return UNDERWATER_TEXTURE;
    }

    @Override
    public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
        fluidFogColor.x = 0.5f;
        fluidFogColor.y = 0.65625f;
        fluidFogColor.z = 0.9375f;
    }
}
