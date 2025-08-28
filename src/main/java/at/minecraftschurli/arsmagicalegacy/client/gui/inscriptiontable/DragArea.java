package at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class DragArea implements Renderable {
    protected final int x;
    protected final int y;
    protected final int width;
    protected final int height;

    public DragArea(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Nullable
    public abstract Draggable elementAt(int mouseX, int mouseY);

    public abstract List<Draggable> getAll();

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public List<Draggable> getVisible() {
        return getAll();
    }

    public boolean canPick(Draggable draggable, int mouseX, int mouseY) {
        return true;
    }

    public boolean canDrop(Draggable draggable, int mouseX, int mouseY) {
        return true;
    }

    public void pick(Draggable draggable, int mouseX, int mouseY) {
    }

    public void drop(Draggable draggable, int mouseX, int mouseY) {
    }

    @SuppressWarnings("DataFlowIssue")
    @Nullable
    protected static Holder<SpellPart> spellPart(Holder<Skill> skill) {
        return ArsMagicaApi.spellPartRegistry().getHolder(skill.getKey().location()).orElse(null);
    }

    @SuppressWarnings("DataFlowIssue")
    @Nullable
    protected static Holder<Skill> skill(Holder<SpellPart> part) {
        return ClientUtil.registryAccess().registryOrThrow(AMRegistryKeys.SKILL).getHolder(part.getKey().location()).orElse(null);
    }
}
