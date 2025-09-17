package at.minecraftschurli.arsmagicalegacy.api.client;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponentType;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface SpellPartCustomizationScreen {
    @FunctionalInterface
    interface Factory<T, S extends Screen & SpellPartCustomizationScreen> {
        S create(Function<DataComponentType<T>, T> valueGetter, BiConsumer<DataComponentType<T>, T> valueSetter);
    }
}
