package at.minecraftschurli.mods.arsmagicalegacy.api.constants;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumHandler;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.MutableSpellFacade;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import org.jspecify.annotations.Nullable;

/**
 * Holds the capabilities added by Ars Magica: Legacy.
 */
public interface AMCapabilities {
    BlockCapability<EtheriumHandler, @Nullable Direction> BLOCK_ETHERIUM = BlockCapability.createSided(ArsMagicaApi.id("etherium"), EtheriumHandler.class);
    ItemCapability<MutableSpellFacade, @Nullable Void> SPELL = ItemCapability.createVoid(ArsMagicaApi.id("spell"), MutableSpellFacade.class);
}
