package at.minecraftschurli.arsmagicalegacy.api.constants;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumHandler;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

/**
 * Holds the capabilities added by Ars Magica: Legacy.
 */
public interface AMCapabilities {
    BlockCapability<EtheriumHandler, @Nullable Direction> BLOCK_ETHERIUM = BlockCapability.createSided(ArsMagicaApi.id("etherium"), EtheriumHandler.class);
}
