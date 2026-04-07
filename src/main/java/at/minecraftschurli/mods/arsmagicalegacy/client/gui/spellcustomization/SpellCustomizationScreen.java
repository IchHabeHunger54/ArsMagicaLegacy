package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.MutableSpellFacade;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.inscriptiontable.ShapeGroupArea;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.packet.SpellCustomizationPacket;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.Objects;

public class SpellCustomizationScreen extends Screen {
    private static final Identifier GRAMMAR = ArsMagicaApi.id("textures/gui/spell_customization/grammar.png");
    private static final Identifier ICONS = ArsMagicaApi.id("textures/gui/spell_customization/icons.png");
    private static final Identifier SHAPE_GROUP = ArsMagicaApi.id("textures/gui/spell_customization/shape_group.png");
    private static final int WIDTH = 180;
    private static final int HEIGHT = 178;
    private final InteractionHand hand;
    private final MutableSpellFacade spell;
    private int leftPos;
    private int topPos;

    public SpellCustomizationScreen(MutableSpellFacade spell, InteractionHand hand) {
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
        editBox.setResponder(s -> {
            if (s.isEmpty()) spell.clearName();
            else spell.setName(Component.literal(s));
            onChange();
        });
        addRenderableWidget(new SpellIconPanel(leftPos + 9, topPos + 25, 160, 69, icon -> {
            spell.setIcon(icon);
            onChange();
        }, spell.icon().orElse(null)));
        RegistryAccess registryAccess = Objects.requireNonNull(minecraft.getConnection()).registryAccess();
        for (int i = 0; i < spell.spell().shapeGroups().size(); i++) {
            SpellShapeGroup shapeGroup = spell.spell().shapeGroups().get(i);
            for (int j = 0; j < shapeGroup.parts().size(); j++) {
                addRenderableWidget(SpellPartButton.create(leftPos + i * ShapeGroupArea.WIDTH + j % 2 * SpellPartButton.SIZE + 2, topPos + 100 + j / 2 * SpellPartButton.SIZE + 1, AMRegistries.SPELL_PARTS.wrapAsHolder(shapeGroup.parts().get(j)), registryAccess, spell, j));
            }
        }
        for (int i = 0; i < spell.spell().grammar().parts().size(); i++) {
            addRenderableWidget(SpellPartButton.create(leftPos + i * SpellPartButton.SIZE + 22, topPos + 138, AMRegistries.SPELL_PARTS.wrapAsHolder(spell.spell().grammar().parts().get(i)), registryAccess, spell, -1));
        }
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, _ -> onClose()).bounds(leftPos, topPos + 158, 180, 20).build());
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        AMClientUtil.blitFull(graphics, ICONS, leftPos + 5, topPos + 21, 168, 77);
        for (int i = 0; i < Spell.MAX_SHAPE_GROUPS; i++) {
            AMClientUtil.blit(graphics, SHAPE_GROUP, leftPos + i * ShapeGroupArea.WIDTH, topPos + 99, ShapeGroupArea.WIDTH, ShapeGroupArea.HEIGHT);
            if (i >= spell.spell().shapeGroups().size() || spell.spell().shapeGroups().get(i).isEmpty()) {
                graphics.fill(leftPos + i * ShapeGroupArea.WIDTH, topPos + 99, leftPos + (i + 1) * ShapeGroupArea.WIDTH, topPos + 99 + ShapeGroupArea.HEIGHT, 0x7f000000);
            }
        }
        AMClientUtil.blit(graphics, GRAMMAR, leftPos + 19, topPos + 135, 142, 22);
    }

    public MutableSpellFacade getSpell() {
        return spell;
    }

    private void onChange() {
        ClientPacketDistributor.sendToServer(new SpellCustomizationPacket(spell, hand));
    }
}
