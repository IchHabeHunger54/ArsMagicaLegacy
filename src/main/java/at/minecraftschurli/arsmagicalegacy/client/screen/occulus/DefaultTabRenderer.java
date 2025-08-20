package at.minecraftschurli.arsmagicalegacy.client.screen.occulus;

import at.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class DefaultTabRenderer extends OcculusTabRenderer {
    public DefaultTabRenderer(OcculusTab tab, ResourceLocation tabId) {
        super(tab, tabId);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        //TODO
    }
}
