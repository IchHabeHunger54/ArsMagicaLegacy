package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable.ShapeGroupArea;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;

public class SpellCustomizationScreen extends Screen {
    private static final ResourceLocation GRAMMAR = ArsMagicaApi.modLoc("textures/gui/spell_customization/grammar.png");
    private static final ResourceLocation SHAPE_GROUP = ArsMagicaApi.modLoc("textures/gui/spell_customization/shape_group.png");
    private static final int WIDTH = 180;
    private static final int HEIGHT = 80;
    private final Spell spell;
    private int leftPos;
    private int topPos;

    public SpellCustomizationScreen(Spell spell) {
        super(spell.name().orElse(AMItems.SPELL.toStack().getHoverName()));
        this.spell = spell;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void init() {
        leftPos = (width - WIDTH) / 2;
        topPos = (height - HEIGHT) / 2;
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $ -> onClose()).bounds(leftPos - 10, topPos + 60, 200, 20).build());
        for (int i = 0; i < spell.shapeGroups().size(); i++) {
            SpellShapeGroup shapeGroup = spell.shapeGroups().get(i);
            for (int j = 0; j < shapeGroup.parts().size(); j++) {
                addRenderableWidget(new SkillButton(leftPos + i * ShapeGroupArea.WIDTH + j % 2 * SkillButton.SIZE + 2, topPos + j / 2 * SkillButton.SIZE + 1, AMUtil.skill(ArsMagicaApi.spellPartRegistry().wrapAsHolder(shapeGroup.parts().get(j)))));
            }
        }
        for (int i = 0; i < spell.grammar().parts().size(); i++) {
            addRenderableWidget(new SkillButton(leftPos + i * SkillButton.SIZE + 22, topPos + 37, AMUtil.skill(ArsMagicaApi.spellPartRegistry().wrapAsHolder(spell.grammar().parts().get(i)))));
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        for (int i = 0; i < Spell.MAX_SHAPE_GROUPS; i++) {
            guiGraphics.blit(SHAPE_GROUP, leftPos + i * ShapeGroupArea.WIDTH, topPos, 0, 0, ShapeGroupArea.WIDTH, ShapeGroupArea.HEIGHT, ShapeGroupArea.WIDTH, ShapeGroupArea.HEIGHT);
            if (i < spell.shapeGroups().size() && !spell.shapeGroups().get(i).isEmpty()) continue;
            PoseStack stack = guiGraphics.pose();
            stack.pushPose();
            stack.translate(0, 0, 1);
            guiGraphics.fill(leftPos + i * ShapeGroupArea.WIDTH, topPos, leftPos + (i + 1) * ShapeGroupArea.WIDTH, topPos + ShapeGroupArea.HEIGHT, 0x7f000000);
            stack.popPose();
        }
        guiGraphics.blit(GRAMMAR, leftPos + 19, topPos + 34, 0, 0, 142, 22, 142, 22);
    }
}
