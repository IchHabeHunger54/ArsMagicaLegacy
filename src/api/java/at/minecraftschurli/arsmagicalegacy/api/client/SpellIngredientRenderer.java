package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;

/**
 * Represents the rendering portion of a {@link SpellIngredient}.
 * <p>
 * Register {@link SpellIngredientRenderer}s during {@link RegisterSpellIngredientRenderersEvent}.
 *
 * @param <T> The type of the spell ingredient renderer.
 */
public interface SpellIngredientRenderer<T extends SpellIngredient> {
    /**
     * Renders the passed ingredient instance in a level.
     *
     * @param ingredient    The ingredient to render.
     * @param poseStack     The {@link PoseStack} to use.
     * @param bufferSource  The {@link MultiBufferSource} to use.
     * @param packedLight   The light value to use.
     * @param packedOverlay The overlay value to use.
     */
    void renderInLevel(T ingredient, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay);

    /**
     * Renders the passed ingredient in a gui.
     *
     * @param ingredient  The ingredient to render.
     * @param guiGraphics The {@link GuiGraphics} to use.
     * @param x           The x position to render at.
     * @param y           The y position to render at.
     * @param mouseX      The x position of the mouse.
     * @param mouseY      The y position of the mouse.
     */
    void renderInGui(T ingredient, GuiGraphics guiGraphics, int x, int y, int mouseX, int mouseY);
}
