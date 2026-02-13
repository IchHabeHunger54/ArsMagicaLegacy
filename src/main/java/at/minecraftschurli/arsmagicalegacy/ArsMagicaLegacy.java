package at.minecraftschurli.arsmagicalegacy;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMAbilities;
import at.minecraftschurli.arsmagicalegacy.init.AMArmorMaterials;
import at.minecraftschurli.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMCreativeTabs;
import at.minecraftschurli.arsmagicalegacy.init.AMCriterionTriggers;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMFluids;
import at.minecraftschurli.arsmagicalegacy.init.AMGrowthTypes;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMLoot;
import at.minecraftschurli.arsmagicalegacy.init.AMMenus;
import at.minecraftschurli.arsmagicalegacy.init.AMMobEffects;
import at.minecraftschurli.arsmagicalegacy.init.AMParticles;
import at.minecraftschurli.arsmagicalegacy.init.AMRecipes;
import at.minecraftschurli.arsmagicalegacy.init.AMSounds;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.init.AMWorldgen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(ArsMagicaApi.MOD_ID)
public final class ArsMagicaLegacy {
    public ArsMagicaLegacy(ModContainer container, IEventBus bus) {
        container.registerConfig(ModConfig.Type.SERVER, AMServerConfig.SPEC);
        register(bus);
    }

    /**
     * Registers the {@link DeferredRegister}s.
     *
     * @param bus The {@link IEventBus} to use.
     */
    private void register(IEventBus bus) {
        AMBlocks.BLOCKS.register(bus);
        AMItems.ITEMS.register(bus);
        AMFluids.FLUIDS.register(bus);
        AMFluids.FLUID_TYPES.register(bus);
        AMArmorMaterials.ARMOR_MATERIALS.register(bus);
        AMDataComponents.DATA_COMPONENTS.register(bus);
        AMAttributes.ATTRIBUTES.register(bus);
        AMBlockEntities.BLOCK_ENTITIES.register(bus);
        AMCreativeTabs.CREATIVE_TABS.register(bus);
        AMCriterionTriggers.TRIGGER_TYPES.register(bus);
        AMEntities.ENTITIES.register(bus);
        AMMenus.MENUS.register(bus);
        AMMobEffects.MOB_EFFECTS.register(bus);
        AMParticles.PARTICLES.register(bus);
        AMMobEffects.POTIONS.register(bus);
        AMSounds.SOUND_EVENTS.register(bus);
        AMWorldgen.FEATURES.register(bus);
        AMWorldgen.RULE_TESTS.register(bus);
        AMLoot.LOOT_CONDITIONS.register(bus);
        AMLoot.NUMBER_PROVIDERS.register(bus);
        AMRecipes.RECIPE_SERIALIZERS.register(bus);
        AMRecipes.RECIPE_TYPES.register(bus);
        AMAttachments.ATTACHMENTS.register(bus);
        AMSpells.DATA_SERIALIZERS.register(bus);
        AMLoot.GLOBAL_LOOT_MODIFIERS.register(bus);
        AMAbilities.ABILITY_EFFECTS.register(bus);
        AMGrowthTypes.GROWTH_TYPES.register(bus);
        AMSpells.SPELL_INGREDIENTS.register(bus);
        AMSpells.SPELL_PARTS.register(bus);
    }
}
