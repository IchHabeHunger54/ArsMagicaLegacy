package at.minecraftschurli.arsmagicalegacy.client.screen.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.block.inscriptiontable.InscriptionTableMenu;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class InscriptionTableScreen extends AbstractContainerScreen<InscriptionTableMenu> {
    private static final ResourceLocation BACKGROUND = ArsMagicaApi.modLoc("textures/gui/inscription_table/background.png");
    private static final ResourceLocation SHAPE_GROUP = ArsMagicaApi.modLoc("textures/gui/inscription_table/shape_group.png");
    private static final ResourceLocation SLOT = ArsMagicaApi.modLoc("textures/gui/inscription_table/slot.png");
    private EditBox searchBar;
    private EditBox nameBar;

    public InscriptionTableScreen(InscriptionTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 220;
        imageHeight = 252;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        guiGraphics.blit(SLOT, leftPos + (ClientUtil.player().isCreative() ? 47 : 101), topPos + 73, 0, 0, 18, 18, 18, 18);
    }

    @Override
    protected void init() {
        super.init();
        if (ClientUtil.player().isCreative()) {
            addRenderableWidget(Button.builder(AMTranslations.INSCRIPTION_TABLE_CREATE_SPELL, $ -> {}).bounds(leftPos + 72, topPos + 72, 100, 20).build());
        }
        searchBar = addRenderableWidget(new EditBox(ClientUtil.font(), leftPos + 40, topPos + 59, 140, 12, searchBar, AMTranslations.INSCRIPTION_TABLE_SEARCH));
        searchBar.setHint(AMTranslations.INSCRIPTION_TABLE_SEARCH);
        nameBar = addRenderableWidget(new EditBox(ClientUtil.font(), leftPos + 40, topPos + 93, 140, 12, nameBar, AMTranslations.INSCRIPTION_TABLE_NAME));
        nameBar.setHint(AMTranslations.INSCRIPTION_TABLE_NAME);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }
}
