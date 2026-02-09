package at.minecraftschurli.arsmagicalegacy.api.plant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a plant. Plants are used by certain mod mechanics, such as the Harvest component or Dryads growing certain crops.
 *
 * @param conditions    A list of {@link ICondition} to check before fully loading the plant.
 * @param growthType    The {@link GrowthType} to use. This dictates most of the plant's logic.
 * @param seed          The seed {@link ItemStack} to use. This is used e.g. for replanting.
 * @param crop          The crop {@link ItemStack} to use. This is used e.g. for harvest bonuses.
 * @param soil          A {@link RuleTest} for the soil the plant can be placed on.
 * @param directions    A set of {@link Direction}s to perform the soil check for.
 *                      Most crops will use {@link Direction#DOWN}, hanging crops will use {@link Direction#UP},
 *                      cocoa beans will use the horizontal directions, and vines will use all directions.
 * @param allStates     A {@link RuleTest} for all states of the crop.
 * @param harvestStates A map of harvest-ready {@link BlockState}s to post-harvest {@link BlockState}s.
 */
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

    /**
     * @param player The {@link ServerPlayer} to use.
     * @param level  The {@link ServerLevel} to use.
     * @param pos    The {@link BlockPos} to use.
     * @param state  The {@link BlockState} to use.
     * @return A new {@link GrowthContext}.
     */
    public GrowthContext createContext(ServerPlayer player, ServerLevel level, BlockPos pos, BlockState state) {
        return new GrowthContext(this, player, level, pos, state);
    }
}
