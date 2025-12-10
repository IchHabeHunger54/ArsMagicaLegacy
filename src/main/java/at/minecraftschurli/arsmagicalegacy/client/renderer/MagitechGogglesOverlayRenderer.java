package at.minecraftschurli.arsmagicalegacy.client.renderer;

import at.minecraftschurli.arsmagicalegacy.client.AMRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;

public final class MagitechGogglesOverlayRenderer {
    private MagitechGogglesOverlayRenderer() {
    }

    public static void render(PoseStack stack, MultiBufferSource bufferSource, AABB aabb, float lineWidth, int color) {
        renderBox(bufferSource.getBuffer(AMRenderTypes.LINES_WITH_WIDTH), stack.last().pose(), (float) aabb.minX, (float) aabb.minY, (float) aabb.minZ, (float) aabb.maxX, (float) aabb.maxY, (float) aabb.maxZ, lineWidth, (color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff, (color >> 24) & 0xff);
    }

    /**
     * @see <a href="https://github.com/ldtteam/Structurize/blob/version/1.21/src/main/java/com/ldtteam/structurize/util/WorldRenderMacros.java#L399-L803">Structurize's WorldRenderMacros class</a>
     */
    @SuppressWarnings("DuplicatedCode")
    private static void renderBox(VertexConsumer vc, Matrix4f m, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float lineWidth, int r, int g, int b, int a) {
        if (a == 0) return;
        float halfWidth = lineWidth / 2;
        float minX1 = minX - halfWidth;
        float minY1 = minY - halfWidth;
        float minZ1 = minZ - halfWidth;
        float minX2 = minX + halfWidth;
        float minY2 = minY + halfWidth;
        float minZ2 = minZ + halfWidth;
        float maxX1 = maxX - halfWidth;
        float maxY1 = maxY - halfWidth;
        float maxZ1 = maxZ - halfWidth;
        float maxX2 = maxX + halfWidth;
        float maxY2 = maxY + halfWidth;
        float maxZ2 = maxZ + halfWidth;

        // z plane

        vertex(vc, m, minX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, minZ1, r, g, b, a);
        vertex(vc, m, maxX1, minY1, minZ1, r, g, b, a);

        vertex(vc, m, minX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, minX2, minY2, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, minZ1, r, g, b, a);

        vertex(vc, m, minX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, minZ1, r, g, b, a);
        vertex(vc, m, minX2, minY2, minZ1, r, g, b, a);

        vertex(vc, m, minX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, minX1, maxY1, minZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, minZ1, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, minZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, minZ1, r, g, b, a);
        vertex(vc, m, minX1, maxY1, minZ1, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, minZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, minZ1, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, minZ1, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, minZ1, r, g, b, a);

        //

        vertex(vc, m, minX1, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY2, minZ2, r, g, b, a);

        vertex(vc, m, minX1, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, minX1, minY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, minY2, minZ2, r, g, b, a);

        vertex(vc, m, minX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, minY1, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY1, minZ2, r, g, b, a);

        vertex(vc, m, minX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY1, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY2, minZ2, r, g, b, a);

        vertex(vc, m, maxX1, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY2, minZ2, r, g, b, a);

        vertex(vc, m, maxX1, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX1, minY2, minZ2, r, g, b, a);

        vertex(vc, m, minX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, minZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY1, minZ2, r, g, b, a);

        vertex(vc, m, minX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, minZ2, r, g, b, a);

        //

        vertex(vc, m, minX1, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, minY2, maxZ2, r, g, b, a);

        vertex(vc, m, minX1, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX1, minY2, maxZ2, r, g, b, a);

        vertex(vc, m, minX2, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY1, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, minY1, maxZ2, r, g, b, a);

        vertex(vc, m, minX2, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY1, maxZ2, r, g, b, a);

        vertex(vc, m, maxX1, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, maxZ2, r, g, b, a);

        vertex(vc, m, maxX1, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX1, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ2, r, g, b, a);

        vertex(vc, m, minX2, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY1, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, maxZ2, r, g, b, a);

        vertex(vc, m, minX2, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, maxZ2, r, g, b, a);

        //

        vertex(vc, m, minX1, minY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX1, minY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ1, r, g, b, a);

        vertex(vc, m, minX1, minY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, minY2, maxZ1, r, g, b, a);

        vertex(vc, m, minX1, minY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, minY2, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, maxZ1, r, g, b, a);

        vertex(vc, m, minX1, minY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, maxZ1, r, g, b, a);
        vertex(vc, m, minX1, maxY1, maxZ1, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, maxZ1, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, maxZ1, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ1, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ1, r, g, b, a);
        vertex(vc, m, maxX1, minY1, maxZ1, r, g, b, a);

        // x plane

        vertex(vc, m, minX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, minX1, minY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX1, minY2, maxZ2, r, g, b, a);

        vertex(vc, m, minX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, minX1, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX1, minY2, minZ2, r, g, b, a);

        vertex(vc, m, minX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, minX1, minY2, minZ2, r, g, b, a);
        vertex(vc, m, minX1, maxY2, minZ2, r, g, b, a);

        vertex(vc, m, minX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, minX1, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, minX1, maxY1, minZ1, r, g, b, a);

        vertex(vc, m, minX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX1, maxY1, minZ1, r, g, b, a);
        vertex(vc, m, minX1, maxY2, minZ2, r, g, b, a);

        vertex(vc, m, minX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX1, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, minX1, maxY2, maxZ2, r, g, b, a);

        vertex(vc, m, minX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX1, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX1, minY2, maxZ2, r, g, b, a);

        vertex(vc, m, minX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX1, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX1, minY1, maxZ1, r, g, b, a);

        //

        vertex(vc, m, minX2, maxY2, minZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, minY2, minZ2, r, g, b, a);

        vertex(vc, m, minX2, maxY2, minZ1, r, g, b, a);
        vertex(vc, m, minX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, minY2, minZ1, r, g, b, a);

        vertex(vc, m, minX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, minY1, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, minY1, minZ2, r, g, b, a);

        vertex(vc, m, minX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, minY1, maxZ2, r, g, b, a);

        vertex(vc, m, minX2, maxY2, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY2, maxZ2, r, g, b, a);

        vertex(vc, m, minX2, maxY2, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, minY2, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, minY2, maxZ2, r, g, b, a);

        vertex(vc, m, minX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY1, minZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY1, maxZ2, r, g, b, a);

        vertex(vc, m, minX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY1, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY2, maxZ2, r, g, b, a);

        //

        vertex(vc, m, maxX2, maxY2, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, minZ2, r, g, b, a);

        vertex(vc, m, maxX2, maxY2, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, minZ2, r, g, b, a);

        vertex(vc, m, maxX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY1, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY1, maxZ2, r, g, b, a);

        vertex(vc, m, maxX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY1, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ2, r, g, b, a);

        vertex(vc, m, maxX2, maxY2, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ2, r, g, b, a);

        vertex(vc, m, maxX2, maxY2, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ1, r, g, b, a);

        vertex(vc, m, maxX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, minZ2, r, g, b, a);

        vertex(vc, m, maxX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, maxZ2, r, g, b, a);

        //

        vertex(vc, m, maxX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX1, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX1, minY1, maxZ1, r, g, b, a);

        vertex(vc, m, maxX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX1, minY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX1, minY2, maxZ2, r, g, b, a);

        vertex(vc, m, maxX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX1, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX1, minY2, minZ2, r, g, b, a);

        vertex(vc, m, maxX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX1, maxY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX1, maxY2, minZ2, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX1, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX1, maxY1, minZ1, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX1, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX1, maxY2, minZ2, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX1, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX1, maxY2, maxZ2, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX1, minY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX1, minY2, maxZ2, r, g, b, a);

        // y plane

        vertex(vc, m, minX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, minX2, minY1, maxZ2, r, g, b, a);
        vertex(vc, m, minX1, minY1, maxZ1, r, g, b, a);

        vertex(vc, m, minX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, minX2, minY1, minZ2, r, g, b, a);
        vertex(vc, m, minX2, minY1, maxZ2, r, g, b, a);

        vertex(vc, m, minX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY1, minZ2, r, g, b, a);
        vertex(vc, m, minX2, minY1, minZ2, r, g, b, a);

        vertex(vc, m, minX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX1, minY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY1, minZ2, r, g, b, a);

        vertex(vc, m, maxX1, minY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY1, minZ2, r, g, b, a);
        vertex(vc, m, maxX1, minY1, minZ1, r, g, b, a);

        vertex(vc, m, maxX1, minY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY1, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY1, minZ2, r, g, b, a);

        vertex(vc, m, maxX1, minY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, minY1, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY1, maxZ2, r, g, b, a);

        vertex(vc, m, maxX1, minY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX1, minY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, minY1, maxZ2, r, g, b, a);

        //

        vertex(vc, m, maxX2, minY2, minZ1, r, g, b, a);
        vertex(vc, m, minX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY2, minZ2, r, g, b, a);

        vertex(vc, m, maxX2, minY2, minZ1, r, g, b, a);
        vertex(vc, m, minX2, minY2, minZ1, r, g, b, a);
        vertex(vc, m, minX2, minY2, minZ2, r, g, b, a);

        vertex(vc, m, minX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, minX1, minY2, minZ2, r, g, b, a);
        vertex(vc, m, minX1, minY2, maxZ2, r, g, b, a);

        vertex(vc, m, minX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, minX1, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, minY2, maxZ2, r, g, b, a);

        vertex(vc, m, maxX2, minY2, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, minY2, maxZ2, r, g, b, a);

        vertex(vc, m, maxX2, minY2, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, minY2, maxZ1, r, g, b, a);

        vertex(vc, m, maxX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX1, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX1, minY2, minZ2, r, g, b, a);

        vertex(vc, m, maxX2, minY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, minY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX1, minY2, maxZ2, r, g, b, a);

        //

        vertex(vc, m, maxX2, maxY2, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY2, minZ2, r, g, b, a);

        vertex(vc, m, maxX2, maxY2, minZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY2, minZ1, r, g, b, a);

        vertex(vc, m, minX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, minX1, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX1, maxY2, minZ2, r, g, b, a);

        vertex(vc, m, minX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, minX1, maxY2, maxZ2, r, g, b, a);

        vertex(vc, m, maxX2, maxY2, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, maxZ2, r, g, b, a);

        vertex(vc, m, maxX2, maxY2, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY2, maxZ2, r, g, b, a);

        vertex(vc, m, maxX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX1, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX1, maxY2, maxZ2, r, g, b, a);

        vertex(vc, m, maxX2, maxY2, minZ2, r, g, b, a);
        vertex(vc, m, maxX1, maxY2, maxZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY2, maxZ2, r, g, b, a);

        //

        vertex(vc, m, minX1, maxY1, minZ1, r, g, b, a);
        vertex(vc, m, minX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY1, maxZ2, r, g, b, a);

        vertex(vc, m, minX1, maxY1, minZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY1, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY1, minZ2, r, g, b, a);

        vertex(vc, m, minX1, maxY1, minZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY1, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, minZ2, r, g, b, a);

        vertex(vc, m, minX1, maxY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, minZ2, r, g, b, a);
        vertex(vc, m, maxX1, maxY1, minZ1, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX1, maxY1, minZ1, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, minZ2, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, minZ2, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, maxZ2, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, maxX2, maxY1, maxZ2, r, g, b, a);
        vertex(vc, m, minX2, maxY1, maxZ2, r, g, b, a);

        vertex(vc, m, maxX1, maxY1, maxZ1, r, g, b, a);
        vertex(vc, m, minX2, maxY1, maxZ2, r, g, b, a);
        vertex(vc, m, minX1, maxY1, maxZ1, r, g, b, a);
    }

    private static void vertex(VertexConsumer vc, Matrix4f m, float x, float y, float z, int r, int g, int b, int a) {
        vc.addVertex(m, x, y, z).setColor(r, g, b, a);
    }
}
