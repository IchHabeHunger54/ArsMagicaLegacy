package at.minecraftschurli.arsmagicalegacy.client;

import at.minecraftschurli.arsmagicalegacy.client.layer.LayerAnchor;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class AMClientConfig {
    public static final ModConfigSpec.IntValue BARS_X;
    public static final ModConfigSpec.IntValue BARS_Y;
    public static final ModConfigSpec.EnumValue<LayerAnchor.X> BARS_X_ANCHOR;
    public static final ModConfigSpec.EnumValue<LayerAnchor.Y> BARS_Y_ANCHOR;
    static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("gui_layers");
        builder.comment("Positions of the mana, burnout and level bars. The size of the layer is 80x30.").push("bars");
        BARS_X = builder
                .comment("Horizontal position of the mana, burnout and level bars.")
                .translation(AMTranslations.CONFIG + "bars_x")
                .defineInRange("x", 6, Short.MIN_VALUE, Short.MAX_VALUE);
        BARS_Y = builder
                .comment("Vertical position of the mana, burnout and level bars.")
                .translation(AMTranslations.CONFIG + "bars_y")
                .defineInRange("y", -34, Short.MIN_VALUE, Short.MAX_VALUE);
        BARS_X_ANCHOR = builder
                .comment("Horizontal anchor of the mana, burnout and level bars.")
                .translation(AMTranslations.CONFIG + "bars_anchor_x")
                .defineEnum("bars_anchor_x", LayerAnchor.X.LEFT);
        BARS_Y_ANCHOR = builder
                .comment("Vertical anchor of the mana, burnout and level bars.")
                .translation(AMTranslations.CONFIG + "bars_anchor_y")
                .defineEnum("bars_anchor_y", LayerAnchor.Y.BOTTOM);
        SPEC = builder.build();
    }
}
