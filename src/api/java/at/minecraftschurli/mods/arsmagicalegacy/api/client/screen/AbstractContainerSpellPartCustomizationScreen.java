package at.minecraftschurli.mods.arsmagicalegacy.api.client.screen;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * A more specialized version of {@link AbstractSpellPartCustomizationScreen} that mimics a container GUI, minus some functionality such as dropping and quick moving.
 * Items are expected to be copied into slots rather than actually be placed, i.e., this screen is not to be used for storage.
 * Since this is a client-only screen, mutations to the inventory are not possible, as they would not be persistent anyway.
 *
 * @param <T> The type of the modified data component.
 */
public abstract class AbstractContainerSpellPartCustomizationScreen<T> extends AbstractSpellPartCustomizationScreen<T> {
    protected final List<Slot> slots = new ArrayList<>();
    protected int imageWidth = 176;
    protected int imageHeight = 166;
    protected int leftPos;
    protected int topPos;
    protected int inventorySlotCount = 36;
    protected ItemStack carried = ItemStack.EMPTY;
    @Nullable
    protected Slot hoveredSlot;

    public AbstractContainerSpellPartCustomizationScreen(Component title, DataComponentType<T> type, Function<DataComponentType<T>, @Nullable T> valueGetter, BiConsumer<DataComponentType<T>, @Nullable T> valueSetter) {
        super(title, type, valueGetter, valueSetter);
    }

    @Override
    protected void init() {
        super.init();
        leftPos = (width - imageWidth) / 2;
        topPos = (height - imageHeight) / 2;
        addSlots();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(leftPos, topPos);
        hoveredSlot = null;
        for (Slot slot : slots) {
            if (slot.isActive()) {
                renderSlot(guiGraphics, slot);
            }
            if (isHovering(slot, mouseX, mouseY) && slot.isActive()) {
                hoveredSlot = slot;
                if (slot.isHighlightable()) {
                    // TODO 26.1
                    //AbstractContainerScreen.renderSlotHighlight(guiGraphics, slot.x, slot.y, 0, 0x80ffffff);
                }
            }
        }
        renderLabels(guiGraphics, mouseX, mouseY);
        if (!carried.isEmpty()) {
            guiGraphics.item(carried, mouseX - leftPos - 8, mouseY - topPos - 8);
        }
        guiGraphics.pose().popMatrix();
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        extractTransparentBackground(guiGraphics);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (super.keyPressed(event)) return true;
        InputConstants.Key mouseKey = InputConstants.getKey(event);
        if (getMinecraft().options.keyInventory.isActiveAndMatches(mouseKey)) {
            onClose();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (super.mouseClicked(event, doubleClick)) return true;
        if (hoveredSlot != null) {
            if (hoveredSlot.index < slots.size() - inventorySlotCount) {
                if (event.hasShiftDown() || carried.isEmpty()) {
                    clearSlot(hoveredSlot);
                } else {
                    setSlot(hoveredSlot);
                }
            } else if (!hoveredSlot.getItem().isEmpty()) {
                if (event.hasShiftDown()) {
                    quickMoveSlot(hoveredSlot);
                } else {
                    pickSlot(hoveredSlot);
                }
            }
        } else {
            carried = ItemStack.EMPTY;
        }
        return true;
    }

    /**
     * Converts an {@link ItemStack} into a {@code T}.
     *
     * @param stack The {@link ItemStack} to convert.
     * @return The converted {@code T}.
     */
    @Nullable
    protected abstract T getValue(ItemStack stack);

    /**
     * Override this to add {@link Slot}s to the screen.
     *
     * @see #addSlot(Slot)
     */
    protected abstract void addSlots();

    /**
     * Adds a {@link Slot} to the screen.
     *
     * @param slot The {@link Slot} to add.
     */
    protected void addSlot(Slot slot) {
        slot.index = slots.size();
        slots.add(slot);
    }

    /**
     * Convenience method to add the player inventory to the screen.
     *
     * @param x The x position of the inventory.
     * @param y The y position of the inventory.
     */
    @SuppressWarnings({"DataFlowIssue", "SameParameterValue"})
    protected void addInventorySlots(int x, int y) {
        Inventory inventory = Minecraft.getInstance().player.getInventory();
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, x + i * 18, y + 58));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, i * 9 + j + 9, x + j * 18, y + i * 18));
            }
        }
    }

    /**
     * Called from {@link #extractRenderState(GuiGraphicsExtractor, int, int, float)} to render inventory labels.
     *
     * @param guiGraphics The {@link GuiGraphicsExtractor} to use.
     * @param mouseX      The mouse x position.
     * @param mouseY      The mouse y position.
     */
    protected void renderLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
    }

    /**
     * Called from {@link #extractRenderState(GuiGraphicsExtractor, int, int, float)} to render a {@link Slot}.
     *
     * @param guiGraphics The {@link GuiGraphicsExtractor} to use.
     * @param slot        The {@link Slot} to render.
     */
    protected void renderSlot(GuiGraphicsExtractor guiGraphics, Slot slot) {
        int x = slot.x;
        int y = slot.y;
        ItemStack stack = slot.getItem();
        guiGraphics.pose().pushMatrix();
        if (stack.isEmpty() && slot.isActive()) {
            Identifier icon = slot.getNoItemIcon();
            if (icon != null) {
                guiGraphics.blitSprite(RenderPipelines.GUI, icon, x, y, 16, 16);
            }
        } else if (!stack.isEmpty()) {
            int seed = x + y * imageWidth;
            if (slot.isFake()) {
                guiGraphics.fakeItem(stack, x, y, seed);
            } else {
                guiGraphics.item(stack, x, y, seed);
            }
            guiGraphics.itemDecorations(font, stack, x, y);
        }
        guiGraphics.pose().popMatrix();
    }

    /**
     * Called from {@link #extractRenderState(GuiGraphicsExtractor, int, int, float)} to render inventory labels.
     *
     * @param guiGraphics The {@link GuiGraphicsExtractor} to use.
     * @param mouseX      The mouse x position.
     * @param mouseY      The mouse y position.
     */
    protected void renderTooltip(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        if (carried.isEmpty() && hoveredSlot != null && hoveredSlot.hasItem()) {
            guiGraphics.setTooltipForNextFrame(font, hoveredSlot.getItem(), mouseX, mouseY);
        }
    }

    /**
     * Called when a non-player inventory slot is shift-clicked.
     * Expected behavior is to clear the given slot.
     *
     * @param slot The affected {@link Slot}.
     */
    protected void clearSlot(Slot slot) {
        slot.set(ItemStack.EMPTY);
    }

    /**
     * Called when a player inventory slot is shift-clicked.
     * Expected behavior is to copy the slot contents to the non-player inventory.
     *
     * @param slot The affected {@link Slot}.
     */
    protected void quickMoveSlot(Slot slot) {
        ItemStack stack = slot.getItem().copyWithCount(1);
        Slot first = slots.getFirst();
        if (first.mayPlace(stack)) {
            first.set(stack);
        }
    }

    /**
     * Called when a non-player inventory slot is clicked.
     * Expected behavior is to set the carried item into the given slot.
     *
     * @param slot The affected {@link Slot}.
     */
    protected void setSlot(Slot slot) {
        if (slot.mayPlace(carried)) {
            slot.set(carried);
        }
    }

    /**
     * Called when a player inventory slot is clicked.
     * Expected behavior is to copy-pick the item in the given slot.
     *
     * @param slot The affected {@link Slot}.
     */
    protected void pickSlot(Slot slot) {
        carried = slot.getItem().copyWithCount(1);
    }

    private boolean isHovering(Slot slot, double mouseX, double mouseY) {
        int x = slot.x;
        int y = slot.y;
        mouseX -= leftPos;
        mouseY -= topPos;
        return mouseX >= x - 1 && mouseX < x + 16 + 1 && mouseY >= y - 1 && mouseY < y + 16 + 1;
    }
}
