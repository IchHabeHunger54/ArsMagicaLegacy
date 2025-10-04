package at.minecraftschurli.arsmagicalegacy;

import at.minecraftschurli.arsmagicalegacy.ability.DamageModifierAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.EndermanPumpkinAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.ExtraDamageAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.FirePunchAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.FrostPunchAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.JumpBoostAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.KillEffectAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.ManaCostModifierAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.SpellCastEffectAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.ability.ThornsAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityHelper;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.event.ManaCostCalculationEvent;
import at.minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.command.AffinityCommand;
import at.minecraftschurli.arsmagicalegacy.command.MagicXpCommand;
import at.minecraftschurli.arsmagicalegacy.command.SkillCommand;
import at.minecraftschurli.arsmagicalegacy.command.SkillPointCommand;
import at.minecraftschurli.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import at.minecraftschurli.arsmagicalegacy.effect.AMMobEffect;
import at.minecraftschurli.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMobEffects;
import at.minecraftschurli.arsmagicalegacy.item.runebag.RuneBagItem;
import at.minecraftschurli.arsmagicalegacy.packet.ForgetSkillsPacket;
import at.minecraftschurli.arsmagicalegacy.packet.InscriptionTableCreateSpellPacket;
import at.minecraftschurli.arsmagicalegacy.packet.InscriptionTableSyncPacket;
import at.minecraftschurli.arsmagicalegacy.packet.LearnSkillPacket;
import at.minecraftschurli.arsmagicalegacy.packet.OpenBookInLecternPacket;
import at.minecraftschurli.arsmagicalegacy.packet.SetActiveShapeGroupPacket;
import at.minecraftschurli.arsmagicalegacy.packet.SetLecternPagePacket;
import at.minecraftschurli.arsmagicalegacy.packet.SpellCustomizationPacket;
import at.minecraftschurli.arsmagicalegacy.packet.TakeSpellRecipeFromLecternPacket;
import at.minecraftschurli.arsmagicalegacy.spell.SpellPartDataManager;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.EnderManAngerEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = ArsMagicaApi.MOD_ID)
final class AMEventHandler {
    private AMEventHandler() {
    }

    @SubscribeEvent
    private static void commonSetup(FMLCommonSetupEvent event) {
        AMMultiblocks.init();
    }

    @SubscribeEvent
    private static void addBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, AMBlocks.WITCHWOOD_SIGN.get(), AMBlocks.WITCHWOOD_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN, AMBlocks.WITCHWOOD_HANGING_SIGN.get(), AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get());
    }

    @SubscribeEvent
    private static void newRegistry(NewRegistryEvent event) {
        event.register(ArsMagicaApi.spellPartRegistry());
        event.register(ArsMagicaApi.spellIngredientRegistry());
        event.register(ArsMagicaApi.abilityEffectRegistry());
    }

    @SubscribeEvent
    private static void newDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(AMRegistryKeys.ABILITY, Ability.DIRECT_CODEC, Ability.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistryKeys.AFFINITY, Affinity.DIRECT_CODEC, Affinity.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistryKeys.ALTAR_CAP_MATERIAL, AltarCapMaterial.DIRECT_CODEC, AltarCapMaterial.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistryKeys.ALTAR_MATERIAL, AltarMaterial.DIRECT_CODEC, AltarMaterial.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistryKeys.OCCULUS_TAB, OcculusTab.DIRECT_CODEC, OcculusTab.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistryKeys.SKILL, Skill.DIRECT_CODEC, Skill.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistryKeys.SKILL_POINT, SkillPoint.DIRECT_CODEC, SkillPoint.DIRECT_CODEC);
    }

    @SubscribeEvent
    private static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(SpellPartDataManager.INSTANCE);
    }

    @SubscribeEvent
    private static void entityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AMAttributes.BURNOUT_REGENERATION);
        event.add(EntityType.PLAYER, AMAttributes.MANA_REGENERATION);
        event.add(EntityType.PLAYER, AMAttributes.MAX_BURNOUT);
        event.add(EntityType.PLAYER, AMAttributes.MAX_MANA);
    }

    @SubscribeEvent
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.ItemHandler.ITEM, RuneBagItem::getItemHandler, AMItems.RUNE_BAG);
    }

    @SubscribeEvent
    private static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar("1")
            .playToClient(OpenBookInLecternPacket.TYPE, OpenBookInLecternPacket.STREAM_CODEC, OpenBookInLecternPacket::handle)
            .playToServer(ForgetSkillsPacket.TYPE, ForgetSkillsPacket.STREAM_CODEC, ForgetSkillsPacket::handle)
            .playToServer(InscriptionTableCreateSpellPacket.TYPE, InscriptionTableCreateSpellPacket.STREAM_CODEC, InscriptionTableCreateSpellPacket::handle)
            .playToServer(InscriptionTableSyncPacket.TYPE, InscriptionTableSyncPacket.STREAM_CODEC, InscriptionTableSyncPacket::handle)
            .playToServer(LearnSkillPacket.TYPE, LearnSkillPacket.STREAM_CODEC, LearnSkillPacket::handle)
            .playToServer(SetActiveShapeGroupPacket.TYPE, SetActiveShapeGroupPacket.STREAM_CODEC, SetActiveShapeGroupPacket::handle)
            .playToServer(SetLecternPagePacket.TYPE, SetLecternPagePacket.STREAM_CODEC, SetLecternPagePacket::handle)
            .playToServer(SpellCustomizationPacket.TYPE, SpellCustomizationPacket.STREAM_CODEC, SpellCustomizationPacket::handle)
            .playToServer(TakeSpellRecipeFromLecternPacket.TYPE, TakeSpellRecipeFromLecternPacket.STREAM_CODEC, TakeSpellRecipeFromLecternPacket::handle);
    }

    @SubscribeEvent
    private static void registerCommands(RegisterCommandsEvent event) {
        CommandBuildContext context = event.getBuildContext();
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal(ArsMagicaApi.MOD_ID).requires(p -> p.hasPermission(2));
        AffinityCommand.register(builder, context);
        MagicXpCommand.register(builder);
        SkillCommand.register(builder, context);
        SkillPointCommand.register(builder, context);
        event.getDispatcher().register(builder);
    }

    @SubscribeEvent
    private static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(Potions.AWKWARD, AMItems.CHIMERITE.get(), AMMobEffects.LESSER_MANA);
        builder.addMix(Potions.AWKWARD, AMItems.WAKEBLOOM.get(), AMMobEffects.STANDARD_MANA);
        builder.addMix(Potions.AWKWARD, AMItems.VINTEUM_DUST.get(), AMMobEffects.GREATER_MANA);
        builder.addMix(Potions.AWKWARD, AMItems.ARCANE_ASH.get(), AMMobEffects.EPIC_MANA);
        builder.addMix(Potions.AWKWARD, AMItems.PURIFIED_VINTEUM_DUST.get(), AMMobEffects.LEGENDARY_MANA);
        builder.addMix(Potions.AWKWARD, AMItems.TARMA_ROOT.get(), AMMobEffects.INFUSED_MANA);
    }

    @SubscribeEvent
    private static void advancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        String advancement = AMServerConfig.MAGIC_ADVANCEMENT.get();
        if (!advancement.isEmpty() && event.getAdvancement().id().toString().equals(advancement)) {
            ArsMagicaApi.magicHelper().initiateMagic(event.getEntity());
        }
    }

    @SubscribeEvent
    private static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        if (level.getBlockEntity(pos) instanceof LecternBlockEntity lectern && AMUtil.handleLecternUse(level, pos, level.getBlockState(pos), lectern, event.getEntity(), event.getHand())) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    private static void entityJoinLevel(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        for (MobEffectInstance instance : entity.getActiveEffects()) {
            if (instance.getEffect() instanceof AMMobEffect effect) {
                effect.startEffect(entity, instance);
            }
        }
    }

    @SubscribeEvent
    private static void entityTickPost(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity living) {
            ManaHelper manaHelper = ArsMagicaApi.manaHelper();
            manaHelper.increaseMana(living, manaHelper.getManaRegeneration(living));
            BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
            burnoutHelper.decreaseBurnout(living, burnoutHelper.getBurnoutRegeneration(living));
            if (living.hasEffect(AMMobEffects.WATERY_GRAVE) && entity.isInWaterOrBubble()) {
                entity.setDeltaMovement(entity.getDeltaMovement().x(), entity.getPose() == Pose.SWIMMING ? 0 : Math.min(0, entity.getDeltaMovement().y()), entity.getDeltaMovement().z());
            }
        }
        if (!entity.hasData(AMAttachments.FROST)) return;
        int frost = entity.getData(AMAttachments.FROST);
        if (frost <= 0) return;
        if (frost == 1 || entity.isOnFire()) {
            entity.removeData(AMAttachments.FROST);
        } else {
            entity.setData(AMAttachments.FROST, frost - 1);
        }
        entity.setTicksFrozen(entity.getTicksFrozen() + 3);
    }

    @SubscribeEvent
    private static void playerTickPost(PlayerTickEvent.Post event) {
        ArsMagicaApi.abilityHelper().getActiveAbilities(event.getEntity()).forEach(holder -> holder.value().effects().forEach(effect -> effect.tick(event.getEntity(), holder)));
    }

    @SubscribeEvent
    private static void livingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        LivingEntity target = event.getEntity();
        AbilityHelper abilityHelper = ArsMagicaApi.abilityHelper();
        if (!target.fireImmune()) {
            abilityHelper.getActiveAbilitiesWithEffect(player, FirePunchAbilityEffect.CODEC).forEach(pair -> target.setRemainingFireTicks(Math.max(target.getRemainingFireTicks(), (int) pair.getSecond()
                .stream()
                .mapToDouble(e -> abilityHelper.scaleToDepth(player, pair.getFirst().value(), e.min(), e.max()))
                .sum())));
        }
        if (target.canFreeze()) {
            abilityHelper.getActiveAbilitiesWithEffect(player, FrostPunchAbilityEffect.CODEC).forEach(pair -> target.setData(AMAttachments.FROST, Math.max(target.getData(AMAttachments.FROST), (int) pair.getSecond()
                .stream()
                .mapToDouble(e -> abilityHelper.scaleToDepth(player, pair.getFirst().value(), e.min(), e.max()))
                .sum())));
        }
    }

    @SubscribeEvent
    private static void livingDamagePre(LivingDamageEvent.Pre event) {
        AbilityHelper abilityHelper = ArsMagicaApi.abilityHelper();
        if (event.getSource().getEntity() instanceof Player player) {
            abilityHelper.triggerEventEffect(event, player, ExtraDamageAbilityEffect.CODEC);
        }
        if (event.getEntity() instanceof Player player) {
            abilityHelper.triggerEventEffect(event, player, DamageModifierAbilityEffect.CODEC);
        }
    }

    @SubscribeEvent
    private static void livingDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ArsMagicaApi.abilityHelper().triggerEventEffect(event, player, ThornsAbilityEffect.CODEC);
    }

    @SubscribeEvent
    private static void livingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AMMobEffects.TEMPORAL_ANCHOR)) {
            entity.removeEffect(AMMobEffects.TEMPORAL_ANCHOR);
            event.setCanceled(true);
            return;
        }
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        ArsMagicaApi.abilityHelper().triggerEventEffect(event, player, KillEffectAbilityEffect.CODEC);
    }

    @SubscribeEvent
    private static void livingJump(LivingEvent.LivingJumpEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ArsMagicaApi.abilityHelper().triggerEventEffect(event, player, JumpBoostAbilityEffect.CODEC);
    }

    @SuppressWarnings("ConstantValue")
    @SubscribeEvent
    private static void potionAdded(MobEffectEvent.Added event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance != null && effectInstance.getEffect().value() instanceof AMMobEffect effect) {
            effect.startEffect(entity, effectInstance);
        }
    }

    @SubscribeEvent
    private static void potionExpiry(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance != null && effectInstance.getEffect().value() instanceof AMMobEffect effect) {
            effect.stopEffect(entity, effectInstance);
        }
    }

    @SubscribeEvent
    private static void potionRemove(MobEffectEvent.Remove event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance != null && effectInstance.getEffect().value() instanceof AMMobEffect effect) {
            effect.stopEffect(entity, effectInstance);
        }
    }

    @SubscribeEvent
    private static void enderEntityTeleport(EntityTeleportEvent.EnderEntity event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            entity.sendSystemMessage(AMTranslations.NO_TELEPORT);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    private static void enderPearlTeleport(EntityTeleportEvent.EnderPearl event) {
        Player entity = event.getPlayer();
        if (entity.hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            entity.sendSystemMessage(AMTranslations.NO_TELEPORT);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    private static void chorusFruitTeleport(EntityTeleportEvent.ChorusFruit event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            entity.sendSystemMessage(AMTranslations.NO_TELEPORT);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    private static void enderManAnger(EnderManAngerEvent event) {
        ArsMagicaApi.abilityHelper().triggerEventEffect(event, event.getPlayer(), EndermanPumpkinAbilityEffect.CODEC);
    }

    @SubscribeEvent
    private static void manaCostCalculation(ManaCostCalculationEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ArsMagicaApi.abilityHelper().triggerEventEffect(event, player, ManaCostModifierAbilityEffect.CODEC);
    }

    @SubscribeEvent
    private static void spellCastPost(SpellCastEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ArsMagicaApi.abilityHelper().triggerEventEffect(event, player, SpellCastEffectAbilityEffect.CODEC);
    }
}
