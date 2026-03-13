package at.minecraftschurli.arsmagicalegacy.client.gui.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

public class Draggable implements Renderable, NarratableEntry {
    public static final int SIZE = 16;
    private final Holder<Skill> skill;
    private final TextureAtlasSprite sprite;
    private final Component name;

    public Draggable(Holder<Skill> skill) {
        this.skill = skill;
        sprite = SkillAtlasHolder.INSTANCE.get().getSprite(skill.value());
        name = Skill.getName(skill);
    }

    public Holder<Skill> getSkill() {
        return skill;
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(mouseX, mouseY, 10, SIZE, SIZE, sprite);
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, name);
    }
}
