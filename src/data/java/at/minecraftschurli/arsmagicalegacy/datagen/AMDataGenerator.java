package at.minecraftschurli.arsmagicalegacy.datagen;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.apiimpl.ArsMagicaClientApiImpl;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMBlockStateProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMItemModelProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMLanguageProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMParticleDescriptionProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMParticleSpawnerProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMSoundDefinitionProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.assets.AMSpriteSourceProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMAbilityProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMAdvancementProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMCuriosProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMDamageTypeProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMDataMapProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMEnchantmentProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMEtheriumTypeProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMGlobalLootModifierProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMLootTableProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMMagicProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMPlantProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMRecipeProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMRitualProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMSpellPartDataProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMTagsProvider;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMToolTierProvider;
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
        if (event.includeClient()) {
            ArsMagicaClientApiImpl.postEvents();
        }
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        AMLanguageProvider languageProvider = new AMLanguageProvider(output);
        generator.addProvider(event.includeClient(), new AMBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new AMItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new AMParticleDescriptionProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new AMParticleSpawnerProvider(output, lookupProvider));
        generator.addProvider(event.includeClient(), new AMSoundDefinitionProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new AMSpriteSourceProvider(output, lookupProvider, existingFileHelper));
        lookupProvider = generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output, lookupProvider, new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, AMDamageTypeProvider::addDamageTypes)
            .add(Registries.ENCHANTMENT, AMEnchantmentProvider::addEnchantments)
            .add(Registries.CONFIGURED_FEATURE, AMWorldgenProvider::addConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, AMWorldgenProvider::addPlacedFeatures)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, AMWorldgenProvider::addBiomeModifiers)
            .add(AMRegistries.Keys.AFFINITY, AMMagicProvider::addAffinities)
            .add(AMRegistries.Keys.ALTAR_CAP_MATERIAL, AMMagicProvider::addAltarCapMaterials)
            .add(AMRegistries.Keys.ALTAR_MATERIAL, AMMagicProvider::addAltarMaterials)
            .add(AMRegistries.Keys.OCCULUS_TAB, AMMagicProvider::addOcculusTabs)
            .add(AMRegistries.Keys.SKILL_POINT, AMMagicProvider::addSkillPoints)
            .add(AMRegistries.Keys.SKILL, AMMagicProvider::addSkills)
            .add(AMRegistries.Keys.ABILITY, AMAbilityProvider::addAbilities)
            .add(AMRegistries.Keys.ETHERIUM_TYPE, AMEtheriumTypeProvider::addEtheriumTypes),
            Set.of(ArsMagicaApi.MOD_ID))).getRegistryProvider();
        AMTagsProvider.addProviders(generator, event.includeServer(), output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), new AMAdvancementProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AMCuriosProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AMDataMapProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new AMGlobalLootModifierProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new AMLootTableProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new AMPlantProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new AMRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new AMRitualProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new AMSpellPartDataProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new AMToolTierProvider(output, lookupProvider));
        generator.addProvider(event.includeClient() || event.includeServer(), new AMPatchouliBookProvider(output, lookupProvider, languageProvider::addCached, event.includeClient(), event.includeServer()));
        generator.addProvider(event.includeClient(), languageProvider);
    }
}
