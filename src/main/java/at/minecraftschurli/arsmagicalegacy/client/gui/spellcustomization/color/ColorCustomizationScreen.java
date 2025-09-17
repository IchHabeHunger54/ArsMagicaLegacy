package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.color;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.AbstractSpellPartCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ColorCustomizationScreen extends AbstractSpellPartCustomizationScreen<Integer> {
    private static final ResourceLocation BACKGROUND = ArsMagicaApi.modLoc("textures/gui/spell_customization/color.png");
    private static final int WIDTH = 180;
    private static final int HEIGHT = 130;
    private int leftPos;
    private int topPos;

    public ColorCustomizationScreen(Function<DataComponentType<Integer>, Integer> value, BiConsumer<DataComponentType<Integer>, Integer> setter) {
        super(AMTranslations.SPELL_CUSTOMIZATION_COLOR, AMSpells.COLOR_COMPONENT.get(), value, setter);
    }

    @Override
    protected void init() {
        leftPos = (width - WIDTH) / 2;
        topPos = (height - HEIGHT - 24) / 2;
        addRenderableWidget(new ColorPickerWidget(leftPos + 50, topPos + HEIGHT / 2 + 7, 50, getTitle()));
        EditBox editBox = addRenderableWidget(new EditBox(AMClientUtil.font(), leftPos + 30, topPos + 121, 50, 12, Component.empty()));
        editBox.setValue(value != null ? "#" + Integer.toHexString(value) : "#");
        editBox.setFilter(s -> {
            if (s.isEmpty()) return true;
            if (!s.startsWith("#") || s.length() > 7) return false;
            try {
                Integer.parseInt(s.substring(1), 16);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });
        editBox.setResponder(s -> {
            if (s.startsWith("#") && s.length() > 1 && s.length() <= 7) {
                value = Integer.parseInt(s.substring(1), 16);
            }
        });
        addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, $ -> {
            value = null;
            onClose();
        }).bounds(leftPos - 10, topPos + HEIGHT + 4, 98, 20).build());
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $ -> onClose()).bounds(leftPos + 92, topPos + HEIGHT + 4, 98, 20).build());
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(BACKGROUND, leftPos, topPos, 0, 0, WIDTH, HEIGHT);
    }
}
