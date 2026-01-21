package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.screen.AbstractContainerSpellPartCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.menu.container.SingleItemContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class PlaceBlockCustomizationScreen extends AbstractContainerSpellPartCustomizationScreen<Block> {
    private static final ResourceLocation INVENTORY = ArsMagicaApi.modLoc("textures/gui/spell_customization/inventory.png");
    private static final ResourceLocation SLOT = ArsMagicaApi.modLoc("textures/gui/spell_customization/place_block.png");
    private final Container container;

    public PlaceBlockCustomizationScreen(Function<DataComponentType<Block>, @Nullable Block> valueGetter, BiConsumer<DataComponentType<Block>, @Nullable Block> valueSetter) {
        super(AMTranslations.SPELL_CUSTOMIZATION_PLACE_BLOCK, AMDataComponents.SPELL_BLOCK.get(), valueGetter, valueSetter);
        imageHeight = 132;
        container = new SingleItemContainer(value == null ? ItemStack.EMPTY : new ItemStack(value.asItem())) {
            @Override
            public void setChanged() {
                value = getValue(stack);
                setValue();
            }
        };
    }

    @Override
    @Nullable
    protected Block getValue(ItemStack stack) {
        Block block = Block.byItem(stack.getItem());
        return block.defaultBlockState().isAir() ? null : block;
    }

    @Override
    protected void addSlots() {
        slots.add(new Slot(container, 0, 80, 8) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof BlockItem;
            }
        });
        addInventorySlots(8, 50);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(SLOT, leftPos + 72, topPos, 0, 0, 32, 32, 32, 32);
        guiGraphics.blit(INVENTORY, leftPos, topPos + 32, 0, 0, 176, 100);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.drawString(font, AMClientUtil.player().getInventory().getDisplayName(), 8, 38, 0x404040, false);
    }
}
