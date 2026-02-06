package at.minecraftschurli.arsmagicalegacy.client.layer;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.client.AMClientConfig;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class BarsLayer implements LayeredDraw.Layer {
    private static final ResourceLocation TEXTURE = ArsMagicaApi.id("textures/gui/bar.png");
    private static final int WIDTH = 80;
    private static final int HEIGHT = 10;

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = AMClientUtil.player();
        MagicHelper magicHelper = ArsMagicaApi.magicHelper();
        if (!magicHelper.knowsMagic(player)) return;
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
        int level = magicHelper.getLevel(player);
        double xp = magicHelper.getXp(player);
        double xpForNextLevel = magicHelper.getXpForNextLevel(level);
        double mana = manaHelper.getMana(player);
        double maxMana = manaHelper.getMaxMana(player);
        double burnout = burnoutHelper.getBurnout(player);
        double maxBurnout = burnoutHelper.getMaxBurnout(player);
        int x = AMClientConfig.BARS_X_ANCHOR.get().getLocation(AMClientConfig.BARS_X);
        int y = AMClientConfig.BARS_Y_ANCHOR.get().getLocation(AMClientConfig.BARS_Y);
        boolean renderLevelAtTop = AMClientConfig.RENDER_LEVEL_AT_TOP.getAsBoolean();
        String text = String.valueOf(level);
        Font font = AMClientUtil.font();
        renderOutlineText(guiGraphics, font, Component.literal(text), x + (WIDTH - font.width(text)) / 2, renderLevelAtTop ? y : y + 30, 0x7777FF);
        renderBar(guiGraphics, font, x, renderLevelAtTop ? y + 10 : y + 20, xp, xpForNextLevel, AMTranslations.BARS_VALUE_XP_KEY, 0x7777FF);
        renderBar(guiGraphics, font, x, renderLevelAtTop ? y + 20 : y, mana, maxMana, AMTranslations.BARS_VALUE_MANA_KEY, 0x99FFFF);
        renderBar(guiGraphics, font, x, renderLevelAtTop ? y + 30 : y + 10, burnout, maxBurnout, AMTranslations.BARS_VALUE_BURNOUT_KEY, 0x880000);
    }

    private static void renderBar(GuiGraphics guiGraphics, Font font, int x, int y, double value, double maxValue, String translationKey, int color) {
        guiGraphics.pose().pushPose();
        RenderSystem.enableBlend();
        guiGraphics.blit(TEXTURE, x, y, 0, 0, WIDTH + 1, HEIGHT - 1);
        float r = AMClientUtil.getRedF(color);
        float g = AMClientUtil.getGreenF(color);
        float b = AMClientUtil.getBlueF(color);
        guiGraphics.setColor(r, g, b, 1);
        RenderSystem.setShaderFogColor(r, g, b);
        guiGraphics.blit(TEXTURE, x + 2, y + 2, 2, HEIGHT + 1, maxValue <= 0 ? -1 : (int) Math.max(Math.ceil(WIDTH * value / maxValue), 0) - 1, HEIGHT - 3);
        RenderSystem.setShaderFogColor(1, 1, 1, 1);
        guiGraphics.setColor(1, 1, 1, 1);
        RenderSystem.disableBlend();
        guiGraphics.pose().popPose();
        if (AMClientConfig.SHOW_VALUES.get()) {
            Component text = Component.translatable(translationKey, String.format("%.2f", value), String.format("%.2f", maxValue));
            renderOutlineText(guiGraphics, font, text, AMClientConfig.BARS_X_ANCHOR.get() == LayerAnchor.X.RIGHT ? x - 3 - font.width(text) : x + 4 + WIDTH, y + 1, color);
        }
    }

    private static void renderOutlineText(GuiGraphics guiGraphics, Font font, Component text, int x, int y, int color) {
        guiGraphics.drawString(font, text, x + 1, y, 0, false);
        guiGraphics.drawString(font, text, x - 1, y, 0, false);
        guiGraphics.drawString(font, text, x, y + 1, 0, false);
        guiGraphics.drawString(font, text, x, y - 1, 0, false);
        guiGraphics.drawString(font, text, x, y, color, false);
    }
}
