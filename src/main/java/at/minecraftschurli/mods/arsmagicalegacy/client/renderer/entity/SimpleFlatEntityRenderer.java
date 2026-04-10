package at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public class SimpleFlatEntityRenderer<T extends Entity> extends FlatEntityRenderer<T, FlatEntityRenderer.State> {
    private final Identifier texture;

    public SimpleFlatEntityRenderer(EntityRendererProvider.Context context, Identifier texture) {
        super(context);
        this.texture = texture;
    }

    @Override
    public Identifier getTextureLocation() {
        return texture;
    }

    @Override
    public State createRenderState() {
        return new State();
    }
}
