package at.minecraftschurli.arsmagicalegacy.datagen;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMBlockStateProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMItemModelProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMLanguageProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMSoundDefinitionProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMSpriteSourceProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMAdvancementProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMBlockTagsProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMItemTagsProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMLootTableProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMMagicProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMRecipeProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMSpellPartDataProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMWorldgenProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
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
        generator.addProvider(event.includeClient(), new AMSoundDefinitionProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new AMSpriteSourceProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AMAdvancementProvider(output, lookupProvider, existingFileHelper));
        AMBlockTagsProvider blockTags = generator.addProvider(event.includeServer(), new AMBlockTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AMItemTagsProvider(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        lookupProvider = generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output, lookupProvider, new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, AMWorldgenProvider::addConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, AMWorldgenProvider::addPlacedFeatures)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, AMWorldgenProvider::addBiomeModifiers)
            .add(AMRegistryKeys.AFFINITY, AMMagicProvider::addAffinities)
            .add(AMRegistryKeys.ALTAR_CAP_MATERIAL, AMMagicProvider::addAltarCapMaterials)
            .add(AMRegistryKeys.ALTAR_MATERIAL, AMMagicProvider::addAltarMaterials)
            .add(AMRegistryKeys.OCCULUS_TAB, AMMagicProvider::addOcculusTabs)
            .add(AMRegistryKeys.SKILL_POINT, AMMagicProvider::addSkillPoints)
            .add(AMRegistryKeys.SKILL, AMMagicProvider::addSkills),
            Set.of(ArsMagicaApi.MOD_ID))).getRegistryProvider();
        generator.addProvider(event.includeServer(), new AMLootTableProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new AMRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new AMSpellPartDataProvider(output, lookupProvider));
    }
}
