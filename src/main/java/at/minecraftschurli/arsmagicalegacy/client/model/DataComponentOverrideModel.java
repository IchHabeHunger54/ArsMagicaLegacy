package at.minecraftschurli.arsmagicalegacy.client.model;

import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Stream;

public class DataComponentOverrideModel extends BakedModelWrapper<BakedModel> {
    public static final ItemOverrides SKILL_POINT_OVERRIDES = new ItemOverrides() {
        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            if (stack.has(AMDataComponents.SKILL_POINT)) {
                ResourceLocation skillPoint = stack.get(AMDataComponents.SKILL_POINT).getKey().location().withPrefix("item/" + stack.getItemHolder().getKey().location().getPath() + "_");
                return Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(skillPoint));
            }
            return super.resolve(model, stack, level, entity, seed);
        }
    };
    private final ItemOverrides overrides;

    public DataComponentOverrideModel(BakedModel originalModel, ItemOverrides overrides) {
        super(originalModel);
        this.overrides = overrides;
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    public static Stream<ModelResourceLocation> getAdditionalModels(DeferredItem<?> item, ResourceKey<?>... variants) {
        return Arrays.stream(variants)
            .map(ResourceKey::location)
            .map(location -> location.withPrefix("item/" + item.getId().getPath() + "_"))
            .map(ModelResourceLocation::standalone);
    }
}
