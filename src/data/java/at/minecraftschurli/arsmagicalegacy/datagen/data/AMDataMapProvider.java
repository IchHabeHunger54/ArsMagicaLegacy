package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.etherium.ObeliskFuel;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public final class AMDataMapProvider extends DataMapProvider {
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
        builder(ObeliskFuel.DATA_MAP)
            .add(AMTags.Items.DUSTS_VINTEUM, new ObeliskFuel(200, 1), false)
            .add(AMTags.Items.STORAGE_BLOCKS_VINTEUM, new ObeliskFuel(900, 2), false);
            //.add(AMItems.LIQUID_ESSENCE_BUCKET.get(), new ObeliskFuel(1000, 2));
    }
}
