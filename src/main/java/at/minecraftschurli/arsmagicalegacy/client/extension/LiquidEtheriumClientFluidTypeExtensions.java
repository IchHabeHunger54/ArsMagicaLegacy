package at.minecraftschurli.arsmagicalegacy.client.extension;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.joml.Vector3f;

public class LiquidEtheriumClientFluidTypeExtensions implements IClientFluidTypeExtensions {
    public static final LiquidEtheriumClientFluidTypeExtensions INSTANCE = new LiquidEtheriumClientFluidTypeExtensions();
    private static final ResourceLocation STILL_TEXTURE = ArsMagicaApi.modLoc("block/liquid_etherium_still");
    private static final ResourceLocation FLOWING_TEXTURE = ArsMagicaApi.modLoc("block/liquid_etherium_flowing");
    private static final ResourceLocation UNDERWATER_TEXTURE = ArsMagicaApi.modLoc("textures/misc/in_liquid_etherium.png");

    private LiquidEtheriumClientFluidTypeExtensions() {
    }

    @Override
    public ResourceLocation getStillTexture() {
        return STILL_TEXTURE;
    }

    @Override
    public ResourceLocation getFlowingTexture() {
        return FLOWING_TEXTURE;
    }

    @Override
    public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
        return UNDERWATER_TEXTURE;
    }

    @Override
    public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
        return new Vector3f(0.25f, 1, 0.75f);
    }
}
