package at.minecraftschurli.arsmagicalegacy.client.screen.occulus;

import at.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public class DefaultTabRenderer extends OcculusTabRenderer {
    private static final int SKILL_SIZE = 32;
    private final List<Skill> skills;
    private float offsetX;
    private float offsetY;
    private Skill tooltipSkill;

    public DefaultTabRenderer(OcculusTab tab, ResourceLocation tabId) {
        super(tab, tabId);
        offsetX = Math.max(0, tab.startX());
        offsetY = Math.max(0, tab.startY());
        skills = ClientUtil.registryAccess()
            .registryOrThrow(AMRegistryKeys.SKILL)
            .stream()
            .filter(skill -> skill.tab().is(tabId))
            .toList();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        mouseX += offsetX;
        mouseY += offsetY;
        tooltipSkill = null;
        PoseStack stack = guiGraphics.pose();
        stack.pushPose();
        stack.translate(-offsetX, -offsetY, 0);
        for (Skill skill : skills) {
            guiGraphics.blit(skill.x(), skill.y(), 16, SKILL_SIZE, SKILL_SIZE, SkillAtlasHolder.INSTANCE.get().getSprite(skill));
            if (mouseX >= skill.x() && mouseX <= skill.x() + SKILL_SIZE && mouseY >= skill.y() && mouseY <= skill.y() + SKILL_SIZE) {
                tooltipSkill = skill;
            }
        }
        stack.popPose();
    }

    @Override
    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (tooltipSkill == null) return;
        ResourceLocation id = ClientUtil.registryAccess().registryOrThrow(AMRegistryKeys.SKILL).getKey(tooltipSkill);
        guiGraphics.renderTooltip(ClientUtil.font(), List.of(
            Skill.getName(id).withColor(tooltipSkill.cost().map(Holder::value).map(SkillPoint::color).orElse(0xffffff) | 0xff000000),
            Skill.getDescription(id).withStyle(ChatFormatting.DARK_GRAY)
        ), Optional.empty(), mouseX, mouseY);
    }
}
