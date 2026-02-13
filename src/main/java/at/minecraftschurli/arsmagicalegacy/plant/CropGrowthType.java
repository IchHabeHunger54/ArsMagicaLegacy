package at.minecraftschurli.arsmagicalegacy.plant;

import at.minecraftschurli.arsmagicalegacy.api.plant.BonemealableGrowthType;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public record CropGrowthType() implements BonemealableGrowthType {
    public static final MapCodec<CropGrowthType> CODEC = MapCodec.unit(CropGrowthType::new);

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
        ServerPlayer player = context.player();
        ServerLevel level = context.level();
        BlockPos pos = context.pos();
        BlockState state = context.state();
        return AMUtil.cancelDestroyBlock(level, pos, state, player) ? List.of() : Block.getDrops(state, level, pos, level.getBlockEntity(pos), player, ItemStack.EMPTY);
    }

    @Override
    public boolean canReplant(GrowthContext context) {
        return !context.plant().seed().isEmpty();
    }

    @Override
    public void replant(GrowthContext context) {
        context.level().setBlockAndUpdate(context.pos(), context.plant().harvestStates().get(context.state()));
    }
}
