package at.minecraftschurli.mods.arsmagicalegacy.client;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.event.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.event.RegisterParticleControllersEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.event.RegisterSpellPartCustomizationScreensEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.apiimpl.ArsMagicaClientApiImpl;
import at.minecraftschurli.mods.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.mods.arsmagicalegacy.client.atlas.SpellIconAtlasHolder;
import at.minecraftschurli.mods.arsmagicalegacy.client.extension.LiquidEtheriumClientFluidTypeExtensions;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.RiftScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.RuneBagScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.SpellBookScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.inscriptiontable.InscriptionTableScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.occulus.AffinityTabRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.occulus.SkillTreeTabRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.PlaceBlockCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.RecallCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.SpellCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.SummonCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.color.ColorCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.client.layer.BarsLayer;
import at.minecraftschurli.mods.arsmagicalegacy.client.layer.ShapeGroupsLayer;
import at.minecraftschurli.mods.arsmagicalegacy.client.layer.SpellBookLayer;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.AMModelLayers;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.AltarCoreModel;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.DryadModel;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.CrystalPhylacteryItemTintSource;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.CrystalPhylacteryRangeSelectItemModelProperty;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.CrystalWrenchActiveItemModelProperty;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.DataComponentOverridesModel;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.EtheriumTypeItemTintSource;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.SpellItemModel;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.ParticleSpawnerManager;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.SimpleParticleProvider;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.SymbolsParticleProvider;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.ApproachEntityController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.ArcToEntityController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.ChangeSizeController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.FadeOutController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.FloatUpwardController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.LeaveTrailController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.MoveInKnockbackDirectionController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.MoveInViewDirectionController;
import at.minecraftschurli.mods.arsmagicalegacy.client.particle.controller.OrbitPointController;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.block.AltarCoreRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.block.BlackAuremRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.block.EtheriumBlockEntityRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.block.SpellRuneRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity.DryadRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity.EmptyRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity.ManaCreeperRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.compat.patchouli.SpellPartPage;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMFluids;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMenus;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMParticles;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.packet.SetActiveShapeGroupPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.SpellBookScrollPacket;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;
import vazkii.patchouli.api.PatchouliAPI;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@EventBusSubscriber(modid = ArsMagicaApi.MOD_ID, value = Dist.CLIENT)
final class AMClientEventHandler {
    private static final KeyMapping.Category KEY_CATEGORY = new KeyMapping.Category(ArsMagicaApi.id("main"));
    private static final KeyMapping NEXT_SHAPE_GROUP = new KeyMapping(AMTranslations.KEY_NEXT_SHAPE_GROUP_KEY, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD, KEY_CATEGORY);
    private static final KeyMapping PREV_SHAPE_GROUP = new KeyMapping(AMTranslations.KEY_PREV_SHAPE_GROUP_KEY, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_COMMA, KEY_CATEGORY);
    private static final KeyMapping SPELL_CUSTOMIZATION = new KeyMapping(AMTranslations.KEY_SPELL_CUSTOMIZATION_KEY, KeyConflictContext.IN_GAME, KeyModifier.SHIFT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY);

    @SubscribeEvent
    private static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ArsMagicaClientApiImpl.postEvents();
            PatchouliAPI.get().registerTemplateAsBuiltin(SpellPartPage.ID, () -> new ByteArrayInputStream(SpellPartPage.TEMPLATE.getBytes(StandardCharsets.UTF_8)));
        });
    }

    @SubscribeEvent
    private static void registerFluidModels(RegisterFluidModelsEvent event) {
        event.register(
            new FluidModel.Unbaked(
                new Material(ArsMagicaApi.id("block/liquid_etherium_still")),
                new Material(ArsMagicaApi.id("block/liquid_etherium_flowing")),
                null,
                null),
            AMFluids.LIQUID_ETHERIUM::value,
            AMFluids.FLOWING_LIQUID_ETHERIUM::value);
    }

    @SubscribeEvent
    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DryadModel.LAYER_LOCATION, DryadModel::createBodyLayer);
        event.registerLayerDefinition(AMModelLayers.WITCHWOOD_BOAT, BoatModel::createBoatModel);
        event.registerLayerDefinition(AMModelLayers.WITCHWOOD_CHEST_BOAT, BoatModel::createChestBoatModel);
    }

    @SubscribeEvent
    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AMEntities.BLIZZARD.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.DRYAD.get(), DryadRenderer::new);
        event.registerEntityRenderer(AMEntities.FALLING_STAR.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.FIRE_RAIN.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.MANA_CREEPER.get(), ManaCreeperRenderer::new);
        event.registerEntityRenderer(AMEntities.MANA_VORTEX.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.PROJECTILE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.WALL.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.WAVE.get(), EmptyRenderer::new);
        event.registerEntityRenderer(AMEntities.WITCHWOOD_BOAT.get(), context -> new BoatRenderer(context, AMModelLayers.WITCHWOOD_BOAT));
        event.registerEntityRenderer(AMEntities.WITCHWOOD_CHEST_BOAT.get(), context -> new BoatRenderer(context, AMModelLayers.WITCHWOOD_CHEST_BOAT));
        event.registerEntityRenderer(AMEntities.ZONE.get(), EmptyRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.ALTAR_CORE.get(), AltarCoreRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.BLACK_AUREM.get(), BlackAuremRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.CELESTIAL_PRISM.get(), EtheriumBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.OBELISK.get(), EtheriumBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(AMBlockEntities.SPELL_RUNE.get(), SpellRuneRenderer::new);
    }

    @SubscribeEvent
    private static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(AMMenus.INSCRIPTION_TABLE.get(), InscriptionTableScreen::new);
        event.register(AMMenus.RIFT.get(), RiftScreen::new);
        event.register(AMMenus.RUNE_BAG.get(), RuneBagScreen::new);
        event.register(AMMenus.SPELL_BOOK.get(), SpellBookScreen::new);
    }

    @SubscribeEvent
    private static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerBelowAll(ArsMagicaApi.id("bars"), new BarsLayer());
        event.registerBelowAll(ArsMagicaApi.id("shape_groups"), new ShapeGroupsLayer());
        event.registerBelowAll(ArsMagicaApi.id("spell_book"), new SpellBookLayer());
    }

    @SubscribeEvent
    private static void registerItemTintSources(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(ArsMagicaApi.id("etherium_type"), EtheriumTypeItemTintSource.CODEC);
        event.register(ArsMagicaApi.id("crystal_phylactery"), CrystalPhylacteryItemTintSource.CODEC);
    }

    @SubscribeEvent
    private static void registerItemModelProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(ArsMagicaApi.id("crystal_wrench_active"), CrystalWrenchActiveItemModelProperty.CODEC);
    }

    @SubscribeEvent
    private static void registerItemModelProperties(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(ArsMagicaApi.id("crystal_phylactery_fill"), CrystalPhylacteryRangeSelectItemModelProperty.CODEC);
    }

    @SubscribeEvent
    private static void registerBlockModels(RegisterBlockStateModels event) {
        event.registerModel(ArsMagicaApi.id("altar_core"), AltarCoreModel.Unbaked.MAP_CODEC);
    }

    @SubscribeEvent
    private static void registerItemModels(RegisterItemModelsEvent event) {
        event.register(ArsMagicaApi.id("spell"), SpellItemModel.Unbaked.MAP_CODEC);
        event.register(ArsMagicaApi.id("data_component_overrides"), DataComponentOverridesModel.Unbaked.MAP_CODEC);
    }

/* TODO render pipeline
    @SubscribeEvent
    private static void registerShaders(RegisterShadersEvent event) {
        try {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), ArsMagicaApi.id("color_wheel"), DefaultVertexFormat.POSITION), ColorWheelShader::setInstance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
*/

    @SubscribeEvent
    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(KEY_CATEGORY);
        event.register(NEXT_SHAPE_GROUP);
        event.register(PREV_SHAPE_GROUP);
        event.register(SPELL_CUSTOMIZATION);
    }

    @SubscribeEvent
    private static void registerTextureAtlases(RegisterTextureAtlasesEvent event) {
        event.register(new AtlasManager.AtlasConfig(SkillAtlasHolder.ATLAS, SkillAtlasHolder.ATLAS_ID, false));
        event.register(new AtlasManager.AtlasConfig(SpellIconAtlasHolder.ATLAS, SpellIconAtlasHolder.ATLAS_ID, false));
    }

    @SubscribeEvent
    private static void registerClientReloadListeners(AddClientReloadListenersEvent event) {
        event.addListener(ParticleSpawnerManager.ID, ParticleSpawnerManager.INSTANCE);
    }

    @SubscribeEvent
    private static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(LiquidEtheriumClientFluidTypeExtensions.INSTANCE, AMFluids.LIQUID_ETHERIUM_TYPE);
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
        event.register(ArsMagicaApi.id("skill_tree"), SkillTreeTabRenderer::new);
        event.register(ArsMagicaApi.id("affinity"), AffinityTabRenderer::new);
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
        event.register(AMSpells.SUMMON, SummonCustomizationScreen::new);
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
                ClientPacketDistributor.sendToServer(new SetActiveShapeGroupPacket(spell.activeShapeGroup()));
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
        ClientPacketDistributor.sendToServer(new SpellBookScrollPacket(scroll > 0));
        event.setCanceled(true);
    }

    /**
     * Adapted from ItemInHandRenderer#renderArmWithItem
     */
    @SubscribeEvent
    private static void renderHand(RenderHandEvent event) {
        /*TODO
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
        */
    }
}
