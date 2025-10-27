package at.minecraftschurli.arsmagicalegacy.client.gui;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.menu.RuneBagMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RuneBagScreen extends AbstractContainerScreen<RuneBagMenu> {
    private static final ResourceLocation BACKGROUND = ArsMagicaApi.modLoc("textures/gui/rune_bag.png");

    public RuneBagScreen(RuneBagMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        inventoryLabelY -= 16;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(BACKGROUND, (width - imageWidth) / 2, (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight);
    }
}
