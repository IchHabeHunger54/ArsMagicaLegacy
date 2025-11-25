package at.minecraftschurli.arsmagicalegacy.compat.jei;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class SkillIngredientRenderer implements IIngredientRenderer<Skill> {
    @Override
    public void render(GuiGraphics guiGraphics, Skill skill) {
        guiGraphics.blit(0, 0, 0, 16, 16, SkillAtlasHolder.INSTANCE.get().getSprite(skill));
    }

    @Override
    public List<Component> getTooltip(Skill skill, TooltipFlag tooltipFlag) {
        return List.of(Skill.getName(AMRegistries.skills().wrapAsHolder(skill)));
    }
}
