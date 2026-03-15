package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.apiimpl.ArsMagicaClientApiImpl;
import at.minecraftschurli.arsmagicalegacy.datagen.AMPatchouliBookProvider;
import com.mojang.datafixers.util.Function3;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

@EventBusSubscriber(modid = ArsMagicaApi.MOD_ID)
final class AMClientDataGenerator {
    @SubscribeEvent
    private static void gatherData(GatherDataEvent.Client event) {
        ArsMagicaClientApiImpl.postEvents();
        DataGenerator.PackGenerator pack = event.getGenerator().getVanillaPack(true);
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        pack.addProvider(wrap(AMModelProvider::new, lookupProvider));
        pack.addProvider(AMParticleDescriptionProvider::new);
        pack.addProvider(wrap(AMParticleSpawnerProvider::new, lookupProvider));
        pack.addProvider(AMSoundDefinitionProvider::new);
        pack.addProvider(wrap(AMSpriteSourceProvider::new, lookupProvider));
        Map<String, String> cached = new HashMap<>();
        pack.addProvider(wrap(AMPatchouliBookProvider::new, lookupProvider, cached::put));
        pack.addProvider(wrap(AMLanguageProvider::new, cached));
    }

    private static <T extends DataProvider, P> DataProvider.Factory<T> wrap(BiFunction<PackOutput, P, T> provider, P param) {
        return output -> provider.apply(output, param);
    }

    private static <T extends DataProvider, P1, P2> DataProvider.Factory<T> wrap(Function3<PackOutput, P1, P2, T> provider, P1 param1, P2 param2) {
        return output -> provider.apply(output, param1, param2);
    }
}
