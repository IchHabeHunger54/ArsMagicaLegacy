package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.core.Holder;

import java.util.List;

/**
 * Represents the rendering portion of an {@link OcculusTab}. One renderer may be associated with multiple {@link OcculusTab}s.
 * For example, in Ars Magica: Legacy, multiple tabs use the default skill tree renderer.
 * <p>
 * Register {@link OcculusTabRenderer}s during {@link RegisterOcculusTabRenderersEvent}, using the {@link OcculusTabRenderer.Factory} interface.
 */
public abstract class OcculusTabRenderer extends AbstractContainerEventHandler implements Renderable {
    public static final int TAB_SIZE = 196;
    protected final Holder<OcculusTab> occulusTab;

    /**
     * Constructs a new {@link OcculusTabRenderer}.
     *
     * @param occulusTab The {@link Holder} of the {@link OcculusTab} being rendered.
     */
    public OcculusTabRenderer(Holder<OcculusTab> occulusTab) {
        this.occulusTab = occulusTab;
    }

    /**
     * Note: Coordinates are normalized to the renderer's top left corner, i.e., rendering at 0/0 uses the top left corner of the occulus frame,
     * not the top left corner of the screen. Additionally, a scissor is enabled around the occulus frame.
     * @see Renderable#render(GuiGraphics, int, int, float) for parameter documentation.
     */
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(OcculusTab.getBackground(occulusTab), 0, 0, 0, 0, TAB_SIZE, TAB_SIZE, TAB_SIZE, TAB_SIZE);
    }

    /**
     * Render tooltips here. This is kept in a separate method to allow drawing outside the scissor space.
     *
     * @param guiGraphics The {@link GuiGraphics} to use.
     * @param mouseX      The mouse X position.
     * @param mouseY      The mouse Y position.
     * @param partialTick The partial tick value.
     */
    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    }

    /**
     * @return Whether to have the occulus screen draw the skill point panel widget when this renderer is active or not.
     */
    public boolean hasSkillPointPanel() {
        return true;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }

    /**
     * Factory interface used in registering the renderer.
     */
    @FunctionalInterface
    public interface Factory {
        /**
         * @param occulusTab The {@link Holder} of the {@link OcculusTab} being rendered.
         */
        OcculusTabRenderer create(Holder<OcculusTab> occulusTab);
    }
}
