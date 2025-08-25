package at.minecraftschurli.arsmagicalegacy.api.data;

import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartData;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.concurrent.CompletableFuture;

/**
 * Datagen helper to generate {@link SpellPartData}. Override {@link AbstractDataProvider#generate(HolderLookup.Provider)} to generate your entries,
 * and use {@link AbstractDataProvider#add(Builder)} to add a specific {@link SpellPartData.Builder}.
 */
public abstract class SpellPartDataProvider extends AbstractDataProvider<SpellPartData> {
    /**
     * @param output         The {@link PackOutput} to use. Get this from {@link GatherDataEvent}.
     * @param lookupProvider The lookup {@link CompletableFuture} to use. Get this from {@link GatherDataEvent}.
     * @param modId          Your mod id.
     */
    public SpellPartDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super("spell_part", "Spell Part Data", SpellPartData.CODEC, output, lookupProvider, modId);
    }

    /**
     * @param part The {@link SpellPart} to generate data for.
     * @param mana The mana cost of the {@link SpellPart}.
     * @return A new {@link SpellPartData.Builder}.
     */
    public SpellPartData.Builder builder(DeferredHolder<SpellPart, ?> part, double mana) {
        return new SpellPartData.Builder(part.getId(), mana);
    }
}
