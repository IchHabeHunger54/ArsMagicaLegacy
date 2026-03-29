package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEtheriumTypes;
import net.minecraft.data.worldgen.BootstrapContext;

public final class AMEtheriumTypeProvider {
    public static void addEtheriumTypes(BootstrapContext<EtheriumType> bootstrap) {
        bootstrap.register(AMEtheriumTypes.LIGHT, new EtheriumType(0x7fa7ef));
        bootstrap.register(AMEtheriumTypes.NEUTRAL, new EtheriumType(0x3fffbf));
        bootstrap.register(AMEtheriumTypes.DARK, new EtheriumType(0x800000));
    }
}
