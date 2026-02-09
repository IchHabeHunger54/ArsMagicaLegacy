package at.minecraftschurli.arsmagicalegacy.api.plant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record Plant(List<ICondition> conditions, GrowthType growthType, ItemStack seed, ItemStack crop, RuleTest soil, Set<Direction> directions, RuleTest allStates, Map<BlockState, BlockState> harvestStates) {
    public static final Codec<Plant> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ICondition.LIST_CODEC.optionalFieldOf("conditions", List.of()).forGetter(Plant::conditions),
        GrowthType.CODEC.fieldOf("growth_type").forGetter(Plant::growthType),
        ItemStack.OPTIONAL_CODEC.optionalFieldOf("seed", ItemStack.EMPTY).forGetter(Plant::seed),
        ItemStack.OPTIONAL_CODEC.optionalFieldOf("crop", ItemStack.EMPTY).forGetter(Plant::crop),
        RuleTest.CODEC.fieldOf("soil").forGetter(Plant::soil),
        Direction.CODEC.listOf().xmap(Set::copyOf, List::copyOf).fieldOf("directions").forGetter(Plant::directions),
        RuleTest.CODEC.fieldOf("all_states").forGetter(Plant::allStates),
        HarvestState.CODEC.listOf().<Map<BlockState, BlockState>>xmap(
            list -> Util.make(new HashMap<>(), map -> list.forEach(pair -> map.put(pair.from(), pair.to()))),
            map -> map.entrySet().stream().map(entry -> new HarvestState(entry.getKey(), entry.getValue())).toList()
        ).fieldOf("harvest_states").forGetter(Plant::harvestStates)
    ).apply(inst, Plant::new));

    public GrowthContext createContext(ServerPlayer player, ServerLevel level, BlockPos pos, BlockState state) {
        return new GrowthContext(this, player, level, pos, state);
    }
}
