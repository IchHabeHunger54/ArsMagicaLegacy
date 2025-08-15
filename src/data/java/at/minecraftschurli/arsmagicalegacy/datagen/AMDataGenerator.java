package at.minecraftschurli.arsmagicalegacy.datagen;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMBlockStateProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMItemModelProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMLanguageProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMBlockTagsProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMDatapackRegistryProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMItemTagsProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMLootTableProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMRecipeProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMSpellPartDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ArsMagicaApi.MOD_ID)
final class AMDataGenerator {
    @SubscribeEvent
    private static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(event.includeClient(), new AMBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new AMItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new AMLanguageProvider(output));
        AMBlockTagsProvider blockTags = generator.addProvider(event.includeServer(), new AMBlockTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AMDatapackRegistryProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new AMItemTagsProvider(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new AMLootTableProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new AMRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new AMSpellPartDataProvider(output, lookupProvider));
    }
}
