package at.minecraftschurli.arsmagicalegacy.api.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;

public record GrowthContext(Plant plant, ServerPlayer player, ServerLevel level, BlockPos pos, BlockState state) {
}
