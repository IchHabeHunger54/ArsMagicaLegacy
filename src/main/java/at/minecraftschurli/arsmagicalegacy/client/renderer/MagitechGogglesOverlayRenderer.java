package at.minecraftschurli.arsmagicalegacy.client.renderer;

import at.minecraftschurli.arsmagicalegacy.client.AMRenderTypes;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

@SuppressWarnings("DuplicatedCode")
public final class MagitechGogglesOverlayRenderer {
    private MagitechGogglesOverlayRenderer() {
    }

    public static boolean shouldRender(Player player) {
        return player.getInventory().getArmor(3).is(AMItems.MAGITECH_GOGGLES) || AMUtil.ifModLoaded("curios", () -> CuriosApi.getCuriosInventory(player)
                .map(ICuriosItemHandler::getCurios)
                .map(map -> map.values()
                    .stream()
                    .map(ICurioStacksHandler::getStacks)
                    .anyMatch(items -> {
                        for (int i = 0; i < items.getSlots(); i++) {
                            if (items.getStackInSlot(i).is(AMItems.MAGITECH_GOGGLES)) return true;
                        }
                        return false;
                    }))
                .orElse(false))
            .orElse(false);
    }

    public static void render(Matrix4f matrix, MultiBufferSource bufferSource, AABB aabb, float lineWidth, int color) {
        drawBox(bufferSource.getBuffer(AMRenderTypes.LINES_WITH_WIDTH), matrix, (float) aabb.minX, (float) aabb.minY, (float) aabb.minZ, (float) aabb.maxX, (float) aabb.maxY, (float) aabb.maxZ, lineWidth, (color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff, (color >> 24) & 0xff);
    }

    private static void drawBox(VertexConsumer vc, Matrix4f m, float x1, float y1, float z1, float x2, float y2, float z2, float lineWidth, int r, int g, int b, int a) {
        if (a == 0) return;
        float halfWidth = lineWidth / 2;
        float minX1 = x1 - halfWidth;
        float minY1 = y1 - halfWidth;
        float minZ1 = z1 - halfWidth;
        float minX2 = x2 - halfWidth;
        float minY2 = y2 - halfWidth;
        float minZ2 = z2 - halfWidth;
        float maxX1 = x1 + halfWidth;
        float maxY1 = y1 + halfWidth;
        float maxZ1 = z1 + halfWidth;
        float maxX2 = x2 + halfWidth;
        float maxY2 = y2 + halfWidth;
        float maxZ2 = z2 + halfWidth;
        drawCube(vc, m, minX1, minY1, minZ1, maxX2, maxY1, maxZ1, r, g, b, a);
        drawCube(vc, m, minX1, minY2, minZ1, maxX2, maxY2, maxZ1, r, g, b, a);
        drawCube(vc, m, minX1, minY1, minZ2, maxX2, maxY1, maxZ2, r, g, b, a);
        drawCube(vc, m, minX1, minY2, minZ2, maxX2, maxY2, maxZ2, r, g, b, a);
        drawCube(vc, m, minX1, minY1, minZ1, maxX1, maxY2, maxZ1, r, g, b, a);
        drawCube(vc, m, minX2, minY1, minZ1, maxX2, maxY2, maxZ1, r, g, b, a);
        drawCube(vc, m, minX1, minY1, minZ2, maxX1, maxY2, maxZ2, r, g, b, a);
        drawCube(vc, m, minX2, minY1, minZ2, maxX2, maxY2, maxZ2, r, g, b, a);
        drawCube(vc, m, minX1, minY1, minZ1, maxX1, maxY1, maxZ2, r, g, b, a);
        drawCube(vc, m, minX2, minY1, minZ1, maxX2, maxY1, maxZ2, r, g, b, a);
        drawCube(vc, m, minX1, minY2, minZ1, maxX1, maxY2, maxZ2, r, g, b, a);
        drawCube(vc, m, minX2, minY2, minZ1, maxX2, maxY2, maxZ2, r, g, b, a);
    }

    private static void drawCube(VertexConsumer vc, Matrix4f m, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, int r, int g, int b, int a) {
        // left
        vertex(vc, m, minX, minY, minZ, r, g, b, a);
        vertex(vc, m, minX, minY, maxZ, r, g, b, a);
        vertex(vc, m, minX, maxY, maxZ, r, g, b, a);
        vertex(vc, m, minX, maxY, minZ, r, g, b, a);
        // right
        vertex(vc, m, maxX, maxY, minZ, r, g, b, a);
        vertex(vc, m, maxX, maxY, maxZ, r, g, b, a);
        vertex(vc, m, maxX, minY, maxZ, r, g, b, a);
        vertex(vc, m, maxX, minY, minZ, r, g, b, a);
        // bottom
        vertex(vc, m, minX, minY, minZ, r, g, b, a);
        vertex(vc, m, maxX, minY, minZ, r, g, b, a);
        vertex(vc, m, maxX, minY, maxZ, r, g, b, a);
        vertex(vc, m, minX, minY, maxZ, r, g, b, a);
        // top
        vertex(vc, m, minX, maxY, maxZ, r, g, b, a);
        vertex(vc, m, maxX, maxY, maxZ, r, g, b, a);
        vertex(vc, m, maxX, maxY, minZ, r, g, b, a);
        vertex(vc, m, minX, maxY, minZ, r, g, b, a);
        // back
        vertex(vc, m, minX, minY, maxZ, r, g, b, a);
        vertex(vc, m, maxX, minY, maxZ, r, g, b, a);
        vertex(vc, m, maxX, maxY, maxZ, r, g, b, a);
        vertex(vc, m, minX, maxY, maxZ, r, g, b, a);
        // front
        vertex(vc, m, minX, maxY, minZ, r, g, b, a);
        vertex(vc, m, maxX, maxY, minZ, r, g, b, a);
        vertex(vc, m, maxX, minY, minZ, r, g, b, a);
        vertex(vc, m, minX, minY, minZ, r, g, b, a);
    }

    private static void vertex(VertexConsumer vc, Matrix4f m, float x, float y, float z, int r, int g, int b, int a) {
        vc.addVertex(m, x, y, z).setColor(r, g, b, a);
    }
}
