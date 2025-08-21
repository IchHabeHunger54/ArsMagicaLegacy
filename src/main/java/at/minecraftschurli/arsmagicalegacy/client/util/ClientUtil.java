package at.minecraftschurli.arsmagicalegacy.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.RegistryAccess;

public final class ClientUtil {
    private ClientUtil() {
    }

    public static Minecraft mc() {
        return Minecraft.getInstance();
    }

    public static LocalPlayer player() {
        return mc().player;
    }

    public static ClientLevel level() {
        return mc().level;
    }

    public static Font font() {
        return mc().font;
    }

    public static RegistryAccess registryAccess() {
        return level().registryAccess();
    }
}
