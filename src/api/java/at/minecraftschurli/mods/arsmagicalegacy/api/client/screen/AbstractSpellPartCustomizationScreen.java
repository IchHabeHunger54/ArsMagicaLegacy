package at.minecraftschurli.mods.arsmagicalegacy.api.client.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Dummy implementation of {@link SpellPartCustomizationScreen}. This implementation, and as such also {@link SpellPartCustomizationScreen.Factory}, expects the screen to modify exactly one data component.
 * It is encouraged to follow that pattern. If you need to store multiple values for a single spell part, adjust the type of the data component to accommodate multiple values.
 * To modify the data component value, change {@link AbstractSpellPartCustomizationScreen#value}. When the screen is closed, the change will be pushed to the parent screen automatically.
 *
 * @param <T> The type of the modified data component.
 */
public abstract class AbstractSpellPartCustomizationScreen<T> extends Screen implements SpellPartCustomizationScreen {
    private final DataComponentType<T> type;
    private final BiConsumer<DataComponentType<T>, T> setter;
    protected T value;

    /**
     * @param title       The title of the screen.
     * @param type        The {@link DataComponentType} to use.
     * @param valueGetter A {@link Function} that extracts the data component value, for initial storage.
     * @param valueSetter A {@link BiConsumer} that is called when the screen is closed, and is responsible for returning the data component value to the parent screen.
     */
    public AbstractSpellPartCustomizationScreen(Component title, DataComponentType<T> type, Function<DataComponentType<T>, @Nullable T> valueGetter, BiConsumer<DataComponentType<T>, @Nullable T> valueSetter) {
        super(title);
        this.type = type;
        this.value = valueGetter.apply(type);
        this.setter = valueSetter;
    }

    @Override
    public void onClose() {
        if (value != null) {
            setValue();
        }
        super.onClose();
    }

    /**
     * Sets the value as if the screen were closed. This does not null-check the value.
     */
    protected void setValue() {
        setter.accept(type, value);
    }
}
