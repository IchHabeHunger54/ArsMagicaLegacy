package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMRegistries {
    DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ArsMagicaApi.MOD_ID);
    DeferredRegister.Items  ITEMS  = DeferredRegister.createItems(ArsMagicaApi.MOD_ID);
    DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArsMagicaApi.MOD_ID);

    static void init(IEventBus bus) {
        AMBlocks.init();
        AMItems.init();
        AMCreativeTabs.init();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        CREATIVE_TABS.register(bus);
    }
}
