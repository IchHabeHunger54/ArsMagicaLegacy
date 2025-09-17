package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable.ShapeGroupArea;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.item.DataComponentNamedItem;
import at.minecraftschurli.arsmagicalegacy.packet.SpellCustomizationPacket;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

public class SpellCustomizationScreen extends Screen {
    private static final ResourceLocation GRAMMAR = ArsMagicaApi.modLoc("textures/gui/spell_customization/grammar.png");
    private static final ResourceLocation SHAPE_GROUP = ArsMagicaApi.modLoc("textures/gui/spell_customization/shape_group.png");
    private static final int WIDTH = 180;
    private static final int HEIGHT = 80;
    private Spell spell;
    private int leftPos;
    private int topPos;

    public SpellCustomizationScreen(Spell spell) {
        super(spell.name().orElse(AMItems.SPELL.toStack().getHoverName()));
        this.spell = spell;
    }

    @Override
    protected void init() {
        leftPos = (width - WIDTH) / 2;
        topPos = (height - HEIGHT) / 2;
        for (int i = 0; i < spell.shapeGroups().size(); i++) {
            SpellShapeGroup shapeGroup = spell.shapeGroups().get(i);
            for (int j = 0; j < shapeGroup.parts().size(); j++) {
                addRenderableWidget(SpellPartButton.create(leftPos + i * ShapeGroupArea.WIDTH + j % 2 * SpellPartButton.SIZE + 2, topPos + j / 2 * SpellPartButton.SIZE + 1, ArsMagicaApi.spellPartRegistry().wrapAsHolder(shapeGroup.parts().get(j)), spell, this::setSpell, j));
            }
        }
        for (int i = 0; i < spell.grammar().parts().size(); i++) {
            addRenderableWidget(SpellPartButton.create(leftPos + i * SpellPartButton.SIZE + 22, topPos + 37, ArsMagicaApi.spellPartRegistry().wrapAsHolder(spell.grammar().parts().get(i)), spell, this::setSpell, -1));
        }
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $ -> onClose()).bounds(leftPos - 10, topPos + 60, 200, 20).build());
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

    private void setSpell(Spell spell) {
        this.spell = spell;
    }

    @Override
    public void onClose() {
        super.onClose();
        ItemStack stack = AMClientUtil.player().getMainHandItem();
        if (stack.has(AMDataComponents.SPELL)) {
            stack.set(AMDataComponents.SPELL, spell);
        }
        PacketDistributor.sendToServer(new SpellCustomizationPacket(spell));
    }
}
