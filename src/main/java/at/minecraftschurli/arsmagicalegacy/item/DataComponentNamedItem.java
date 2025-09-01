package at.minecraftschurli.arsmagicalegacy.item;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiFunction;

public class DataComponentNamedItem<T> extends Item {
    private final DataComponentType<T> dataComponent;
    private BiFunction<T, Component, Component> nameGetter;
    private BiFunction<T, String, String> translationKeyGetter;

    public DataComponentNamedItem(Properties properties, DataComponentType<T> dataComponent) {
        super(properties);
        this.dataComponent = dataComponent;
    }

    public static <T> ItemStack set(ItemStack stack, DataComponentType<T> type, T value) {
        stack.set(type, value);
        return stack;
    }

    public DataComponentNamedItem<T> withNameGetter(BiFunction<T, Component, Component> nameGetter) {
        this.nameGetter = nameGetter;
        return this;
    }

    public DataComponentNamedItem<T> withTranslationKeyGetter(BiFunction<T, String, String> translationKeyGetter) {
        this.translationKeyGetter = translationKeyGetter;
        return this;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return translationKeyGetter != null && stack.has(dataComponent) ? translationKeyGetter.apply(stack.get(dataComponent), super.getDescriptionId(stack)) : super.getDescriptionId(stack);
    }

    @Override
    public Component getName(ItemStack stack) {
        return nameGetter != null && stack.has(dataComponent) ? nameGetter.apply(stack.get(dataComponent), super.getName(stack)) : super.getName(stack);
    }
}
