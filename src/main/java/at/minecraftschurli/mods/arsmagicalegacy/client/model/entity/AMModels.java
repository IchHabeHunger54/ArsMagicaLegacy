package at.minecraftschurli.mods.arsmagicalegacy.client.model.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public interface AMModels {
    ModelLayerLocation WITCHWOOD_BOAT = new ModelLayerLocation(ArsMagicaApi.id("boat/witchwood"), "main");
    ModelLayerLocation WITCHWOOD_CHEST_BOAT = new ModelLayerLocation(ArsMagicaApi.id("chest_boat/witchwood"), "main");
    ModelLayerLocation DRYAD = new ModelLayerLocation(ArsMagicaApi.id("dryad"), "main");

    static LayerDefinition createDryadLayer() {
        return LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0), 64, 32);
    }
}
