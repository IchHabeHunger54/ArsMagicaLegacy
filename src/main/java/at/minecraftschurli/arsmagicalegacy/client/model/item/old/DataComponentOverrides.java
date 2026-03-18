package at.minecraftschurli.arsmagicalegacy.client.model.item.old;

import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelIdentifier;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import org.apache.commons.lang3.function.TriFunction;
import org.jspecify.annotations.Nullable;

import java.util.stream.Stream;

public class DataComponentOverrides<T> extends ItemOverrides {
    private final DataComponentType<T> dataComponent;
    private final TriFunction<T, BakedModel, ItemStack, @Nullable ModelIdentifier> modelFunction;

    public DataComponentOverrides(DataComponentType<T> dataComponent, TriFunction<T, BakedModel, ItemStack, @Nullable ModelIdentifier> modelFunction) {
        this.dataComponent = dataComponent;
        this.modelFunction = modelFunction;
    }

    @Override
    @Nullable
    public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        ModelIdentifier location = stack.has(dataComponent) ? modelFunction.apply(stack.get(dataComponent), model, stack) : null;
        return location == null ? super.resolve(model, stack, level, entity, seed) : AMClientUtil.mc().getModelManager().getModel(location);
    }

    @SuppressWarnings("DataFlowIssue")
    public static <T> TriFunction<Holder<T>, BakedModel, ItemStack, ModelIdentifier> holder() {
        return (holder, model, stack) -> ModelIdentifier.standalone(holder.getKey().identifier().withPrefix("item/" + stack.getItemHolder().getKey().identifier().getPath() + "_"));
    }

    public static Stream<ModelIdentifier> getAdditionalModels(Stream<Identifier> stream, DeferredItem<?> item) {
        return stream
            .map(location -> location.withPrefix("item/" + item.getId().getPath() + "_"))
            .map(ModelIdentifier::standalone);
    }
}
