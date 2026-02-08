package at.minecraftschurli.arsmagicalegacy.plant;

import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public record CropGrowthType() implements GrowthType {
    public static final MapCodec<CropGrowthType> CODEC = MapCodec.unit(CropGrowthType::new);

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canGrow(Plant plant, Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return !canHarvest(plant, level, pos) && state.getBlock() instanceof BonemealableBlock block && block.isValidBonemealTarget(level, pos, state);
    }

    @Override
    public void grow(Plant plant, Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof BonemealableBlock block && level instanceof ServerLevel serverLevel) {
            block.performBonemeal(serverLevel, serverLevel.getRandom(), pos, state);
        }
    }

    @Override
    public boolean canHarvest(Plant plant, Level level, BlockPos pos) {
        return plant.harvestStates().containsKey(level.getBlockState(pos));
    }

    @Override
    public List<ItemStack> harvest(Plant plant, ServerPlayer player, ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return AMUtil.cancelDestroyBlock(level, pos, state, player) ? List.of() : Block.getDrops(state, level, pos, level.getBlockEntity(pos), player, ItemStack.EMPTY);
    }
}
