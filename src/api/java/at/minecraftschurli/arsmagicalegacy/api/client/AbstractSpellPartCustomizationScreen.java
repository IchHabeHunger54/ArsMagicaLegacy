package at.minecraftschurli.arsmagicalegacy.api.client;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;

import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class AbstractSpellPartCustomizationScreen<T> extends Screen implements SpellPartCustomizationScreen {
    private final DataComponentType<T> type;
    private final BiConsumer<DataComponentType<T>, T> setter;
    protected T value;

    public AbstractSpellPartCustomizationScreen(Component title, DataComponentType<T> type, Function<DataComponentType<T>, T> value, BiConsumer<DataComponentType<T>, T> setter) {
        super(title);
        this.type = type;
        this.value = value.apply(type);
        this.setter = setter;
    }

    @Override
    public void onClose() {
        setter.accept(type, value);
        super.onClose();
    }
}
