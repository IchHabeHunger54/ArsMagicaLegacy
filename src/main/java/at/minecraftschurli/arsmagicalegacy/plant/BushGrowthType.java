package at.minecraftschurli.arsmagicalegacy.plant;

import at.minecraftschurli.arsmagicalegacy.api.plant.BonemealableGrowthType;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public record BushGrowthType() implements BonemealableGrowthType {
    public static final MapCodec<BushGrowthType> CODEC = MapCodec.unit(BushGrowthType::new);

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        return context.plant().harvestStates().containsKey(context.state());
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context) {
        BlockPos pos = context.pos();
        Block.beginCapturingDrops();
        context.state().useWithoutItem(context.level(), context.player(), new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, true));
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
}
