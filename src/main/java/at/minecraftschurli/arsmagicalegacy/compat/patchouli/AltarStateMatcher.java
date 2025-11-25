package at.minecraftschurli.arsmagicalegacy.compat.patchouli;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

public class AltarStateMatcher implements IStateMatcher {
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    public AltarStateMatcher() {
        predicate = (level, pos, state) -> AMRegistries.altarCapMaterials(level instanceof Level l ? l.registryAccess() : AMRegistries.registryAccess())
            .stream()
            .anyMatch(material -> state.is(material.block()));
    }

    @Override
    public BlockState getDisplayedState(long ticks) {
        AltarMaterial material = AMUtil.getByTick(AMRegistries.altarMaterials()
            .stream()
            .toArray(AltarMaterial[]::new), (int) ticks / 20);
        return material == null ? Blocks.AIR.defaultBlockState() : material.block().defaultBlockState();
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
