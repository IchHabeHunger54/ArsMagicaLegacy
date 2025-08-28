package at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.block.inscriptiontable.InscriptionTableData;
import at.minecraftschurli.arsmagicalegacy.block.inscriptiontable.InscriptionTableMenu;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import at.minecraftschurli.arsmagicalegacy.packet.InscriptionTableSyncPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InscriptionTableScreen extends AbstractContainerScreen<InscriptionTableMenu> {
    private static final ResourceLocation BACKGROUND = ArsMagicaApi.modLoc("textures/gui/inscription_table/background.png");
    private static final ResourceLocation SHAPE_GROUP = ArsMagicaApi.modLoc("textures/gui/inscription_table/shape_group.png");
    private static final ResourceLocation SLOT = ArsMagicaApi.modLoc("textures/gui/inscription_table/slot.png");
    private final List<DragArea> dragAreas = new ArrayList<>();
    private Draggable dragged;
    private GrammarArea grammarArea;
    private List<ShapeGroupArea> shapeGroupAreas;
    private EditBox searchBar;
    private EditBox nameBar;

    public InscriptionTableScreen(InscriptionTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 220;
        imageHeight = 252;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        guiGraphics.blit(SLOT, leftPos + (ClientUtil.player().isCreative() ? 47 : 101), topPos + 73, 0, 0, 18, 18, 18, 18);
        for (int i = 0; i < Spell.MAX_SHAPE_GROUPS; i++) {
            guiGraphics.blit(SHAPE_GROUP, leftPos + 20 + i * ShapeGroupArea.WIDTH, topPos + 107, 0, 0, ShapeGroupArea.WIDTH, ShapeGroupArea.HEIGHT, ShapeGroupArea.WIDTH, ShapeGroupArea.HEIGHT);
            if (i < menu.getShapeGroups()) continue;
            PoseStack stack = guiGraphics.pose();
            stack.pushPose();
            stack.translate(0, 0, 1);
            guiGraphics.fill(leftPos + 20 + i * ShapeGroupArea.WIDTH, topPos + 107, leftPos + 20 + (i + 1) * ShapeGroupArea.WIDTH, topPos + 107 + ShapeGroupArea.HEIGHT, 0x7f000000);
            stack.popPose();
        }
    }

    @Override
    protected void init() {
        super.init();
        if (ClientUtil.player().isCreative()) {
            addRenderableWidget(Button.builder(AMTranslations.INSCRIPTION_TABLE_CREATE_SPELL, $ -> {}).bounds(leftPos + 72, topPos + 72, 100, 20).build());
        }
        SpellPartSourceArea sourceArea = new SpellPartSourceArea(leftPos + 42, topPos + 6, 136, 48);
        grammarArea = new GrammarArea(leftPos + 42, topPos + 144, 136, 16);
        shapeGroupAreas = new ArrayList<>();
        for (int i = 0; i < menu.getShapeGroups(); i++) {
            shapeGroupAreas.add(new ShapeGroupArea(leftPos + 20 + i * ShapeGroupArea.WIDTH, topPos + 107));
        }
        dragAreas.add(sourceArea);
        dragAreas.add(grammarArea);
        dragAreas.addAll(shapeGroupAreas);
        searchBar = addRenderableWidget(new EditBox(ClientUtil.font(), leftPos + 40, topPos + 59, 140, 12, searchBar, AMTranslations.INSCRIPTION_TABLE_SEARCH));
        searchBar.setHint(AMTranslations.INSCRIPTION_TABLE_SEARCH);
        searchBar.setResponder(sourceArea::setNameFilter);
        nameBar = addRenderableWidget(new EditBox(ClientUtil.font(), leftPos + 40, topPos + 93, 140, 12, nameBar, AMTranslations.INSCRIPTION_TABLE_NAME));
        nameBar.setHint(AMTranslations.INSCRIPTION_TABLE_NAME);
        InscriptionTableData data = menu.getBlockEntity().getData();
        data.name().ifPresent(name -> nameBar.setValue(name.getString()));
        grammarArea.setFromData(data);
        for (int i = 0; i < data.shapeGroups().size(); i++) {
            shapeGroupAreas.get(i).setFromData(data.shapeGroups().get(i));
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        for (DragArea area : dragAreas) {
            area.render(guiGraphics, mouseX, mouseY, partialTick);
        }
        if (dragged != null) {
            dragged.render(guiGraphics, mouseX - Draggable.SIZE / 2, mouseY - Draggable.SIZE / 2, partialTick);
        } else {
            Draggable part = getHoveredSkill(mouseX, mouseY);
            if (part != null) {
                guiGraphics.renderTooltip(ClientUtil.font(), Skill.getName(part.getSkill()), mouseX, mouseY);
            }
        }
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Nullable
    private DragArea getHoveredArea(int mouseX, int mouseY) {
        return dragAreas.stream().filter(area -> area.isHovered(mouseX, mouseY)).findFirst().orElse(null);
    }

    @Nullable
    private Draggable getHoveredSkill(int mouseX, int mouseY) {
        DragArea area = getHoveredArea(mouseX, mouseY);
        return area == null ? null : area.elementAt(mouseX, mouseY);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (dragged != null) return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        int x = (int) mouseX;
        int y = (int) mouseY;
        DragArea area = getHoveredArea(x, y);
        Draggable skill = getHoveredSkill(x, y);
        if (area == null || skill == null || !area.canPick(skill, x, y)) return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        area.pick(skill, x, y);
        dragged = skill;
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (dragged == null) return super.mouseReleased(mouseX, mouseY, button);
        int x = (int) mouseX;
        int y = (int) mouseY;
        DragArea area = getHoveredArea(x, y);
        if (area != null && area.canDrop(dragged, x, y)) {
            area.drop(dragged, x, y);
            dragged = null;
            return true;
        }
        dragged = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {
        InscriptionTableData data = new InscriptionTableData(
            Optional.of(Component.literal(nameBar.getValue())),
            grammarArea.getVisible().stream().map(Draggable::getSkill).toList(),
            shapeGroupAreas.stream().map(area -> area.getVisible().stream().map(Draggable::getSkill).toList()).toList());
        menu.getBlockEntity().setData(data);
        PacketDistributor.sendToServer(new InscriptionTableSyncPacket(menu.getBlockEntity().getBlockPos(), data));
        super.onClose();
    }
}
