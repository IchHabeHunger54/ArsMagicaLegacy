package at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.block.inscriptiontable.InscriptionTableData;
import at.minecraftschurli.arsmagicalegacy.block.inscriptiontable.InscriptionTableMenu;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import at.minecraftschurli.arsmagicalegacy.packet.InscriptionTableCreateSpellPacket;
import at.minecraftschurli.arsmagicalegacy.packet.InscriptionTableSyncPacket;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.Holder;
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
    private final List<ShapeGroupArea> shapeGroupAreas = new ArrayList<>();
    private Draggable dragged;
    private SpellPartSourceArea sourceArea;
    private GrammarArea grammarArea;
    private EditBox searchBar;
    private EditBox nameBar;
    private InscriptionTableData cachedData;

    public InscriptionTableScreen(InscriptionTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 220;
        imageHeight = 252;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        updateCachedData();
        guiGraphics.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        guiGraphics.blit(SLOT, leftPos + 101, topPos + 73, 0, 0, 18, 18, 18, 18);
        for (int i = 0; i < Spell.MAX_SHAPE_GROUPS; i++) {
            guiGraphics.blit(SHAPE_GROUP, leftPos + 20 + i * ShapeGroupArea.WIDTH, topPos + 107, 0, 0, ShapeGroupArea.WIDTH, ShapeGroupArea.HEIGHT, ShapeGroupArea.WIDTH, ShapeGroupArea.HEIGHT);
            if (i < menu.getShapeGroups() && !shapeGroupAreas.get(i).locked) continue;
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
        grammarArea = new GrammarArea(leftPos + 42, topPos + 144, 136, 16, this::onDrop);
        for (int i = 0; i < menu.getShapeGroups(); i++) {
            shapeGroupAreas.add(new ShapeGroupArea(leftPos + 20 + i * ShapeGroupArea.WIDTH, topPos + 107, this::onDrop));
        }
        sourceArea = new SpellPartSourceArea(leftPos + 42, topPos + 6, 136, 48, this);
        dragAreas.add(sourceArea);
        dragAreas.add(grammarArea);
        dragAreas.addAll(shapeGroupAreas);
        searchBar = addRenderableWidget(new EditBox(ClientUtil.font(), leftPos + 40, topPos + 59, 140, 12, searchBar, AMTranslations.INSCRIPTION_TABLE_SEARCH));
        searchBar.setHint(AMTranslations.INSCRIPTION_TABLE_SEARCH);
        searchBar.setResponder(sourceArea::setNameFilter);
        addRenderableWidget(Button.builder(AMTranslations.INSCRIPTION_TABLE_CLEAR, $ -> clear()).bounds(leftPos + 40, topPos + 72, 60, 20).build());
        if (ClientUtil.player().isCreative()) {
            addRenderableWidget(Button.builder(AMTranslations.INSCRIPTION_TABLE_GIVE_SPELL, $ -> giveSpellRecipe()).bounds(leftPos + 120, topPos + 72, 60, 20).build());
        }
        nameBar = addRenderableWidget(new EditBox(ClientUtil.font(), leftPos + 40, topPos + 93, 140, 12, nameBar, AMTranslations.INSCRIPTION_TABLE_NAME));
        nameBar.setHint(AMTranslations.INSCRIPTION_TABLE_NAME);
        nameBar.setResponder($ -> sync());
        updateCachedData();
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
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == InputConstants.KEY_ESCAPE && shouldCloseOnEsc()) {
            onClose();
            return true;
        }
        if (keyCode == InputConstants.KEY_TAB) {
            boolean flag = !hasShiftDown();
            FocusNavigationEvent event = new FocusNavigationEvent.TabNavigation(flag);
            ComponentPath componentPath = nextFocusPath(event);
            if (componentPath != null) {
                changeFocus(componentPath);
            }
            return false;
        }
        if (getFocused() instanceof EditBox editBox) {
            editBox.keyPressed(keyCode, scanCode, modifiers);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void setFocused(@Nullable GuiEventListener listener) {
        if (getFocused() instanceof EditBox editBox) {
            editBox.setFocused(false);
        }
        super.setFocused(listener);
        if (listener instanceof EditBox editBox) {
            editBox.setFocused(true);
        }
    }

    @Override
    public void onClose() {
        sync();
        super.onClose();
    }

    public GrammarArea getGrammarArea() {
        return grammarArea;
    }

    public List<ShapeGroupArea> getShapeGroupAreas() {
        return shapeGroupAreas;
    }

    private void sync() {
        InscriptionTableData data = new InscriptionTableData(
            Optional.of(Component.literal(nameBar.getValue())),
            grammarArea.getVisible().stream().map(Draggable::getSkill).toList(),
            shapeGroupAreas.stream().map(area -> area.getVisible().stream().map(Draggable::getSkill).toList()).toList());
        menu.getBlockEntity().setData(data);
        PacketDistributor.sendToServer(new InscriptionTableSyncPacket(menu.getBlockEntity().getBlockPos(), data));
    }

    private void onDrop() {
        sync();
        setLocksAndFilters();
    }

    private void giveSpellRecipe() {
        sync();
        PacketDistributor.sendToServer(new InscriptionTableCreateSpellPacket(menu.getBlockEntity().getBlockPos()));
    }

    private void clear() {
        grammarArea.getAll().clear();
        shapeGroupAreas.forEach(area -> area.getAll().clear());
        searchBar.setValue("");
        nameBar.setValue("");
        sync();
    }

    @SuppressWarnings("DataFlowIssue")
    private void setLocksAndFilters() {
        sourceArea.setTypeFilter(
            shapeGroupAreas.stream().anyMatch(ShapeGroupArea::isEmpty),
            shapeGroupAreas.stream().anyMatch(e -> !e.isEmpty() && e.isNotFull() && e.getAll().stream().noneMatch(p -> {
                Optional<Holder.Reference<SpellPart>> holder = ArsMagicaApi.spellPartRegistry().getHolder(p.getSkill().getKey().location());
                return holder.isPresent() && holder.get().value().isSecondaryShape();
            })),
            grammarArea.isNotFull(),
            (shapeGroupAreas.stream().anyMatch(e -> !e.isEmpty() && e.isNotFull()) || !grammarArea.isEmpty() && grammarArea.isNotFull())
        );
        shapeGroupAreas.forEach(area -> area.locked = true);
        shapeGroupAreas.getFirst().locked = false;
        for (int i = 1; i < Math.min(shapeGroupAreas.size(), menu.getShapeGroups()); i++) {
            shapeGroupAreas.get(i).locked = shapeGroupAreas.get(i - 1).isEmpty();
        }
    }

    private void updateCachedData() {
        InscriptionTableData data = menu.getBlockEntity().getData();
        if (data == cachedData) return;
        cachedData = data;
        cachedData.name().ifPresent(name -> nameBar.setValue(name.getString()));
        grammarArea.setFromData(cachedData);
        for (int i = 0; i < cachedData.shapeGroups().size(); i++) {
            shapeGroupAreas.get(i).setFromData(cachedData.shapeGroups().get(i));
        }
        onDrop();
    }
}
