package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

public interface AMCreativeTabs {
    Supplier<CreativeModeTab> MAIN = AMRegistries.CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + ArsMagicaApi.MOD_ID))
            .displayItems((display, output) -> {
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
            })
            .build());

    static void init() {
    }
}
