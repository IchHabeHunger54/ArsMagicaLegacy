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
}
