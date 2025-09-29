package at.minecraftschurli.arsmagicalegacy.data;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.material.FluidState;

public class SunstoneOreFeature extends Feature<OreConfiguration> {
    public SunstoneOreFeature() {
        super(OreConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<OreConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        OreConfiguration config = context.config();
        BlockPos origin = context.origin();
        int i = random.nextInt(config.size + 1);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int j = 0; j < i; j++) {
            int magnitude = Math.min(j, 7);
            pos.setWithOffset(origin, getRandomRelativePlacement(random, magnitude), getRandomRelativePlacement(random, magnitude), getRandomRelativePlacement(random, magnitude));
            BlockState state = level.getBlockState(pos);
            for (OreConfiguration.TargetBlockState target : config.targetStates) {
                if (target.target.test(state, random) && checkNeighbors(level::getBlockState, pos, s -> {
                    FluidState fluidState = s.getFluidState();
                    return fluidState.is(FluidTags.LAVA) && fluidState.isSource();
                })) {
                    level.setBlock(pos, target.state, 2);
                    break;
                }
            }
        }
        return true;
    }

    private static int getRandomRelativePlacement(RandomSource random, int magnitude) {
        return Math.round((random.nextFloat() - random.nextFloat()) * (float) magnitude);
    }
}
