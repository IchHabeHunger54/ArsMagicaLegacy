package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public interface AMRegistries {
    // @formatter:off
    DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ArsMagicaApi.MOD_ID);
    DeferredRegister.Items  ITEMS  = DeferredRegister.createItems(ArsMagicaApi.MOD_ID);
    DeferredRegister.DataComponents DATA_COMPONENTS       = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE,           ArsMagicaApi.MOD_ID);
    DeferredRegister<Attribute>                               ATTRIBUTES            = DeferredRegister.create(Registries.ATTRIBUTE,                                     ArsMagicaApi.MOD_ID);
    DeferredRegister<BlockEntityType<?>>                      BLOCK_ENTITIES        = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,                             ArsMagicaApi.MOD_ID);
    DeferredRegister<CreativeModeTab>                         CREATIVE_TABS         = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,                             ArsMagicaApi.MOD_ID);
    DeferredRegister<EntityType<?>>                           ENTITIES              = DeferredRegister.create(Registries.ENTITY_TYPE,                                   ArsMagicaApi.MOD_ID);
    DeferredRegister<MenuType<?>>                             MENUS                 = DeferredRegister.create(Registries.MENU,                                          ArsMagicaApi.MOD_ID);
    DeferredRegister<MobEffect>                               MOB_EFFECTS           = DeferredRegister.create(Registries.MOB_EFFECT,                                    ArsMagicaApi.MOD_ID);
    DeferredRegister<ParticleType<?>>                         PARTICLES             = DeferredRegister.create(Registries.PARTICLE_TYPE,                                 ArsMagicaApi.MOD_ID);
    DeferredRegister<Potion>                                  POTIONS               = DeferredRegister.create(Registries.POTION,                                        ArsMagicaApi.MOD_ID);
    DeferredRegister<SoundEvent>                              SOUND_EVENTS          = DeferredRegister.create(Registries.SOUND_EVENT,                                   ArsMagicaApi.MOD_ID);
    DeferredRegister<Feature<?>>                              FEATURES              = DeferredRegister.create(Registries.FEATURE,                                       ArsMagicaApi.MOD_ID);
    DeferredRegister<LootItemConditionType>                   LOOT_CONDITIONS       = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE,                           ArsMagicaApi.MOD_ID);
    DeferredRegister<AttachmentType<?>>                       ATTACHMENTS           = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES,                 ArsMagicaApi.MOD_ID);
    DeferredRegister<EntityDataSerializer<?>>                 DATA_SERIALIZERS      = DeferredRegister.create(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS,          ArsMagicaApi.MOD_ID);
    DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ArsMagicaApi.MOD_ID);
    DeferredRegister<MapCodec<? extends AbilityEffect>>       ABILITY_EFFECTS       = DeferredRegister.create(AMRegistryKeys.ABILITY_EFFECT,                            ArsMagicaApi.MOD_ID);
    DeferredRegister<MapCodec<? extends SpellIngredient>>     SPELL_INGREDIENTS     = DeferredRegister.create(AMRegistryKeys.SPELL_INGREDIENT,                          ArsMagicaApi.MOD_ID);
    DeferredRegister<SpellPart>                               SPELL_PARTS           = DeferredRegister.create(AMRegistryKeys.SPELL_PART,                                ArsMagicaApi.MOD_ID);
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
        AMEntities.init();
        AMMenus.init();
        AMMobEffects.init();
        AMParticles.init();
        AMSounds.init();
        AMWorldgen.init();
        AMLoot.init();
        AMAttachments.init();
        AMAbilities.init();
        AMSpells.init();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        DATA_COMPONENTS.register(bus);
        ATTRIBUTES.register(bus);
        BLOCK_ENTITIES.register(bus);
        CREATIVE_TABS.register(bus);
        ENTITIES.register(bus);
        MENUS.register(bus);
        MOB_EFFECTS.register(bus);
        PARTICLES.register(bus);
        POTIONS.register(bus);
        SOUND_EVENTS.register(bus);
        FEATURES.register(bus);
        LOOT_CONDITIONS.register(bus);
        ATTACHMENTS.register(bus);
        DATA_SERIALIZERS.register(bus);
        GLOBAL_LOOT_MODIFIERS.register(bus);
        ABILITY_EFFECTS.register(bus);
        SPELL_INGREDIENTS.register(bus);
        SPELL_PARTS.register(bus);
    }
}
