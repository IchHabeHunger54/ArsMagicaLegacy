package at.minecraftschurli.arsmagicalegacy.api.constants;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.network.chat.Component;

public interface AMTranslations {
    // @formatter:off
    String CONFIG_KEY             = ArsMagicaApi.MOD_ID + ".configuration.";
    String BARS_VALUE_BURNOUT_KEY = ArsMagicaApi.MOD_ID + ".bars_value.burnout";
    String BARS_VALUE_MANA_KEY    = ArsMagicaApi.MOD_ID + ".bars_value.mana";
    String BARS_VALUE_XP_KEY      = ArsMagicaApi.MOD_ID + ".bars_value.xp";
    String OCCULUS_MISSING_KEY    = "gui." + ArsMagicaApi.MOD_ID + ".occulus.missing";

    String PREVENT_BLOCK_KEY              = ArsMagicaApi.MOD_ID + ".prevent.block";
    String SPELL_CAST_BURNED_OUT_KEY      = ArsMagicaApi.MOD_ID + ".spell_cast.burned_out";
    String SPELL_CAST_MALFORMED_KEY       = ArsMagicaApi.MOD_ID + ".spell_cast.malformed";
    String SPELL_CAST_NOT_ENOUGH_MANA_KEY = ArsMagicaApi.MOD_ID + ".spell_cast.not_enough_mana";
    String OCCULUS_KEY                    = "gui." + ArsMagicaApi.MOD_ID + ".occulus";
    String OCCULUS_NEXT_KEY               = "gui." + ArsMagicaApi.MOD_ID + ".occulus.next";
    String OCCULUS_PREV_KEY               = "gui." + ArsMagicaApi.MOD_ID + ".occulus.prev";

    Component PREVENT_BLOCK              = Component.translatable(PREVENT_BLOCK_KEY);
    Component SPELL_CAST_BURNED_OUT      = Component.translatable(SPELL_CAST_BURNED_OUT_KEY);
    Component SPELL_CAST_MALFORMED       = Component.translatable(SPELL_CAST_MALFORMED_KEY);
    Component SPELL_CAST_NOT_ENOUGH_MANA = Component.translatable(SPELL_CAST_NOT_ENOUGH_MANA_KEY);
    Component OCCULUS                    = Component.translatable(OCCULUS_KEY);
    Component OCCULUS_NEXT               = Component.translatable(OCCULUS_NEXT_KEY);
    Component OCCULUS_PREV               = Component.translatable(OCCULUS_PREV_KEY);
    // @formatter:on
}
