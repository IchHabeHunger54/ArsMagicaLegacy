package at.minecraftschurli.mods.arsmagicalegacy.client.model.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.client.model.geom.ModelLayerLocation;

public interface AMModelLayers {
    ModelLayerLocation WITCHWOOD_BOAT = new ModelLayerLocation(ArsMagicaApi.id("boat/witchwood"), "main");
    ModelLayerLocation WITCHWOOD_CHEST_BOAT = new ModelLayerLocation(ArsMagicaApi.id("chest_boat/witchwood"), "main");
}
