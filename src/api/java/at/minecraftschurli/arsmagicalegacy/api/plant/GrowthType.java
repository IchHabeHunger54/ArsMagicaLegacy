package at.minecraftschurli.arsmagicalegacy.api.plant;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public interface GrowthType {
    Codec<GrowthType> CODEC = Codec.lazyInitialized(() -> ArsMagicaApi.growthTypeRegistry().byNameCodec().dispatch(GrowthType::codec, Function.identity()));

    MapCodec<? extends GrowthType> codec();

    boolean canGrow(GrowthContext context);

    void grow(GrowthContext context);

    boolean canHarvest(GrowthContext context);

    List<ItemStack> harvest(GrowthContext context);

    boolean canReplant(GrowthContext context);

    void replant(GrowthContext context);
}
