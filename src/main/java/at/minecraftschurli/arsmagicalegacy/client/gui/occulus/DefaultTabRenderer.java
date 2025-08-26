package at.minecraftschurli.arsmagicalegacy.client.gui.occulus;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.helper.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import at.minecraftschurli.arsmagicalegacy.client.util.ColorUtil;
import at.minecraftschurli.arsmagicalegacy.packet.LearnSkillPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Optional;

public class DefaultTabRenderer extends OcculusTabRenderer {
    private static final Component MISSING = Component.translatable(AMTranslations.OCCULUS_MISSING_KEY).withStyle(ChatFormatting.DARK_RED);
    private static final int SKILL_SIZE = 32;
    private final List<Skill> skills;
    private double offsetX;
    private double offsetY;
    private Skill hoveredSkill;

    public DefaultTabRenderer(Holder<OcculusTab> occulusTab) {
        super(occulusTab);
        offsetX = Math.max(0, occulusTab.value().startX());
        offsetY = Math.max(0, occulusTab.value().startY());
        skills = ClientUtil.registryAccess()
            .registryOrThrow(AMRegistryKeys.SKILL)
            .stream()
            .filter(skill -> skill.tab().getKey() == occulusTab.getKey())
            .toList();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        MagicHelper helper = ArsMagicaApi.magicHelper();
        RegistryAccess registryAccess = ClientUtil.registryAccess();
        Registry<Skill> registry = registryAccess.registryOrThrow(AMRegistryKeys.SKILL);
        LocalPlayer player = ClientUtil.player();
        mouseX += (int) offsetX;
        mouseY += (int) offsetY;
        hoveredSkill = null;
        PoseStack stack = guiGraphics.pose();
        stack.pushPose();
        stack.translate(-offsetX, -offsetY, 0);
        for (Skill skill : skills) {
            float endX = skill.x() + SKILL_SIZE / 2f;
            float endY = skill.y() + SKILL_SIZE / 2f;
            boolean knowsSkill = helper.knows(player, registry.wrapAsHolder(skill));
            for (Skill parent : skill.getParents(registryAccess)) {
                float startX = parent.x() + SKILL_SIZE / 2f;
                float startY = parent.y() + SKILL_SIZE / 2f;
                boolean knowsParent = helper.knows(player, registry.wrapAsHolder(parent));
                int startColor = knowsParent && knowsSkill ? 0xffffffff : knowsParent ? getColorForSkill(parent) : 0xff000000;
                int endColor = knowsParent && knowsSkill ? 0xffffffff : knowsParent ? getColorForSkill(skill) : 0xff000000;
                stack.pushPose();
                stack.translate(startX, startY, 8);
                Vec2 vec = new Vec2(endX - startX, endY - startY);
                float angle = (float) Math.acos(new Vec2(0, 1).dot(vec.normalized()));
                stack.mulPose(Axis.ZP.rotation(vec.x > 0 ? -angle : angle));
                stack.translate(-0.5f, 0, 0);
                guiGraphics.fillGradient(0, 0, 1, (int) vec.length(), startColor, endColor);
                stack.popPose();
            }
        }
        float tick = 0.75f + ((player.tickCount % 80) >= 40 ? (player.tickCount % 40) / 80f - 0.25f : 0.25f - (player.tickCount % 40) / 80f);
        for (Skill skill : skills) {
            Holder<Skill> holder = registry.wrapAsHolder(skill);
            boolean knows = helper.knows(player, holder);
            if (!knows && !helper.canLearn(player, holder)) {
                guiGraphics.setColor(0.5f, 0.5f, 0.5f, 1);
            } else if (!knows) {
                int color = getColorForSkill(skill);
                float red = Math.max(ColorUtil.getRed(color), 0.75f) * tick;
                float green = Math.max(ColorUtil.getGreen(color), 0.75f) * tick;
                float blue = Math.max(ColorUtil.getBlue(color), 0.75f) * tick;
                guiGraphics.setColor(red, green, blue, 1);
            }
            RenderSystem.enableBlend();
            guiGraphics.blit(skill.x(), skill.y(), 16, SKILL_SIZE, SKILL_SIZE, SkillAtlasHolder.INSTANCE.get().getSprite(skill));
            RenderSystem.disableBlend();
            guiGraphics.setColor(1, 1, 1, 1);
            if (mouseX >= skill.x() && mouseX <= skill.x() + SKILL_SIZE && mouseY >= skill.y() && mouseY <= skill.y() + SKILL_SIZE) {
                hoveredSkill = skill;
            }
        }
        stack.popPose();
    }

    @Override
    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (hoveredSkill == null) return;
        MagicHelper helper = ArsMagicaApi.magicHelper();
        LocalPlayer player = ClientUtil.player();
        Registry<Skill> registry = ClientUtil.registryAccess().registryOrThrow(AMRegistryKeys.SKILL);
        Holder<Skill> holder = registry.wrapAsHolder(hoveredSkill);
        guiGraphics.renderTooltip(ClientUtil.font(), List.of(
            Skill.getName(holder).withColor(getColorForSkill(hoveredSkill)),
            helper.knows(player, holder) || helper.canLearn(player, holder) ? Skill.getDescription(holder).withStyle(ChatFormatting.DARK_GRAY) : MISSING
        ), Optional.empty(), mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !(mouseX > 0) || !(mouseX < TAB_SIZE) || !(mouseY > 0) || !(mouseY < TAB_SIZE)) return super.mouseClicked(mouseX, mouseY, button);
        if (hoveredSkill != null) {
            Holder<Skill> holder = ClientUtil.registryAccess().registryOrThrow(AMRegistryKeys.SKILL).wrapAsHolder(hoveredSkill);
            LocalPlayer player = ClientUtil.player();
            if (ArsMagicaApi.magicHelper().canLearn(player, holder) || player.isCreative()) {
                PacketDistributor.sendToServer(new LearnSkillPacket(holder));
                return true;
            }
        }
        setDragging(true);
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        offsetX = Math.clamp(offsetX - dragX, 0, occulusTab.value().width() - TAB_SIZE);
        offsetY = Math.clamp(offsetY - dragY, 0, occulusTab.value().height() - TAB_SIZE);
        return true;
    }

    private static int getColorForSkill(Skill skill) {
        return skill.cost().map(Holder::value).map(SkillPoint::color).orElse(0xcccccc) | 0xff000000;
    }
}
