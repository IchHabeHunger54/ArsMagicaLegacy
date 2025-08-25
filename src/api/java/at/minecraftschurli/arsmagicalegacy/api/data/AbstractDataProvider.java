package at.minecraftschurli.arsmagicalegacy.api.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Abstract helper class for {@link Codec}-based {@link DataProvider}s added by Ars Magica: Legacy.
 *
 * @param <T> The type of the thing being generated.
 */
public abstract class AbstractDataProvider<T> implements DataProvider {
    protected final String name;
    protected final Codec<T> codec;
    protected final String modId;
    private final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final List<Builder<T>> builders = new ArrayList<>();

    /**
     * @param folder         The name of the folder objects will be written to. Will be prefixed with "arsmagicalegacy/".
     * @param name           The human-readable name of the provider. Used in {@link AbstractDataProvider#getName()}.
     * @param codec          The {@link Codec} used to write objects.
     * @param output         The {@link PackOutput} to use. Get this from {@link GatherDataEvent}.
     * @param lookupProvider The lookup {@link CompletableFuture} to use. Get this from {@link GatherDataEvent}.
     * @param modId          Your mod id.
     */
    public AbstractDataProvider(String folder, String name, Codec<T> codec, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        this.name = name;
        this.codec = codec;
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, ArsMagicaApi.MOD_ID + "/" + folder);
        this.lookupProvider = lookupProvider;
        this.modId = modId;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return lookupProvider.thenCompose(provider -> {
            generate(provider);
            DynamicOps<JsonElement> ops = provider.createSerializationContext(JsonOps.INSTANCE);
            Set<ResourceLocation> ids = Collections.synchronizedSet(new HashSet<>());
            return CompletableFuture.allOf(builders.stream().map(builder -> {
                if (!ids.add(builder.id)) throw new IllegalStateException("Duplicate datagenned object " + builder.id);
                Path path = pathProvider.json(builder.id);
                return CompletableFuture
                    .supplyAsync(() -> codec.encodeStart(ops, builder.build()).getOrThrow(msg -> new RuntimeException("Failed to encode %s: %s".formatted(path, msg))))
                    .thenComposeAsync(json -> DataProvider.saveStable(output, json, path));
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName() {
        return name + ": " + modId;
    }

    /**
     * Adds an object to the {@link AbstractDataProvider}.
     *
     * @param builder The {@link Builder} of the object to add.
     */
    public void add(Builder<T> builder) {
        builders.add(builder);
    }

    /**
     * Override this to generate your objects.
     *
     * @param provider The {@link HolderLookup.Provider} provided by the system. Use this to perform registry lookups if needed.
     */
    public abstract void generate(HolderLookup.Provider provider);

    /**
     * Abstract builder class.
     *
     * @param <T> The type of the thing being generated.
     */
    public static abstract class Builder<T> {
        private final ResourceLocation id;

        /**
         * @param id The id of the thing being generated.
         */
        public Builder(ResourceLocation id) {
            this.id = id;
        }

        /**
         * @return A "built" version of the thing being generated.
         */
        public abstract T build();
    }
}
