package at.minecraftschurli.arsmagicalegacy.plant;

import at.minecraftschurli.arsmagicalegacy.api.plant.BonemealableGrowthType;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.api.plant.HarvestState;
import at.minecraftschurli.arsmagicalegacy.api.plant.ReplantableGrowthType;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public record CropGrowthType(List<HarvestState> harvestStates) implements BonemealableGrowthType, ReplantableGrowthType {
    public static final MapCodec<CropGrowthType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        HarvestState.CODEC.listOf().fieldOf("harvest_states").forGetter(CropGrowthType::harvestStates)
    ).apply(inst, CropGrowthType::new));

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        BlockState state = context.state();
        return harvestStates.stream().map(HarvestState::from).anyMatch(e -> e == state);
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context) {
        return AMUtil.destroyBlockAndGetDrops(context.level(), context.pos(), context.state(), context.player(), context.plant().tool().copy());
    }

    @Override
    public boolean canReplant(GrowthContext context) {
        return !context.plant().seed().isEmpty();
    }

    @Override
    public void replant(GrowthContext context) {
        BlockState state = context.state();
        harvestStates.stream()
            .filter(e -> e.from() == state)
            .findFirst()
            .ifPresent(e -> context.level().setBlockAndUpdate(context.pos(), e.to()));
    }
}
