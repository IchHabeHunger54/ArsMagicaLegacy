package at.minecraftschurli.arsmagicalegacy.api.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.Map;

/**
 * Interface declaring additional operations on a reload listener. Get various instances from {@link ArsMagicaApi}.
 *
 * @param <T> The type of data in the data manager.
 */
public interface JsonDataManager<T> extends PreparableReloadListener {
    /**
     * {@return The object associated with the id.}
     * 
     * @param id The id to query.
     */
    T get(Identifier id);

    /**
     * {@return The object associated with the id, or the default value if the id was not present.}
     * 
     * @param id           The id to query.
     * @param defaultValue The default value to use if the id was not present.
     */
    T getOrDefault(Identifier id, T defaultValue);

    /**
     * {@return An unmodifiable view of all entries in this data manager.}
     */
    Map<Identifier, T> getAll();

    /**
     * {@return The id of this data manager.}
     */
    Identifier id();
}
