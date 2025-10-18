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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public enum Part implements StringRepresentableEnum {
        LOWER, MIDDLE, UPPER
    }
}
