package at.minecraftschurli.arsmagicalegacy.client.layer;

import at.minecraftschurli.arsmagicalegacy.client.AMClientConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.client.ColorUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class BarsLayer implements LayeredDraw.Layer {
    private static final ResourceLocation BAR_TEXTURE = ArsMagicaApi.modLoc("textures/gui/bar.png");
    private static final int WIDTH = 80;
    private static final int HEIGHT = 10;
    private final Supplier<LayerAnchor.X> xAnchor = AMClientConfig.BARS_X_ANCHOR;
    private final Supplier<LayerAnchor.Y> yAnchor = AMClientConfig.BARS_Y_ANCHOR;
    private final IntSupplier x = AMClientConfig.BARS_X;
    private final IntSupplier y = AMClientConfig.BARS_Y;

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        ManaHelper manaHelper = ArsMagicaApi.getManaHelper();
        int xLocation = xAnchor.get().getLocation(x);
        int yLocation = yAnchor.get().getLocation(y);
        renderBar(guiGraphics, xLocation, yLocation + 10, manaHelper.getMana(player), manaHelper.getMaxMana(player), 0x99FFFF);
    }

    protected void renderBar(GuiGraphics guiGraphics, int x, int y, double value, double maxValue, int color) {
        int relWidth = maxValue == 0 ? 0 : (int) Math.max(Math.ceil(WIDTH * value / maxValue), 0);
        guiGraphics.pose().pushPose();
        RenderSystem.enableBlend();
        guiGraphics.blit(BAR_TEXTURE, x, y, 0, 0, WIDTH + 1, HEIGHT - 1);
        float r = ColorUtil.getRed(color);
        float g = ColorUtil.getGreen(color);
        float b = ColorUtil.getBlue(color);
        guiGraphics.setColor(r, g, b, 1);
        RenderSystem.setShaderFogColor(r, g, b);
        guiGraphics.blit(BAR_TEXTURE, x + 2, y + 2, 2, HEIGHT + 1, relWidth - 1, HEIGHT - 3);
        RenderSystem.setShaderFogColor(1, 1, 1, 1);
        guiGraphics.setColor(1, 1, 1, 1);
        RenderSystem.disableBlend();
        guiGraphics.pose().popPose();
    }
}
