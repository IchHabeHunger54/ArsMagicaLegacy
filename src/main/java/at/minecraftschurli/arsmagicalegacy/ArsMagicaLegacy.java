package at.minecraftschurli.arsmagicalegacy;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(ArsMagicaApi.MOD_ID)
public final class ArsMagicaLegacy {
    public ArsMagicaLegacy(ModContainer container, IEventBus bus) {
        container.registerConfig(ModConfig.Type.SERVER, AMServerConfig.SPEC);
        AMRegistries.init(bus);
    }
}
