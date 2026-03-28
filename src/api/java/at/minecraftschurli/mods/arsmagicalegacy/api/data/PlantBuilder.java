package at.minecraftschurli.mods.arsmagicalegacy.api.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.Plant;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Builder class for {@link Plant}s, for use in {@link PlantProvider}. Get an instance via {@link PlantProvider#builder(String, GrowthType, RuleTest)}.
 */
public class PlantBuilder extends AbstractDataProvider.Builder<Plant> {
    private final GrowthType growthType;
    private final RuleTest allStates;
    private @Nullable ItemStackTemplate seed = null;
    private @Nullable ItemStackTemplate crop = null;
    private @Nullable ItemStackTemplate tool = null;

    /**
     * @param id         The id of the plant.
     * @param growthType The {@link GrowthType} of the plant.
     * @param allStates  A {@link RuleTest} for all states of the plant.
     */
    public PlantBuilder(Identifier id, GrowthType growthType, RuleTest allStates) {
        super(id);
        this.growthType = growthType;
        this.allStates = allStates;
    }

    /**
     * @param seed The seed {@link Item} to set.
     * @return This builder, for chaining.
     */
    public PlantBuilder seed(Item seed) {
        return seed(new ItemStackTemplate(seed));
    }

    /**
     * @param seed The seed {@link ItemStack} to set.
     * @return This builder, for chaining.
     */
    public PlantBuilder seed(ItemStackTemplate seed) {
        this.seed = seed;
        return this;
    }

    /**
     * @param crop The crop {@link Item} to set.
     * @return This builder, for chaining.
     */
    public PlantBuilder crop(Item crop) {
        return crop(new ItemStackTemplate(crop));
    }

    /**
     * @param crop The crop {@link ItemStack} to set.
     * @return This builder, for chaining.
     */
    public PlantBuilder crop(ItemStackTemplate crop) {
        this.crop = crop;
        return this;
    }

    /**
     * @param tool The tool {@link Item} to set.
     * @return This builder, for chaining.
     */
    public PlantBuilder tool(Item tool) {
        return tool(new ItemStackTemplate(tool));
    }

    /**
     * @param tool The tool {@link ItemStack} to set.
     * @return This builder, for chaining.
     */
    public PlantBuilder tool(ItemStackTemplate tool) {
        this.tool = tool;
        return this;
    }

    @Override
    public Plant build() {
        return new Plant(growthType, allStates, Optional.ofNullable(seed), Optional.ofNullable(crop), Optional.ofNullable(tool));
    }
}
