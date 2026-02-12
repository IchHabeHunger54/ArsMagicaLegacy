package at.minecraftschurli.arsmagicalegacy.api.plant;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

/**
 * Holds a {@link Plant}'s growth logic.
 */
public interface GrowthType {
    Codec<GrowthType> CODEC = Codec.lazyInitialized(() -> ArsMagicaApi.growthTypeRegistry().byNameCodec().dispatch(GrowthType::codec, Function.identity()));

    /**
     * @return The registered {@link MapCodec}.
     */
    MapCodec<? extends GrowthType> codec();

    /**
     * @param context The {@link GrowthContext} to use.
     * @return Whether the plant can currently be grown or not.
     */
    boolean canGrow(GrowthContext context);

    /**
     * Grows the plant, if possible.
     *
     * @param context The {@link GrowthContext} to use.
     */
    void grow(GrowthContext context);

    /**
     * @param context The {@link GrowthContext} to use.
     * @return Whether the plant can currently be harvested or not.
     */
    boolean canHarvest(GrowthContext context);

    /**
     * Harvests the plant, if possible.
     *
     * @param context The {@link GrowthContext} to use.
     * @return A list of {@link ItemStack}, representing the drops of the plant.
     */
    List<ItemStack> harvest(GrowthContext context);

    /**
     * @param context The {@link GrowthContext} to use.
     * @return Whether the plant can currently be replanted or not.
     */
    boolean canReplant(GrowthContext context);

    /**
     * Replants the plant, if possible.
     *
     * @param context The {@link GrowthContext} to use.
     */
    void replant(GrowthContext context);

    /**
     * Helper method for increasing a plant's age (if it has one) by one.
     *
     * @param context The {@link GrowthContext} to use.
     */
    default void increaseAge(GrowthContext context) {
        ServerLevel level = context.level();
        BlockState state = context.state();
        if (state.hasProperty(BlockStateProperties.AGE_1) && state.getValue(BlockStateProperties.AGE_1) < 1) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_1, state.getValue(BlockStateProperties.AGE_1) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_2) && state.getValue(BlockStateProperties.AGE_2) < 2) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_2, state.getValue(BlockStateProperties.AGE_2) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_3) && state.getValue(BlockStateProperties.AGE_3) < 3) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_3, state.getValue(BlockStateProperties.AGE_3) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_4) && state.getValue(BlockStateProperties.AGE_4) < 4) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_4, state.getValue(BlockStateProperties.AGE_4) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_5) && state.getValue(BlockStateProperties.AGE_5) < 5) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_5, state.getValue(BlockStateProperties.AGE_5) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_7) && state.getValue(BlockStateProperties.AGE_7) < 7) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_7, state.getValue(BlockStateProperties.AGE_7) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_15) && state.getValue(BlockStateProperties.AGE_15) < 15) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_15, state.getValue(BlockStateProperties.AGE_15) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_25) && state.getValue(BlockStateProperties.AGE_25) < 25) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_25, state.getValue(BlockStateProperties.AGE_25) + 1));
        }
    }
}
