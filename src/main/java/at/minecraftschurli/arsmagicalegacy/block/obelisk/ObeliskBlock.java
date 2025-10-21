package at.minecraftschurli.arsmagicalegacy.block.obelisk;

import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.util.StringRepresentableEnum;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class ObeliskBlock extends AbstractFurnaceBlock {
    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    private static final MapCodec<ObeliskBlock> CODEC = simpleCodec(ObeliskBlock::new);
    private static final BlockEntityTicker<?> TICKER = (level, pos, state, blockEntity) -> {
        if (blockEntity instanceof ObeliskBlockEntity obelisk) {
            obelisk.tick(level, pos, state);
        }
    };

    public ObeliskBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(PART, Part.LOWER));
    }

    @Nullable
    public static ObeliskBlockEntity getBlockEntity(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.is(AMBlocks.OBELISK)) return null;
        pos = switch (state.getValue(PART)) {
            case LOWER -> pos;
            case MIDDLE -> pos.below();
            case UPPER -> pos.below(2);
        };
        return level.getBlockState(pos).is(AMBlocks.OBELISK) && level.getBlockState(pos).getValue(PART) == Part.LOWER && level.getBlockEntity(pos) instanceof ObeliskBlockEntity blockEntity ? blockEntity : null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PART);
    }

    @Override
    protected MapCodec<? extends AbstractFurnaceBlock> codec() {
        return CODEC;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PART) == Part.LOWER ? new ObeliskBlockEntity(pos, state) : null;
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        ObeliskBlockEntity blockEntity = getBlockEntity(level, pos);
        if (blockEntity != null) {
            player.openMenu(blockEntity, buf -> buf.writeBlockPos(pos));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == AMBlockEntities.OBELISK.get() && state.getValue(PART) == Part.LOWER ? (BlockEntityTicker<T>) TICKER : null;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        for (int i = 0; i <= 2; i++) {
            BlockPos pos = context.getClickedPos().above(i);
            if (level.isOutsideBuildHeight(pos) || !level.getBlockState(pos).canBeReplaced(context)) return null;
        }
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (state.getValue(PART) != Part.LOWER) return;
        level.setBlockAndUpdate(pos.above(), state.setValue(PART, Part.MIDDLE));
        level.setBlockAndUpdate(pos.above(2), state.setValue(PART, Part.UPPER));
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        Part part = state.getValue(PART);
        BlockPos pos0 = switch (part) {
            case UPPER -> pos.below();
            case MIDDLE -> pos.above();
            case LOWER -> pos.above(2);
        };
        BlockPos pos1 = switch (part) {
            case UPPER -> pos.below(2);
            case MIDDLE -> pos.below();
            case LOWER -> pos.above();
        };
        destroy(level, player, pos0);
        destroy(level, player, pos1);
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    private void destroy(Level level, Player player, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.is(this)) return;
        level.removeBlock(pos, false);
        spawnDestroyParticles(level, player, pos, state);
        level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(player, state));
        if (!level.isClientSide()) {
            dropResources(state, level, pos, level.getBlockEntity(pos));
        }
    }

    public enum Part implements StringRepresentableEnum {
        LOWER, MIDDLE, UPPER
    }
}
