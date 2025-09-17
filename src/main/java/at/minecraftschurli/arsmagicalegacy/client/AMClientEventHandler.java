package at.minecraftschurli.arsmagicalegacy.client;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.RegisterSpellIngredientRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.RegisterSpellPartCustomizationScreensEvent;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.apiimpl.ArsMagicaClientApiImpl;
import at.minecraftschurli.arsmagicalegacy.block.altar.AltarCoreBlock;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable.InscriptionTableScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.occulus.AffinityTabRenderer;
import at.minecraftschurli.arsmagicalegacy.client.gui.occulus.SkillTreeTabRenderer;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.SpellCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color.ColorCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color.ColorWheelShader;
import at.minecraftschurli.arsmagicalegacy.client.layer.BarsLayer;
import at.minecraftschurli.arsmagicalegacy.client.model.AltarCoreModel;
import at.minecraftschurli.arsmagicalegacy.client.model.DataComponentOverrides;
import at.minecraftschurli.arsmagicalegacy.client.model.ItemOverridesModel;
import at.minecraftschurli.arsmagicalegacy.client.renderer.AltarCoreRenderer;
import at.minecraftschurli.arsmagicalegacy.client.renderer.ItemSpellIngredientRenderer;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMMenus;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.stream.Stream;

@EventBusSubscriber(modid = ArsMagicaApi.MOD_ID, value = Dist.CLIENT)
final class AMClientEventHandler {
    private static final Lazy<KeyMapping> SPELL_CUSTOMIZATION = Lazy.of(() -> new KeyMapping(AMTranslations.SPELL_CUSTOMIZATION_KEY, KeyConflictContext.IN_GAME, KeyModifier.SHIFT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, AMTranslations.KEY_CATEGORY_KEY));

    @SubscribeEvent
    private static void clientSetup(FMLClientSetupEvent event) {
        ArsMagicaClientApiImpl.postEvents();
    }

    @SubscribeEvent
    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(AMBlockEntities.ALTAR_CORE.get(), AltarCoreRenderer::new);
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
    private static void registerShaders(RegisterShadersEvent event) {
        try {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), ArsMagicaApi.modLoc("color_wheel"), DefaultVertexFormat.POSITION), ColorWheelShader::setInstance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(SPELL_CUSTOMIZATION.get());
    }

    @SubscribeEvent
    private static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(SkillAtlasHolder.INSTANCE.get());
    }

    @SubscribeEvent
    private static void registerOcculusTabRenderers(RegisterOcculusTabRenderersEvent event) {
        event.register(ArsMagicaApi.modLoc("skill_tree"), SkillTreeTabRenderer::new);
        event.register(ArsMagicaApi.modLoc("affinity"), AffinityTabRenderer::new);
    }

    @SubscribeEvent
    private static void registerSpellIngredientRenderers(RegisterSpellIngredientRenderersEvent event) {
        event.register(AMSpells.ITEM_SPELL_INGREDIENT.get(), ItemSpellIngredientRenderer.INSTANCE);
    }

    @SubscribeEvent
    private static void registerSpellPartCustomizationScreens(RegisterSpellPartCustomizationScreensEvent event) {
        event.register(AMSpells.COLOR, ColorCustomizationScreen::new);
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
        event.getModels().computeIfPresent(BlockModelShaper.stateToModelLocation(AMBlocks.ALTAR_CORE.get().defaultBlockState().setValue(AltarCoreBlock.FORMED, true)), ($, model) -> new AltarCoreModel(model));
    }

    @SubscribeEvent
    private static void clientTickPost(ClientTickEvent.Post event) {
        LocalPlayer player = AMClientUtil.player();
        if (player == null) return;
        ItemStack stack = player.getMainHandItem();
        while (stack.has(AMDataComponents.SPELL) && SPELL_CUSTOMIZATION.get().consumeClick()) {
            AMClientUtil.mc().setScreen(new SpellCustomizationScreen(stack.get(AMDataComponents.SPELL)));
        }
    }
}
