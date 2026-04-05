package at.minecraftschurli.mods.arsmagicalegacy.api.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.Ritual;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

/**
 * Data provider for {@link Ritual}s. Override {@link RitualProvider#generate(HolderLookup.Provider)} to generate your entries,
 * and use {@link RitualProvider#builder(String, RitualTrigger)} to create a new {@link RitualBuilder}.
 */
public abstract class RitualProvider extends AbstractDataProvider<Ritual<?>, RitualBuilder> {
    /**
     * @param output         The {@link PackOutput} to use. Get this from {@link GatherDataEvent}.
     * @param lookupProvider The lookup {@link CompletableFuture} to use. Get this from {@link GatherDataEvent}.
     * @param modId          Your mod id.
     */
    public RitualProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(PackOutput.Target.DATA_PACK, ArsMagicaApi.MOD_ID + "/ritual", "Ritual", Ritual.CODEC, output, lookupProvider, modId);
    }

    /**
     * Creates and adds a new {@link RitualBuilder}.
     *
     * @param name    The name of the ritual.
     * @param trigger The {@link RitualTrigger} to use.
     * @return The new {@link RitualBuilder}.
     */
    public final RitualBuilder builder(String name, RitualTrigger<?> trigger) {
        RitualBuilder builder = new RitualBuilder(Identifier.fromNamespaceAndPath(modId, name), trigger);
        add(builder);
        return builder;
    }
}
