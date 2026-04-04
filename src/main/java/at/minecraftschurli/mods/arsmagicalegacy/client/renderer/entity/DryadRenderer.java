package at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.entity.AMModels;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.entity.DryadModel;
import at.minecraftschurli.mods.arsmagicalegacy.entity.Dryad;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;

public class DryadRenderer extends HumanoidMobRenderer<Dryad, HumanoidRenderState, DryadModel> {
    private static final Identifier TEXTURE = ArsMagicaApi.id("textures/entity/dryad.png");

    public DryadRenderer(EntityRendererProvider.Context context) {
        super(context, new DryadModel(context.bakeLayer(AMModels.DRYAD)), 0.5f);
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState state) {
        return TEXTURE;
    }
}
