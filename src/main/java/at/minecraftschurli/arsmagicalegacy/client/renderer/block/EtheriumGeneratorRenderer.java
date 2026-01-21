package at.minecraftschurli.arsmagicalegacy.client.renderer.block;

import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMCapabilities;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumHandler;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

public class EtheriumGeneratorRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    @SuppressWarnings("unused")
    public EtheriumGeneratorRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (ArsMagicaClientApi.shouldRenderGogglesOutline()) {
            ArsMagicaClientApi.renderGogglesOutline(blockEntity, poseStack, bufferSource);
        }
    }

    @Override
    public boolean shouldRenderOffScreen(T blockEntity) {
        return true;
    }
}
