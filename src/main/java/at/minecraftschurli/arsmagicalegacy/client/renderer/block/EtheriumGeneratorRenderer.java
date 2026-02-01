package at.minecraftschurli.arsmagicalegacy.client.renderer.block;

import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

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
