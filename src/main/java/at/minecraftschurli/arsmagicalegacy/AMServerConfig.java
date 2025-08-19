package at.minecraftschurli.arsmagicalegacy;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class AMServerConfig {
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
    static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        MAGIC_ADVANCEMENT = builder
            .comment("Completing this advancement will unlock magic for the player. Leave empty to not require an advancement and have magic unlocked from the start.")
            .define("magic_advancement", ArsMagicaApi.modLoc("root").toString(), AMServerConfig::isValidResourceLocationOrEmpty);
        MANA_TO_BURNOUT_RATIO = builder
            .comment("The default mana to burnout ratio, used in calculating spell costs.")
            .translation(AMTranslations.CONFIG + "mana_to_burnout_ratio")
            .defineInRange("mana_to_burnout_ratio", 0.5, 0, 10);
        builder.push("mana");
        MANA_BASE = builder
            .comment("The base value for mana calculation. Mana is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG + "mana_base")
            .worldRestart()
            .defineInRange("base", 200., 0, 1000000);
        MANA_MULTIPLIER = builder
            .comment("The multiplier for mana calculation. Mana is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG + "mana_multiplier")
            .worldRestart()
            .defineInRange("multiplier", 25., 0, 1000000);
        MANA_REGENERATION = builder
            .comment("The multiplier for mana regeneration. Mana regeneration is calculated as (base + multiplier * (level - 1)) * regeneration.")
            .translation(AMTranslations.CONFIG + "mana_regeneration")
            .worldRestart()
            .defineInRange("regeneration", 0.001, 0, 1000000);
        builder.pop();
        builder.push("burnout");
        BURNOUT_BASE = builder
            .comment("The base value for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG + "burnout_base")
            .worldRestart()
            .defineInRange("base", 200., 0, 1000000);
        BURNOUT_MULTIPLIER = builder
            .comment("The multiplier for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG + "burnout_multiplier")
            .worldRestart()
            .defineInRange("multiplier", 25., 0, 1000000);
        BURNOUT_REGENERATION = builder
            .comment("The multiplier for burnout regeneration. Burnout regeneration is calculated as (base + multiplier * (level - 1)) * regeneration.")
            .translation(AMTranslations.CONFIG + "burnout_regeneration")
            .worldRestart()
            .defineInRange("regeneration", 0.001, 0, 1000000);
        builder.pop();
        builder.push("level");
        LEVEL_BASE = builder
            .comment("The base value for leveling calculation. XP cost is calculated as multiplier * base ^ (level - 1).")
            .translation(AMTranslations.CONFIG + "level_base")
            .worldRestart()
            .defineInRange("base", 1.2, 0, 10000);
        LEVEL_MULTIPLIER = builder
            .comment("The multiplier for leveling calculation. XP cost is calculated as multiplier * base ^ (level - 1).")
            .translation(AMTranslations.CONFIG + "level_multiplier")
            .worldRestart()
            .defineInRange("multiplier", 2.4, 0, 10000);
        EXTRA_SKILL_POINTS = builder
            .comment("The extra blue skill points a player gets at level 1, in addition to the one they already get.")
            .translation(AMTranslations.CONFIG + "extra_skill_points")
            .defineInRange("extra_skill_points", 2, 0, Short.MAX_VALUE);
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
