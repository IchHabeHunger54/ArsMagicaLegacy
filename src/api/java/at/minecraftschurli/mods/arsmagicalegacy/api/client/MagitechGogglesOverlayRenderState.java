package at.minecraftschurli.mods.arsmagicalegacy.api.client;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMCapabilities;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

/**
 * Represents a render state for the Magitech Goggles overlay. Also contains methods for extracting and submitting.
 */
public interface MagitechGogglesOverlayRenderState {
    /**
     * Clears the internal state of the render state.
     */
    void clear();

    /**
     * Extracts the render state from the given {@link BlockEntity}. This requires the {@link BlockEntity} to expose the {@link AMCapabilities#BLOCK_ETHERIUM} capability.
     *
     * @param blockEntity The {@link BlockEntity} to extract from.
     */
    void extract(BlockEntity blockEntity);

    /**
     * Stores a line between two {@link BlockPos}es in the render state.
     *
     * @param pos1  The first {@link BlockPos}.
     * @param pos2  The second {@link BlockPos}.
     * @param width The width of the line.
     * @param color The color of the line.
     */
    void extractLine(BlockPos pos1, BlockPos pos2, float width, int color);

    /**
     * Stores an {@link AABB} in the render state.
     *
     * @param aabb  The {@link AABB} to store.
     * @param width The width of the line.
     * @param color The color of the line.
     */
    void extractBox(AABB aabb, float width, int color);

    /**
     * Submits the state to the given {@link SubmitNodeCollector}.
     *
     * @param stack     The {@link PoseStack} to use.
     * @param collector The {@link SubmitNodeCollector} to submit to.
     */
    void submit(PoseStack stack, SubmitNodeCollector collector);
}
