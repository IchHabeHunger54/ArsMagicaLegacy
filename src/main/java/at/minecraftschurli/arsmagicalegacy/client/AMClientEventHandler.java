package at.minecraftschurli.arsmagicalegacy.client;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterParticleControllersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterSpellPartCustomizationScreensEvent;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMCapabilities;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumHandler;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.apiimpl.ArsMagicaClientApiImpl;
import at.minecraftschurli.arsmagicalegacy.block.AltarCoreBlock;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SpellIconAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.gui.RuneBagScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.SpellBookScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable.InscriptionTableScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.occulus.AffinityTabRenderer;
import at.minecraftschurli.arsmagicalegacy.client.gui.occulus.SkillTreeTabRenderer;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.PlaceBlockCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.RecallCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.SpellCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color.ColorCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color.ColorWheelShader;
import at.minecraftschurli.arsmagicalegacy.client.layer.BarsLayer;
import at.minecraftschurli.arsmagicalegacy.client.layer.ShapeGroupsLayer;
import at.minecraftschurli.arsmagicalegacy.client.layer.SpellBookLayer;
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
import at.minecraftschurli.arsmagicalegacy.client.renderer.MagitechGogglesOverlayRenderer;
import at.minecraftschurli.arsmagicalegacy.client.renderer.block.AltarCoreRenderer;
import at.minecraftschurli.arsmagicalegacy.client.renderer.block.BlackAuremRenderer;
import at.minecraftschurli.arsmagicalegacy.client.renderer.entity.EmptyRenderer;
import at.minecraftschurli.arsmagicalegacy.client.renderer.item.SpellItemRenderer;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMMenus;
import at.minecraftschurli.arsmagicalegacy.init.AMParticles;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.item.CrystalWrenchItem;
import at.minecraftschurli.arsmagicalegacy.packet.SetActiveShapeGroupPacket;
import at.minecraftschurli.arsmagicalegacy.packet.SpellBookScrollPacket;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

@EventBusSubscriber(modid = ArsMagicaApi.MOD_ID, value = Dist.CLIENT)
final class AMClientEventHandler {
    private static final KeyMapping NEXT_SHAPE_GROUP = new KeyMapping(AMTranslations.KEY_NEXT_SHAPE_GROUP_KEY, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, AMTranslations.KEY_CATEGORY_KEY);
    private static final KeyMapping PREV_SHAPE_GROUP = new KeyMapping(AMTranslations.KEY_PREV_SHAPE_GROUP_KEY, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_COMMA, AMTranslations.KEY_CATEGORY_KEY);
    private static final KeyMapping SPELL_CUSTOMIZATION = new KeyMapping(AMTranslations.KEY_SPELL_CUSTOMIZATION_KEY, KeyConflictContext.IN_GAME, KeyModifier.SHIFT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, AMTranslations.KEY_CATEGORY_KEY);

    @SuppressWarnings("DataFlowIssue")
    @SubscribeEvent
    private static void clientSetup(FMLClientSetupEvent event) {
        ArsMagicaClientApiImpl.postEvents();
        event.enqueueWork(() -> ItemProperties.register(AMItems.CRYSTAL_WRENCH.get(), CrystalWrenchItem.ACTIVE, (stack, level, player, seed) -> stack.has(AMDataComponents.STORED_POSITIONS) && !stack.get(AMDataComponents.STORED_POSITIONS).isEmpty() ? 1 : 0));
    }

    @SubscribeEvent
    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AMEntities.BLIZZARD.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.FALLING_STAR.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.FIRE_RAIN.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.PROJECTILE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.WALL.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.WAVE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.ZONE.get(), EmptyRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.ALTAR_CORE.get(), AltarCoreRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.BLACK_AUREM.get(), BlackAuremRenderer::new);
    }

    @SubscribeEvent
    private static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(AMMenus.INSCRIPTION_TABLE.get(), InscriptionTableScreen::new);
        event.register(AMMenus.RUNE_BAG.get(), RuneBagScreen::new);
        event.register(AMMenus.SPELL_BOOK.get(), SpellBookScreen::new);
    }

    @SubscribeEvent
    private static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerBelowAll(ArsMagicaApi.modLoc("bars"), new BarsLayer());
        event.registerBelowAll(ArsMagicaApi.modLoc("shape_groups"), new ShapeGroupsLayer());
        event.registerBelowAll(ArsMagicaApi.modLoc("spell_book"), new SpellBookLayer());
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
        event.register(NEXT_SHAPE_GROUP);
        event.register(PREV_SHAPE_GROUP);
        event.register(SPELL_CUSTOMIZATION);
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
        event.registerItem(new SpellItemRenderer(), AMItems.SPELL_BOOK);
    }

    @SuppressWarnings("DataFlowIssue")
    @SubscribeEvent
    private static void registerColorHandlersItem(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> tintIndex == 0 && stack.has(AMDataComponents.ETHERIUM_TYPE) ? 0xff000000 | stack.get(AMDataComponents.ETHERIUM_TYPE).value().color() : -1, AMItems.ETHERIUM_PLACEHOLDER);
        event.register((stack, tintIndex) -> tintIndex == 1 ? 0xff000000 | DyedItemColor.getOrDefault(stack, 0) : -1, AMItems.SPELL_BOOK.get());
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
    private static void registerSpellPartCustomizationScreens(RegisterSpellPartCustomizationScreensEvent event) {
        event.register(AMSpells.COLOR, ColorCustomizationScreen::new);
        event.register(AMSpells.PLACE_BLOCK, PlaceBlockCustomizationScreen::new);
        event.register(AMSpells.RECALL, RecallCustomizationScreen::new);
    }

    @SubscribeEvent
    private static void modelRegisterAdditional(ModelEvent.RegisterAdditional event) {
        DataComponentOverrides.getAdditionalModels(AMMagic.AFFINITIES_WITH_NONE.stream().map(ResourceKey::location), AMItems.SPELL).forEach(event::register);
        DataComponentOverrides.getAdditionalModels(Stream.of(1, 2, 3).map(i -> ArsMagicaApi.modLoc("tier_" + i)), AMItems.INSCRIPTION_TABLE).forEach(event::register);
        DataComponentOverrides.getAdditionalModels(AMMagic.SKILL_POINTS.stream().map(ResourceKey::location), AMItems.INFINITY_ORB).forEach(event::register);
        DataComponentOverrides.getAdditionalModels(AMMagic.AFFINITIES.stream().map(ResourceKey::location), AMItems.AFFINITY_ESSENCE).forEach(event::register);
        DataComponentOverrides.getAdditionalModels(AMMagic.AFFINITIES_WITH_NONE.stream().map(ResourceKey::location), AMItems.AFFINITY_TOME).forEach(event::register);
    }

    @SubscribeEvent
    private static void modelModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        Map<ModelResourceLocation, BakedModel> models = event.getModels();
        models.computeIfPresent(BlockModelShaper.stateToModelLocation(AMBlocks.ALTAR_CORE.get().defaultBlockState().setValue(AltarCoreBlock.FORMED, true)), ($, model) -> new AltarCoreModel(model));
        models.computeIfPresent(ModelResourceLocation.inventory(AMItems.SPELL.getId()), ($, model) -> new SpellItemModel(model));
        models.computeIfPresent(ModelResourceLocation.inventory(AMItems.SPELL_BOOK.getId()), ($, model) -> new SpellItemModel(model));
        ItemOverridesModel.register(models, AMItems.INSCRIPTION_TABLE, new DataComponentOverrides<>(AMDataComponents.TIER.get(), (tier, model, stack) -> tier == 0 ? null : ModelResourceLocation.standalone(ArsMagicaApi.modLoc("item/inscription_table_tier_" + tier))));
        ItemOverridesModel.register(models, AMItems.INFINITY_ORB, new DataComponentOverrides<>(AMDataComponents.SKILL_POINT.get(), DataComponentOverrides.holder()));
        ItemOverridesModel.register(models, AMItems.AFFINITY_ESSENCE, new DataComponentOverrides<>(AMDataComponents.AFFINITY.get(), DataComponentOverrides.holder()));
        ItemOverridesModel.register(models, AMItems.AFFINITY_TOME, new DataComponentOverrides<>(AMDataComponents.AFFINITY.get(), DataComponentOverrides.holder()));
    }

    @SuppressWarnings("DataFlowIssue")
    @SubscribeEvent
    private static void clientTickPost(ClientTickEvent.Post event) {
        LocalPlayer player = AMClientUtil.player();
        if (player == null) return;
        InteractionHand hand = InteractionHand.MAIN_HAND;
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.has(AMDataComponents.SPELL)) {
            hand =  InteractionHand.OFF_HAND;
            stack = player.getItemInHand(hand);
        }
        if (stack.has(AMDataComponents.SPELL)) {
            Spell originalSpell = stack.get(AMDataComponents.SPELL);
            Spell spell = originalSpell;
            while (NEXT_SHAPE_GROUP.consumeClick()) {
                spell = spell.nextShapeGroup();
            }
            while (PREV_SHAPE_GROUP.consumeClick()) {
                spell = spell.prevShapeGroup();
            }
            if (spell != originalSpell) {
                stack.set(AMDataComponents.SPELL, spell);
                PacketDistributor.sendToServer(new SetActiveShapeGroupPacket(spell.activeShapeGroup()));
            }
            while (SPELL_CUSTOMIZATION.consumeClick()) {
                AMClientUtil.mc().setScreen(new SpellCustomizationScreen(spell, hand));
            }
        }
    }

    @SubscribeEvent
    private static void inputMouseScrolling(InputEvent.MouseScrollingEvent event) {
        double scroll = event.getScrollDeltaY();
        if (scroll == 0) return;
        Player player = AMClientUtil.player();
        if (player == null || !player.isSecondaryUseActive()) return;
        ItemStack stack = player.getMainHandItem();
        if (!stack.is(AMItems.SPELL_BOOK)) {
            stack = player.getOffhandItem();
            if (!stack.is(AMItems.SPELL_BOOK)) return;
        }
        PacketDistributor.sendToServer(new SpellBookScrollPacket(scroll > 0));
        event.setCanceled(true);
    }

    @SubscribeEvent
    private static void renderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL || !MagitechGogglesOverlayRenderer.shouldRender(AMClientUtil.player())) return;
        Minecraft mc = AMClientUtil.mc();
        int renderDistance = mc.options.getEffectiveRenderDistance();
        ClientLevel level = AMClientUtil.level();
        Vec3 camera = event.getCamera().getPosition();
        PoseStack stack = event.getPoseStack();
        MultiBufferSource bufferSource = mc.renderBuffers().bufferSource();
        for (int x = -renderDistance; x <= renderDistance; x++) {
            for (int z = -renderDistance; z <= renderDistance; z++) {
                for (BlockPos pos : level.getChunk(x, z, ChunkStatus.FULL).getBlockEntitiesPos()) {
                    EtheriumHandler cap = level.getCapability(AMCapabilities.BLOCK_ETHERIUM, pos, null);
                    if (cap == null) continue;
                    BlockState state = level.getBlockState(pos);
                    AABB outline = cap.getOutline(level, pos, state);
                    int color = cap.getOutlineColor(level, pos, state);
                    stack.pushPose();
                    stack.translate(pos.getX() - camera.x, pos.getY() - camera.y, pos.getZ() - camera.z);
                    if (outline != null) {
                        MagitechGogglesOverlayRenderer.renderBox(stack, bufferSource, outline, 0.025f, 0xff000000 | color);
                    }
                    for (BlockPos connectedPos : cap.getConnectedPositions()) {
                        EtheriumHandler connectedCap = level.getCapability(AMCapabilities.BLOCK_ETHERIUM, connectedPos, null);
                        int connectedColor = connectedCap == null ? color : AMClientUtil.averageColors(color, connectedCap.getOutlineColor(level, connectedPos, level.getBlockState(connectedPos)));
                        MagitechGogglesOverlayRenderer.renderLine(stack, bufferSource, pos, connectedPos, 0.025f, 0xff000000 | connectedColor);
                    }
                    stack.popPose();
                }
            }
        }
    }

    /**
     * Adapted from ItemInHandRenderer#renderArmWithItem
     */
    @SubscribeEvent
    private static void renderHand(RenderHandEvent event) {
        if (!(AMClientUtil.player() instanceof LocalPlayer player) || player.isInvisible() || !ArsMagicaApi.magicHelper().knowsMagic(player)) return;
        ItemStack item = event.getItemStack();
        if (!item.is(AMTags.Items.SHOWS_SPELL_VISUALS) || !item.has(AMDataComponents.SPELL)) return;
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
