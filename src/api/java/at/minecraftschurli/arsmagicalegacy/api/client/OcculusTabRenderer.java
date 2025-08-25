package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public abstract class OcculusTabRenderer extends AbstractContainerEventHandler implements Renderable {
    public static final int TAB_SIZE = 196;
    protected final OcculusTab tab;
    protected final ResourceLocation tabId;

    public OcculusTabRenderer(OcculusTab tab, ResourceLocation tabId) {
        this.tab = tab;
        this.tabId = tabId;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(OcculusTab.getBackground(tabId), 0, 0, 0, 0, TAB_SIZE, TAB_SIZE, TAB_SIZE, TAB_SIZE);
    }

    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    }

    public boolean hasSkillPointPanel() {
        return true;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }

    @FunctionalInterface
    public interface Factory {
        OcculusTabRenderer create(OcculusTab tab, ResourceLocation tabId);
    }
}
