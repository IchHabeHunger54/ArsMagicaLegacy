package at.minecraftschurli.arsmagicalegacy.compat.patchouli;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

public class AltarStairStateMatcher implements IStateMatcher {
    private final Direction direction;
    private final Half half;
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    public AltarStairStateMatcher(Direction direction, Half half) {
        this.direction = direction;
        this.half = half;
        this.predicate = (level, pos, state) -> AMUtil.registryAccess(level)
            .registryOrThrow(AMRegistryKeys.ALTAR_MATERIAL)
            .stream()
            .anyMatch(material -> state.is(material.stair()) && state.getValue(StairBlock.FACING) == direction && state.getValue(StairBlock.HALF) == half);
    }

    @Override
    public BlockState getDisplayedState(long ticks) {
        AltarMaterial material = AMUtil.getByTick(AMUtil.registryAccess().registryOrThrow(AMRegistryKeys.ALTAR_MATERIAL).stream().toArray(AltarMaterial[]::new), (int) ticks / 20);
        return material == null ? Blocks.AIR.defaultBlockState() : material.stair().defaultBlockState().setValue(StairBlock.FACING, direction).setValue(StairBlock.HALF, half);
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
