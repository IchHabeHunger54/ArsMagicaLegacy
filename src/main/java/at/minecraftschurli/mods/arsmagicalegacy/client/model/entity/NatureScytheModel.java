package at.minecraftschurli.mods.arsmagicalegacy.client.model.entity;

import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity.ModelEntityRenderState;
import net.minecraft.client.model.geom.ModelPart;

public class NatureScytheModel<T extends ModelEntityRenderState> extends AMEntityModel<T> {
    public NatureScytheModel(ModelPart root) {
        super(root);
        root.getChild("blade_curve").xRot = 30;
        root.getChild("blade_tip").xRot = 60;
    }
}
