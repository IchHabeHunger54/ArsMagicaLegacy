package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterSpellPartCustomizationScreensEvent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.component.DataComponentType;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Represents a screen for customizing a {@link SpellPart}.
 * Implementers are expected to extend {@link Screen}, however this is left as a marker interface to allow other screen classes to be used, e.g. {@link AbstractContainerScreen}.
 * See {@link AbstractSpellPartCustomizationScreen} for a dummy implementation.
 */
public interface SpellPartCustomizationScreen {
    /**
     * The factory interface for {@link SpellPartCustomizationScreen}s, used in {@link RegisterSpellPartCustomizationScreensEvent}.
     *
     * @param <T> A {@link Function} to get the initial value.
     * @param <S> A {@link BiConsumer} to set the value when the screen is closed.
     */
    @FunctionalInterface
    interface Factory<T, S extends Screen & SpellPartCustomizationScreen> {
        S create(Function<DataComponentType<T>, @Nullable T> valueGetter, BiConsumer<DataComponentType<T>, @Nullable T> valueSetter);
    }
}
