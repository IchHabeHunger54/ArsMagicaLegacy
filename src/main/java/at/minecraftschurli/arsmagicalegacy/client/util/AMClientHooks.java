package at.minecraftschurli.arsmagicalegacy.client.util;

import at.minecraftschurli.arsmagicalegacy.client.screen.occulus.OcculusScreen;
import net.minecraft.client.Minecraft;

public final class AMClientHooks {
    private AMClientHooks() {
    }

    public static void setOcculusScreen() {
        Minecraft.getInstance().setScreen(new OcculusScreen());
    }
}
