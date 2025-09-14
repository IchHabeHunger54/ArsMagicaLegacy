package at.minecraftschurli.arsmagicalegacy.client.renderer;

import at.minecraftschurli.arsmagicalegacy.api.client.SpellIngredientRenderer;
import at.minecraftschurli.arsmagicalegacy.spell.ItemSpellIngredient;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ItemSpellIngredientRenderer implements SpellIngredientRenderer<ItemSpellIngredient> {
    public static final ItemSpellIngredientRenderer INSTANCE = new ItemSpellIngredientRenderer();

    private ItemSpellIngredientRenderer() {
    }

    @Override
    public void renderInLevel(ItemSpellIngredient ingredient, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack stack = AMUtil.getByTick(ingredient.item().getItems(), AMClientUtil.player().tickCount / 20);
        ItemRenderer itemRenderer = AMClientUtil.mc().getItemRenderer();
        BakedModel model = itemRenderer.getModel(stack, null, null, 0);
        itemRenderer.render(stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, model);
    }

    @Override
    public void renderInGui(ItemSpellIngredient ingredient, GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY) {
        ItemStack stack = AMUtil.getByTick(ingredient.item().getItems(), AMClientUtil.player().tickCount / 20).copyWithCount(ingredient.count());
        guiGraphics.renderItem(stack, x, y);
        guiGraphics.renderItemDecorations(AMClientUtil.font(), stack, x, y);
    }
}
