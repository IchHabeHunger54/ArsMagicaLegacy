package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable.ShapeGroupArea;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.packet.SpellCustomizationPacket;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

public class SpellCustomizationScreen extends Screen {
    private static final Identifier GRAMMAR = ArsMagicaApi.id("textures/gui/spell_customization/grammar.png");
    private static final Identifier ICONS = ArsMagicaApi.id("textures/gui/spell_customization/icons.png");
    private static final Identifier SHAPE_GROUP = ArsMagicaApi.id("textures/gui/spell_customization/shape_group.png");
    private static final int WIDTH = 180;
    private static final int HEIGHT = 178;
    private final InteractionHand hand;
    private Spell spell;
    private int leftPos;
    private int topPos;

    public SpellCustomizationScreen(Spell spell, InteractionHand hand) {
        super(spell.name().orElse(AMItems.SPELL.toStack().getHoverName()));
        this.spell = spell;
        this.hand = hand;
    }

    @Override
    protected void init() {
        leftPos = (width - WIDTH) / 2;
        topPos = (height - HEIGHT) / 2;
        EditBox editBox = addRenderableWidget(new EditBox(AMClientUtil.font(), leftPos, topPos, WIDTH, 20, AMTranslations.SPELL_CUSTOMIZATION));
        spell.name().ifPresent(name -> editBox.setValue(name.getString()));
        editBox.setResponder(s -> setSpell(s.isEmpty() ? spell.clearName() : spell.setName(Component.literal(s))));
        addRenderableWidget(new SpellIconPanel(leftPos + 9, topPos + 25, 160, 69, this, spell.icon().orElse(null)));
        for (int i = 0; i < spell.shapeGroups().size(); i++) {
            SpellShapeGroup shapeGroup = spell.shapeGroups().get(i);
            for (int j = 0; j < shapeGroup.parts().size(); j++) {
                addRenderableWidget(SpellPartButton.create(leftPos + i * ShapeGroupArea.WIDTH + j % 2 * SpellPartButton.SIZE + 2, topPos + 100 + j / 2 * SpellPartButton.SIZE + 1, AMRegistries.SPELL_PARTS.wrapAsHolder(shapeGroup.parts().get(j)), this, j));
            }
        }
        for (int i = 0; i < spell.grammar().parts().size(); i++) {
            addRenderableWidget(SpellPartButton.create(leftPos + i * SpellPartButton.SIZE + 22, topPos + 138, AMRegistries.SPELL_PARTS.wrapAsHolder(spell.grammar().parts().get(i)), this, -1));
        }
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, _ -> onClose()).bounds(leftPos, topPos + 158, 180, 20).build());
    }

    @Override
    public void renderBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(ICONS, leftPos + 5, topPos + 21, 0, 0, 168, 77);
        for (int i = 0; i < Spell.MAX_SHAPE_GROUPS; i++) {
            guiGraphics.blit(SHAPE_GROUP, leftPos + i * ShapeGroupArea.WIDTH, topPos + 99, 0, 0, ShapeGroupArea.WIDTH, ShapeGroupArea.HEIGHT, ShapeGroupArea.WIDTH, ShapeGroupArea.HEIGHT);
            if (i < spell.shapeGroups().size() && !spell.shapeGroups().get(i).isEmpty()) continue;
            PoseStack stack = guiGraphics.pose();
            stack.pushPose();
            stack.translate(0, 0, 1);
            guiGraphics.fill(leftPos + i * ShapeGroupArea.WIDTH, topPos + 99, leftPos + (i + 1) * ShapeGroupArea.WIDTH, topPos + ShapeGroupArea.HEIGHT + 99, 0x7f000000);
            stack.popPose();
        }
        guiGraphics.blit(GRAMMAR, leftPos + 19, topPos + 135, 0, 0, 142, 22, 142, 22);
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
        ItemStack stack = AMClientUtil.player().getItemInHand(hand);
        if (stack.has(AMDataComponents.SPELL)) {
            stack.set(AMDataComponents.SPELL, spell);
        }
        PacketDistributor.sendToServer(new SpellCustomizationPacket(spell, hand));
    }

    @Override
    public void onClose() {
        super.onClose();
    }
}
