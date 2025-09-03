package at.minecraftschurli.arsmagicalegacy.client.gui.occulus;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Ability;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.item.DataComponentNamedItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

import java.util.List;

public class AffinityTabRenderer extends OcculusTabRenderer {
    private static final int RADIUS = 5;
    private static final int DISTANCE = 55;
    private static final int LABEL_DISTANCE = 75;
    private static final int STACK_DISTANCE = 5;
    private static final float FRACTAL = 0.1f;
    private final RandomSource random = ClientUtil.level().getRandom();

    public AffinityTabRenderer(Holder<OcculusTab> occulusTab) {
        super(occulusTab);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        Registry<Affinity> affinities = ClientUtil.registryAccess().registryOrThrow(AMRegistryKeys.AFFINITY);
        Registry<Ability> abilities = ClientUtil.registryAccess().registryOrThrow(AMRegistryKeys.ABILITY);
        Font font = ClientUtil.font();
        int center = TAB_SIZE / 2 + RADIUS;
        int count = affinities.size() - 1;
        double angleStep = 360. / count;
        List<Holder.Reference<Affinity>> list = affinities.holders()
            .filter(holder -> !holder.is(Affinity.NONE))
            .toList();
        for (int i = 0; i < list.size(); i++) {
            Holder<Affinity> affinity = list.get(i);
            int color = affinity.value().color();
            double depth = ArsMagicaApi.magicHelper().getAffinityDepth(ClientUtil.player(), affinity);
            double angle = Math.toRadians(angleStep * i);
            double cosAngle = Math.cos(angle);
            double sinAngle = Math.sin(angle);
            double angleMinusHalf = angle - angleStep / 2;
            double anglePlusHalf = angle + angleStep / 2;
            float startX1 = (float) (Math.cos(angleMinusHalf) * RADIUS) + center;
            float startY1 = (float) (Math.sin(angleMinusHalf) * RADIUS) + center;
            float startX2 = (float) (Math.cos(anglePlusHalf) * RADIUS) + center;
            float startY2 = (float) (Math.sin(anglePlusHalf) * RADIUS) + center;
            float endX = (float) (cosAngle * (RADIUS + depth * DISTANCE)) + center;
            float endY = (float) (sinAngle * (RADIUS + depth * DISTANCE)) + center;
            if (depth >= 0.01) {
                float displace = (Math.abs(startX1 - startX2) + Math.abs(startY1 - startY2)) / 2;
                renderFractalLine(guiGraphics, startX1, startY1, endX, endY, color, displace, 1 - FRACTAL);
                renderFractalLine(guiGraphics, startX2, startY2, endX, endY, color, displace, 1 - FRACTAL);
                renderFractalLine(guiGraphics, startX1, startY1, endX, endY, color, displace, 1 + FRACTAL);
                renderFractalLine(guiGraphics, startX2, startY2, endX, endY, color, displace, 1 + FRACTAL);
            } else {
                renderLine(guiGraphics, startX1, startY1, endX, endY, color);
                renderLine(guiGraphics, startX2, startY2, endX, endY, color);
            }
            String text = "%.2f".formatted(depth);
            double width = font.width(text) / 2.;
            double height = font.lineHeight / 2.;
            int textX = (int) (cosAngle * LABEL_DISTANCE * 0.9 + center - width - height);
            int textY = (int) (sinAngle * LABEL_DISTANCE * 0.9 + center - height);
            int stackX = (int) (cosAngle * LABEL_DISTANCE * 1.1 + center - width - height + Math.signum(cosAngle) * STACK_DISTANCE) + 1;
            int stackY = (int) (sinAngle * LABEL_DISTANCE * 1.1 + center - height - height + (angle == Math.PI ? 0 : Math.signum(sinAngle) * STACK_DISTANCE));
            guiGraphics.drawString(font, text, textX, textY, color, false);
            ItemStack stack = DataComponentNamedItem.set(AMItems.AFFINITY_ESSENCE.toStack(), AMDataComponents.AFFINITY.get(), affinity);
            guiGraphics.renderItem(stack, stackX, stackY);
            guiGraphics.renderItemDecorations(font, stack, stackX, stackY);
            //TODO ability tooltip
        }
    }

    @Override
    public boolean hasSkillPointPanel() {
        return false;
    }

    private void renderLine(GuiGraphics guiGraphics, float startX, float startY, float endX, float endY, int color) {
        PoseStack pose = guiGraphics.pose();
        Matrix4f matrix = pose.last().pose();
        pose.pushPose();
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        buffer.addVertex(matrix, startX, startY, 0).setColor(color).setNormal(1, 1, 0);
        buffer.addVertex(matrix, endX, endY, 0).setColor(color).setNormal(1, 1, 0);
        BufferUploader.drawWithShader(buffer.buildOrThrow());
        pose.popPose();
    }

    private void renderFractalLine(GuiGraphics guiGraphics, float startX, float startY, float endX, float endY, int color, float displace, float fractal) {
        if (displace < fractal) {
            renderLine(guiGraphics, startX, startY, endX, endY, color);
            return;
        }
        float x = (startX + endX) / 2 + (random.nextFloat() - 0.5f) * displace;
        float y = (startY + endY) / 2 + (random.nextFloat() - 0.5f) * displace;
        renderFractalLine(guiGraphics, startX, startY, x, y, color, displace / 2, fractal);
        renderFractalLine(guiGraphics, endX, endY, x, y, color, displace / 2, fractal);
    }
}
