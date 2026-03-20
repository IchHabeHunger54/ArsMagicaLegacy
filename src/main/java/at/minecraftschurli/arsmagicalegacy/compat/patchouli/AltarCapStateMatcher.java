/* TODO patchouli
package at.minecraftschurli.arsmagicalegacy.compat.patchouli;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

public class AltarCapStateMatcher implements IStateMatcher {
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    public AltarCapStateMatcher() {
        predicate = (level, pos, state) -> AMRegistries.altarCapMaterials(level instanceof Level l ? l.registryAccess() : AMRegistries.registryAccess(false))
            .stream()
            .anyMatch(material -> state.is(material.block()));
    }

    @Override
    public BlockState getDisplayedState(long ticks) {
        AltarCapMaterial material = AMUtil.getByTick(AMRegistries.altarCapMaterials(false)
            .stream()
            .toArray(AltarCapMaterial[]::new), (int) ticks / 20);
        return material == null ? Blocks.AIR.defaultBlockState() : material.block().defaultBlockState();
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
*/
