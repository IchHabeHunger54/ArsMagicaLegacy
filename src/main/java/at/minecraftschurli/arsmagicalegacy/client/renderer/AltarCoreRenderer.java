package at.minecraftschurli.arsmagicalegacy.client.renderer;

import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.arsmagicalegacy.api.client.SpellIngredientRenderer;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.block.altar.AltarCoreBlock;
import at.minecraftschurli.arsmagicalegacy.block.altar.AltarCoreBlockEntity;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AltarCoreRenderer implements BlockEntityRenderer<AltarCoreBlockEntity> {
    private final BlockEntityRenderDispatcher dispatcher;
    private final Font font;
    private final ItemRenderer itemRenderer;

    public AltarCoreRenderer(BlockEntityRendererProvider.Context context) {
        dispatcher = context.getBlockEntityRenderDispatcher();
        font = context.getFont();
        itemRenderer = context.getItemRenderer();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void render(AltarCoreBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (!blockEntity.getBlockState().getValue(AltarCoreBlock.FORMED)) return;
        Level level = blockEntity.getLevel();
        BlockPos lecternPos = blockEntity.getLecternPos();
        if (lecternPos == null) return;
        BlockState lectern = level.getBlockState(lecternPos);
        if (!lectern.is(Blocks.LECTERN) || !lectern.getValue(LecternBlock.HAS_BOOK)) return;
        BlockPos pos = blockEntity.getBlockPos();
        SpellIngredient ingredient = blockEntity.getCurrentIngredient();
        int light = LevelRenderer.getLightColor(level, lecternPos.above());
        poseStack.pushPose();
        poseStack.translate(lecternPos.getX() - pos.getX() + 0.5, lecternPos.getY() - pos.getY() + 1.5, lecternPos.getZ() - pos.getZ() + 0.5);
        poseStack.pushPose();
        poseStack.translate(0, 0.9, 0);
        poseStack.mulPose(dispatcher.camera.rotation());
        poseStack.scale(0.025f, -0.025f, 0.025f);
        int backgroundColor = (int) (AMClientUtil.mc().options.getBackgroundOpacity(0.25f) * 255) << 24;
        List<Component> components = blockEntity.hasRecipe() ? ingredient.tooltip() : List.of(AMTranslations.ALTAR_CORE_LOW_POWER);
        float offset = (font.lineHeight + 1) * (components.size() - 1.5f);
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            float x = -font.width(component) / 2f;
            float y = (font.lineHeight + 1) * i - offset;
            font.drawInBatch(component, x, y, 0xbbffffff, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.SEE_THROUGH, backgroundColor, light);
            font.drawInBatch(component, x, y, -1, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, light);
        }
        poseStack.popPose();
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(level.getGameTime() % 360 + partialTick));
        if (blockEntity.hasRecipe()) {
            SpellIngredientRenderer<SpellIngredient> renderer = ArsMagicaClientApi.spellIngredientRenderer(ingredient);
            if (renderer != null) {
                renderer.renderInLevel(ingredient, poseStack, bufferSource, light, packedOverlay);
            }
        } else {
            ItemStack stack = new ItemStack(Blocks.BARRIER);
            itemRenderer.render(stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, light, OverlayTexture.NO_OVERLAY, itemRenderer.getModel(stack, level, null, 0));
        }
        poseStack.popPose();
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(AltarCoreBlockEntity blockEntity) {
        return true;
    }

    @Override
    public AABB getRenderBoundingBox(AltarCoreBlockEntity blockEntity) {
        return AABB.INFINITE;
    }
}
