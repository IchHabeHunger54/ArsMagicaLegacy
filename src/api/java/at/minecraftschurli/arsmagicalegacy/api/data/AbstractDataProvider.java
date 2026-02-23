package at.minecraftschurli.arsmagicalegacy.api.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Abstract superclass for most data providers this mod adds.
 *
 * @param <T> The type of the objects being generated.
 * @param <B> The builder type to use.
 */
public abstract class AbstractDataProvider<T, B extends AbstractDataProvider.Builder<T>> implements DataProvider {
    private static final String EXCEPTION_MESSAGE = "Failed to encode %s: %s";
    private final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    protected final String modId;
    protected final String name;
    private final Codec<T> codec;
    private final List<B> builders = new ArrayList<>();

    /**
     * @param target         The {@link net.minecraft.data.PackOutput.Target} to use.
     * @param folder         The folder location to use, relative to {@code assets/} or {@code data/}.
     * @param name           The name of the provider, for use in {@link AbstractDataProvider#getName()}.
     * @param codec          The {@link Codec} to use.
     * @param output         The {@link PackOutput} to use. Get this from {@link GatherDataEvent}.
     * @param lookupProvider The lookup {@link CompletableFuture} to use. Get this from {@link GatherDataEvent}.
     * @param modId          Your mod id.
     */
    public AbstractDataProvider(PackOutput.Target target, String folder, String name, Codec<T> codec, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        this.name = name;
        this.codec = codec;
        this.pathProvider = output.createPathProvider(target, folder);
        this.lookupProvider = lookupProvider;
        this.modId = modId;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return lookupProvider.thenCompose(provider -> {
            generate(provider);
            DynamicOps<JsonElement> ops = provider.createSerializationContext(ConditionalOps.create(JsonOps.INSTANCE, provider));
            Set<ResourceLocation> ids = Collections.synchronizedSet(new HashSet<>());
            return CompletableFuture.allOf(builders.stream().map(builder -> {
                if (!ids.add(builder.id)) throw new IllegalStateException("Duplicate datagenned object " + builder.id);
                Path path = pathProvider.json(builder.id);
                return CompletableFuture
                    .supplyAsync(() -> {
                        JsonObject json = codec.encodeStart(ops, builder.build()).getOrThrow(message -> new RuntimeException(EXCEPTION_MESSAGE.formatted(path, message))).getAsJsonObject();
                        List<ICondition> conditions = builder.getConditions();
                        if (!conditions.isEmpty()) {
                            json.add(ConditionalOps.DEFAULT_CONDITIONS_KEY, ICondition.LIST_CODEC.encodeStart(ops, conditions).getOrThrow(message -> new RuntimeException(EXCEPTION_MESSAGE.formatted(path, message))));
                        }
                        return json;
                    })
                    .thenComposeAsync(json -> DataProvider.saveStable(output, json, path));
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName() {
        return name + ": " + modId;
    }

    /**
     * @param builder The builder to add.
     */
    public void add(B builder) {
        builders.add(builder);
    }

    /**
     * Override this to generate your objects.
     *
     * @param provider The {@link HolderLookup.Provider} provided by the system. Use this to perform registry lookups if needed.
     */
    public abstract void generate(HolderLookup.Provider provider);

    /**
     * Abstract superclass for all data builders this mod adds.
     *
     * @param <T> The type of the objects being built.
     */
    public static abstract class Builder<T> {
        public final ResourceLocation id;
        private final List<ICondition> conditions = new ArrayList<>();

        /**
         * @param id The id of the object being built.
         */
        public Builder(ResourceLocation id) {
            this.id = id;
        }

        /**
         * Adds a {@link ICondition} to the builder.
         *
         * @param condition The {@link ICondition} to add.
         * @return This builder, for chaining.
         */
        public Builder<T> addCondition(ICondition condition) {
            conditions.add(condition);
            return this;
        }

        /**
         * @return All {@link ICondition} in the builder.
         */
        public List<ICondition> getConditions() {
            return Collections.unmodifiableList(conditions);
        }

        /**
         * @return The built object.
         */
        public abstract T build();
    }
}
