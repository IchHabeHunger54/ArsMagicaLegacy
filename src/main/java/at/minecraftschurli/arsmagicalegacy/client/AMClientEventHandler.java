package at.minecraftschurli.arsmagicalegacy.client;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.apiimpl.ArsMagicaClientApiImpl;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.layer.BarsLayer;
import at.minecraftschurli.arsmagicalegacy.client.screen.occulus.AffinityTabRenderer;
import at.minecraftschurli.arsmagicalegacy.client.screen.occulus.DefaultTabRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = ArsMagicaApi.MOD_ID, value = Dist.CLIENT)
final class AMClientEventHandler {
    @SubscribeEvent
    private static void clientSetup(FMLClientSetupEvent event) {
        ArsMagicaClientApiImpl.postEvent();
    }

    @SubscribeEvent
    private static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerBelowAll(ArsMagicaApi.modLoc("bars"), new BarsLayer());
    }

    @SubscribeEvent
    private static void registerOcculusTabRenderers(RegisterOcculusTabRenderersEvent event) {
        event.register(ArsMagicaApi.modLoc("default"), DefaultTabRenderer::new);
        event.register(ArsMagicaApi.modLoc("affinity"), AffinityTabRenderer::new);
    }

    @SubscribeEvent
    private static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(SkillAtlasHolder.INSTANCE.get());
    }
}
