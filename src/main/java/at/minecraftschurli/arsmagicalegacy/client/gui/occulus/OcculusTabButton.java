package at.minecraftschurli.arsmagicalegacy.client.gui.occulus;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

public class OcculusTabButton extends Button {
    public static final int SIZE = 22;
    public static final int ICON_SIZE = 20;
    private static final ResourceLocation TEXTURE = ArsMagicaApi.modLoc("textures/gui/occulus/tab_button.png");
    private final Holder<OcculusTab> tab;

    public OcculusTabButton(Holder<OcculusTab> tab, int x, int y, OnPress onPress) {
        super(x, y, SIZE, SIZE, OcculusTab.getName(tab), onPress, DEFAULT_NARRATION);
        this.tab = tab;
        setTooltip(Tooltip.create(getMessage()));
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(TEXTURE, getX(), getY(), 0, 0, SIZE, SIZE, SIZE, SIZE);
        guiGraphics.blit(OcculusTab.getIcon(tab), getX() + 1, getY() + 1, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
    }
}
