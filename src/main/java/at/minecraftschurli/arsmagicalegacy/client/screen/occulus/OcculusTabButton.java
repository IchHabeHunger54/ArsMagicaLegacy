package at.minecraftschurli.arsmagicalegacy.client.screen.occulus;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.resources.ResourceLocation;

public class OcculusTabButton extends Button {
    public static final int SIZE = 22;
    public static final int ICON_SIZE = 20;
    private static final ResourceLocation TEXTURE = ArsMagicaApi.modLoc("textures/gui/occulus/tab_button.png");
    private final ResourceLocation tab;

    public OcculusTabButton(ResourceLocation tab, int x, int y, OnPress onPress) {
        super(x, y, SIZE, SIZE, OcculusTab.name(tab), onPress, DEFAULT_NARRATION);
        this.tab = tab;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(TEXTURE, getX(), getY(), 0, 0, SIZE, SIZE, SIZE, SIZE);
        guiGraphics.blit(OcculusTab.icon(tab), getX() + 1, getY() + 1, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
    }

    @Override
    public Tooltip getTooltip() {
        return Tooltip.create(getMessage());
    }
}
