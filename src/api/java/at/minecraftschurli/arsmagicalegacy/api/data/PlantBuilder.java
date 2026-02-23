package at.minecraftschurli.arsmagicalegacy.api.data;

import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.api.plant.Plant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

/**
 * Builder class for {@link Plant}s, for use in {@link PlantProvider}. Get an instance via {@link PlantProvider#builder(String, GrowthType, RuleTest)}.
 */
public class PlantBuilder extends AbstractDataProvider.Builder<Plant> {
    private final GrowthType growthType;
    private final RuleTest allStates;
    private ItemStack seed = ItemStack.EMPTY;
    private ItemStack crop = ItemStack.EMPTY;
    private ItemStack tool = ItemStack.EMPTY;

    /**
     * @param id         The id of the plant.
     * @param growthType The {@link GrowthType} of the plant.
     * @param allStates  A {@link RuleTest} for all states of the plant.
     */
    public PlantBuilder(ResourceLocation id, GrowthType growthType, RuleTest allStates) {
        super(id);
        this.growthType = growthType;
        this.allStates = allStates;
    }

    /**
     * @param seed The seed {@link Item} to set.
     * @return This builder, for chaining.
     */
    public PlantBuilder seed(Item seed) {
        return seed(new ItemStack(seed));
    }

    /**
     * @param seed The seed {@link ItemStack} to set.
     * @return This builder, for chaining.
     */
    public PlantBuilder seed(ItemStack seed) {
        this.seed = seed;
        return this;
    }

    /**
     * @param crop The crop {@link Item} to set.
     * @return This builder, for chaining.
     */
    public PlantBuilder crop(Item crop) {
        return crop(new ItemStack(crop));
    }

    /**
     * @param crop The crop {@link ItemStack} to set.
     * @return This builder, for chaining.
     */
    public PlantBuilder crop(ItemStack crop) {
        this.crop = crop;
        return this;
    }

    /**
     * @param tool The tool {@link Item} to set.
     * @return This builder, for chaining.
     */
    public PlantBuilder tool(Item tool) {
        return tool(new ItemStack(tool));
    }

    /**
     * @param tool The tool {@link ItemStack} to set.
     * @return This builder, for chaining.
     */
    public PlantBuilder tool(ItemStack tool) {
        this.tool = tool;
        return this;
    }

    @Override
    public Plant build() {
        return new Plant(growthType, allStates, seed, crop, tool);
    }
}
