package at.minecraftschurli.arsmagicalegacy.api.client;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Event that fires when {@link ParticleController}s are registered.
 * <p>
 * This event is not cancelable. This event is fired on the mod event bus, only on the physical client.
 */
public class RegisterParticleControllersEvent extends Event implements IModBusEvent {
    private final Map<ResourceLocation, ParticleController.Type> controllers = new HashMap<>();

    /**
     * Registers a {@link ParticleController}.
     *
     * @param key   The id of the {@link ParticleController}.
     * @param codec A {@link MapCodec} for the {@link ParticleController}.
     */
    public synchronized void register(ResourceLocation key, MapCodec<? extends ParticleController> codec) {
        controllers.put(key, new ParticleController.Type(key, codec));
    }

    /**
     * @return An unmodifiable view of all registered {@link ParticleController}s.
     */
    public Map<ResourceLocation, ParticleController.Type> getControllers() {
        return Collections.unmodifiableMap(controllers);
    }
}
