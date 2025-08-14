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

    static void init() {
    }
}
