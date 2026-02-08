package at.minecraftschurli.arsmagicalegacy.api.plant;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Function;

public interface GrowthType {
    Codec<GrowthType> CODEC = Codec.lazyInitialized(() -> ArsMagicaApi.growthTypeRegistry().byNameCodec().dispatch(GrowthType::codec, Function.identity()));

    MapCodec<? extends GrowthType> codec();

    boolean canGrow(Plant plant, Level level, BlockPos pos);

    void grow(Plant plant, Level level, BlockPos pos);

    boolean canHarvest(Plant plant, Level level, BlockPos pos);

    List<ItemStack> harvest(Plant plant, ServerPlayer player, ServerLevel level, BlockPos pos);
}
