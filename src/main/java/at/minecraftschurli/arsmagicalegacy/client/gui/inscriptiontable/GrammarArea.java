package at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.block.inscriptiontable.InscriptionTableData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.Nullable;

public class GrammarArea extends DragTargetArea {
    private static final int X_PADDING = 4;

    public GrammarArea(int x, int y, int width, int height, Runnable onDrop) {
        super(x, y, width, height, 8, onDrop);
    }

    @Override
    @Nullable
    public Draggable elementAt(int mouseX, int mouseY) {
        if (mouseX < x + X_PADDING || mouseX >= x + maxSize * Draggable.SIZE + X_PADDING || mouseY < y || mouseY >= y + Draggable.SIZE) return null;
        int index = (mouseX - x - X_PADDING) / Draggable.SIZE;
        return contents.size() > index ? contents.get(index) : null;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        for (int i = 0; i < contents.size(); i++) {
            contents.get(i).render(guiGraphics, x + i * Draggable.SIZE + X_PADDING, y, partialTick);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public boolean canPick(Draggable draggable, int mouseX, int mouseY) {
        if (contents.size() < 2) return true;
        SpellPart part = spellPart(draggable.getSkill()).value();
        return part.isModifier() || spellPart(contents.getFirst().getSkill()).value() != part || !spellPart(contents.get(1).getSkill()).value().isModifier();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public boolean canDrop(Draggable draggable, int mouseX, int mouseY) {
        if (!super.canDrop(draggable, mouseX, mouseY)) return false;
        Holder<Skill> skill = draggable.getSkill();
        SpellPart part = spellPart(skill).value();
        return part.isComponent() && contents.stream().noneMatch(e -> e.getSkill().getKey() == skill.getKey()) || part.isModifier() && !contents.isEmpty() && spellPart(contents.getFirst().getSkill()).value().isComponent();
    }

    public void setFromData(InscriptionTableData data) {
        data.grammar()
            .stream()
            .map(Draggable::new)
            .forEach(contents::add);
    }
}
