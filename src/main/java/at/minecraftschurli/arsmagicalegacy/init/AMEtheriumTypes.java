package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import net.minecraft.resources.ResourceKey;

public interface AMEtheriumTypes {
    ResourceKey<EtheriumType> LIGHT   = ResourceKey.create(AMRegistryKeys.ETHERIUM_TYPE, ArsMagicaApi.modLoc("light"));
    ResourceKey<EtheriumType> NEUTRAL = ResourceKey.create(AMRegistryKeys.ETHERIUM_TYPE, ArsMagicaApi.modLoc("neutral"));
    ResourceKey<EtheriumType> DARK    = ResourceKey.create(AMRegistryKeys.ETHERIUM_TYPE, ArsMagicaApi.modLoc("dark"));
}
