package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.screen.AbstractContainerSpellPartCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.item.CrystalPhylacteryItem;
import at.minecraftschurli.arsmagicalegacy.menu.container.SingleItemContainer;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.util.CrystalPhylacteryContentsSize;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class SummonCustomizationScreen extends AbstractContainerSpellPartCustomizationScreen<EntityType<?>> {
    private static final Identifier INVENTORY = ArsMagicaApi.id("textures/gui/spell_customization/inventory.png");
    private static final Identifier SLOT = ArsMagicaApi.id("textures/gui/spell_customization/summon.png");
    private final Container container;

    public SummonCustomizationScreen(Function<DataComponentType<EntityType<?>>, @Nullable EntityType<?>> valueGetter, BiConsumer<DataComponentType<EntityType<?>>, @Nullable EntityType<?>> valueSetter) {
        super(AMTranslations.SPELL_CUSTOMIZATION_SUMMON, AMDataComponents.SPELL_SUMMON.get(), valueGetter, valueSetter);
        imageHeight = 132;
        container = new SingleItemContainer(value == null ? ItemStack.EMPTY : CrystalPhylacteryItem.getFilled(value)) {
            @Override
            public void setChanged() {
                value = getValue(stack);
                setValue();
            }
        };
    }

    @Override
    @Nullable
    protected EntityType<?> getValue(ItemStack stack) {
        CrystalPhylacteryItem.Contents contents = stack.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        if (contents == null) return null;
        EntityType<?> type = contents.type();
        int size = CrystalPhylacteryContentsSize.get(type);
        return size > 0 && contents.amount() >= size ? type : null;
    }

    @Override
    protected void addSlots() {
        slots.add(new Slot(container, 0, 80, 8) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(AMItems.CRYSTAL_PHYLACTERY) && CrystalPhylacteryItem.isFull(stack);
            }
        });
        addInventorySlots(8, 50);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        graphics.blit(RenderPipelines.GUI_TEXTURED, SLOT, leftPos + 72, topPos, 0, 0, 32, 32, 32, 32);
        graphics.blit(RenderPipelines.GUI_TEXTURED, INVENTORY, leftPos, topPos + 32, 0, 0, 176, 100, 256, 256);
    }

    @Override
    protected void renderLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);
        graphics.text(font, AMClientUtil.player().getInventory().getDisplayName(), 8, 38, 0x404040, false);
    }
}
