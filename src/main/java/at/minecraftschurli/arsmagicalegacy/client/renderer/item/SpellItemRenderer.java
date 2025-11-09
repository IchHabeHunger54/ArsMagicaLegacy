package at.minecraftschurli.arsmagicalegacy.client.renderer.item;

import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class SpellItemRenderer extends BlockEntityWithoutLevelRenderer implements IClientItemExtensions {
    public SpellItemRenderer() {
        super(AMClientUtil.mc().getBlockEntityRenderDispatcher(), AMClientUtil.mc().getEntityModels());
    }

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return this;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (!stack.has(AMDataComponents.SPELL)) return;
        ItemRenderer renderer = AMClientUtil.mc().getItemRenderer();
        // Pops off the transformation applied by ItemRenderer before calling this, thanks to XFactHD from the NeoForge team for the fix!
        poseStack.popPose();
        poseStack.pushPose();
        BakedModel model = renderer.getModel(stack, AMClientUtil.level(), AMClientUtil.player(), 0).applyTransform(displayContext, poseStack, displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND);
        poseStack.translate(-0.5, -0.5, -0.5);
        renderer.renderModelLists(model, stack, packedLight, packedOverlay, poseStack, ItemRenderer.getFoilBufferDirect(buffer, ItemBlockRenderTypes.getRenderType(stack, true), true, stack.hasFoil()));
    }
}
