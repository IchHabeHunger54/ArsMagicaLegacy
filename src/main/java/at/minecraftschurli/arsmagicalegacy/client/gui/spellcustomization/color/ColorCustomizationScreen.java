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
import java.util.function.Consumer;
import java.util.function.Function;

public class ColorCustomizationScreen extends AbstractSpellPartCustomizationScreen<Integer> {
    private static final ResourceLocation BACKGROUND = ArsMagicaApi.modLoc("textures/gui/spell_customization/color.png");
    private static final int WIDTH = 180;
    private static final int HEIGHT = 130;
    private final Consumer<String> responder = s -> {
        if (s.startsWith("#") && s.length() > 1 && s.length() <= 7) {
            setColorRgb(Integer.parseInt(s.substring(1), 16));
        }
    };
    private int leftPos;
    private int topPos;
    private float hue;
    private float saturation;
    private float brightness;
    private int red;
    private int green;
    private int blue;
    private ColorPickerWidget colorPicker;
    private EditBox editBox;

    public ColorCustomizationScreen(Function<DataComponentType<Integer>, Integer> value, BiConsumer<DataComponentType<Integer>, Integer> setter) {
        super(AMTranslations.SPELL_CUSTOMIZATION_COLOR, AMSpells.COLOR_COMPONENT.get(), value, setter);
    }

    @Override
    protected void init() {
        leftPos = (width - WIDTH) / 2;
        topPos = (height - HEIGHT - 24) / 2;
        colorPicker = addRenderableWidget(new ColorPickerWidget(leftPos + 50, topPos + HEIGHT / 2 + 7, 50, getTitle(), this::setColorHsb));
        editBox = addRenderableWidget(new EditBox(AMClientUtil.font(), leftPos + 30, topPos + 121, 50, 12, Component.empty()));
        editBox.setFilter(s -> {
            if (s.isEmpty() || "#".equals(s)) return true;
            if (!s.startsWith("#") || s.length() > 7) return false;
            try {
                Integer.parseInt(s.substring(1), 16);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });
        addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, $ -> {
            value = null;
            onClose();
        }).bounds(leftPos - 10, topPos + HEIGHT + 4, 98, 20).build());
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $ -> onClose()).bounds(leftPos + 92, topPos + HEIGHT + 4, 98, 20).build());
        setColorRgb(value == null ? 0xffffff : value);
        String hex = Integer.toHexString(value);
        editBox.setValue("#" + "0".repeat(6 - hex.length()) + hex);
        editBox.setResponder(responder);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(BACKGROUND, leftPos, topPos, 0, 0, WIDTH, HEIGHT);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return editBox.active ? editBox.keyPressed(keyCode, scanCode, modifiers) : super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void setColorRgb(int rgb) {
        red = AMClientUtil.getRedI(rgb);
        green = AMClientUtil.getGreenI(rgb);
        blue = AMClientUtil.getBlueI(rgb);
        float[] hsb = AMClientUtil.rgbToHsb(red, green, blue);
        hue = hsb[0];
        saturation = hsb[1];
        brightness = hsb[2];
        updateColorWidgets();
    }

    private void setColorHsb(float h, float s, float b) {
        hue = h;
        saturation = s;
        brightness = b;
        int[] rgb = AMClientUtil.hsbToRgb(hue, saturation, brightness);
        red = rgb[0];
        green = rgb[1];
        blue = rgb[2];
        updateColorWidgets();
    }

    @SuppressWarnings("DataFlowIssue")
    private void updateColorWidgets() {
        value = red << 16 | green << 8 | blue;
        colorPicker.setValue(hue, saturation, brightness);
        editBox.setResponder(null);
        editBox.setValue("#" + Integer.toHexString(value));
        editBox.setResponder(responder);
    }

    static int blackOrWhite(float hue, float saturation, float brightness) {
        int[] rgb = AMClientUtil.hsbToRgb(hue, saturation, brightness);
        return (rgb[0] << 16) * 0.299 + (rgb[1] << 8) * 0.587 + rgb[2] * 0.114 > 186 ? 0xff000000 : 0xffffffff;
    }
}
