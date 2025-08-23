package at.minecraftschurli.arsmagicalegacy.client.screen.occulus;

import at.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;

import java.util.List;
import java.util.Optional;

public class DefaultTabRenderer extends OcculusTabRenderer {
    private static final int SKILL_SIZE = 32;
    private final List<Skill> skills;
    private double offsetX;
    private double offsetY;
    private Skill hoveredSkill;

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
        mouseX += (int) offsetX;
        mouseY += (int) offsetY;
        hoveredSkill = null;
        PoseStack stack = guiGraphics.pose();
        stack.pushPose();
        stack.translate(-offsetX, -offsetY, 0);
        for (Skill skill : skills) {
            List<Skill> parents = skill.getParents(ClientUtil.registryAccess());
            float skillX = skill.x() + SKILL_SIZE / 2f;
            float skillY = skill.y() + SKILL_SIZE / 2f;
            for (Skill parent : parents) {
                drawLine(guiGraphics, parent.x() + SKILL_SIZE / 2f, parent.y() + SKILL_SIZE / 2f, skillX, skillY, 8, 0xff00ff00, 1);
            }
        }
        for (Skill skill : skills) {
            guiGraphics.blit(skill.x(), skill.y(), 16, SKILL_SIZE, SKILL_SIZE, SkillAtlasHolder.INSTANCE.get().getSprite(skill));
            if (mouseX >= skill.x() && mouseX <= skill.x() + SKILL_SIZE && mouseY >= skill.y() && mouseY <= skill.y() + SKILL_SIZE) {
                hoveredSkill = skill;
            }
        }
        stack.popPose();
    }

    @Override
    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (hoveredSkill == null) return;
        ResourceLocation id = ClientUtil.registryAccess().registryOrThrow(AMRegistryKeys.SKILL).getKey(hoveredSkill);
        guiGraphics.renderTooltip(ClientUtil.font(), List.of(
            Skill.getName(id).withColor(hoveredSkill.cost().map(Holder::value).map(SkillPoint::color).orElse(0xffffff) | 0xff000000),
            Skill.getDescription(id).withStyle(ChatFormatting.DARK_GRAY)
        ), Optional.empty(), mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && mouseX > 0 && mouseX < TAB_SIZE && mouseY > 0 && mouseY < TAB_SIZE) {
            setDragging(true);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        offsetX = Math.clamp(offsetX - dragX, 0, tab.width() - TAB_SIZE);
        offsetY = Math.clamp(offsetY - dragY, 0, tab.height() - TAB_SIZE);
        return true;
    }

    private static void drawLine(GuiGraphics graphics, float startX, float startY, float endX, float endY, int z, int color, int size) {
        PoseStack stack = graphics.pose();
        stack.pushPose();
        stack.translate(startX, startY, z);
        Vec2 vec = new Vec2(endX - startX, endY - startY);
        float length = vec.length();
        stack.mulPose(Axis.ZP.rotation((float) Math.acos(new Vec2(1, 0).dot(vec.y < 0 ? vec.normalized().negated() : vec.normalized()))));
        stack.translate(0, -size / 2f, 0);
        graphics.fill(0, 0, (int) (vec.y < 0 ? -length : length), size, color);
        stack.popPose();
    }
}
