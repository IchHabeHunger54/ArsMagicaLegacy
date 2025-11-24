package at.minecraftschurli.arsmagicalegacy.api.etherium;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a block that has an {@link EtheriumHandler} {@link BlockEntity} attached. Methods in this class mainly serve selection or visual purposes.
 */
public interface EtheriumHandlerBlock {
    /**
     * Returns the {@link BlockEntity} at the given position. If this is a block larger than 1x1x1,
     * this should return the {@link BlockEntity} that actually controls the logic. If an actual block entity is not present, null should be returned.
     *
     * @param level The {@link Level} to use.
     * @param pos   The {@link BlockPos} to use.
     * @param state The {@link BlockState} to use.
     * @return The {@link BlockEntity} at the given position.
     */
    @Nullable
    BlockEntity getBlockEntity(Level level, BlockPos pos, BlockState state);

    /**
     * Returns the block outline {@link AABB} to render when the Magitech Goggles are equipped.
     * If this is a block larger than 1x1x1, only the part that actually controls the logic should return an {@link AABB}, all other parts should return null.
     *
     * @param level The {@link Level} to use.
     * @param pos   The {@link BlockPos} to use.
     * @param state The {@link BlockState} to use.
     * @return The block outline {@link AABB}.
     */
    @Nullable
    AABB getOutline(Level level, BlockPos pos, BlockState state);
}
