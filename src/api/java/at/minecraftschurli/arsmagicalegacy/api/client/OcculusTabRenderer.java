package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import net.minecraft.client.gui.components.Renderable;

public abstract class OcculusTabRenderer implements Renderable {
    public static final int TAB_SIZE = 196;
    private final OcculusTab tab;

    public OcculusTabRenderer(OcculusTab tab) {
        this.tab = tab;
    }

    @FunctionalInterface
    public interface Factory {
        OcculusTabRenderer create(OcculusTab tab);
    }
}
