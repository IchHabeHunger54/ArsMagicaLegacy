package at.minecraftschurli.arsmagicalegacy.plant;

import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public record BushGrowthType() implements GrowthType {
    public static final MapCodec<BushGrowthType> CODEC = MapCodec.unit(BushGrowthType::new);

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
        BlockPos pos = context.pos();
        Block.beginCapturingDrops();
        context.state().useWithoutItem(context.level(), context.player(), new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, true));
        return Block.stopCapturingDrops()
            .stream()
            .map(ItemEntity::getItem)
            .toList();
    }

    @Override
    public boolean canReplant(GrowthContext context) {
        return false;
    }

    @Override
    public void replant(GrowthContext context) {
    }
}
