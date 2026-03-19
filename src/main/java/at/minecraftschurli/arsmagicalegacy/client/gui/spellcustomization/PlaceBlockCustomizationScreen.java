package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.screen.AbstractContainerSpellPartCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.menu.container.SingleItemContainer;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class PlaceBlockCustomizationScreen extends AbstractContainerSpellPartCustomizationScreen<Block> {
    private static final Identifier INVENTORY = ArsMagicaApi.id("textures/gui/spell_customization/inventory.png");
    private static final Identifier SLOT = ArsMagicaApi.id("textures/gui/spell_customization/place_block.png");
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
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SLOT, leftPos + 72, topPos, 0, 0, 32, 32, 32, 32);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, INVENTORY, leftPos, topPos + 32, 0, 0, 176, 100, 256, 256);
    }

    @Override
    protected void renderLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.text(font, AMClientUtil.player().getInventory().getDisplayName(), 8, 38, 0x404040, false);
    }
}
