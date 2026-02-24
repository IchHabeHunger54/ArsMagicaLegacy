package at.minecraftschurli.arsmagicalegacy.client.model;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.entity.Dryad;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class DryadModel extends HumanoidModel<Dryad> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ArsMagicaApi.id("dryad"), "main");

    public DryadModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0), 64, 32);
    }
}
