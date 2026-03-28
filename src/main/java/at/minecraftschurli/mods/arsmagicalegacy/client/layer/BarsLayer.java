package at.minecraftschurli.mods.arsmagicalegacy.client.layer;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.mods.arsmagicalegacy.client.AMClientConfig;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.GuiLayer;

public class BarsLayer implements GuiLayer {
    private static final Identifier TEXTURE = ArsMagicaApi.id("textures/gui/bar.png");
    private static final int WIDTH = 80;
    private static final int HEIGHT = 10;

    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
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
        renderOutlineText(graphics, font, Component.literal(text), x + (WIDTH - font.width(text)) / 2, renderLevelAtTop ? y : y + 30, 0x7777FF);
        renderBar(graphics, font, x, renderLevelAtTop ? y + 10 : y + 20, xp, xpForNextLevel, AMTranslations.BARS_VALUE_XP_KEY, 0x7777FF);
        renderBar(graphics, font, x, renderLevelAtTop ? y + 20 : y, mana, maxMana, AMTranslations.BARS_VALUE_MANA_KEY, 0x99FFFF);
        renderBar(graphics, font, x, renderLevelAtTop ? y + 30 : y + 10, burnout, maxBurnout, AMTranslations.BARS_VALUE_BURNOUT_KEY, 0x880000);
    }

    private static void renderBar(GuiGraphicsExtractor graphics, Font font, int x, int y, double value, double maxValue, String translationKey, int color) {
        graphics.blit(RenderPipelines.GUI, TEXTURE, x, y, 0, 0, WIDTH + 1, HEIGHT - 1, 256, 256);
        graphics.blit(RenderPipelines.GUI, TEXTURE, x + 2, y + 2, 2, HEIGHT + 1, maxValue <= 0 ? -1 : (int) Math.max(Math.ceil(WIDTH * value / maxValue), 0) - 1, HEIGHT - 3, 256, 256, color);
        if (AMClientConfig.SHOW_VALUES.get()) {
            Component text = Component.translatable(translationKey, String.format("%.2f", value), String.format("%.2f", maxValue));
            renderOutlineText(graphics, font, text, AMClientConfig.BARS_X_ANCHOR.get() == LayerAnchor.X.RIGHT ? x - 3 - font.width(text) : x + 4 + WIDTH, y + 1, color);
        }
    }

    private static void renderOutlineText(GuiGraphicsExtractor graphics, Font font, Component text, int x, int y, int color) {
        graphics.text(font, text, x + 1, y, 0, false);
        graphics.text(font, text, x - 1, y, 0, false);
        graphics.text(font, text, x, y + 1, 0, false);
        graphics.text(font, text, x, y - 1, 0, false);
        graphics.text(font, text, x, y, color, false);
    }
}
