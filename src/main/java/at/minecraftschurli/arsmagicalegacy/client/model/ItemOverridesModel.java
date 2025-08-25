package at.minecraftschurli.arsmagicalegacy.client.model;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Map;

public class ItemOverridesModel extends BakedModelWrapper<BakedModel> {
    private final ItemOverrides overrides;

    public ItemOverridesModel(BakedModel originalModel, ItemOverrides overrides) {
        super(originalModel);
        this.overrides = overrides;
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    public static void register(Map<ModelResourceLocation, BakedModel> models, DeferredItem<?> item, ItemOverrides overrides) {
        models.computeIfPresent(ModelResourceLocation.inventory(item.getId()), (k, v) -> new ItemOverridesModel(v, overrides));
    }
}
