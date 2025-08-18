package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.data.SpellPartDataProvider;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public final class AMSpellPartDataProvider extends SpellPartDataProvider {
    public AMSpellPartDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        add(builder(AMSpells.SELF, 0.5));
        add(builder(AMSpells.HEAL, 60));
    }
}
