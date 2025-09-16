package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

public class SkillButton extends Button {
    public static final int SIZE = 16;
    private final Holder<Skill> skill;
    private final TextureAtlasSprite sprite;
    private final Component name;

    public SkillButton(int x, int y, Holder<Skill> skill) {
        super(x, y, SIZE, SIZE, Component.empty(), $ -> {}, DEFAULT_NARRATION);
        this.skill = skill;
        sprite = SkillAtlasHolder.INSTANCE.get().getSprite(skill.value());
        name = Skill.getName(skill);
        setTooltip(Tooltip.create(name));
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(getX(), getY(), 10, SIZE, SIZE, sprite);
    }
}
