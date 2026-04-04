package at.minecraftschurli.mods.arsmagicalegacy.client.model.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;

public interface AMModels {
    ModelLayerLocation WITCHWOOD_BOAT = modelLayerLocation("boat/witchwood");
    ModelLayerLocation WITCHWOOD_CHEST_BOAT = modelLayerLocation("chest_boat/witchwood");
    ModelLayerLocation DRYAD = modelLayerLocation("dryad");
    ModelLayerLocation WINTERS_GRASP = modelLayerLocation("winters_grasp");
    Identifier WINTERS_GRASP_TEXTURE = ArsMagicaApi.id("textures/entity/ice_guardian.png");

    private static ModelLayerLocation modelLayerLocation(String path) {
        return new ModelLayerLocation(ArsMagicaApi.id(path), "main");
    }

    static LayerDefinition createDryadLayer() {
        return LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0), 64, 32);
    }

    static LayerDefinition createWintersGraspLayer() {
        MeshDefinition md = new MeshDefinition();
        PartDefinition pd = md.getRoot();
        AMClientUtil.addCube(pd, "arm", 94, 0, -3, -15, -3, 6, 26, 6, 0, 0, 0, 90, 0, 0);
        AMClientUtil.addCube(pd, "hand", 82, 44, -3, 12, -2, 6, 1, 5, 0, 0, 0, 90, 0, 0);
        AMClientUtil.addCube(pd, "outer_fingers", 104, 44, 2, 11, -2, 1, 1, 5, 0, 0, 0, 90, 0, 0);
        AMClientUtil.addCube(pd, "inner_fingers", 104, 44, -3, 11, -2, 1, 1, 5, 0, 0, 0, 90, 0, 0);
        AMClientUtil.addCube(pd, "thumb", 82, 50, 0, 11, -3, 3, 2, 1, 0, 0, 0, 90, 0, 0);
        return LayerDefinition.create(md, 128, 64);
    }
}
