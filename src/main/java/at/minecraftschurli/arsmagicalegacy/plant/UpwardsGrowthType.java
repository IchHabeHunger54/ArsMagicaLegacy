package at.minecraftschurli.arsmagicalegacy.plant;

import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record UpwardsGrowthType(int minHeight, int maxHeight, Optional<BlockState> topState) implements GrowthType {
    public static final MapCodec<UpwardsGrowthType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        ExtraCodecs.POSITIVE_INT.optionalFieldOf("min_height", 1).forGetter(UpwardsGrowthType::minHeight),
        ExtraCodecs.POSITIVE_INT.fieldOf("max_height").forGetter(UpwardsGrowthType::maxHeight),
        BlockState.CODEC.optionalFieldOf("top_state").forGetter(UpwardsGrowthType::topState)
    ).apply(inst, UpwardsGrowthType::new));

    public UpwardsGrowthType(int minHeight, int maxHeight) {
        this(minHeight, maxHeight, Optional.empty());
    }

    public UpwardsGrowthType(int minHeight, int maxHeight, BlockState topState) {
        this(minHeight, maxHeight, Optional.of(topState));
    }

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canGrow(GrowthContext context) {
        List<BlockPos> column = getColumn(context);
        if (column.size() >= maxHeight) return false;
        return topState.isEmpty() || context.level().getBlockState(column.getLast()) != topState.get();
    }

    @Override
    public void grow(GrowthContext context) {
        BlockState state = context.state();
        ServerLevel level = context.level();
        if (state.getBlock() instanceof BonemealableBlock block) {
            block.performBonemeal(level, level.getRandom(), context.pos(), state);
        } else {
            level.setBlockAndUpdate(getColumn(context).getLast().above(), state);
        }
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        return getColumn(context).size() > minHeight;
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context) {
        List<BlockPos> column = getColumn(context);
        List<ItemStack> drops = new ArrayList<>();
        ServerLevel level = context.level();
        while (column.size() > minHeight) {
            BlockPos last = column.getLast();
            drops.addAll(AMUtil.destroyBlockAndGetDrops(level, last, level.getBlockState(last), context.player()));
            column.removeLast();
        }
        return drops;
    }

    @Override
    public boolean canReplant(GrowthContext context) {
        return false;
    }

    @Override
    public void replant(GrowthContext context) {
    }

    private List<BlockPos> getColumn(GrowthContext context) {
        ServerLevel level = context.level();
        BlockPos originalPos = context.pos();
        Block block = context.state().getBlock();
        List<BlockPos> list = new ArrayList<>();
        list.add(originalPos);
        BlockPos pos = originalPos.below();
        while (level.getBlockState(pos).is(block)) {
            list.addFirst(pos);
            pos = pos.below();
        }
        pos = originalPos.above();
        while (level.getBlockState(pos).is(block)) {
            list.add(pos);
            pos = pos.above();
        }
        return list;
    }
}
