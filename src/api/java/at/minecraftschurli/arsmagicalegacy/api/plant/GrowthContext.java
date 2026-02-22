package at.minecraftschurli.arsmagicalegacy.api.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A context record used for most methods in {@link GrowthType}.
 *
 * @param plant  The {@link Plant} to use.
 * @param player The {@link ServerPlayer} to use.
 * @param level  The {@link ServerLevel} to use.
 * @param pos    The {@link BlockPos} to use.
 * @param state  The {@link BlockState} to use.
 * @param tool   The {@link ItemStack} to use.
 */
public record GrowthContext(Plant plant, ServerPlayer player, ServerLevel level, BlockPos pos, BlockState state, ItemStack tool) {
}
