package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMRegistries {
    // @formatter:off
    DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ArsMagicaApi.MOD_ID);
    DeferredRegister.Items  ITEMS  = DeferredRegister.createItems(ArsMagicaApi.MOD_ID);
    DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ArsMagicaApi.MOD_ID);
    DeferredRegister<CreativeModeTab> CREATIVE_TABS  = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArsMagicaApi.MOD_ID);
    DeferredRegister<Feature<?>>      FEATURES       = DeferredRegister.create(Registries.FEATURE,           ArsMagicaApi.MOD_ID);
    // @formatter:on

    /**
     * Classloads the registration classes and registers the {@link DeferredRegister}s.
     *
     * @param bus The {@link IEventBus} to use.
     */
    static void init(IEventBus bus) {
        AMBlocks.init();
        AMItems.init();
        AMDataComponents.init();
        AMCreativeTabs.init();
        AMWorldgen.init();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        DATA_COMPONENTS.register(bus);
        CREATIVE_TABS.register(bus);
        FEATURES.register(bus);
    }
}
