package at.minecraftschurli.arsmagicalegacy;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.helper.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.helper.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.command.AffinityCommand;
import at.minecraftschurli.arsmagicalegacy.command.MagicXpCommand;
import at.minecraftschurli.arsmagicalegacy.command.SkillCommand;
import at.minecraftschurli.arsmagicalegacy.command.SkillPointCommand;
import at.minecraftschurli.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import at.minecraftschurli.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.packet.ForgetSkillsPacket;
import at.minecraftschurli.arsmagicalegacy.packet.InscriptionTableCreateSpellPacket;
import at.minecraftschurli.arsmagicalegacy.packet.InscriptionTableSyncPacket;
import at.minecraftschurli.arsmagicalegacy.packet.LearnSkillPacket;
import at.minecraftschurli.arsmagicalegacy.packet.OpenBookInLecternPacket;
import at.minecraftschurli.arsmagicalegacy.packet.SetLecternPagePacket;
import at.minecraftschurli.arsmagicalegacy.packet.TakeSpellRecipeFromLecternPacket;
import at.minecraftschurli.arsmagicalegacy.spell.SpellPartDataManager;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
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
        event.register(ArsMagicaApi.spellDataComponentRegistry());
        event.register(ArsMagicaApi.spellIngredientRegistry());
    }

    @SubscribeEvent
    private static void newDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
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
    private static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar("1")
            .playToClient(OpenBookInLecternPacket.TYPE, OpenBookInLecternPacket.STREAM_CODEC, OpenBookInLecternPacket::handle)
            .playToServer(ForgetSkillsPacket.TYPE, ForgetSkillsPacket.STREAM_CODEC, ForgetSkillsPacket::handle)
            .playToServer(InscriptionTableCreateSpellPacket.TYPE, InscriptionTableCreateSpellPacket.STREAM_CODEC, InscriptionTableCreateSpellPacket::handle)
            .playToServer(InscriptionTableSyncPacket.TYPE, InscriptionTableSyncPacket.STREAM_CODEC, InscriptionTableSyncPacket::handle)
            .playToServer(LearnSkillPacket.TYPE, LearnSkillPacket.STREAM_CODEC, LearnSkillPacket::handle)
            .playToServer(SetLecternPagePacket.TYPE, SetLecternPagePacket.STREAM_CODEC, SetLecternPagePacket::handle)
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
    private static void entityTickPost(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity living)) return;
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        manaHelper.increaseMana(living, manaHelper.getManaRegeneration(living));
        BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
        burnoutHelper.decreaseBurnout(living, burnoutHelper.getBurnoutRegeneration(living));
    }
}
