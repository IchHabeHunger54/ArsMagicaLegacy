package at.minecraftschurli.arsmagicalegacy.client.renderer.entity;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.client.model.DryadModel;
import at.minecraftschurli.arsmagicalegacy.entity.Dryad;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DryadRenderer extends HumanoidMobRenderer<Dryad, DryadModel> {
    private static final ResourceLocation TEXTURE = ArsMagicaApi.id("textures/entity/dryad.png");

    public DryadRenderer(EntityRendererProvider.Context context) {
        super(context, new DryadModel(context.bakeLayer(DryadModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Dryad entity) {
        return TEXTURE;
    }
}
