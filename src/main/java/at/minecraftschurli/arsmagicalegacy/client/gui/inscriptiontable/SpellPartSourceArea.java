package at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SpellPartSourceArea extends DragArea {
    private static final int X_PADDING = 4;
    private static final int ROWS = 3;
    private static final int COLUMNS = 8;
    private String nameFilter;
    private boolean primaryShapes = true;
    private boolean secondaryShapes = true;
    private boolean components = true;
    private boolean modifiers = true;
    private List<Pair<Draggable, Pair<Integer, Integer>>> cache = new ArrayList<>();

    public SpellPartSourceArea(int x, int y, int width, int height) {
        super(x, y, width, height);
        updateCache();
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter.toLowerCase(Locale.ROOT);
        updateCache();
    }

    public void setTypeFilter(boolean primaryShapes, boolean secondaryShapes, boolean components, boolean modifiers) {
        this.primaryShapes = primaryShapes;
        this.secondaryShapes = secondaryShapes;
        this.components = components;
        this.modifiers = modifiers;
        updateCache();
    }

    @Override
    @Nullable
    public Draggable elementAt(int mouseX, int mouseY) {
        return cache.stream()
            .filter(e -> mouseX >= e.getSecond().getFirst() && mouseX < e.getSecond().getFirst() + Draggable.SIZE && mouseY >= e.getSecond().getSecond() && mouseY < e.getSecond().getSecond() + Draggable.SIZE)
            .findAny()
            .map(Pair::getFirst)
            .orElse(null);
    }

    @Override
    public List<Draggable> getAll() {
        return ClientUtil.registryAccess()
            .registryOrThrow(AMRegistryKeys.SKILL)
            .holders()
            .filter(e -> ArsMagicaApi.magicHelper().knows(ClientUtil.player(), e))
            .map(Draggable::new)
            .toList();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        for (Pair<Draggable, Pair<Integer, Integer>> pair : cache) {
            Pair<Integer, Integer> xy = pair.getSecond();
            pair.getFirst().render(guiGraphics, xy.getFirst(), xy.getSecond(), partialTick);
        }
    }

    @Override
    public List<Draggable> getVisible() {
        updateCache();
        return cache.stream().map(Pair::getFirst).toList();
    }

    @Override
    public boolean canDrop(Draggable draggable, int mouseX, int mouseY) {
        return false;
    }

    private void updateCache() {
        cache.clear();
        List<Draggable> list = getAll()
            .stream()
            .map(Draggable::getSkill)
            .filter(e -> nameFilter == null || Skill.getName(e).getString().toLowerCase(Locale.ROOT).contains(nameFilter))
            .map(DragArea::spellPart)
            .filter(Objects::nonNull)
            .filter(e -> e.value().isPrimaryShape() && primaryShapes || e.value().isSecondaryShape() && secondaryShapes || e.value().isComponent() && components || e.value().isModifier() && (primaryShapes || secondaryShapes || components) && modifiers)
            .map(DragArea::skill)
            .filter(Objects::nonNull)
            .limit(ROWS * COLUMNS)
            .map(Draggable::new)
            .toList();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                int index = i * COLUMNS + j;
                if (index >= list.size()) return;
                cache.add(Pair.of(list.get(index), Pair.of(x + j * Draggable.SIZE + X_PADDING, y + i * Draggable.SIZE)));
            }
        }
    }
}
