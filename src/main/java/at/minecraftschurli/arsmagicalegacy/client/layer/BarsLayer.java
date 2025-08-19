package at.minecraftschurli.arsmagicalegacy.client.layer;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.client.AMClientConfig;
import at.minecraftschurli.arsmagicalegacy.client.ColorUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class BarsLayer implements LayeredDraw.Layer {
    private static final ResourceLocation BAR_TEXTURE = ArsMagicaApi.modLoc("textures/gui/bar.png");
    private static final int WIDTH = 80;
    private static final int HEIGHT = 10;

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        MagicHelper magicHelper = ArsMagicaApi.getMagicHelper();
        if (!magicHelper.knowsMagic(player)) return;
        ManaHelper manaHelper = ArsMagicaApi.getManaHelper();
        BurnoutHelper burnoutHelper = ArsMagicaApi.getBurnoutHelper();
        int x = AMClientConfig.BARS_X_ANCHOR.get().getLocation(AMClientConfig.BARS_X);
        int y = AMClientConfig.BARS_Y_ANCHOR.get().getLocation(AMClientConfig.BARS_Y);
        boolean renderLevelAtTop = AMClientConfig.RENDER_LEVEL_AT_TOP.getAsBoolean();
        int level = magicHelper.getLevel(player);
        String text = String.valueOf(level);
        Font font = Minecraft.getInstance().font;
        renderOutlineText(guiGraphics, font, text, x + (WIDTH - font.width(text)) / 2, renderLevelAtTop ? y : y + 30, 0x7777FF);
        renderBar(guiGraphics, x, renderLevelAtTop ? y + 10 : y + 20, magicHelper.getXp(player), magicHelper.getXpForNextLevel(level), 0x7777FF);
        renderBar(guiGraphics, x, renderLevelAtTop ? y + 20 : y, manaHelper.getMana(player), manaHelper.getMaxMana(player), 0x99FFFF);
        renderBar(guiGraphics, x, renderLevelAtTop ? y + 30 : y + 10, burnoutHelper.getBurnout(player), burnoutHelper.getMaxBurnout(player), 0x880000);
    }

    private static void renderBar(GuiGraphics guiGraphics, int x, int y, double value, double maxValue, int color) {
        int relWidth = maxValue <= 0 ? 0 : (int) Math.max(Math.ceil(WIDTH * value / maxValue), 0);
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

    public static void renderOutlineText(GuiGraphics guiGraphics, Font font, String text, int x, int y, int color) {
        guiGraphics.drawString(font, text, x + 1, y, 0, false);
        guiGraphics.drawString(font, text, x - 1, y, 0, false);
        guiGraphics.drawString(font, text, x, y + 1, 0, false);
        guiGraphics.drawString(font, text, x, y - 1, 0, false);
        guiGraphics.drawString(font, text, x, y, color, false);
    }
}
