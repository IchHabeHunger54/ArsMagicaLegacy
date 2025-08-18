package at.minecraftschurli.arsmagicalegacy;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class AMServerConfig {
    public static final ModConfigSpec.ConfigValue<String> MAGIC_ADVANCEMENT;
    public static final ModConfigSpec.DoubleValue MANA_BASE;
    public static final ModConfigSpec.DoubleValue MANA_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue MANA_REGENERATION;
    static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        MAGIC_ADVANCEMENT = builder
            .comment("Completing this advancement will unlock magic for the player.")
            .define("magic_advancement", ArsMagicaApi.modLoc("root").toString(), AMServerConfig::isValidResourceLocation);
        builder.push("mana");
        MANA_BASE = builder
            .comment("The base value for mana calculation. Mana is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG + "mana.base")
            .worldRestart()
            .defineInRange("base", 200., 0, 1000000);
        MANA_MULTIPLIER = builder
            .comment("The multiplier for mana calculation. Mana is calculated as base + multiplier * (level - 1).")
            .translation(AMTranslations.CONFIG + "mana.multiplier")
            .worldRestart()
            .defineInRange("multiplier", 25., 0, 1000000);
        MANA_REGENERATION = builder
            .comment("The multiplier for mana regeneration. Mana regen is calculated as (base + multiplier * (level - 1)) * regeneration.")
            .translation(AMTranslations.CONFIG + "mana.regeneration")
            .worldRestart()
            .defineInRange("regeneration", 0.001, 0, 1000000);
        builder.pop();
        SPEC = builder.build();
    }

    private static boolean isValidResourceLocation(Object o) {
        if (o == null) return false;
        String s = o.toString();
        if (!s.contains(":")) return ResourceLocation.isValidPath(s);
        String[] split = s.split(":");
        return split.length == 2 && ResourceLocation.isValidNamespace(split[0]) && ResourceLocation.isValidPath(split[1]);
    }
}
