package at.minecraftschurli.arsmagicalegacy;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.helper.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.helper.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = ArsMagicaApi.MOD_ID)
final class AMEventHandler {
    private AMEventHandler() {
    }

    @SubscribeEvent
    private static void addBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, AMBlocks.WITCHWOOD_SIGN.get(), AMBlocks.WITCHWOOD_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN, AMBlocks.WITCHWOOD_HANGING_SIGN.get(), AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get());
    }

    @SubscribeEvent
    private static void newRegistry(NewRegistryEvent event) {
        event.register(ArsMagicaApi.getSpellPartRegistry());
        event.register(ArsMagicaApi.getSpellDataComponentRegistry());
    }

    @SubscribeEvent
    private static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener((PreparableReloadListener) ArsMagicaApi.getSpellPartDataManager());
    }

    @SubscribeEvent
    private static void entityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AMAttributes.BURNOUT_REGENERATION);
        event.add(EntityType.PLAYER, AMAttributes.MANA_REGENERATION);
        event.add(EntityType.PLAYER, AMAttributes.MAX_BURNOUT);
        event.add(EntityType.PLAYER, AMAttributes.MAX_MANA);
    }

    @SubscribeEvent
    private static void advancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        String advancement = AMServerConfig.MAGIC_ADVANCEMENT.get();
        if (!advancement.isEmpty() && event.getAdvancement().id().toString().equals(advancement)) {
            ArsMagicaApi.getMagicHelper().initiateMagic(event.getEntity());
        }
    }

    @SubscribeEvent
    private static void entityTickPost(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity living)) return;
        ManaHelper manaHelper = ArsMagicaApi.getManaHelper();
        manaHelper.increaseMana(living, manaHelper.getManaRegeneration(living));
        BurnoutHelper burnoutHelper = ArsMagicaApi.getBurnoutHelper();
        burnoutHelper.decreaseBurnout(living, burnoutHelper.getBurnoutRegeneration(living));
    }
}
