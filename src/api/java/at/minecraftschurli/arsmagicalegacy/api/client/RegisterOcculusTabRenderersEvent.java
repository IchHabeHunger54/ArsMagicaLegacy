package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Event that fires when {@link OcculusTabRenderer.Factory}s are registered.
 * <p>
 * This event is not cancelable. This event is fired on the main event bus, only on the physical client.
 */
@SuppressWarnings("unused")
public class RegisterOcculusTabRenderersEvent extends Event {
    private final Map<ResourceLocation, OcculusTabRenderer.Factory> renderers = new HashMap<>();

    /**
     * Registers an {@link OcculusTabRenderer.Factory}.
     *
     * @param key     The id of the {@link OcculusTabRenderer.Factory}. May be referenced in {@link OcculusTab}s.
     * @param factory The {@link OcculusTabRenderer.Factory} to register.
     */
    public void register(ResourceLocation key, OcculusTabRenderer.Factory factory) {
        renderers.put(key, factory);
    }

    /**
     * @return An unmodifiable view of all registered {@link OcculusTabRenderer.Factory}.
     */
    public Map<ResourceLocation, OcculusTabRenderer.Factory> getRenderers() {
        return Collections.unmodifiableMap(renderers);
    }
}
