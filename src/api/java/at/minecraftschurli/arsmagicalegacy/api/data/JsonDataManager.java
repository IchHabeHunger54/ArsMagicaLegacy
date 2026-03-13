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
     * @param id The id to query.
     * @return The object associated with the id.
     */
    T get(Identifier id);

    /**
     * @param id           The id to query.
     * @param defaultValue The default value to use if the id was not present.
     * @return The object associated with the id, or the default value if the id was not present.
     */
    T getOrDefault(Identifier id, T defaultValue);

    /**
     * @return An unmodifiable view of all entries in this data manager.
     */
    Map<Identifier, T> getAll();
}
