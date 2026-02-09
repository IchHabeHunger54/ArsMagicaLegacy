package at.minecraftschurli.arsmagicalegacy.api.data;

import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.api.plant.Plant;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlantBuilder extends AbstractDataProvider.Builder<Plant> {
    private final List<ICondition> conditions = new ArrayList<>();
    private final GrowthType growthType;
    private final ItemStack seed;
    private final ItemStack crop;
    private final RuleTest soil;
    private final Set<Direction> directions;
    private final RuleTest allStates;
    private final Map<BlockState, BlockState> harvestStates = new HashMap<>();

    public PlantBuilder(ResourceLocation id, GrowthType growthType, ItemStack seed, ItemStack crop, RuleTest soil, RuleTest allStates, Direction... directions) {
        super(id);
        this.growthType = growthType;
        this.seed = seed;
        this.crop = crop;
        this.soil = soil;
        this.directions = Set.of(directions);
        this.allStates = allStates;
    }

    public PlantBuilder addCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    public PlantBuilder harvest(BlockState from, BlockState to) {
        harvestStates.put(from, to);
        return this;
    }

    @Override
    public Plant build() {
        return new Plant(conditions, growthType, seed, crop, soil, directions, allStates, harvestStates);
    }
}
