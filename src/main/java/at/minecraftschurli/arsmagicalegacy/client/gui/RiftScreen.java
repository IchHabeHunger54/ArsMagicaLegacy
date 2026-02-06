package at.minecraftschurli.arsmagicalegacy.client.gui;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.menu.RiftMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RiftScreen extends AbstractContainerScreen<RiftMenu> {
    private static final ResourceLocation BACKGROUND = ArsMagicaApi.modLoc("textures/gui/rift/background.png");
    private static final ResourceLocation SLOT = ArsMagicaApi.modLoc("textures/gui/rift/slot.png");
    private final int rows;

    public RiftScreen(RiftMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        rows = Math.ceilDiv(menu.getSlotCount(), 9);
        imageHeight = 114 + rows * 18;
        inventoryLabelY = imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(BACKGROUND, x, y, 0, 0, imageWidth, 17, imageWidth, imageHeight);
        int size = menu.getSlotCount();
        for (int i = 0; i < rows; i++) {
            guiGraphics.blit(BACKGROUND, x, y + 17 + i * 18, 0, 17, imageWidth, 18, imageWidth, imageHeight);
            for (int j = 0; j < 9; j++) {
                if (i * 9 + j < size) {
                    guiGraphics.blit(SLOT, x + 7 + j * 18, y + 17 + i * 18, 0, 0, 18, 18, 18, 18);
                }
            }
        }
        guiGraphics.blit(BACKGROUND, x, y + 17 + rows * 18, 0, 35, imageWidth, imageHeight - 35 - (rows - 1) * 18, imageWidth, imageHeight - (rows - 1) * 18);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
