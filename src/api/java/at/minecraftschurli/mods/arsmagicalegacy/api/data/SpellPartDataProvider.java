package at.minecraftschurli.mods.arsmagicalegacy.api.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPartData;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.concurrent.CompletableFuture;

/**
 * Data provider for {@link SpellPartData}. Override {@link SpellPartDataProvider#generate(HolderLookup.Provider)} to generate your entries,
 * and use {@link SpellPartDataProvider#builder(DeferredHolder, double)} to create a new {@link SpellPartDataBuilder}.
 */
public abstract class SpellPartDataProvider extends AbstractDataProvider<SpellPartData, SpellPartDataBuilder> {
    /**
     * @param output         The {@link PackOutput} to use. Get this from {@link GatherDataEvent}.
     * @param lookupProvider The lookup {@link CompletableFuture} to use. Get this from {@link GatherDataEvent}.
     * @param modId          Your mod id.
     */
    public SpellPartDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(PackOutput.Target.DATA_PACK, ArsMagicaApi.MOD_ID + "/spell_part", "Spell Part Data", SpellPartData.CODEC, output, lookupProvider, modId);
    }

    /**
     * Creates and adds a new {@link SpellPartDataBuilder}.
     *
     * @param part The {@link SpellPart} to generate data for.
     * @param mana The mana cost of the {@link SpellPart}.
     * @return The new {@link SpellPartDataBuilder}.
     */
    public SpellPartDataBuilder builder(DeferredHolder<SpellPart, ?> part, double mana) {
        SpellPartDataBuilder builder = new SpellPartDataBuilder(part.getId(), mana);
        add(builder);
        return builder;
    }
}
