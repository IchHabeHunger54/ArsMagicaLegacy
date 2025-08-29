package at.minecraftschurli.arsmagicalegacy.api.constants;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.network.chat.Component;

/**
 * Holds all translation keys and constant {@link Component}s used by Ars Magica: Legacy.
 */
public interface AMTranslations {
    // @formatter:off
    String CONFIG_KEY                               = ArsMagicaApi.MOD_ID + ".configuration.";
    String BARS_VALUE_BURNOUT_KEY                   = ArsMagicaApi.MOD_ID + ".bars_value.burnout";
    String BARS_VALUE_MANA_KEY                      = ArsMagicaApi.MOD_ID + ".bars_value.mana";
    String BARS_VALUE_XP_KEY                        = ArsMagicaApi.MOD_ID + ".bars_value.xp";
    String SPELL_INGREDIENT_COUNT_KEY               = ArsMagicaApi.MOD_ID + ".spell_ingredient_count";
    String COMMAND_MAGIC_XP_ADD_LEVELS_MULTIPLE_KEY = "commands." + ArsMagicaApi.MOD_ID + ".magic_xp.add.levels.multiple.success";
    String COMMAND_MAGIC_XP_ADD_LEVELS_SINGLE_KEY   = "commands." + ArsMagicaApi.MOD_ID + ".magic_xp.add.levels.single.success";
    String COMMAND_MAGIC_XP_ADD_POINTS_MULTIPLE_KEY = "commands." + ArsMagicaApi.MOD_ID + ".magic_xp.add.points.multiple.success";
    String COMMAND_MAGIC_XP_ADD_POINTS_SINGLE_KEY   = "commands." + ArsMagicaApi.MOD_ID + ".magic_xp.add.points.single.success";
    String COMMAND_MAGIC_XP_GET_LEVELS_KEY          = "commands." + ArsMagicaApi.MOD_ID + ".magic_xp.get.levels.success";
    String COMMAND_MAGIC_XP_GET_POINTS_KEY          = "commands." + ArsMagicaApi.MOD_ID + ".magic_xp.get.points.success";
    String COMMAND_MAGIC_XP_SET_LEVELS_MULTIPLE_KEY = "commands." + ArsMagicaApi.MOD_ID + ".magic_xp.set.levels.multiple.success";
    String COMMAND_MAGIC_XP_SET_LEVELS_SINGLE_KEY   = "commands." + ArsMagicaApi.MOD_ID + ".magic_xp.set.levels.single.success";
    String COMMAND_MAGIC_XP_SET_POINTS_MULTIPLE_KEY = "commands." + ArsMagicaApi.MOD_ID + ".magic_xp.set.points.multiple.success";
    String COMMAND_MAGIC_XP_SET_POINTS_SINGLE_KEY   = "commands." + ArsMagicaApi.MOD_ID + ".magic_xp.set.points.single.success";
    String COMMAND_SKILL_FORGET_ALL_MULTIPLE_KEY    = "commands." + ArsMagicaApi.MOD_ID + ".skill.forget.all.multiple.success";
    String COMMAND_SKILL_FORGET_ALL_SINGLE_KEY      = "commands." + ArsMagicaApi.MOD_ID + ".skill.forget.all.single.success";
    String COMMAND_SKILL_FORGET_MULTIPLE_KEY        = "commands." + ArsMagicaApi.MOD_ID + ".skill.forget.multiple.success";
    String COMMAND_SKILL_FORGET_SINGLE_KEY          = "commands." + ArsMagicaApi.MOD_ID + ".skill.forget.single.success";
    String COMMAND_SKILL_LEARN_ALL_MULTIPLE_KEY     = "commands." + ArsMagicaApi.MOD_ID + ".skill.learn.all.multiple.success";
    String COMMAND_SKILL_LEARN_ALL_SINGLE_KEY       = "commands." + ArsMagicaApi.MOD_ID + ".skill.learn.all.single.success";
    String COMMAND_SKILL_LEARN_MULTIPLE_KEY         = "commands." + ArsMagicaApi.MOD_ID + ".skill.learn.multiple.success";
    String COMMAND_SKILL_LEARN_SINGLE_KEY           = "commands." + ArsMagicaApi.MOD_ID + ".skill.learn.single.success";
    String COMMAND_SKILL_LIST_ALL_KEY               = "commands." + ArsMagicaApi.MOD_ID + ".skill.list.all.success";
    String COMMAND_SKILL_LIST_KNOWN_KEY             = "commands." + ArsMagicaApi.MOD_ID + ".skill.list.known.success";
    String COMMAND_SKILL_LIST_UNKNOWN_KEY           = "commands." + ArsMagicaApi.MOD_ID + ".skill.list.unknown.success";
    String COMMAND_SKILL_POINT_ADD_MULTIPLE_KEY     = "commands." + ArsMagicaApi.MOD_ID + ".skill_point.add.multiple.success";
    String COMMAND_SKILL_POINT_ADD_SINGLE_KEY       = "commands." + ArsMagicaApi.MOD_ID + ".skill_point.add.single.success";
    String COMMAND_SKILL_POINT_GET_KEY              = "commands." + ArsMagicaApi.MOD_ID + ".skill_point.get.success";
    String COMMAND_SKILL_POINT_SET_MULTIPLE_KEY     = "commands." + ArsMagicaApi.MOD_ID + ".skill_point.set.multiple.success";
    String COMMAND_SKILL_POINT_SET_SINGLE_KEY       = "commands." + ArsMagicaApi.MOD_ID + ".skill_point.set.single.success";
    String OCCULUS_MISSING_KEY                      = "gui." + ArsMagicaApi.MOD_ID + ".occulus.missing";

    String PREVENT_BLOCK_KEY                  = ArsMagicaApi.MOD_ID + ".prevent.block";
    String PREVENT_ITEM_KEY                   = ArsMagicaApi.MOD_ID + ".prevent.item";
    String SPELL_CAST_BURNED_OUT_KEY          = ArsMagicaApi.MOD_ID + ".spell_cast.burned_out";
    String SPELL_CAST_MALFORMED_KEY           = ArsMagicaApi.MOD_ID + ".spell_cast.malformed";
    String SPELL_CAST_NOT_ENOUGH_MANA_KEY     = ArsMagicaApi.MOD_ID + ".spell_cast.not_enough_mana";
    String COMMAND_SKILL_LIST_SEPARATOR_KEY   = "commands." + ArsMagicaApi.MOD_ID + ".skill.list.separator";
    String INSCRIPTION_TABLE_KEY              = "gui." + ArsMagicaApi.MOD_ID + ".inscription_table";
    String INSCRIPTION_TABLE_CREATE_SPELL_KEY = "gui." + ArsMagicaApi.MOD_ID + ".inscription_table.create_spell";
    String INSCRIPTION_TABLE_NAME_KEY         = "gui." + ArsMagicaApi.MOD_ID + ".inscription_table.name";
    String INSCRIPTION_TABLE_SEARCH_KEY       = "gui." + ArsMagicaApi.MOD_ID + ".inscription_table.search";
    String OCCULUS_KEY                        = "gui." + ArsMagicaApi.MOD_ID + ".occulus";
    String OCCULUS_FORGET_ALL_KEY             = "gui." + ArsMagicaApi.MOD_ID + ".occulus.forget_all";
    String OCCULUS_FORGET_ALL_TOOLTIP_KEY     = "gui." + ArsMagicaApi.MOD_ID + ".occulus.forget_all.tooltip";
    String OCCULUS_NEXT_KEY                   = "gui." + ArsMagicaApi.MOD_ID + ".occulus.next";
    String OCCULUS_PREV_KEY                   = "gui." + ArsMagicaApi.MOD_ID + ".occulus.prev";

    Component PREVENT_BLOCK                  = Component.translatable(PREVENT_BLOCK_KEY);
    Component PREVENT_ITEM                   = Component.translatable(PREVENT_ITEM_KEY);
    Component SPELL_CAST_BURNED_OUT          = Component.translatable(SPELL_CAST_BURNED_OUT_KEY);
    Component SPELL_CAST_MALFORMED           = Component.translatable(SPELL_CAST_MALFORMED_KEY);
    Component SPELL_CAST_NOT_ENOUGH_MANA     = Component.translatable(SPELL_CAST_NOT_ENOUGH_MANA_KEY);
    Component COMMAND_SKILL_LIST_SEPARATOR   = Component.translatable(COMMAND_SKILL_LIST_SEPARATOR_KEY);
    Component INSCRIPTION_TABLE              = Component.translatable(INSCRIPTION_TABLE_KEY);
    Component INSCRIPTION_TABLE_CREATE_SPELL = Component.translatable(INSCRIPTION_TABLE_CREATE_SPELL_KEY);
    Component INSCRIPTION_TABLE_NAME         = Component.translatable(INSCRIPTION_TABLE_NAME_KEY);
    Component INSCRIPTION_TABLE_SEARCH       = Component.translatable(INSCRIPTION_TABLE_SEARCH_KEY);
    Component OCCULUS                        = Component.translatable(OCCULUS_KEY);
    Component OCCULUS_FORGET_ALL             = Component.translatable(OCCULUS_FORGET_ALL_KEY);
    Component OCCULUS_FORGET_ALL_TOOLTIP     = Component.translatable(OCCULUS_FORGET_ALL_TOOLTIP_KEY);
    Component OCCULUS_NEXT                   = Component.translatable(OCCULUS_NEXT_KEY);
    Component OCCULUS_PREV                   = Component.translatable(OCCULUS_PREV_KEY);
    // @formatter:on
}
