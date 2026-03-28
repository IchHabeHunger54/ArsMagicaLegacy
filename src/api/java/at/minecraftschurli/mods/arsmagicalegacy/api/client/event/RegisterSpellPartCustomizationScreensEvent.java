package at.minecraftschurli.mods.arsmagicalegacy.api.client.event;

import at.minecraftschurli.mods.arsmagicalegacy.api.client.screen.SpellPartCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.core.Holder;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Event that fires when {@link SpellPartCustomizationScreen.Factory}s are registered.
 * <p>
 * This event is not cancelable. This event is fired on the mod event bus, only on the physical client.
 */
public class RegisterSpellPartCustomizationScreensEvent extends Event implements IModBusEvent {
    private final Map<Holder<SpellPart>, SpellPartCustomizationScreen.Factory<?, ?>> screens = new HashMap<>();

    /**
     * Registers a {@link SpellPartCustomizationScreen.Factory}.
     *
     * @param key     The {@link SpellPart} that will open the {@link SpellPartCustomizationScreen}.
     * @param factory The {@link SpellPartCustomizationScreen.Factory} to register.
     */
    public synchronized <T> void register(Holder<SpellPart> key, SpellPartCustomizationScreen.Factory<T, ?> factory) {
        screens.put(key, factory);
    }

    /**
     * @return An unmodifiable view of all registered {@link SpellPartCustomizationScreen.Factory}s.
     */
    public Map<Holder<SpellPart>, SpellPartCustomizationScreen.Factory<?, ?>> getScreens() {
        return Collections.unmodifiableMap(screens);
    }
}
