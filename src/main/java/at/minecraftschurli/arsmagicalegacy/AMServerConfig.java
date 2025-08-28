package at.minecraftschurli.arsmagicalegacy;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class AMServerConfig {
    public static final ModConfigSpec.BooleanValue INSCRIPTION_TABLE_IN_WORLD_UPGRADING;
    public static final ModConfigSpec.ConfigValue<String> MAGIC_ADVANCEMENT;
    public static final ModConfigSpec.DoubleValue MANA_TO_BURNOUT_RATIO;
    public static final ModConfigSpec.DoubleValue MANA_BASE;
    public static final ModConfigSpec.DoubleValue MANA_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue MANA_REGENERATION;
    public static final ModConfigSpec.DoubleValue BURNOUT_BASE;
    public static final ModConfigSpec.DoubleValue BURNOUT_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue BURNOUT_REGENERATION;
    public static final ModConfigSpec.DoubleValue LEVEL_BASE;
    public static final ModConfigSpec.DoubleValue LEVEL_MULTIPLIER;
    public static final ModConfigSpec.IntValue EXTRA_SKILL_POINTS;
    public static final ModConfigSpec.DoubleValue AFFINITY_TO_XP_RATIO;
    public static final ModConfigSpec.DoubleValue CONTINUOUS_MODIFIER;
    public static final ModConfigSpec.DoubleValue DIRECT_OPPOSITE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue MAJOR_OPPOSITE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue MINOR_OPPOSITE_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue ADJACENT_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue AFFINITY_GAINS_MODIFIER;
    public static final ModConfigSpec.DoubleValue AFFINITY_GAINS_XP_MODIFIER;
    static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        INSCRIPTION_TABLE_IN_WORLD_UPGRADING = builder
            .comment("Whether inscription table upgrading is allowed in-world. If disabled, the upgrades must be applied through crafting.")
            .translation(AMTranslations.CONFIG_KEY + "inscription_table_in_world_upgrading")
            .define("inscription_table_in_world_upgrading", true);
        MAGIC_ADVANCEMENT = builder
            .comment("Completing this advancement will unlock magic for the player. Leave empty to not require an advancement and have magic unlocked from the start.")
            .translation(AMTranslations.CONFIG_KEY + "magic_advancement")
            .define("magic_advancement", ArsMagicaApi.modLoc("root").toString(), AMServerConfig::isValidResourceLocationOrEmpty);
        MANA_TO_BURNOUT_RATIO = builder
            .comment("The default mana to burnout ratio, used in calculating spell costs.")
            .translation(AMTranslations.CONFIG_KEY + "mana_to_burnout_ratio")
            .defineInRange("mana_to_burnout_ratio", 0.5, 0, 10);
        builder.push("mana");
        MANA_BASE = builder
            .comment("The base value for mana calculation. Mana is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG_KEY + "mana_base")
            .worldRestart()
            .defineInRange("base", 200., 0, 1000000);
        MANA_MULTIPLIER = builder
            .comment("The multiplier for mana calculation. Mana is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG_KEY + "mana_multiplier")
            .worldRestart()
            .defineInRange("multiplier", 25., 0, 1000000);
        MANA_REGENERATION = builder
            .comment("The multiplier for mana regeneration. Mana regeneration is calculated as (base + multiplier * (level - 1)) * regeneration.")
            .translation(AMTranslations.CONFIG_KEY + "mana_regeneration")
            .worldRestart()
            .defineInRange("regeneration", 0.001, 0, 1000000);
        builder.pop();
        builder.push("burnout");
        BURNOUT_BASE = builder
            .comment("The base value for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG_KEY + "burnout_base")
            .worldRestart()
            .defineInRange("base", 200., 0, 1000000);
        BURNOUT_MULTIPLIER = builder
            .comment("The multiplier for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG_KEY + "burnout_multiplier")
            .worldRestart()
            .defineInRange("multiplier", 25., 0, 1000000);
        BURNOUT_REGENERATION = builder
            .comment("The multiplier for burnout regeneration. Burnout regeneration is calculated as (base + multiplier * (level - 1)) * regeneration.")
            .translation(AMTranslations.CONFIG_KEY + "burnout_regeneration")
            .worldRestart()
            .defineInRange("regeneration", 0.001, 0, 1000000);
        builder.pop();
        builder.push("level");
        LEVEL_BASE = builder
            .comment("The base value for leveling calculation. XP cost is calculated as multiplier * base ^ (level - 1).")
            .translation(AMTranslations.CONFIG_KEY + "level_base")
            .worldRestart()
            .defineInRange("base", 1.2, 0, 10000);
        LEVEL_MULTIPLIER = builder
            .comment("The multiplier for leveling calculation. XP cost is calculated as multiplier * base ^ (level - 1).")
            .translation(AMTranslations.CONFIG_KEY + "level_multiplier")
            .worldRestart()
            .defineInRange("multiplier", 2.4, 0, 10000);
        EXTRA_SKILL_POINTS = builder
            .comment("The extra blue skill points a player gets at level 1, in addition to the one they already get.")
            .translation(AMTranslations.CONFIG_KEY + "extra_skill_points")
            .defineInRange("extra_skill_points", 2, 0, Short.MAX_VALUE);
        builder.pop();
        builder.push("affinity");
        AFFINITY_TO_XP_RATIO = builder
            .comment("The affinity to xp ratio. When awarding xp, the amount of used affinities will be multiplied with this modifier.")
            .translation(AMTranslations.CONFIG_KEY + "affinity_to_xp_ration")
            .defineInRange("affinity_to_xp_ratio", 0.05, 0, 1);
        CONTINUOUS_MODIFIER = builder
            .comment("By what factor affinity and xp gain will be amplified when a continuous spell shape is used.")
            .translation(AMTranslations.CONFIG_KEY + "continuous_modifier")
            .defineInRange("continuous_modifier", 0.25, 0, 1);
        DIRECT_OPPOSITE_MULTIPLIER = builder
            .comment("When an affinity shift is applied, what portion of it is subtracted from the direct opposite affinity.")
            .translation(AMTranslations.CONFIG_KEY + "direct_opposite_multiplier")
            .defineInRange("direct_opposite_multiplier", 0.75, 0, 1);
        MAJOR_OPPOSITE_MULTIPLIER = builder
            .comment("When an affinity shift is applied, what portion of it is subtracted from the major opposite affinities.")
            .translation(AMTranslations.CONFIG_KEY + "major_opposite_multiplier")
            .defineInRange("major_opposite_multiplier", 0.5, 0, 1);
        MINOR_OPPOSITE_MULTIPLIER = builder
            .comment("When an affinity shift is applied, what portion of it is subtracted from the minor opposite affinities.")
            .translation(AMTranslations.CONFIG_KEY + "minor_opposite_multiplier")
            .defineInRange("minor_opposite_multiplier", 0.25, 0, 1);
        ADJACENT_MULTIPLIER = builder
            .comment("When an affinity shift is applied, what portion of it is added to the adjacent affinities.")
            .translation(AMTranslations.CONFIG_KEY + "adjacent_multiplier")
            .defineInRange("adjacent_multiplier", 0.25, 0, 1);
        builder.pop();
        builder.push("skills");
        AFFINITY_GAINS_MODIFIER = builder
            .comment("When the Affinity Gains talent is learned, by what factor affinity gain will be amplified.")
            .translation(AMTranslations.CONFIG_KEY + "affinity_gains_modifier")
            .defineInRange("affinity_gains_modifier", 1.1, 1, 100);
        AFFINITY_GAINS_XP_MODIFIER = builder
            .comment("When the Affinity Gains talent is learned, by what factor XP gain will be amplified.")
            .translation(AMTranslations.CONFIG_KEY + "affinity_gains_xp_modifier")
            .defineInRange("affinity_gains_xp_modifier", 0.9, 0, 1);
        builder.pop();
        SPEC = builder.build();
    }

    private static boolean isValidResourceLocationOrEmpty(Object o) {
        if (o == null) return false;
        String s = o.toString();
        if (s.isEmpty()) return true;
        if (!s.contains(":")) return ResourceLocation.isValidPath(s);
        String[] split = s.split(":");
        return split.length == 2 && ResourceLocation.isValidNamespace(split[0]) && ResourceLocation.isValidPath(split[1]);
    }
}
