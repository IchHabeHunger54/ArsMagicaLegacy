package at.minecraftschurli.mods.arsmagicalegacy.util;

import at.minecraftschurli.mods.arsmagicalegacy.api.data.JsonDataManager;
import com.mojang.serialization.Codec;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AMDataManager<T> extends SimpleJsonResourceReloadListener<T> implements JsonDataManager<T> {
    private final Map<Identifier, T> values = new HashMap<>();
    private final Identifier id;

    public AMDataManager(Identifier id, Codec<T> codec) {
        super(codec, FileToIdConverter.registry(ResourceKey.createRegistryKey(id)));
        this.id = id;
    }

    @Override
    public T get(Identifier id) {
        return values.get(id);
    }

    @Override
    public T getOrDefault(Identifier id, T defaultValue) {
        return values.getOrDefault(id, defaultValue);
    }

    @Override
    public Map<Identifier, T> getAll() {
        return Collections.unmodifiableMap(values);
    }

    @Override
    public Identifier id() {
        return id;
    }

    @Override
    protected void apply(Map<Identifier, T> map, ResourceManager resourceManager, ProfilerFiller profiler) {
        values.clear();
        values.putAll(map);
    }
}
