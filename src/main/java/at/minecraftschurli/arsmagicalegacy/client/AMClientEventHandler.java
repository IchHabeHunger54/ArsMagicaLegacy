package at.minecraftschurli.arsmagicalegacy.client;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.RegisterSpellIngredientRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.apiimpl.ArsMagicaClientApiImpl;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.layer.BarsLayer;
import at.minecraftschurli.arsmagicalegacy.client.model.DataComponentOverrides;
import at.minecraftschurli.arsmagicalegacy.client.model.ItemOverridesModel;
import at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable.InscriptionTableScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.occulus.AffinityTabRenderer;
import at.minecraftschurli.arsmagicalegacy.client.gui.occulus.DefaultTabRenderer;
import at.minecraftschurli.arsmagicalegacy.client.renderer.ItemSpellIngredientRenderer;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMMenus;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.util.stream.Stream;

@EventBusSubscriber(modid = ArsMagicaApi.MOD_ID, value = Dist.CLIENT)
final class AMClientEventHandler {
    @SubscribeEvent
    private static void clientSetup(FMLClientSetupEvent event) {
        ArsMagicaClientApiImpl.postEvents();
    }

    @SubscribeEvent
    private static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(AMMenus.INSCRIPTION_TABLE.get(), InscriptionTableScreen::new);
    }

    @SubscribeEvent
    private static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerBelowAll(ArsMagicaApi.modLoc("bars"), new BarsLayer());
    }

    @SubscribeEvent
    private static void registerOcculusTabRenderers(RegisterOcculusTabRenderersEvent event) {
        event.register(ArsMagicaApi.modLoc("default"), DefaultTabRenderer::new);
        event.register(ArsMagicaApi.modLoc("affinity"), AffinityTabRenderer::new);
    }

    @SubscribeEvent
    private static void registerSpellIngredientRenderers(RegisterSpellIngredientRenderersEvent event) {
        event.register(AMSpells.ITEM_SPELL_INGREDIENT.get(), ItemSpellIngredientRenderer.INSTANCE);
    }

    @SubscribeEvent
    private static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(SkillAtlasHolder.INSTANCE.get());
    }

    @SubscribeEvent
    private static void modelRegisterAdditional(ModelEvent.RegisterAdditional event) {
        DataComponentOverrides.getAdditionalModels(Stream.of(1, 2, 3).map(i -> ArsMagicaApi.modLoc("tier_" + i)), AMItems.INSCRIPTION_TABLE).forEach(event::register);
        DataComponentOverrides.getAdditionalModels(AMMagic.SKILL_POINTS.stream().map(ResourceKey::location), AMItems.INFINITY_ORB).forEach(event::register);
        DataComponentOverrides.getAdditionalModels(AMMagic.AFFINITIES.stream().map(ResourceKey::location), AMItems.AFFINITY_ESSENCE).forEach(event::register);
    }

    @SubscribeEvent
    private static void modelModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        ItemOverridesModel.register(event.getModels(), AMItems.INSCRIPTION_TABLE, new DataComponentOverrides<>(AMDataComponents.TIER.get(), (tier, model, stack) -> tier == 0 ? null : ModelResourceLocation.standalone(ArsMagicaApi.modLoc("item/inscription_table_tier_" + tier))));
        ItemOverridesModel.register(event.getModels(), AMItems.INFINITY_ORB, new DataComponentOverrides<>(AMDataComponents.SKILL_POINT.get(), DataComponentOverrides.holder()));
        ItemOverridesModel.register(event.getModels(), AMItems.AFFINITY_ESSENCE, new DataComponentOverrides<>(AMDataComponents.AFFINITY.get(), DataComponentOverrides.holder()));
    }
}
