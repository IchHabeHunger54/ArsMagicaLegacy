package at.minecraftschurli.arsmagicalegacy.plant;

import at.minecraftschurli.arsmagicalegacy.api.plant.BonemealableGrowthType;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.api.plant.Plant;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record HangingBushGrowthType(List<BlockState> harvestStates, int minHeight, int maxHeight, Optional<BlockState> bottomState) implements BonemealableGrowthType {
    public static final MapCodec<HangingBushGrowthType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        BlockState.CODEC.listOf().fieldOf("harvest_states").forGetter(HangingBushGrowthType::harvestStates),
        ExtraCodecs.POSITIVE_INT.optionalFieldOf("min_height", 1).forGetter(HangingBushGrowthType::minHeight),
        ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("max_height", 0).forGetter(HangingBushGrowthType::maxHeight),
        BlockState.CODEC.optionalFieldOf("bottom_state").forGetter(HangingBushGrowthType::bottomState)
    ).apply(inst, HangingBushGrowthType::new));

    public HangingBushGrowthType(List<BlockState> harvestStates, int minHeight, int maxHeight) {
        this(harvestStates, minHeight, maxHeight, Optional.empty());
    }

    public HangingBushGrowthType(List<BlockState> harvestStates, int minHeight, int maxHeight, BlockState bottomState) {
        this(harvestStates, minHeight, maxHeight, Optional.of(bottomState));
    }

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canGrow(GrowthContext context) {
        Plant plant = context.plant();
        ServerPlayer player = context.player();
        ServerLevel level = context.level();
        List<BlockPos> column = getColumn(context);
        for (BlockPos pos : column) {
            if (BonemealableGrowthType.super.canGrow(new GrowthContext(plant, player, level, pos, level.getBlockState(pos)))) return true;
        }
        if (maxHeight > 0 && column.size() >= maxHeight) return false;
        BlockPos last = column.getLast();
        if (!level.getBlockState(last.below()).canBeReplaced()) return false;
        return bottomState.isEmpty() || level.getBlockState(column.getLast()) != bottomState.get();
    }

    @Override
    public void grow(GrowthContext context) {
        BlockState state = context.state();
        Plant plant = context.plant();
        ServerPlayer player = context.player();
        ServerLevel level = context.level();
        boolean bonemealed = false;
        List<BlockPos> column = getColumn(context);
        for (BlockPos pos : column) {
            BlockState current = level.getBlockState(pos);
            if (BonemealableGrowthType.super.canGrow(new GrowthContext(plant, player, level, pos, current)) && current.getBlock() instanceof BonemealableBlock block) {
                block.performBonemeal(level, level.getRandom(), pos, current);
                bonemealed = true;
            }
        }
        if (bonemealed) return;
        if (bottomState.isEmpty() || level.getBlockState(column.getLast()) != bottomState.get()) {
            level.setBlockAndUpdate(getColumn(context).getLast().below(), state);
        }
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        ServerLevel level = context.level();
        return getColumn(context).stream().anyMatch(pos -> {
            BlockState state = level.getBlockState(pos);
            return harvestStates.stream().anyMatch(e -> e == state);
        });
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context) {
        List<BlockPos> column = getColumn(context);
        ServerLevel level = context.level();
        ServerPlayer player = context.player();
        ItemStack tool = context.plant().tool();
        Block.beginCapturingDrops();
        while (column.size() > minHeight) {
            BlockPos lastPos = column.getLast();
            BlockState lastState = level.getBlockState(lastPos);
            BlockHitResult hitResult = new BlockHitResult(Vec3.atCenterOf(lastPos), Direction.UP, lastPos, true);
            if (tool.isEmpty()) {
                lastState.useWithoutItem(level, player, hitResult);
            } else {
                lastState.useItemOn(tool.copy(), level, player, InteractionHand.MAIN_HAND, hitResult);
            }
            column.removeLast();
        }
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

    private List<BlockPos> getColumn(GrowthContext context) {
        ServerLevel level = context.level();
        BlockPos originalPos = context.pos();
        Block block = context.state().getBlock();
        List<BlockPos> list = new ArrayList<>();
        list.add(originalPos);
        BlockPos pos = originalPos.above();
        while (level.getBlockState(pos).is(block)) {
            list.addFirst(pos);
            pos = pos.above();
        }
        pos = originalPos.below();
        while (level.getBlockState(pos).is(block)) {
            list.add(pos);
            pos = pos.below();
        }
        return list;
    }
}
