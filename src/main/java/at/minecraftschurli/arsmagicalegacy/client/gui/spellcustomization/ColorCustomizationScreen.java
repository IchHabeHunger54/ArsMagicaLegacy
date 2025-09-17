package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.arsmagicalegacy.api.client.AbstractSpellPartCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ColorCustomizationScreen extends AbstractSpellPartCustomizationScreen<Integer> {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 44;
    private int leftPos;
    private int topPos;

    public ColorCustomizationScreen(Function<DataComponentType<Integer>, Integer> value, BiConsumer<DataComponentType<Integer>, Integer> setter) {
        super(Component.literal("TODO"), AMSpells.COLOR_COMPONENT.get(), value, setter);
    }

    @Override
    protected void init() {
        leftPos = (width - WIDTH) / 2;
        topPos = (height - HEIGHT) / 2;
        EditBox editBox = new EditBox(AMClientUtil.font(), leftPos, topPos, 200, 20, Component.empty());
        if (value != null) {
            editBox.setValue(Integer.toHexString(value));
        }
        editBox.setFilter(s -> {
            if (s.isEmpty()) return true;
            if (s.length() > 6) return false;
            try {
                Integer.parseInt(s, 16);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });
        editBox.setResponder(s -> value = s.isEmpty() ? 0 : Integer.valueOf(s, 16));
        addRenderableWidget(editBox);
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $ -> onClose()).bounds(leftPos, topPos + 24, 200, 20).build());
    }
}
