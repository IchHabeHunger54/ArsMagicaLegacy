package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.resources.ResourceLocation;

public abstract class OcculusTabRenderer implements Renderable {
    public static final int TAB_SIZE = 196;
    protected final OcculusTab tab;
    protected final ResourceLocation tabId;

    public OcculusTabRenderer(OcculusTab tab, ResourceLocation tabId) {
        this.tab = tab;
        this.tabId = tabId;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(OcculusTab.background(tabId), 0, 0, 0, 0, TAB_SIZE, TAB_SIZE, TAB_SIZE, TAB_SIZE);
    }

    @FunctionalInterface
    public interface Factory {
        OcculusTabRenderer create(OcculusTab tab, ResourceLocation tabId);
    }
}
