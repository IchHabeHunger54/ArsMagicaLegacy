package at.minecraftschurli.arsmagicalegacy.client;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.client.layer.LayerAnchor;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class AMClientConfig {
    public static final ModConfigSpec.IntValue BARS_X;
    public static final ModConfigSpec.IntValue BARS_Y;
    public static final ModConfigSpec.EnumValue<LayerAnchor.X> BARS_X_ANCHOR;
    public static final ModConfigSpec.EnumValue<LayerAnchor.Y> BARS_Y_ANCHOR;
    public static final ModConfigSpec.BooleanValue RENDER_LEVEL_AT_TOP;
    public static final ModConfigSpec.BooleanValue SHOW_VALUES;
    static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("gui_layers");
        builder.push("bars");
        BARS_X = builder
            .comment("Horizontal position of the mana, burnout and level bars.")
            .translation(AMTranslations.CONFIG_KEY + "bars_x")
            .defineInRange("x", 6, Short.MIN_VALUE, Short.MAX_VALUE);
        BARS_Y = builder
            .comment("Vertical position of the mana, burnout and level bars.")
            .translation(AMTranslations.CONFIG_KEY + "bars_y")
            .defineInRange("y", -44, Short.MIN_VALUE, Short.MAX_VALUE);
        BARS_X_ANCHOR = builder
            .comment("Horizontal anchor of the mana, burnout and level bars.")
            .translation(AMTranslations.CONFIG_KEY + "bars_anchor_x")
            .defineEnum("bars_anchor_x", LayerAnchor.X.LEFT);
        BARS_Y_ANCHOR = builder
            .comment("Vertical anchor of the mana, burnout and level bars.")
            .translation(AMTranslations.CONFIG_KEY + "bars_anchor_y")
            .defineEnum("bars_anchor_y", LayerAnchor.Y.BOTTOM);
        RENDER_LEVEL_AT_TOP = builder
            .comment("If true, renders the bars in order level number -> level bar -> mana bar -> burnout bar.")
            .comment("If false, renders the bars in order mana bar -> burnout bar -> level bar -> level number.")
            .translation(AMTranslations.CONFIG_KEY + "render_level_at_top")
            .define("render_level_at_top", true);
        SHOW_VALUES = builder
            .comment("Whether to show the exact values for mana, burnout and xp.")
            .translation(AMTranslations.CONFIG_KEY + "show_values")
            .define("show_values", false);
        SPEC = builder.build();
    }
}
