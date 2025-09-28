package at.minecraftschurli.arsmagicalegacy.client;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterParticleControllersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterSpellIngredientRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterSpellPartCustomizationScreensEvent;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.apiimpl.ArsMagicaClientApiImpl;
import at.minecraftschurli.arsmagicalegacy.block.altar.AltarCoreBlock;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SpellIconAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable.InscriptionTableScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.occulus.AffinityTabRenderer;
import at.minecraftschurli.arsmagicalegacy.client.gui.occulus.SkillTreeTabRenderer;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.SpellCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color.ColorCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color.ColorWheelShader;
import at.minecraftschurli.arsmagicalegacy.client.layer.BarsLayer;
import at.minecraftschurli.arsmagicalegacy.client.model.AltarCoreModel;
import at.minecraftschurli.arsmagicalegacy.client.model.item.DataComponentOverrides;
import at.minecraftschurli.arsmagicalegacy.client.model.item.ItemOverridesModel;
import at.minecraftschurli.arsmagicalegacy.client.model.item.SpellItemModel;
import at.minecraftschurli.arsmagicalegacy.client.particle.ParticleSpawnerManager;
import at.minecraftschurli.arsmagicalegacy.client.particle.SimpleParticleProvider;
import at.minecraftschurli.arsmagicalegacy.client.particle.SymbolsParticleProvider;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.ApproachEntityController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.ArcToEntityController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.ChangeSizeController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.FadeOutController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.FloatUpwardController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.LeaveTrailController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.MoveInKnockbackDirectionController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.MoveInViewDirectionController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.OrbitPointController;
import at.minecraftschurli.arsmagicalegacy.client.renderer.AltarCoreRenderer;
import at.minecraftschurli.arsmagicalegacy.client.renderer.EmptyRenderer;
import at.minecraftschurli.arsmagicalegacy.client.renderer.ItemSpellIngredientRenderer;
import at.minecraftschurli.arsmagicalegacy.client.renderer.SpellItemRenderer;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMMenus;
import at.minecraftschurli.arsmagicalegacy.init.AMParticles;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
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
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.Map;
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
        event.registerEntityRenderer(AMEntities.PROJECTILE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.ZONE.get(), EmptyRenderer::new);
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
        event.registerReloadListener(ParticleSpawnerManager.INSTANCE);
        event.registerReloadListener(SkillAtlasHolder.INSTANCE.get());
        event.registerReloadListener(SpellIconAtlasHolder.INSTANCE.get());
    }

    @SubscribeEvent
    private static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new SpellItemRenderer(), AMItems.SPELL);
    }

    @SubscribeEvent
    private static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        // @formatter:off
        event.registerSpriteSet(AMParticles.NONE_HAND.get(),      SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.WATER_HAND.get(),     SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.FIRE_HAND.get(),      SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.EARTH_HAND.get(),     SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.AIR_HAND.get(),       SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.ICE_HAND.get(),       SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.LIGHTNING_HAND.get(), SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.NATURE_HAND.get(),    SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.LIFE_HAND.get(),      SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.ARCANE_HAND.get(),    SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.ENDER_HAND.get(),     SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.ARCANE.get(),         SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.CLOCK.get(),          SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.EMBER.get(),          SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.EXPLOSION.get(),      SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.GHOST.get(),          SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.LEAF.get(),           SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.LENS_FLARE.get(),     SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.LIGHTS.get(),         SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.PLANT.get(),          SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.PULSE.get(),          SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.ROCK.get(),           SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.ROTATING_RINGS.get(), SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.STARDUST.get(),       SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.WATER_BALL.get(),     SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.WIND.get(),           SimpleParticleProvider::new);
        event.registerSpriteSet(AMParticles.SYMBOLS.get(),        SymbolsParticleProvider::new);
        // @formatter:on
    }

    @SubscribeEvent
    private static void registerOcculusTabRenderers(RegisterOcculusTabRenderersEvent event) {
        event.register(ArsMagicaApi.modLoc("skill_tree"), SkillTreeTabRenderer::new);
        event.register(ArsMagicaApi.modLoc("affinity"), AffinityTabRenderer::new);
    }

    @SubscribeEvent
    private static void registerParticleControllers(RegisterParticleControllersEvent event) {
        // @formatter:off
        event.register(ApproachEntityController.ID,           ApproachEntityController.CODEC);
        event.register(ArcToEntityController.ID,              ArcToEntityController.CODEC);
        event.register(ChangeSizeController.ID,               ChangeSizeController.CODEC);
        event.register(FadeOutController.ID,                  FadeOutController.CODEC);
        event.register(FloatUpwardController.ID,              FloatUpwardController.CODEC);
        event.register(LeaveTrailController.ID,               LeaveTrailController.CODEC);
        event.register(MoveInKnockbackDirectionController.ID, MoveInKnockbackDirectionController.CODEC);
        event.register(MoveInViewDirectionController.ID,      MoveInViewDirectionController.CODEC);
        event.register(OrbitPointController.ID,               OrbitPointController.CODEC);
        // @formatter:on
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
        DataComponentOverrides.getAdditionalModels(AMMagic.AFFINITIES.stream().map(ResourceKey::location), AMItems.SPELL).forEach(event::register);
    }

    @SubscribeEvent
    private static void modelModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        Map<ModelResourceLocation, BakedModel> models = event.getModels();
        ItemOverridesModel.register(models, AMItems.INSCRIPTION_TABLE, new DataComponentOverrides<>(AMDataComponents.TIER.get(), (tier, model, stack) -> tier == 0 ? null : ModelResourceLocation.standalone(ArsMagicaApi.modLoc("item/inscription_table_tier_" + tier))));
        ItemOverridesModel.register(models, AMItems.INFINITY_ORB, new DataComponentOverrides<>(AMDataComponents.SKILL_POINT.get(), DataComponentOverrides.holder()));
        ItemOverridesModel.register(models, AMItems.AFFINITY_ESSENCE, new DataComponentOverrides<>(AMDataComponents.AFFINITY.get(), DataComponentOverrides.holder()));
        models.computeIfPresent(ModelResourceLocation.inventory(AMItems.SPELL.getId()), ($, model) -> new SpellItemModel(model));
        models.computeIfPresent(BlockModelShaper.stateToModelLocation(AMBlocks.ALTAR_CORE.get().defaultBlockState().setValue(AltarCoreBlock.FORMED, true)), ($, model) -> new AltarCoreModel(model));
    }

    @SuppressWarnings("DataFlowIssue")
    @SubscribeEvent
    private static void clientTickPost(ClientTickEvent.Post event) {
        LocalPlayer player = AMClientUtil.player();
        if (player == null) return;
        ItemStack stack = player.getMainHandItem();
        while (stack.has(AMDataComponents.SPELL) && SPELL_CUSTOMIZATION.get().consumeClick()) {
            AMClientUtil.mc().setScreen(new SpellCustomizationScreen(stack.get(AMDataComponents.SPELL)));
        }
    }

    /**
     * Adapted from ItemInHandRenderer#renderArmWithItem
     */
    @SubscribeEvent
    private static void renderHand(RenderHandEvent event) {
        if (!(AMClientUtil.player() instanceof LocalPlayer player) || player.isInvisible() || !ArsMagicaApi.magicHelper().knowsMagic(player)) return;
        ItemStack item = event.getItemStack();
        if (!item.is(AMItems.SPELL)) return;
        float swing = event.getSwingProgress();
        float swingSqrt = Mth.sqrt(swing);
        boolean isRightHand = (event.getHand() == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite()) != HumanoidArm.LEFT;
        int armMultiplier = isRightHand ? 1 : -1;
        PoseStack stack = event.getPoseStack();
        stack.pushPose();
        RenderSystem.setShaderTexture(0, player.getSkin().texture());
        stack.translate(armMultiplier * (-0.3 * Mth.sin((float) (swingSqrt * Math.PI)) + 0.64), 0.4 * Mth.sin((float) (swingSqrt * (Math.PI * 2))) - 0.6 + event.getEquipProgress() * -0.6, -0.4 * Mth.sin((float) (swing * Math.PI)) - 0.72);
        stack.mulPose(Axis.YP.rotationDegrees(armMultiplier * 45));
        stack.mulPose(Axis.YP.rotationDegrees(armMultiplier * Mth.sin((float) (swingSqrt * Math.PI)) * 70));
        stack.mulPose(Axis.ZP.rotationDegrees(armMultiplier * Mth.sin((float) (swing * swing * Math.PI)) * -20));
        stack.translate(-armMultiplier, 3.6, 3.5);
        stack.mulPose(Axis.ZP.rotationDegrees(armMultiplier * 120));
        stack.mulPose(Axis.XP.rotationDegrees(200));
        stack.mulPose(Axis.YP.rotationDegrees(armMultiplier * -135));
        stack.translate(armMultiplier * 5.6, 0, 0);
        if (isRightHand) {
            ((PlayerRenderer) AMClientUtil.mc().getEntityRenderDispatcher().getRenderer(player)).renderRightHand(stack, event.getMultiBufferSource(), event.getPackedLight(), player);
        } else {
            ((PlayerRenderer) AMClientUtil.mc().getEntityRenderDispatcher().getRenderer(player)).renderLeftHand(stack, event.getMultiBufferSource(), event.getPackedLight(), player);
        }
        stack.popPose();
    }
}
