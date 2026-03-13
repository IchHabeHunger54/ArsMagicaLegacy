package at.minecraftschurli.arsmagicalegacy.compat.jei;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class SkillIngredientRenderer implements IIngredientRenderer<Skill> {
    @Override
    public void render(GuiGraphicsExtractor guiGraphics, Skill skill) {
        guiGraphics.blitSprite(RenderPipelines.GUI, SkillAtlasHolder.getSprite(skill), 0, 0, 16, 16);
    }

    @Override
    public List<Component> getTooltip(Skill skill, TooltipFlag tooltipFlag) {
        return List.of(Skill.getName(AMRegistries.skills(true).wrapAsHolder(skill)));
    }
}
