package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public interface AMRegistries {
    // @formatter:off
    DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ArsMagicaApi.MOD_ID);
    DeferredRegister.Items  ITEMS  = DeferredRegister.createItems(ArsMagicaApi.MOD_ID);
    DeferredRegister.DataComponents                       DATA_COMPONENTS   = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ArsMagicaApi.MOD_ID);
    DeferredRegister<Attribute>                           ATTRIBUTES        = DeferredRegister.create(Registries.ATTRIBUTE,                         ArsMagicaApi.MOD_ID);
    DeferredRegister<BlockEntityType<?>>                  BLOCK_ENTITIES    = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,                 ArsMagicaApi.MOD_ID);
    DeferredRegister<CreativeModeTab>                     CREATIVE_TABS     = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,                 ArsMagicaApi.MOD_ID);
    DeferredRegister<MenuType<?>>                         MENUS             = DeferredRegister.create(Registries.MENU,                              ArsMagicaApi.MOD_ID);
    DeferredRegister<SoundEvent>                          SOUND_EVENTS      = DeferredRegister.create(Registries.SOUND_EVENT,                       ArsMagicaApi.MOD_ID);
    DeferredRegister<Feature<?>>                          FEATURES          = DeferredRegister.create(Registries.FEATURE,                           ArsMagicaApi.MOD_ID);
    DeferredRegister<AttachmentType<?>>                   ATTACHMENTS       = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES,     ArsMagicaApi.MOD_ID);
    DeferredRegister<SpellPart>                           SPELL_PARTS       = DeferredRegister.create(AMRegistryKeys.SPELL_PART,                    ArsMagicaApi.MOD_ID);
    DeferredRegister<MapCodec<? extends SpellIngredient>> SPELL_INGREDIENTS = DeferredRegister.create(AMRegistryKeys.SPELL_INGREDIENT,              ArsMagicaApi.MOD_ID);
    DeferredRegister<MapCodec<? extends AbilityEffect>>   ABILITY_EFFECTS   = DeferredRegister.create(AMRegistryKeys.ABILITY_EFFECT,                ArsMagicaApi.MOD_ID);
    // @formatter:on

    /**
     * Classloads the registration classes and registers the {@link DeferredRegister}s.
     *
     * @param bus The {@link IEventBus} to use.
     */
    static void init(IEventBus bus) {
        AMBlocks.init();
        AMItems.init();
        AMDataComponents.init();
        AMAttributes.init();
        AMBlockEntities.init();
        AMCreativeTabs.init();
        AMMenus.init();
        AMSounds.init();
        AMWorldgen.init();
        AMAttachments.init();
        AMSpells.init();
        AMAbilities.init();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        DATA_COMPONENTS.register(bus);
        ATTRIBUTES.register(bus);
        BLOCK_ENTITIES.register(bus);
        CREATIVE_TABS.register(bus);
        MENUS.register(bus);
        SOUND_EVENTS.register(bus);
        FEATURES.register(bus);
        ATTACHMENTS.register(bus);
        SPELL_INGREDIENTS.register(bus);
        SPELL_PARTS.register(bus);
        ABILITY_EFFECTS.register(bus);
    }
}
