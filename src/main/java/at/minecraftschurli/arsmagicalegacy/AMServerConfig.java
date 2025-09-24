package at.minecraftschurli.arsmagicalegacy;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class AMServerConfig {
    public static final ModConfigSpec.IntValue ALTAR_CHECK_INTERVAL;
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
    public static final ModConfigSpec.DoubleValue EFFECT_DURATION;
    public static final ModConfigSpec.DoubleValue ATTRACT_RANGE;
    public static final ModConfigSpec.DoubleValue ATTRACT_SPEED;
    public static final ModConfigSpec.DoubleValue BANISH_RAIN_DURATION;
    public static final ModConfigSpec.DoubleValue BLINK_RANGE;
    public static final ModConfigSpec.DoubleValue EXPLOSION_RANGE;
    public static final ModConfigSpec.DoubleValue FLING_SPEED;
    public static final ModConfigSpec.BooleanValue FORGE_SMELTS_VILLAGERS;
    public static final ModConfigSpec.DoubleValue FROST_DURATION;
    public static final ModConfigSpec.DoubleValue LIFE_DRAIN_DAMAGE;
    public static final ModConfigSpec.DoubleValue LIFE_TAP_DAMAGE;
    public static final ModConfigSpec.DoubleValue LIFE_TAP_FACTOR;
    public static final ModConfigSpec.DoubleValue MANA_BLAST_FACTOR;
    public static final ModConfigSpec.DoubleValue MANA_DRAIN_MAX;
    public static final ModConfigSpec.DoubleValue MELT_ARMOR_FACTOR;
    public static final ModConfigSpec.IntValue RANDOM_TELEPORT_MAX_TRIES;
    public static final ModConfigSpec.DoubleValue RANDOM_TELEPORT_RANGE;
    public static final ModConfigSpec.DoubleValue STORM_DURATION;
    public static final ModConfigSpec.DoubleValue STORM_RANGE;
    public static final ModConfigSpec.DoubleValue STORM_LIGHTNING_BOLT_CHANCE;
    public static final ModConfigSpec.DoubleValue STORM_LIGHTNING_BOLT_TARGET_CHANCE;
    public static final ModConfigSpec.DoubleValue WIZARDS_AUTUMN_RANGE;
    public static final ModConfigSpec.DoubleValue PROJECTILE_DURATION;
    public static final ModConfigSpec.DoubleValue PROJECTILE_GRAVITY;
    public static final ModConfigSpec.DoubleValue PROJECTILE_SPEED;
    static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        ALTAR_CHECK_INTERVAL = builder
            .comment("The time in ticks between multiblock checks for the altar.")
            .translation(AMTranslations.CONFIG_KEY + "altar_check_interval")
            .defineInRange("altar_check_interval", 20, 1, 200);
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
        builder.comment("Configuration for the mana leveling and regeneration of players.").push("mana");
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
        builder.comment("Configuration for the burnout leveling and regeneration of players.").push("burnout");
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
        builder.comment("Configuration for the magic leveling of players.").push("level");
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
        builder.comment("Configuration for the affinity shifting of players.").push("affinity");
        AFFINITY_TO_XP_RATIO = builder
            .comment("The affinity to xp ratio. When awarding xp, the amount of used affinities will be multiplied with this modifier.")
            .translation(AMTranslations.CONFIG_KEY + "affinity_to_xp_ratio")
            .defineInRange("affinity_to_xp_ratio", 1., 0, 10);
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
        AFFINITY_GAINS_MODIFIER = builder
            .comment("When the Affinity Gains talent is learned, by what factor affinity gain will be amplified.")
            .translation(AMTranslations.CONFIG_KEY + "affinity_gains_modifier")
            .defineInRange("affinity_gains_modifier", 1.1, 1, 100);
        AFFINITY_GAINS_XP_MODIFIER = builder
            .comment("When the Affinity Gains talent is learned, by what factor XP gain will be amplified.")
            .translation(AMTranslations.CONFIG_KEY + "affinity_gains_xp_modifier")
            .defineInRange("affinity_gains_xp_modifier", 0.9, 0, 1);
        builder.pop();
        builder.comment("Configuration of various component-specific values.").push("components");
        EFFECT_DURATION = builder
            .comment("The duration of effect-based components, in ticks. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "effect_duration")
            .defineInRange("effect_duration", 600., 1, Short.MAX_VALUE);
        ATTRACT_RANGE = builder
            .comment("The range of the Attract component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "attract_range")
            .defineInRange("attract_range", 4., 1, 16);
        ATTRACT_SPEED = builder
            .comment("The speed of the Attract component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "attract_speed")
            .defineInRange("attract_speed", 1., 1, 16);
        BANISH_RAIN_DURATION = builder
            .comment("The duration used by the Banish Rain component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "banish_rain_duration")
            .defineInRange("banish_rain_duration", 24000., 1, Integer.MAX_VALUE);
        BLINK_RANGE = builder
            .comment("The range of the Blink component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "blink_range")
            .defineInRange("blink_range", 16., 1, 64);
        EXPLOSION_RANGE = builder
            .comment("The range of the Explosion component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "explosion_range")
            .defineInRange("explosion_range", 2., 1, 16);
        FLING_SPEED = builder
            .comment("The speed of the Fling component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "fling_speed")
            .defineInRange("fling_speed", 1., 1, 16);
        FORGE_SMELTS_VILLAGERS = builder
            .comment("Whether the Forge component instantly kills villagers, dropping emeralds.")
            .translation(AMTranslations.CONFIG_KEY + "forge_smelts_villagers")
            .define("forge_smelts_villagers", true);
        FROST_DURATION = builder
            .comment("The duration of the Frost component, in ticks. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "frost_duration")
            .defineInRange("frost_duration", 600., 1, Short.MAX_VALUE);
        LIFE_DRAIN_DAMAGE = builder
            .comment("The damage of the Life Drain component, in ticks. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "life_drain_damage")
            .defineInRange("life_drain_damage", 2., 1, 100);
        LIFE_TAP_DAMAGE = builder
            .comment("The damage of the Life Tap component, in ticks. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "life_tap_damage")
            .defineInRange("life_tap_damage", 2., 1, 100);
        LIFE_TAP_FACTOR = builder
            .comment("When the Life Tap component is cast, the caster regenerates the damage dealt, times their max mana, times this factor.")
            .translation(AMTranslations.CONFIG_KEY + "life_tap_factor")
            .defineInRange("life_tap_factor", 0.01, 0, 1);
        MANA_BLAST_FACTOR = builder
            .comment("When the Mana Blast component is cast, the damage is the caster's current mana, times this factor, potentially amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "mana_blast_factor")
            .defineInRange("mana_blast_factor", 0.04, 0, 1);
        MANA_DRAIN_MAX = builder
            .comment("The maximum amount of mana drained by the Mana Drain component.")
            .translation(AMTranslations.CONFIG_KEY + "mana_drain_max")
            .defineInRange("mana_drain_max", 250., 0, Short.MAX_VALUE);
        MELT_ARMOR_FACTOR = builder
            .comment("When the Melt Armor component is cast, what factor the armor's durability will be multiplied with.")
            .translation(AMTranslations.CONFIG_KEY + "melt_armor_factor")
            .defineInRange("melt_armor_factor", 0.75, 0, 1);
        RANDOM_TELEPORT_MAX_TRIES = builder
            .comment("How many times the Random Teleport component will try to find a position.")
            .translation(AMTranslations.CONFIG_KEY + "random_teleport_max_tries")
            .defineInRange("random_teleport_max_tries", 64, 1, Short.MAX_VALUE);
        RANDOM_TELEPORT_RANGE = builder
            .comment("The range of the Random Teleport component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "random_teleport_range")
            .defineInRange("random_teleport_range", 16., 1, 64);
        STORM_DURATION = builder
            .comment("The duration used by the Storm component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "storm_duration")
            .defineInRange("storm_duration", 72000., 1, Integer.MAX_VALUE);
        STORM_RANGE = builder
            .comment("The range used by the Storm component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "storm_range")
            .defineInRange("storm_range", 64., 1, 256);
        STORM_LIGHTNING_BOLT_CHANCE = builder
            .comment("The chance for the Storm component to summon a lightning bolt somewhere in range.")
            .translation(AMTranslations.CONFIG_KEY + "storm_lightning_bolt_chance")
            .defineInRange("storm_lightning_bolt_chance", 0.2, 0, 1);
        STORM_LIGHTNING_BOLT_TARGET_CHANCE = builder
            .comment("The chance for the Storm component to summon a target-seeking lightning bolt somewhere in range.")
            .translation(AMTranslations.CONFIG_KEY + "storm_lightning_bolt_target_chance")
            .defineInRange("storm_lightning_bolt_target_chance", 0.2, 0, 1);
        WIZARDS_AUTUMN_RANGE = builder
            .comment("The range used by the Wizard's Autumn component. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "wizards_autumn_range")
            .defineInRange("wizards_autumn_range", 2., 1, 64);
        builder.pop();
        builder.comment("Configuration of various shape-specific values.").push("shapes");
        PROJECTILE_DURATION = builder
            .comment("The duration used by the Projectile shape. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "projectile_duration")
            .defineInRange(AMTranslations.CONFIG_KEY + "projectile_duration", 30., 1, Short.MAX_VALUE);
        PROJECTILE_GRAVITY = builder
            .comment("If a Gravity modifier is present on the Projectile, by how much gravity will be increased.")
            .translation(AMTranslations.CONFIG_KEY + "projectile_gravity")
            .defineInRange(AMTranslations.CONFIG_KEY + "projectile_gravity", 0.025, 0, 1);
        PROJECTILE_SPEED = builder
            .comment("The speed used by the Projectile shape. May be amplified by spell modifiers.")
            .translation(AMTranslations.CONFIG_KEY + "projectile_speed")
            .defineInRange(AMTranslations.CONFIG_KEY + "projectile_speed", 0.2, 0, 10);
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
