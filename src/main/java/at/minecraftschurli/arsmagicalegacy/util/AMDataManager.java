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
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AMDataManager<T> extends SimpleJsonResourceReloadListener implements JsonDataManager<T> {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().setLenient().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(AMDataManager.class);
    private final Codec<Optional<WithConditions<T>>> codec;
    private final Map<ResourceLocation, T> values = new HashMap<>();

    public AMDataManager(String directory, Codec<T> codec) {
        super(GSON, ArsMagicaApi.MOD_ID + "/" + directory);
        this.codec = ConditionalOps.createConditionalCodecWithConditions(codec);
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
            ResourceLocation key = entry.getKey();
            codec.parse(makeConditionalOps(), entry.getValue())
                .ifSuccess(e -> e.map(WithConditions::carrier).ifPresentOrElse(o -> values.put(key, o), () -> LOGGER.debug("Skipping loading data file {} as its conditions were not met", key)))
                .ifError(e -> LOGGER.error("Parsing error loading data file {}: {}", key, e.message()));
        }
    }
}
