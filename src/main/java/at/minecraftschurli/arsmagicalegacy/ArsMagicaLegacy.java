package at.minecraftschurli.arsmagicalegacy;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ArsMagicaApi.MOD_ID)
public final class ArsMagicaLegacy {
    public ArsMagicaLegacy(IEventBus bus) {
        AMRegistries.init(bus);
    }
}
