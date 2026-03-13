package at.minecraftschurli.arsmagicalegacy.client.renderer.entity;

import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public class EmptyRenderer extends EntityRenderer<Entity> {
    public EmptyRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(Entity entity) {
        return AMUtil.MISSINGNO;
    }
}
