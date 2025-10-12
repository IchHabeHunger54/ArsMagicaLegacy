package at.minecraftschurli.arsmagicalegacy.client.renderer;

import at.minecraftschurli.arsmagicalegacy.api.client.SpellIngredientRenderer;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.spell.EtheriumSpellIngredient;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EtheriumSpellIngredientRenderer implements SpellIngredientRenderer<EtheriumSpellIngredient> {
    public static final EtheriumSpellIngredientRenderer INSTANCE = new EtheriumSpellIngredientRenderer();

    private EtheriumSpellIngredientRenderer() {
    }

    @Override
    public void renderInLevel(EtheriumSpellIngredient ingredient, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack stack = AMItems.ETHERIUM_PLACEHOLDER.toStack();
        setEtheriumType(ingredient, stack);
        ItemRenderer itemRenderer = AMClientUtil.mc().getItemRenderer();
        BakedModel model = itemRenderer.getModel(stack, null, null, 0);
        itemRenderer.render(stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, model);
    }

    @Override
    public void renderInGui(EtheriumSpellIngredient ingredient, GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY) {
        ItemStack stack = AMItems.ETHERIUM_PLACEHOLDER.toStack();
        setEtheriumType(ingredient, stack);
        guiGraphics.renderItem(stack, x, y);
        guiGraphics.renderItemDecorations(AMClientUtil.font(), stack, x, y);
    }

    private void setEtheriumType(EtheriumSpellIngredient ingredient, ItemStack stack) {
        stack.set(AMDataComponents.ETHERIUM_TYPE, ingredient.etheriumType().isPresent() ? ingredient.etheriumType().get() : AMUtil.getByTick(AMClientUtil.registryAccess().registryOrThrow(AMRegistryKeys.ETHERIUM_TYPE).holders().toList(), AMClientUtil.player().tickCount / 20));
    }
}
