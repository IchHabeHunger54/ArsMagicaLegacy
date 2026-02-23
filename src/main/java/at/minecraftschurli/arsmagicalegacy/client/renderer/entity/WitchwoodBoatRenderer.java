package at.minecraftschurli.arsmagicalegacy.client.renderer.entity;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

public class WitchwoodBoatRenderer extends BoatRenderer {
    public static final ModelLayerLocation BOAT = new ModelLayerLocation(ArsMagicaApi.id("witchwood_boat"), "main");
    public static final ModelLayerLocation CHEST_BOAT = new ModelLayerLocation(ArsMagicaApi.id("witchwood_chest_boat"), "main");
    private static final ResourceLocation BOAT_TEXTURE = ArsMagicaApi.id("textures/entity/boat/witchwood.png");
    private static final ResourceLocation CHEST_BOAT_TEXTURE = ArsMagicaApi.id("textures/entity/chest_boat/witchwood.png");
    private final Pair<ResourceLocation, ListModel<Boat>> modelWithLocation;

    public WitchwoodBoatRenderer(EntityRendererProvider.Context context, boolean chestBoat) {
        super(context, chestBoat);
        modelWithLocation = Pair.of(chestBoat ? CHEST_BOAT_TEXTURE : BOAT_TEXTURE, chestBoat ? new ChestBoatModel(context.bakeLayer(CHEST_BOAT)) : new BoatModel(context.bakeLayer(BOAT)));
    }

    @Override
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        return modelWithLocation;
    }
}
