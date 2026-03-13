package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.arsmagicalegacy.client.atlas.SpellIconAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import org.jetbrains.annotations.Nullable;

import java.util.List;

class SpellIconPanel extends ScrollPanel {
    private static final int ICON_SIZE = 16;
    private static final int SELECTED_COLOR = 0xffffff00;
    private static final int HOVERED_COLOR = 0xffffffff;
    private final SpellCustomizationScreen screen;
    private final List<Identifier> icons;
    private final int iconsPerRow;
    private Identifier selected;

    public SpellIconPanel(int x, int y, int width, int height, SpellCustomizationScreen screen, @Nullable Identifier selected) {
        super(AMClientUtil.mc(), width, height, y, x, 0);
        this.screen = screen;
        this.selected = selected;
        icons = SpellIconAtlasHolder.INSTANCE
            .get()
            .getIcons()
            .stream()
            .filter(icon -> icon != null && !icon.equals(MissingTextureAtlasSprite.getLocation()))
            .sorted()
            .toList();
        iconsPerRow = (width - 1) / (ICON_SIZE + 1);
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
    }

    @Override
    protected int getContentHeight() {
        return Math.ceilDiv(icons.size(), iconsPerRow) * (ICON_SIZE + 1) + 1;
    }

    @Override
    protected void drawBackground(GuiGraphicsExtractor guiGraphics, Tesselator tess, float partialTick) {
    }

    @Override
    protected void drawPanel(GuiGraphicsExtractor guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
        int i = 0;
        Identifier hovered = getHovered(mouseX - left, mouseY - top + scrollDistance);
        for (Identifier icon : icons) {
            int x = i % iconsPerRow * (ICON_SIZE + 1) + left + 1;
            int y = i / iconsPerRow * (ICON_SIZE + 1) + relativeY + 1;
            if (y + ICON_SIZE > 0 && y < bottom) {
                if (icon.equals(selected)) {
                    guiGraphics.fill(x - 1, y - 1, x + ICON_SIZE + 1, y + ICON_SIZE + 1, SELECTED_COLOR);
                } else if (icon.equals(hovered)) {
                    guiGraphics.fill(x - 1, y - 1, x + ICON_SIZE + 1, y + ICON_SIZE + 1, HOVERED_COLOR);
                }
                guiGraphics.blit(x, y, 0, ICON_SIZE, ICON_SIZE, SpellIconAtlasHolder.INSTANCE.get().getSprite(icon));
            }
            i++;
        }
    }

    @Override
    protected boolean clickPanel(double mouseX, double mouseY, int button) {
        Identifier hovered = getHovered(mouseX, mouseY);
        if (hovered == null) return super.clickPanel(mouseX, mouseY, button);
        selected = hovered;
        screen.setSpell(screen.getSpell().setIcon(selected));
        return true;
    }

    @Override
    protected int getScrollAmount() {
        return ICON_SIZE + 1;
    }

    @Nullable
    private Identifier getHovered(double mouseX, double mouseY) {
        if (mouseX < 0 || mouseX >= width || mouseY < scrollDistance || mouseY >= scrollDistance + height) return null;
        int x = Math.floorDiv((int) mouseX, ICON_SIZE + 1);
        int y = Math.floorDiv((int) mouseY, ICON_SIZE + 1);
        if (x < 0 || y < 0 || x >= iconsPerRow) return null;
        int i = x + y * iconsPerRow;
        return i >= 0 && i < icons.size() ? icons.get(i) : null;
    }
}
