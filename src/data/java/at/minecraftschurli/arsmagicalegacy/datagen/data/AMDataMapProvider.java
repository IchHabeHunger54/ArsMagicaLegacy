package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class AMDataMapProvider extends DataMapProvider {
    public AMDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(NeoForgeDataMaps.COMPOSTABLES)
            .add(AMItems.WITCHWOOD_LEAVES.getId(), new Compostable(0.3f, false), false)
            .add(AMItems.WITCHWOOD_SAPLING.getId(), new Compostable(0.3f, false), false)
            .add(AMItems.AUM.getId(), new Compostable(0.65f, false), false)
            .add(AMItems.CERUBLOSSOM.getId(), new Compostable(0.65f, false), false)
            .add(AMItems.DESERT_NOVA.getId(), new Compostable(0.65f, false), false)
            .add(AMItems.TARMA_ROOT.getId(), new Compostable(0.65f, false), false)
            .add(AMItems.WAKEBLOOM.getId(), new Compostable(0.65f, false), false);
    }
}
