package at.minecraftschurli.arsmagicalegacy.client.util;

import at.minecraftschurli.arsmagicalegacy.client.gui.occulus.OcculusScreen;

public final class AMClientHooks {
    private AMClientHooks() {
    }

    public static void setOcculusScreen() {
        ClientUtil.mc().setScreen(new OcculusScreen());
    }
}
