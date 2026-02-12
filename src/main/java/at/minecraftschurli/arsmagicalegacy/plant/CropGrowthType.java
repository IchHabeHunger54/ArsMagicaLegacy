package at.minecraftschurli.arsmagicalegacy.plant;

import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.List;

public record CropGrowthType() implements GrowthType {
    public static final MapCodec<CropGrowthType> CODEC = MapCodec.unit(CropGrowthType::new);

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canGrow(GrowthContext context) {
        BlockState state = context.state();
        return !canHarvest(context) && state.getBlock() instanceof BonemealableBlock block && block.isValidBonemealTarget(context.level(), context.pos(), state);
    }

    @Override
    public void grow(GrowthContext context) {
        ServerLevel level = context.level();
        BlockState state = context.state();
        if (state.getBlock() instanceof BonemealableBlock block) {
            block.performBonemeal(level, level.getRandom(), context.pos(), state);
        } else {
            increaseAge(context);
        }
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        return context.plant().harvestStates().containsKey(context.state());
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context) {
        ServerPlayer player = context.player();
        ServerLevel level = context.level();
        BlockPos pos = context.pos();
        BlockState state = context.state();
        return AMUtil.cancelDestroyBlock(level, pos, state, player) ? List.of() : Block.getDrops(state, level, pos, level.getBlockEntity(pos), player, ItemStack.EMPTY);
    }

    @Override
    public boolean canReplant(GrowthContext context) {
        return !context.plant().seed().isEmpty();
    }

    @Override
    public void replant(GrowthContext context) {
        context.level().setBlockAndUpdate(context.pos(), context.plant().harvestStates().get(context.state()));
    }
}
