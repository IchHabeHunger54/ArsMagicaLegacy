package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface AMCreativeTabs {
    Supplier<CreativeModeTab> MAIN = AMRegistries.CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup." + ArsMagicaApi.MOD_ID))
        .icon(AMItems.OCCULUS::toStack)
        .displayItems((display, output) -> {
            output.accept(AMItems.OCCULUS);
            acceptVariants(display, output, AMItems.INFINITY_ORB, AMRegistryKeys.SKILL_POINT, (stack, holder) -> stack.set(AMDataComponents.SKILL_POINT, holder));
            output.accept(AMItems.CHIMERITE_ORE);
            output.accept(AMItems.DEEPSLATE_CHIMERITE_ORE);
            output.accept(AMItems.CHIMERITE);
            output.accept(AMItems.CHIMERITE_BLOCK);
            output.accept(AMItems.TOPAZ_ORE);
            output.accept(AMItems.DEEPSLATE_TOPAZ_ORE);
            output.accept(AMItems.TOPAZ);
            output.accept(AMItems.TOPAZ_BLOCK);
            output.accept(AMItems.VINTEUM_ORE);
            output.accept(AMItems.DEEPSLATE_VINTEUM_ORE);
            output.accept(AMItems.VINTEUM_DUST);
            output.accept(AMItems.VINTEUM_BLOCK);
            output.accept(AMItems.MOONSTONE_ORE);
            output.accept(AMItems.DEEPSLATE_MOONSTONE_ORE);
            output.accept(AMItems.MOONSTONE);
            output.accept(AMItems.MOONSTONE_BLOCK);
            output.accept(AMItems.SUNSTONE_ORE);
            output.accept(AMItems.SUNSTONE);
            output.accept(AMItems.SUNSTONE_BLOCK);
            output.accept(AMItems.ARCANE_COMPOUND);
            output.accept(AMItems.ARCANE_ASH);
            output.accept(AMItems.PURIFIED_VINTEUM_DUST);
            output.accept(AMItems.WITCHWOOD_LOG);
            output.accept(AMItems.WITCHWOOD);
            output.accept(AMItems.STRIPPED_WITCHWOOD_LOG);
            output.accept(AMItems.STRIPPED_WITCHWOOD);
            output.accept(AMItems.WITCHWOOD_LEAVES);
            output.accept(AMItems.WITCHWOOD_SAPLING);
            output.accept(AMItems.WITCHWOOD_PLANKS);
            output.accept(AMItems.WITCHWOOD_SLAB);
            output.accept(AMItems.WITCHWOOD_STAIRS);
            output.accept(AMItems.WITCHWOOD_FENCE);
            output.accept(AMItems.WITCHWOOD_FENCE_GATE);
            output.accept(AMItems.WITCHWOOD_DOOR);
            output.accept(AMItems.WITCHWOOD_TRAPDOOR);
            output.accept(AMItems.WITCHWOOD_BUTTON);
            output.accept(AMItems.WITCHWOOD_PRESSURE_PLATE);
            output.accept(AMItems.WITCHWOOD_SIGN);
            output.accept(AMItems.WITCHWOOD_HANGING_SIGN);
            output.accept(AMItems.AUM);
            output.accept(AMItems.CERUBLOSSOM);
            output.accept(AMItems.DESERT_NOVA);
            output.accept(AMItems.TARMA_ROOT);
            output.accept(AMItems.WAKEBLOOM);
            output.accept(AMItems.VINTEUM_TORCH);
        })
        .build());

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }

    private static <T> void acceptVariants(CreativeModeTab.ItemDisplayParameters display, CreativeModeTab.Output output, DeferredItem<?> item, ResourceKey<Registry<T>> registryKey, BiConsumer<ItemStack, Holder<T>> consumer) {
        display.holders().lookup(registryKey).ifPresent(registry -> registry.listElements().forEach(holder -> {
            ItemStack stack = item.toStack();
            consumer.accept(stack, holder);
            output.accept(stack);
        }));
    }
}
