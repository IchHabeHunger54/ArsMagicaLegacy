package at.minecraftschurli.arsmagicalegacy.api.data;

import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.api.plant.Plant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SequencedMap;

/**
 * Builder class for {@link Plant}s, for use in {@link PlantProvider}. Get an instance via {@link PlantProvider#builder(String, GrowthType, ItemStack, ItemStack, RuleTest)}.
 */
public class PlantBuilder extends AbstractDataProvider.Builder<Plant> {
    private final List<ICondition> conditions = new ArrayList<>();
    private final GrowthType growthType;
    private final ItemStack seed;
    private final ItemStack crop;
    private final RuleTest allStates;
    private final SequencedMap<BlockState, BlockState> harvestStates = new LinkedHashMap<>();

    /**
     * @param id         The id of the plant.
     * @param growthType The {@link GrowthType} of the plant.
     * @param seed       The seed {@link ItemStack} of the plant.
     * @param crop       The crop {@link ItemStack} of the plant.
     * @param allStates  A {@link RuleTest} for all states of the plant.
     */
    public PlantBuilder(ResourceLocation id, GrowthType growthType, ItemStack seed, ItemStack crop, RuleTest allStates) {
        super(id);
        this.growthType = growthType;
        this.seed = seed;
        this.crop = crop;
        this.allStates = allStates;
    }

    /**
     * Adds a {@link ICondition} to the builder.
     *
     * @param condition The {@link ICondition} to add.
     * @return This builder, for chaining.
     */
    public PlantBuilder addCondition(ICondition condition) {
        conditions.add(condition);
        return this;
    }

    /**
     * Adds a harvesting transition to the builder.
     *
     * @param from The old {@link BlockState}.
     * @param to   The new {@link BlockState}.
     * @return This builder, for chaining.
     */
    public PlantBuilder harvest(BlockState from, BlockState to) {
        harvestStates.put(from, to);
        return this;
    }

    @Override
    public Plant build() {
        return new Plant(conditions, growthType, seed, crop, allStates, harvestStates);
    }
}
