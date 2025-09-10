package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Datagen helper to generate {@link SpellPartData}. Override {@link SpellPartDataProvider#generate(HolderLookup.Provider)} to generate your entries,
 * and use {@link SpellPartDataProvider#builder(DeferredHolder, double)} to create a new {@link SpellPartDataBuilder}.
 */
public abstract class SpellPartDataProvider implements DataProvider {
    private final String modId;
    private final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final List<SpellPartDataBuilder> builders = new ArrayList<>();

    /**
     * @param output         The {@link PackOutput} to use. Get this from {@link GatherDataEvent}.
     * @param lookupProvider The lookup {@link CompletableFuture} to use. Get this from {@link GatherDataEvent}.
     * @param modId          Your mod id.
     */
    public SpellPartDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, ArsMagicaApi.MOD_ID + "/spell_part");
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
                    .supplyAsync(() -> SpellPartData.CODEC.encodeStart(ops, builder.build()).getOrThrow(msg -> new RuntimeException("Failed to encode %s: %s".formatted(path, msg))))
                    .thenComposeAsync(json -> DataProvider.saveStable(output, json, path));
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName() {
        return "Spell Part Data: " + modId;
    }

    /**
     * @param part The {@link SpellPart} to generate data for.
     * @param mana The mana cost of the {@link SpellPart}.
     * @return A new {@link SpellPartDataBuilder}.
     */
    public SpellPartDataBuilder builder(DeferredHolder<SpellPart, ?> part, double mana) {
        SpellPartDataBuilder builder = new SpellPartDataBuilder(part.getId(), mana);
        builders.add(builder);
        return builder;
    }

    /**
     * Override this to generate your objects.
     *
     * @param provider The {@link HolderLookup.Provider} provided by the system. Use this to perform registry lookups if needed.
     */
    public abstract void generate(HolderLookup.Provider provider);
}
