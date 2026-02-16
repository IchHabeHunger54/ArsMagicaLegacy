package at.minecraftschurli.arsmagicalegacy.util;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.data.JsonDataManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AMDataManager<T> extends SimpleJsonResourceReloadListener implements JsonDataManager<T> {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setLenient().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(AMDataManager.class);
    private final Codec<T> codec;
    private final Map<ResourceLocation, T> values = new HashMap<>();

    public AMDataManager(String directory, Codec<T> codec) {
        super(GSON, ArsMagicaApi.MOD_ID + "/" + directory);
        this.codec = codec;
    }

    @Override
    public T get(ResourceLocation id) {
        return values.get(id);
    }

    @Override
    public T getOrDefault(ResourceLocation id, T defaultValue) {
        return values.getOrDefault(id, defaultValue);
    }

    @Override
    public Map<ResourceLocation, T> getAll() {
        return Collections.unmodifiableMap(values);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler) {
        values.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            codec.parse(makeConditionalOps(), entry.getValue())
                .ifSuccess(e -> values.put(entry.getKey(), e))
                .ifError(e -> LOGGER.error("Failed to parse data file {}: {}", entry.getKey(), e.message()));
        }
    }
}
