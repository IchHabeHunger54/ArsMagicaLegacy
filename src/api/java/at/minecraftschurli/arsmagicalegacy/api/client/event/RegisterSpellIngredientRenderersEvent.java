package at.minecraftschurli.arsmagicalegacy.api.client.event;

import at.minecraftschurli.arsmagicalegacy.api.client.SpellIngredientRenderer;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Event that fires when {@link SpellIngredientRenderer}s are registered.
 * <p>
 * This event is not cancelable. This event is fired on the mod event bus, only on the physical client.
 */
public class RegisterSpellIngredientRenderersEvent extends Event implements IModBusEvent {
    private final Map<MapCodec<? extends SpellIngredient>, SpellIngredientRenderer<?>> renderers = new HashMap<>();

    /**
     * Registers a {@link SpellIngredientRenderer}.
     *
     * @param type     The {@link MapCodec} of the {@link SpellIngredient} to associate the renderer with.
     * @param renderer The {@link SpellIngredientRenderer} to register.
     */
    public synchronized <T extends SpellIngredient> void register(MapCodec<? extends SpellIngredient> type, SpellIngredientRenderer<T> renderer) {
        renderers.put(type, renderer);
    }

    /**
     * @return An unmodifiable view of all registered {@link SpellIngredientRenderer}s.
     */
    public Map<MapCodec<? extends SpellIngredient>, SpellIngredientRenderer<?>> getRenderers() {
        return Collections.unmodifiableMap(renderers);
    }
}
