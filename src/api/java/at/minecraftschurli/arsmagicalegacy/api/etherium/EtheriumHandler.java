package at.minecraftschurli.arsmagicalegacy.api.etherium;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.SequencedSet;

/**
 * Represents an etherium capability handler.
 */
public interface EtheriumHandler {
    /**
     * @return The {@link EtheriumType}s supported by the handler.
     */
    List<Holder<EtheriumType>> getEtheriumTypes();

    /**
     * @param type The {@link EtheriumType} to get the stored amount for.
     * @return The amount of the {@link EtheriumType} stored in the handler.
     */
    int getAmount(Holder<EtheriumType> type);

    /**
     * @param type The {@link EtheriumType} to get the maximum amount for.
     * @return The maximum amount of the {@link EtheriumType} that can be stored in the handler.
     */
    int getMaxAmount(Holder<EtheriumType> type);

    /**
     * Sets the stored amount for the given {@link EtheriumType}.
     *
     * @param type   The {@link EtheriumType} to set the stored amount for.
     * @param amount The stored amount to set.
     */
    void setAmount(Holder<EtheriumType> type, int amount);

    /**
     * Adds the given amount to the stored amount for the given {@link EtheriumType}.
     * <p>
     * Returns the amount left to be added. For example, if all etherium was successfully added,
     * 0 will be returned. If the handler is full, the amount will be returned.
     *
     * @param type   The {@link EtheriumType} to add the given amount for.
     * @param amount The amount to add.
     * @return The amount left to be added.
     */
    int addAmount(Holder<EtheriumType> type, int amount);

    /**
     * Subtracts the given amount to the stored amount for the given {@link EtheriumType}.
     * <p>
     * Returns the amount left to be subtracted. For example, if all etherium was successfully subtracted,
     * 0 will be returned. If the handler is full, the amount will be returned.
     *
     * @param type   The {@link EtheriumType} to subtract the given amount for.
     * @param amount The amount to subtract.
     * @return The amount left to be subtracted.
     */
    int subtractAmount(Holder<EtheriumType> type, int amount);

    /**
     * Returns the block outline {@link AABB} to render when the Magitech Goggles are equipped. If the handler is not in a block context, this method should return null.
     * If this is a block larger than 1x1x1, only the part that actually controls the logic should return an {@link AABB}, all other parts should return null.
     *
     * @param level The {@link Level} to use.
     * @param pos   The {@link BlockPos} to use.
     * @param state The {@link BlockState} to use.
     * @return The block outline {@link AABB}.
     */
    @Nullable
    AABB getOutline(Level level, BlockPos pos, BlockState state);

    /**
     * Returns the block outline color, in ARGB format.
     *
     * @param level The {@link Level} to use.
     * @param pos   The {@link BlockPos} to use.
     * @param state The {@link BlockState} to use.
     * @return The block outline color.
     */
    int getOutlineColor(Level level, BlockPos pos, BlockState state);

    /**
     * @return Whether the handler is allowed to have connections to other handlers.
     */
    boolean canHaveConnectedPositions();

    /**
     * @return A {@link SequencedSet} of connections to other handlers. If {@link #canHaveConnectedPositions()} returns false, this is expected to be always empty.
     */
    SequencedSet<BlockPos> getConnectedPositions();

    /**
     * Adds a {@link BlockPos} to connect. If {@link #canHaveConnectedPositions()} returns false, this method should do nothing.
     *
     * @param pos The {@link BlockPos} to add.
     */
    void addConnectedPosition(BlockPos pos);

    /**
     * Removes a {@link BlockPos} to connect. If {@link #canHaveConnectedPositions()} returns false, this method should do nothing.
     *
     * @param pos The {@link BlockPos} to remove.
     */
    void removeConnectedPosition(BlockPos pos);
}
