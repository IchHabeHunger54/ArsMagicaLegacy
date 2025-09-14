package at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class SpellPartSourceArea extends DragArea {
    private static final int X_PADDING = 4;
    private static final int ROWS = 3;
    private static final int COLUMNS = 8;
    private final InscriptionTableScreen screen;
    private final List<Pair<Draggable, Pair<Integer, Integer>>> cache = new ArrayList<>();
    private String nameFilter;
    private boolean primaryShapes = true;
    private boolean secondaryShapes = true;
    private boolean components = true;
    private boolean modifiers = true;

    public SpellPartSourceArea(int x, int y, int width, int height, InscriptionTableScreen screen) {
        super(x, y, width, height);
        this.screen = screen;
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
        return AMClientUtil.registryAccess()
            .registryOrThrow(AMRegistryKeys.SKILL)
            .holders()
            .filter(e -> ArsMagicaApi.magicHelper().knows(AMClientUtil.player(), e))
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
            .filter(this::isSkillVisible)
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

    private boolean isSkillVisible(Holder<Skill> skill) {
        Holder<SpellPart> holder = spellPart(skill);
        if (holder == null) return false;
        SpellPart spellPart = holder.value();
        if (spellPart.isPrimaryShape() && primaryShapes) return true;
        if (spellPart.isSecondaryShape() && secondaryShapes) return true;
        if (spellPart.isComponent() && components && screen.getGrammarArea().contents
            .stream()
            .map(Draggable::getSkill)
            .map(DragArea::spellPart)
            .filter(Objects::nonNull)
            .map(Holder::value)
            .noneMatch(part -> part == spellPart)) return true;
        if (spellPart.isModifier()) {
            if (!modifiers || !primaryShapes && !secondaryShapes && !components) return false;
            Set<SpellStat> stats = spellPart.getStats();
            return Stream.concat(screen.getGrammarArea().contents.stream(), screen.getShapeGroupAreas().stream().map(area -> area.contents).flatMap(List::stream))
                .map(Draggable::getSkill)
                .map(DragArea::spellPart)
                .filter(Objects::nonNull)
                .map(Holder::value)
                .filter(part -> !part.isModifier())
                .map(SpellPart::getStats)
                .flatMap(Set::stream)
                .anyMatch(stats::contains);
        }
        return false;
    }
}
