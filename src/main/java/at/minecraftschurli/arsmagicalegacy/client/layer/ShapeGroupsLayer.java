package at.minecraftschurli.arsmagicalegacy.client.layer;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.arsmagicalegacy.client.AMClientConfig;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable.Draggable;
import at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable.ShapeGroupArea;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.GuiLayer;

import java.util.List;

public class ShapeGroupsLayer implements GuiLayer {
    private static final Identifier TEXTURE = ArsMagicaApi.id("textures/gui/shape_group.png");
    private static final int ROWS = ShapeGroupArea.ROWS;
    private static final int COLUMNS = ShapeGroupArea.COLUMNS;
    private static final int X_PADDING = ShapeGroupArea.X_PADDING;
    private static final int Y_PADDING = ShapeGroupArea.Y_PADDING;
    private static final int WIDTH = ShapeGroupArea.WIDTH;
    private static final int HEIGHT = ShapeGroupArea.HEIGHT;
    private static final int SIZE = Draggable.SIZE;

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (AMClientUtil.mc().options.hideGui) return;
        Player player = AMClientUtil.player();
        if (player == null) return;
        ItemStack item = player.getMainHandItem();
        if (!item.is(AMTags.Items.SHOWS_SPELL_VISUALS) || !item.has(AMDataComponents.SPELL)) {
            item = player.getOffhandItem();
            if (!item.is(AMTags.Items.SHOWS_SPELL_VISUALS) || !item.has(AMDataComponents.SPELL)) return;
        }
        Spell spell = item.get(AMDataComponents.SPELL);
        int x = AMClientConfig.SHAPE_GROUPS_X_ANCHOR.get().getLocation(AMClientConfig.SHAPE_GROUPS_X);
        int y = AMClientConfig.SHAPE_GROUPS_Y_ANCHOR.get().getLocation(AMClientConfig.SHAPE_GROUPS_Y);
        List<SpellShapeGroup> shapeGroups = spell.shapeGroups();
        for (int i = 0; i < shapeGroups.size(); i++) {
            List<SpellPart> shapeGroup = shapeGroups.get(i).parts();
            if (shapeGroup.isEmpty()) continue;
            graphics.blit(TEXTURE, x + i * WIDTH, y, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
            for (int j = 0; j < ROWS; j++) {
                for (int k = 0; k < COLUMNS; k++) {
                    int index = j * COLUMNS + k;
                    if (index >= shapeGroup.size()) continue;
                    TextureAtlasSprite sprite = SkillAtlasHolder.getSprite(AMRegistries.SPELL_PARTS.getKey(shapeGroup.get(index)));
                    graphics.blitSprite(RenderPipelines.GUI, sprite, x + i * WIDTH + k * SIZE + X_PADDING, y + j * SIZE + Y_PADDING, SIZE, SIZE);
                }
            }
            if (i == spell.activeShapeGroup()) continue;
            graphics.fill(i * WIDTH, 0, (i + 1) * WIDTH, HEIGHT, 0x7f000000);
        }
    }
}
