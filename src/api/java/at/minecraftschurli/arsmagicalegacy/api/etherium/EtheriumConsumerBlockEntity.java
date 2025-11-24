package at.minecraftschurli.arsmagicalegacy.api.etherium;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import net.minecraft.core.BlockPos;

import java.util.SequencedSet;

/**
 * Represents a system, usually a block entity, that consumes etherium. Using a Crystal Wrench on a block
 * will check its block entity for an instance of this class, and the block for {@link AMTags.Blocks#ETHERIUM_CONSUMERS}.
 */
public interface EtheriumConsumerBlockEntity {
    /**
     * @return A set of bound {@link BlockPos}es to pull from.
     */
    SequencedSet<BlockPos> getBoundPositions();

    /**
     * Adds a {@link BlockPos} to pull from.
     *
     * @param pos The {@link BlockPos} to add.
     */
    void addPosition(BlockPos pos);

    /**
     * Removes a {@link BlockPos} to pull from.
     *
     * @param pos The {@link BlockPos} to remove.
     */
    void removePosition(BlockPos pos);
}
