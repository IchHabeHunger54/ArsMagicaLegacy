package at.minecraftschurli.mods.arsmagicalegacy.util;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

/**
 * Helper interface that automatically implements {@link StringRepresentable#getSerializedName()} using {@link Enum#name()}.
 */
public interface StringRepresentableEnum extends StringRepresentable {
    String name();

    @Override
    default String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
