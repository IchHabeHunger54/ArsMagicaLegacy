package at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

public class SimpleModelEntityRenderer<T extends Entity, M extends EntityModel<ModelEntityRenderState>> extends ModelEntityRenderer<T, ModelEntityRenderState, M> {
    private final Identifier texture;

    public SimpleModelEntityRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation, Function<ModelPart, M> modelFactory, Identifier texture) {
        super(context, modelFactory.apply(context.bakeLayer(modelLayerLocation)));
        this.texture = texture;
    }

    @Override
    protected Identifier getTexture(ModelEntityRenderState state) {
        return texture;
    }

    @Override
    public ModelEntityRenderState createRenderState() {
        return new ModelEntityRenderState();
    }
}
