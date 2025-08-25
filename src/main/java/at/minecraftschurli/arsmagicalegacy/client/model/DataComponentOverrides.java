package at.minecraftschurli.arsmagicalegacy.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class DataComponentOverrides<T> extends ItemOverrides {
    private final DataComponentType<T> dataComponent;
    private final TriFunction<T, BakedModel, ItemStack, @Nullable ModelResourceLocation> modelFunction;

    public DataComponentOverrides(DataComponentType<T> dataComponent, TriFunction<T, BakedModel, ItemStack, @Nullable ModelResourceLocation> modelFunction) {
        this.dataComponent = dataComponent;
        this.modelFunction = modelFunction;
    }

    @Override
    @Nullable
    public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        ModelResourceLocation location = stack.has(dataComponent) ? modelFunction.apply(stack.get(dataComponent), model, stack) : null;
        return location == null ? super.resolve(model, stack, level, entity, seed) : Minecraft.getInstance().getModelManager().getModel(location);
    }

    public static <T> TriFunction<Holder<T>, BakedModel, ItemStack, ModelResourceLocation> holder() {
        return (holder, model, stack) -> ModelResourceLocation.standalone(holder.getKey().location().withPrefix("item/" + stack.getItemHolder().getKey().location().getPath() + "_"));
    }

    public static Stream<ModelResourceLocation> getAdditionalModels(Stream<ResourceLocation> stream, DeferredItem<?> item) {
        return stream
            .map(location -> location.withPrefix("item/" + item.getId().getPath() + "_"))
            .map(ModelResourceLocation::standalone);
    }
}
