package at.minecraftschurli.arsmagicalegacy.client.renderer.block;

import at.minecraftschurli.arsmagicalegacy.blockentity.SpellRuneBlockEntity;
import at.minecraftschurli.arsmagicalegacy.init.AMMobEffects;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class SpellRuneRenderer implements BlockEntityRenderer<SpellRuneBlockEntity> {
    private final BlockRenderDispatcher dispatcher;

    public SpellRuneRenderer(BlockEntityRendererProvider.Context context) {
        dispatcher = context.getBlockRenderDispatcher();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void render(SpellRuneBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Player player = AMClientUtil.player();
        if (!player.isCreative() && !player.hasEffect(AMMobEffects.TRUE_SIGHT)) return;
        BlockState state = blockEntity.getBlockState();
        RenderType renderType = ItemBlockRenderTypes.getRenderType(state, false);
        dispatcher.getModelRenderer().renderModel(poseStack.last(), bufferSource.getBuffer(renderType), state, dispatcher.getBlockModel(state), 1, 1, 1, packedLight, packedOverlay, blockEntity.getModelData(), renderType);
    }
}
