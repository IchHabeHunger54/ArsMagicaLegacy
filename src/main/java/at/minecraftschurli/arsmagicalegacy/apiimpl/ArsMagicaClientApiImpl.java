package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.arsmagicalegacy.api.client.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModLoader;

import java.util.HashMap;
import java.util.Map;

public final class ArsMagicaClientApiImpl extends ArsMagicaClientApi {
    private static final Map<ResourceLocation, OcculusTabRenderer.Factory> RENDERERS = new HashMap<>();

    @Override
    protected OcculusTabRenderer.Factory getOcculusTabRendererFactory(OcculusTab tab) {
        return RENDERERS.get(tab.renderer());
    }

    public static void postEvent() {
        RENDERERS.putAll(ModLoader.postEventWithReturn(new RegisterOcculusTabRenderersEvent()).getRenderers());
    }
}
