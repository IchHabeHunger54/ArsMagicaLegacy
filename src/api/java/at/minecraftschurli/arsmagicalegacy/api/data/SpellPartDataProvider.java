package at.minecraftschurli.arsmagicalegacy.api.data;

import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartData;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.concurrent.CompletableFuture;

public abstract class SpellPartDataProvider extends AbstractDataProvider<SpellPartData> {
    public SpellPartDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super("spell_part", "Spell Part Data", SpellPartData.CODEC, output, lookupProvider, modId);
    }

    public SpellPartData.Builder builder(DeferredHolder<SpellPart, ?> part, float mana) {
        return new SpellPartData.Builder(part.getId(), mana);
    }
}
