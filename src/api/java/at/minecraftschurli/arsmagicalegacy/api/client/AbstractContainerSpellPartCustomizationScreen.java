package at.minecraftschurli.arsmagicalegacy.api.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * A more specialized version of {@link AbstractSpellPartCustomizationScreen} that mimics a container GUI.
 * The implementation is adapted from {@link AbstractContainerMenu} and {@link AbstractContainerScreen}.
 *
 * @param <T> The type of the modified data component.
 */
public abstract class AbstractContainerSpellPartCustomizationScreen<T> extends AbstractSpellPartCustomizationScreen<T> {
    private static final int OFFHAND = 40;
    public final List<Slot> slots = new ArrayList<>();
    protected final Set<Slot> quickCraftSlots = new HashSet<>();
    private final SlotAccess slotAccess = new SlotAccess() {
        @Override
        public ItemStack get() {
            return carried;
        }

        @Override
        public boolean set(ItemStack stack) {
            setCarried(stack);
            return true;
        }
    };
    protected Component playerInventoryTitle;
    protected int imageWidth = 176;
    protected int imageHeight = 166;
    protected int titleLabelX = 8;
    protected int titleLabelY = 6;
    protected int inventoryLabelX = 8;
    protected int inventoryLabelY = 72;
    protected int leftPos;
    protected int topPos;
    protected Slot hoveredSlot;
    private ItemStack carried = ItemStack.EMPTY;
    private ItemStack lastQuickMoved = ItemStack.EMPTY;
    private ItemStack draggingItem = ItemStack.EMPTY;
    private Slot clickedSlot;
    private Slot lastClickSlot;
    private long lastClickTime;
    private int lastClickButton;
    private boolean doubleclick;
    private boolean skipNextRelease = true;
    private boolean isSplittingStack;
    private ItemStack snapbackItem = ItemStack.EMPTY;
    private Slot snapbackEnd;
    private int snapbackStartX;
    private int snapbackStartY;
    private long snapbackTime;
    private Slot quickdropSlot;
    private long quickdropTime;
    private boolean isQuickCrafting;
    private int quickCraftType;
    private int quickCraftButton;
    private int quickCraftRemainder;
    private int quickCraftStatus;

    public AbstractContainerSpellPartCustomizationScreen(Component title, DataComponentType<T> type, Function<DataComponentType<T>, @Nullable T> valueGetter, BiConsumer<DataComponentType<T>, @Nullable T> valueSetter) {
        super(title, type, valueGetter, valueSetter);
    }

    @Override
    protected void init() {
        leftPos = (width - imageWidth) / 2;
        topPos = (height - imageHeight) / 2;
        playerInventoryTitle = Objects.requireNonNull(getMinecraft().player).getInventory().getDisplayName();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        for (Renderable renderable : renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }
        RenderSystem.disableDepthTest();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(leftPos, topPos, 0);
        hoveredSlot = null;
        for (Slot slot : slots) {
            if (slot.isActive()) {
                renderSlot(guiGraphics, slot);
            }
            if (isHovering(slot, mouseX, mouseY) && slot.isActive()) {
                hoveredSlot = slot;
                if (slot.isHighlightable()) {
                    AbstractContainerScreen.renderSlotHighlight(guiGraphics, slot.x, slot.y, 0, 0x80ffffff);
                }
            }
        }
        renderLabels(guiGraphics, mouseX, mouseY);
        ItemStack carried = draggingItem.isEmpty() ? this.carried : draggingItem;
        if (!carried.isEmpty()) {
            int offset = draggingItem.isEmpty() ? 8 : 16;
            String s = null;
            if (!draggingItem.isEmpty() && isSplittingStack) {
                carried = carried.copyWithCount(Mth.ceil(carried.getCount() / 2f));
            } else if (isQuickCrafting && quickCraftSlots.size() > 1) {
                carried = carried.copyWithCount(quickCraftRemainder);
                if (carried.isEmpty()) {
                    s = ChatFormatting.YELLOW + "0";
                }
            }
            renderFloatingItem(guiGraphics, carried, mouseX - leftPos - 8, mouseY - topPos - offset, s);
        }
        if (!snapbackItem.isEmpty()) {
            float time = (Util.getMillis() - snapbackTime) / 100f;
            if (time >= 1) {
                time = 1;
                snapbackItem = ItemStack.EMPTY;
            }
            renderFloatingItem(guiGraphics, snapbackItem, snapbackStartX + (int) ((snapbackEnd.x - snapbackStartX) * time), snapbackStartY + (int) ((snapbackEnd.y - snapbackStartY) * time), null);
        }
        guiGraphics.pose().popPose();
        RenderSystem.enableDepthTest();
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderTransparentBackground(guiGraphics);
    }

    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        int x = slot.x;
        int y = slot.y;
        ItemStack stack = slot.getItem();
        boolean quickReplace = false;
        boolean isClickedWithItem = slot == clickedSlot && !draggingItem.isEmpty() && !isSplittingStack;
        String countString = null;
        if (slot == clickedSlot && !draggingItem.isEmpty() && isSplittingStack && !stack.isEmpty()) {
            stack = stack.copyWithCount(stack.getCount() / 2);
        } else if (isQuickCrafting && quickCraftSlots.contains(slot) && !carried.isEmpty()) {
            if (quickCraftSlots.size() == 1) return;
            if (AbstractContainerMenu.canItemQuickReplace(slot, carried, true)) {
                quickReplace = true;
                int maxStackSize = Math.min(carried.getMaxStackSize(), slot.getMaxStackSize(carried));
                int count = AbstractContainerMenu.getQuickCraftPlaceCount(quickCraftSlots, quickCraftType, carried) + (slot.getItem().isEmpty() ? 0 : slot.getItem().getCount());
                if (count > maxStackSize) {
                    count = maxStackSize;
                    countString = ChatFormatting.YELLOW.toString() + maxStackSize;
                }
                stack = carried.copyWithCount(count);
            } else {
                quickCraftSlots.remove(slot);
                recalculateQuickCraftRemaining();
            }
        }
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 100);
        if (stack.isEmpty() && slot.isActive()) {
            Pair<ResourceLocation, ResourceLocation> pair = slot.getNoItemIcon();
            if (pair != null) {
                TextureAtlasSprite textureatlassprite = getMinecraft().getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
                guiGraphics.blit(x, y, 0, 16, 16, textureatlassprite);
                isClickedWithItem = true;
            }
        }
        if (!isClickedWithItem) {
            if (quickReplace) {
                guiGraphics.fill(x, y, x + 16, y + 16, 0x80ffffff);
            }
            int seed = slot.x + slot.y * imageWidth;
            if (slot.isFake()) {
                guiGraphics.renderFakeItem(stack, x, y, seed);
            } else {
                guiGraphics.renderItem(stack, x, y, seed);
            }
            guiGraphics.renderItemDecorations(font, stack, x, y, countString);
        }
        guiGraphics.pose().popPose();
    }

    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(font, title, titleLabelX, titleLabelY, 0x404040, false);
        guiGraphics.drawString(font, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 0x404040, false);
    }

    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        if (carried.isEmpty() && hoveredSlot != null && hoveredSlot.hasItem()) {
            ItemStack stack = hoveredSlot.getItem();
            guiGraphics.renderTooltip(font, getTooltipFromItem(getMinecraft(), stack), stack.getTooltipImage(), stack, x, y);
        }
    }

    protected void setCarried(ItemStack stack) {
        carried = stack;
    }

    protected void clearDraggingState() {
        draggingItem = ItemStack.EMPTY;
        clickedSlot = null;
    }

    protected void resetQuickCraft() {
        quickCraftStatus = AbstractContainerMenu.QUICKCRAFT_HEADER_START;
        quickCraftSlots.clear();
    }

    protected Slot addSlot(Slot slot) {
        slot.index = slots.size();
        slots.add(slot);
        return slot;
    }

    protected Slot getSlot(int slotId) {
        return slots.get(slotId);
    }

    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        return mouseX < guiLeft || mouseY < guiTop || mouseX >= guiLeft + imageWidth || mouseY >= guiTop + imageHeight;
    }

    private void renderFloatingItem(GuiGraphics guiGraphics, ItemStack stack, int x, int y, String text) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 232);
        guiGraphics.renderItem(stack, x, y);
        Font font = IClientItemExtensions.of(stack).getFont(stack, IClientItemExtensions.FontContext.ITEM_COUNT);
        guiGraphics.renderItemDecorations(font == null ? this.font : font, stack, x, y - (draggingItem.isEmpty() ? 0 : 8), text);
        guiGraphics.pose().popPose();
    }

    private void recalculateQuickCraftRemaining() {
        if (carried.isEmpty() || !isQuickCrafting) return;
        if (quickCraftType == AbstractContainerMenu.QUICKCRAFT_TYPE_CLONE) {
            quickCraftRemainder = carried.getMaxStackSize();
        } else {
            quickCraftRemainder = carried.getCount();
            for (Slot slot : quickCraftSlots) {
                ItemStack stack = slot.getItem();
                int count = stack.isEmpty() ? 0 : stack.getCount();
                int placeCount = Math.min(AbstractContainerMenu.getQuickCraftPlaceCount(quickCraftSlots, quickCraftType, carried) + count, Math.min(carried.getMaxStackSize(), slot.getMaxStackSize(carried)));
                quickCraftRemainder -= placeCount - count;
            }
        }
    }

    private boolean isHovering(Slot slot, double mouseX, double mouseY) {
        int x = slot.x;
        int y = slot.y;
        mouseX -= leftPos;
        mouseY -= topPos;
        return mouseX >= x - 1 && mouseX < x + 16 + 1 && mouseY >= y - 1 && mouseY < y + 16 + 1;
    }

    @Nullable
    private Slot findSlot(double mouseX, double mouseY) {
        return slots.stream().filter(slot -> isHovering(slot, mouseX, mouseY) && slot.isActive()).findFirst().orElse(null);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) return true;
        InputConstants.Key mouseKey = InputConstants.getKey(keyCode, scanCode);
        if (getMinecraft().options.keyInventory.isActiveAndMatches(mouseKey)) {
            onClose();
            return true;
        }
        if (hoveredSlot == null || !hoveredSlot.hasItem()) {
            if (getMinecraft().options.keyDrop.isActiveAndMatches(mouseKey)) return true;
        } else {
            if (getMinecraft().options.keyPickItem.isActiveAndMatches(mouseKey)) {
                slotClicked(hoveredSlot.index, GLFW.GLFW_MOUSE_BUTTON_1, ClickType.CLONE);
                return true;
            }
            if (getMinecraft().options.keyDrop.isActiveAndMatches(mouseKey)) {
                slotClicked(hoveredSlot.index, hasControlDown() ? GLFW.GLFW_MOUSE_BUTTON_2 : GLFW.GLFW_MOUSE_BUTTON_1, ClickType.THROW);
                return true;
            }
        }
        if (carried.isEmpty() && hoveredSlot != null) {
            if (getMinecraft().options.keySwapOffhand.isActiveAndMatches(InputConstants.getKey(keyCode, scanCode))) {
                slotClicked(hoveredSlot.index, OFFHAND, ClickType.SWAP);
                return true;
            }
            for (int i = 0; i < 9; i++) {
                if (getMinecraft().options.keyHotbarSlots[i].isActiveAndMatches(InputConstants.getKey(keyCode, scanCode))) {
                    slotClicked(hoveredSlot.index, i, ClickType.SWAP);
                    return true;
                }
            }
        }
        return false;
    }

    public ItemStack quickMoveStack(Player player, int index) {
        int slotCount = slots.size() - 36;
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack originalStack = stack.copy();
        if (index < slotCount) { // If slot is a container slot
            // Try moving to the hotbar or inventory
            if (!moveItemStackTo(slot.getItem(), slotCount, slotCount + 36))
                return ItemStack.EMPTY;
        } else if (index < slotCount + 9) { // If slot is a hotbar slot
            // Try moving to the container
            if (!moveItemStackTo(stack, 0, slotCount))
                return ItemStack.EMPTY;
            // Try moving to the inventory
            if (!moveItemStackTo(stack, slotCount + 9, slotCount + 36))
                return ItemStack.EMPTY;
        } else if (index < slotCount + 36) { // If slot is an inventory slot
            // Try moving to the container
            if (!moveItemStackTo(stack, 0, slotCount))
                return ItemStack.EMPTY;
            // Try moving to the hotbar
            if (!moveItemStackTo(stack, slotCount, slotCount + 9))
                return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        return originalStack;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex) {
        boolean moved = false;
        int index = startIndex;
        if (stack.isStackable()) {
            while (!stack.isEmpty() && index < endIndex) {
                Slot slot = slots.get(index);
                ItemStack slotStack = slot.getItem();
                if (!slotStack.isEmpty() && ItemStack.isSameItemSameComponents(stack, slotStack)) {
                    int count = slotStack.getCount() + stack.getCount();
                    int maxStackSize = slot.getMaxStackSize(slotStack);
                    if (count <= maxStackSize) {
                        stack.setCount(0);
                        slotStack.setCount(count);
                        slot.setChanged();
                        moved = true;
                    } else if (slotStack.getCount() < maxStackSize) {
                        stack.shrink(maxStackSize - slotStack.getCount());
                        slotStack.setCount(maxStackSize);
                        slot.setChanged();
                        moved = true;
                    }
                }
                index++;
            }
        }
        if (!stack.isEmpty()) {
            index = startIndex;
            while (index < endIndex) {
                Slot slot = slots.get(index);
                ItemStack slotStack = slot.getItem();
                if (slotStack.isEmpty() && slot.mayPlace(stack)) {
                    int maxStackSize = slot.getMaxStackSize(stack);
                    slot.setByPlayer(stack.split(Math.min(stack.getCount(), maxStackSize)));
                    slot.setChanged();
                    moved = true;
                    break;
                }
                index++;
            }
        }
        return moved;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) return true;
        Slot slot = findSlot(mouseX, mouseY);
        boolean isLeftClick = button == GLFW.GLFW_MOUSE_BUTTON_1;
        boolean isRightClick = button == GLFW.GLFW_MOUSE_BUTTON_2;
        InputConstants.Key mouseKey = InputConstants.Type.MOUSE.getOrCreate(button);
        boolean pick = getMinecraft().options.keyPickItem.isActiveAndMatches(mouseKey);
        long time = Util.getMillis();
        doubleclick = lastClickSlot == slot && time - lastClickTime < 250 && lastClickButton == button;
        skipNextRelease = false;
        if (!isLeftClick && !isRightClick && !pick) {
            if (hoveredSlot != null && carried.isEmpty()) {
                if (getMinecraft().options.keySwapOffhand.matchesMouse(button)) {
                    slotClicked(hoveredSlot.index, OFFHAND, ClickType.SWAP);
                } else {
                    for (int i = 0; i < 9; i++) {
                        if (getMinecraft().options.keyHotbarSlots[i].matchesMouse(button)) {
                            slotClicked(hoveredSlot.index, i, ClickType.SWAP);
                        }
                    }
                }
            }
        } else {
            boolean outside = slot == null && hasClickedOutside(mouseX, mouseY, leftPos, topPos, button);
            int index = slot != null ? slot.index : outside ? AbstractContainerMenu.SLOT_CLICKED_OUTSIDE : -1;
            boolean touchscreen = getMinecraft().options.touchscreen().get();
            if (touchscreen && outside && carried.isEmpty()) {
                onClose();
                return true;
            }
            if (index != -1) {
                if (touchscreen) {
                    if (slot != null && slot.hasItem()) {
                        clickedSlot = slot;
                        draggingItem = ItemStack.EMPTY;
                        isSplittingStack = isRightClick;
                    } else {
                        clickedSlot = null;
                    }
                } else if (!isQuickCrafting) {
                    if (carried.isEmpty()) {
                        if (pick) {
                            slotClicked(index, button, ClickType.CLONE);
                        } else {
                            boolean isShiftDown = index != AbstractContainerMenu.SLOT_CLICKED_OUTSIDE && hasShiftDown();
                            ClickType clickType = ClickType.PICKUP;
                            if (isShiftDown) {
                                lastQuickMoved = slot.hasItem() ? slot.getItem().copy() : ItemStack.EMPTY;
                                clickType = ClickType.QUICK_MOVE;
                            } else if (index == AbstractContainerMenu.SLOT_CLICKED_OUTSIDE) {
                                clickType = ClickType.THROW;
                            }
                            slotClicked(index, button, clickType);
                        }
                        skipNextRelease = true;
                    } else {
                        isQuickCrafting = true;
                        quickCraftButton = button;
                        quickCraftSlots.clear();
                        quickCraftType = switch (button) {
                            case GLFW.GLFW_MOUSE_BUTTON_1 -> AbstractContainerMenu.QUICKCRAFT_TYPE_CHARITABLE;
                            case GLFW.GLFW_MOUSE_BUTTON_2 -> AbstractContainerMenu.QUICKCRAFT_TYPE_GREEDY;
                            default -> AbstractContainerMenu.QUICKCRAFT_TYPE_CLONE;
                        };
                    }
                }
            }
        }
        lastClickSlot = slot;
        lastClickTime = time;
        lastClickButton = button;
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        Slot slot = findSlot(mouseX, mouseY);
        boolean isLeftClick = button == GLFW.GLFW_MOUSE_BUTTON_1;
        boolean isRightClick = button == GLFW.GLFW_MOUSE_BUTTON_2;
        if (clickedSlot != null && getMinecraft().options.touchscreen().get()) {
            if (isLeftClick || isRightClick) {
                if (draggingItem.isEmpty()) {
                    if (slot != clickedSlot && !clickedSlot.getItem().isEmpty()) {
                        draggingItem = clickedSlot.getItem().copy();
                    }
                } else if (draggingItem.getCount() > 1 && slot != null && AbstractContainerMenu.canItemQuickReplace(slot, draggingItem, false)) {
                    long time = Util.getMillis();
                    if (quickdropSlot == slot) {
                        if (time - quickdropTime > 500) {
                            slotClicked(clickedSlot.index, GLFW.GLFW_MOUSE_BUTTON_1, ClickType.PICKUP);
                            slotClicked(slot.index, GLFW.GLFW_MOUSE_BUTTON_2, ClickType.PICKUP);
                            slotClicked(clickedSlot.index, GLFW.GLFW_MOUSE_BUTTON_1, ClickType.PICKUP);
                            quickdropTime = time + 750;
                            draggingItem.shrink(1);
                        }
                    } else {
                        quickdropSlot = slot;
                        quickdropTime = time;
                    }
                }
            }
        } else if (isQuickCrafting && slot != null && !carried.isEmpty() && (carried.getCount() > quickCraftSlots.size() || quickCraftType == AbstractContainerMenu.QUICKCRAFT_TYPE_CLONE) && AbstractContainerMenu.canItemQuickReplace(slot, carried, true) && slot.mayPlace(carried)) {
            quickCraftSlots.add(slot);
            recalculateQuickCraftRemaining();
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);
        Slot slot = findSlot(mouseX, mouseY);
        boolean isLeftClick = button == GLFW.GLFW_MOUSE_BUTTON_1;
        boolean isRightClick = button == GLFW.GLFW_MOUSE_BUTTON_2;
        boolean outside = slot == null && hasClickedOutside(mouseX, mouseY, leftPos, topPos, button);
        InputConstants.Key mouseKey = InputConstants.Type.MOUSE.getOrCreate(button);
        int index = slot != null ? slot.index : outside ? AbstractContainerMenu.SLOT_CLICKED_OUTSIDE : -1;
        if (doubleclick && slot != null && isLeftClick) {
            if (!hasShiftDown()) {
                slotClicked(index, button, ClickType.PICKUP_ALL);
            } else if (!lastQuickMoved.isEmpty()) {
                for (Slot s : slots) {
                    if (s != null && s.mayPickup(Objects.requireNonNull(getMinecraft().player)) && s.hasItem() && s.isSameInventory(slot) && AbstractContainerMenu.canItemQuickReplace(s, lastQuickMoved, true)) {
                        slotClicked(s.index, button, ClickType.QUICK_MOVE);
                    }
                }
            }
            doubleclick = false;
            lastClickTime = 0;
        } else {
            if (isQuickCrafting && quickCraftButton != button) {
                isQuickCrafting = false;
                quickCraftSlots.clear();
                skipNextRelease = true;
                return true;
            }
            if (skipNextRelease) {
                skipNextRelease = false;
                return true;
            }
            if (clickedSlot != null && getMinecraft().options.touchscreen().get()) {
                if (isLeftClick || isRightClick) {
                    if (draggingItem.isEmpty() && slot != clickedSlot) {
                        draggingItem = clickedSlot.getItem();
                    }
                    if (index != -1 && !draggingItem.isEmpty() && AbstractContainerMenu.canItemQuickReplace(slot, draggingItem, false)) {
                        slotClicked(clickedSlot.index, button, ClickType.PICKUP);
                        slotClicked(index, GLFW.GLFW_MOUSE_BUTTON_1, ClickType.PICKUP);
                        if (carried.isEmpty()) {
                            snapbackItem = ItemStack.EMPTY;
                        } else {
                            slotClicked(clickedSlot.index, button, ClickType.PICKUP);
                            snapbackStartX = Mth.floor(mouseX - leftPos);
                            snapbackStartY = Mth.floor(mouseY - topPos);
                            snapbackEnd = clickedSlot;
                            snapbackItem = draggingItem;
                            snapbackTime = Util.getMillis();
                        }
                    } else if (!draggingItem.isEmpty()) {
                        snapbackStartX = Mth.floor(mouseX - leftPos);
                        snapbackStartY = Mth.floor(mouseY - topPos);
                        snapbackEnd = clickedSlot;
                        snapbackItem = draggingItem;
                        snapbackTime = Util.getMillis();
                    }
                    clearDraggingState();
                }
            } else if (isQuickCrafting && !quickCraftSlots.isEmpty()) {
                slotClicked(AbstractContainerMenu.SLOT_CLICKED_OUTSIDE, AbstractContainerMenu.getQuickcraftMask(AbstractContainerMenu.QUICKCRAFT_HEADER_START, quickCraftType), ClickType.QUICK_CRAFT);
                for (Slot s : quickCraftSlots) {
                    slotClicked(s.index, AbstractContainerMenu.getQuickcraftMask(AbstractContainerMenu.QUICKCRAFT_HEADER_CONTINUE, quickCraftType), ClickType.QUICK_CRAFT);
                }
                slotClicked(AbstractContainerMenu.SLOT_CLICKED_OUTSIDE, AbstractContainerMenu.getQuickcraftMask(AbstractContainerMenu.QUICKCRAFT_HEADER_END, quickCraftType), ClickType.QUICK_CRAFT);
            } else if (!carried.isEmpty()) {
                if (getMinecraft().options.keyPickItem.isActiveAndMatches(mouseKey)) {
                    slotClicked(index, button, ClickType.CLONE);
                } else {
                    boolean hasShiftDown = index != AbstractContainerMenu.SLOT_CLICKED_OUTSIDE && Screen.hasShiftDown();
                    if (hasShiftDown) {
                        lastQuickMoved = slot != null && slot.hasItem() ? slot.getItem().copy() : ItemStack.EMPTY;
                    }
                    slotClicked(index, button, hasShiftDown ? ClickType.QUICK_MOVE : ClickType.PICKUP);
                }
            }
        }
        if (carried.isEmpty()) {
            lastClickTime = 0;
        }
        isQuickCrafting = false;
        return true;
    }

    protected void slotClicked(int slotId, int button, ClickType clickType) {
        Player player = Objects.requireNonNull(getMinecraft().player);
        Inventory inventory = player.getInventory();
        boolean isLeftClick = button == GLFW.GLFW_MOUSE_BUTTON_1;
        boolean isRightClick = button == GLFW.GLFW_MOUSE_BUTTON_2;
        if (clickType == ClickType.QUICK_CRAFT) {
            int status = quickCraftStatus;
            quickCraftStatus = AbstractContainerMenu.getQuickcraftHeader(button);
            if ((status != 1 || quickCraftStatus != AbstractContainerMenu.QUICKCRAFT_HEADER_END) && status != quickCraftStatus || carried.isEmpty()) {
                resetQuickCraft();
            } else if (quickCraftStatus == AbstractContainerMenu.QUICKCRAFT_HEADER_START) {
                quickCraftType = AbstractContainerMenu.getQuickcraftType(button);
                if (AbstractContainerMenu.isValidQuickcraftType(quickCraftType, player)) {
                    quickCraftStatus = AbstractContainerMenu.QUICKCRAFT_HEADER_CONTINUE;
                    quickCraftSlots.clear();
                } else {
                    resetQuickCraft();
                }
            } else if (quickCraftStatus == AbstractContainerMenu.QUICKCRAFT_HEADER_CONTINUE) {
                Slot slot = slots.get(slotId);
                if (AbstractContainerMenu.canItemQuickReplace(slot, carried, true) && slot.mayPlace(carried) && (quickCraftType == AbstractContainerMenu.QUICKCRAFT_TYPE_CLONE || carried.getCount() > quickCraftSlots.size())) {
                    quickCraftSlots.add(slot);
                }
            } else {
                if (quickCraftStatus == AbstractContainerMenu.QUICKCRAFT_HEADER_END && !quickCraftSlots.isEmpty()) {
                    if (quickCraftSlots.size() == 1) {
                        resetQuickCraft();
                        slotClicked(quickCraftSlots.iterator().next().index, quickCraftType, ClickType.PICKUP);
                        return;
                    }
                    ItemStack carriedCopy = carried.copy();
                    if (carriedCopy.isEmpty()) {
                        resetQuickCraft();
                        return;
                    }
                    int count = carried.getCount();
                    for (Slot slot : quickCraftSlots) {
                        if (slot != null && AbstractContainerMenu.canItemQuickReplace(slot, carried, true) && slot.mayPlace(carried) && (quickCraftType == AbstractContainerMenu.QUICKCRAFT_TYPE_CLONE || carried.getCount() >= quickCraftSlots.size())) {
                            int slotCount = slot.hasItem() ? slot.getItem().getCount() : 0;
                            int maxStackSize = Math.min(carriedCopy.getMaxStackSize(), slot.getMaxStackSize(carriedCopy));
                            int placeCount = Math.min(AbstractContainerMenu.getQuickCraftPlaceCount(quickCraftSlots, quickCraftType, carriedCopy) + slotCount, maxStackSize);
                            count -= placeCount - slotCount;
                            slot.setByPlayer(carriedCopy.copyWithCount(placeCount));
                        }
                    }
                    carriedCopy.setCount(count);
                    setCarried(carriedCopy);
                }
                resetQuickCraft();
            }
        } else if (quickCraftStatus != AbstractContainerMenu.QUICKCRAFT_HEADER_START) {
            resetQuickCraft();
        } else if ((clickType == ClickType.PICKUP || clickType == ClickType.QUICK_MOVE) && (isLeftClick || isRightClick)) {
            ClickAction action = isLeftClick ? ClickAction.PRIMARY : ClickAction.SECONDARY;
            if (slotId == AbstractContainerMenu.SLOT_CLICKED_OUTSIDE) {
                if (!carried.isEmpty()) {
                    if (action == ClickAction.PRIMARY) {
                        player.drop(carried, true);
                        setCarried(ItemStack.EMPTY);
                    } else {
                        player.drop(carried.split(1), true);
                    }
                }
            } else {
                if (slotId < 0) return;
                Slot slot = slots.get(slotId);
                if (clickType == ClickType.QUICK_MOVE) {
                    if (!slot.mayPickup(player)) return;
                    ItemStack quickMovedStack = quickMoveStack(player, slotId);
                    while (!quickMovedStack.isEmpty() && ItemStack.isSameItem(slot.getItem(), quickMovedStack)) {
                        quickMovedStack = quickMoveStack(player, slotId);
                    }
                } else {
                    ItemStack stack = slot.getItem();
                    player.updateTutorialInventoryAction(carried, slot.getItem(), action);
                    FeatureFlagSet featureFlags = player.level().enabledFeatures();
                    if (!CommonHooks.onItemStackedOn(carried, stack, slot, action, player, slotAccess) && (!carried.isItemEnabled(featureFlags) || !carried.overrideStackedOnOther(slot, action, player)) && (!stack.isItemEnabled(featureFlags) || !stack.overrideOtherStackedOnMe(carried, slot, action, player, slotAccess))) {
                        if (stack.isEmpty()) {
                            if (!carried.isEmpty()) {
                                setCarried(slot.safeInsert(carried, action == ClickAction.PRIMARY ? carried.getCount() : 1));
                            }
                        } else if (slot.mayPickup(player)) {
                            if (carried.isEmpty()) {
                                slot.tryRemove(action == ClickAction.PRIMARY ? stack.getCount() : (stack.getCount() + 1) / 2, AbstractContainerMenu.CARRIED_SLOT_SIZE, player).ifPresent(item -> {
                                    setCarried(item);
                                    slot.onTake(player, item);
                                });
                            } else if (slot.mayPlace(carried)) {
                                if (ItemStack.isSameItemSameComponents(stack, carried)) {
                                    setCarried(slot.safeInsert(carried, action == ClickAction.PRIMARY ? carried.getCount() : 1));
                                } else if (carried.getCount() <= slot.getMaxStackSize(carried)) {
                                    setCarried(stack);
                                    slot.setByPlayer(carried);
                                }
                            } else if (ItemStack.isSameItemSameComponents(stack, carried)) {
                                slot.tryRemove(stack.getCount(), carried.getMaxStackSize() - carried.getCount(), player).ifPresent(item -> {
                                    carried.grow(item.getCount());
                                    slot.onTake(player, item);
                                });
                            }
                        }
                    }
                    slot.setChanged();
                }
            }
        } else if (clickType == ClickType.SWAP && (button >= 0 && button < 9 || button == OFFHAND)) {
            ItemStack invStack = inventory.getItem(button);
            Slot slot = slots.get(slotId);
            ItemStack stack = slot.getItem();
            if (!invStack.isEmpty() || !stack.isEmpty()) {
                if (invStack.isEmpty()) {
                    if (slot.mayPickup(player)) {
                        inventory.setItem(button, stack);
                        slot.setByPlayer(ItemStack.EMPTY);
                        slot.onTake(player, stack);
                    }
                } else if (stack.isEmpty()) {
                    if (slot.mayPlace(invStack)) {
                        int maxStackSize = slot.getMaxStackSize(invStack);
                        if (invStack.getCount() > maxStackSize) {
                            slot.setByPlayer(invStack.split(maxStackSize));
                        } else {
                            inventory.setItem(button, ItemStack.EMPTY);
                            slot.setByPlayer(invStack);
                        }
                    }
                } else if (slot.mayPickup(player) && slot.mayPlace(invStack)) {
                    int maxStackSize = slot.getMaxStackSize(invStack);
                    if (invStack.getCount() > maxStackSize) {
                        slot.setByPlayer(invStack.split(maxStackSize));
                        slot.onTake(player, stack);
                        if (!inventory.add(stack)) {
                            player.drop(stack, true);
                        }
                    } else {
                        inventory.setItem(button, stack);
                        slot.setByPlayer(invStack);
                        slot.onTake(player, stack);
                    }
                }
            }
        } else if (clickType == ClickType.CLONE && player.hasInfiniteMaterials() && carried.isEmpty() && slotId >= 0) {
            Slot slot = slots.get(slotId);
            if (slot.hasItem()) {
                ItemStack stack = slot.getItem();
                setCarried(stack.copyWithCount(stack.getMaxStackSize()));
            }
        } else if (clickType == ClickType.THROW && carried.isEmpty() && slotId >= 0) {
            Slot slot = slots.get(slotId);
            int count = isLeftClick ? 1 : slot.getItem().getCount();
            player.drop(slot.safeTake(count, AbstractContainerMenu.CARRIED_SLOT_SIZE, player), true);
        } else if (clickType == ClickType.PICKUP_ALL && slotId >= 0) {
            Slot slot = slots.get(slotId);
            if (!carried.isEmpty() && (!slot.hasItem() || !slot.mayPickup(player))) {
                int index = isLeftClick ? 0 : slots.size() - 1;
                int direction = isLeftClick ? 1 : -1;
                for (int i = index; i >= 0 && i < slots.size() && carried.getCount() < carried.getMaxStackSize(); i += direction) {
                    Slot currentSlot = slots.get(i);
                    if (currentSlot.hasItem() && AbstractContainerMenu.canItemQuickReplace(currentSlot, carried, true) && currentSlot.mayPickup(player)) {
                        ItemStack stack = currentSlot.getItem();
                        if (stack.getCount() != stack.getMaxStackSize()) {
                            carried.grow(currentSlot.safeTake(stack.getCount(), carried.getMaxStackSize() - carried.getCount(), player).getCount());
                        }
                    }
                }
                for (int i = index; i >= 0 && i < slots.size() && carried.getCount() < carried.getMaxStackSize(); i += direction) {
                    Slot currentSlot = slots.get(i);
                    if (currentSlot.hasItem() && AbstractContainerMenu.canItemQuickReplace(currentSlot, carried, true) && currentSlot.mayPickup(player)) {
                        carried.grow(currentSlot.safeTake(currentSlot.getItem().getCount(), carried.getMaxStackSize() - carried.getCount(), player).getCount());
                    }
                }
            }
        }
    }
}
