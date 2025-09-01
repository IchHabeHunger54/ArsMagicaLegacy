package at.minecraftschurli.arsmagicalegacy.compat.patchouli;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

public class AltarCapStateMatcher implements IStateMatcher {
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    public AltarCapStateMatcher() {
        this.predicate = (level, pos, state) -> AMUtil.registryAccess(level)
            .registryOrThrow(AMRegistryKeys.ALTAR_CAP_MATERIAL)
            .stream()
            .anyMatch(material -> state.is(material.block()));
    }

    @Override
    public BlockState getDisplayedState(long ticks) {
        AltarCapMaterial material = AMUtil.getByTick(AMUtil.registryAccess().registryOrThrow(AMRegistryKeys.ALTAR_CAP_MATERIAL).stream().toArray(AltarCapMaterial[]::new), (int) ticks);
        return material == null ? Blocks.AIR.defaultBlockState() : material.block().defaultBlockState();
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
