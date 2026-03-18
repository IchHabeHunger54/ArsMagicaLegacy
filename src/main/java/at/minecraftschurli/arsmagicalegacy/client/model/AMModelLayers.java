package at.minecraftschurli.arsmagicalegacy.client.model;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.client.model.geom.ModelLayerLocation;

public interface AMModelLayers {
    ModelLayerLocation WITCHWOOD_BOAT = new ModelLayerLocation(ArsMagicaApi.id("boat/witchwood"), "main");
    ModelLayerLocation WITCHWOOD_CHEST_BOAT = new ModelLayerLocation(ArsMagicaApi.id("chest_boat/witchwood"), "main");
}
