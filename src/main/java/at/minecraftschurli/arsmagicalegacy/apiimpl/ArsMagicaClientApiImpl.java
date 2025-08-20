package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.arsmagicalegacy.api.client.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.NeoForge;

import java.util.HashMap;
import java.util.Map;

public final class ArsMagicaClientApiImpl extends ArsMagicaClientApi {
    private static final Map<ResourceLocation, OcculusTabRenderer.Factory> RENDERERS = new HashMap<>();

    @Override
    protected OcculusTabRenderer.Factory _getOcculusTabRendererFactory(OcculusTab tab) {
        return RENDERERS.get(tab.renderer());
    }

    public static void postEvent() {
        RENDERERS.putAll(NeoForge.EVENT_BUS.post(new RegisterOcculusTabRenderersEvent()).getRenderers());
    }
}
