package at.minecraftschurli.arsmagicalegacy.block.obelisk;

import at.minecraftschurli.arsmagicalegacy.api.etherium.ObeliskFuel;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.util.StringRepresentableEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ObeliskBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    private static final BlockEntityTicker<?> TICKER = (level, pos, state, blockEntity) -> {
        if (blockEntity instanceof ObeliskBlockEntity obelisk) {
            obelisk.tick(level, pos, state);
        }
    };

    public ObeliskBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false).setValue(PART, Part.LOWER));
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
        builder.add(FACING, LIT, PART);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        for (int i = 0; i <= 2; i++) {
            BlockPos pos = context.getClickedPos().above(i);
            if (level.isOutsideBuildHeight(pos) || !level.getBlockState(pos).canBeReplaced(context)) return null;
        }
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
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
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.is(newState.getBlock())) return;
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof ObeliskBlockEntity obelisk) {
            if (level instanceof ServerLevel) {
                Containers.dropContents(level, pos, obelisk);
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
            level.updateNeighbourForOutputSignal(pos, this);
        } else {
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!ObeliskFuel.isFuel(stack)) return ItemInteractionResult.CONSUME;
        ObeliskBlockEntity blockEntity = getBlockEntity(level, pos);
        if (blockEntity == null) return ItemInteractionResult.CONSUME;
        ItemStack slotStack = blockEntity.getItem(0).copy();
        if (!slotStack.isEmpty() && !ItemStack.isSameItemSameComponents(slotStack, stack)) return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        if (slotStack.isEmpty()) {
            blockEntity.setItem(0, stack.copyWithCount(1));
        } else {
            slotStack.grow(1);
            blockEntity.setItem(0, slotStack);
        }
        stack.shrink(1);
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromContainer(getBlockEntity(level, pos));
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PART) == Part.LOWER ? new ObeliskBlockEntity(pos, state) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == AMBlockEntities.OBELISK.get() && state.getValue(PART) == Part.LOWER ? (BlockEntityTicker<T>) TICKER : null;
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
