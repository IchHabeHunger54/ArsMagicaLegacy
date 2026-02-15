package at.minecraftschurli.arsmagicalegacy.api.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.api.plant.Plant;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

/**
 * Data provider for {@link Plant}s. Override {@link PlantProvider#generate(HolderLookup.Provider)} to generate your entries,
 * and use {@link PlantProvider#builder(String, GrowthType, RuleTest)} to create a new {@link PlantBuilder}.
 */
public abstract class PlantProvider extends AbstractDataProvider<Plant, PlantBuilder> {
    /**
     * @param output         The {@link PackOutput} to use. Get this from {@link GatherDataEvent}.
     * @param lookupProvider The lookup {@link CompletableFuture} to use. Get this from {@link GatherDataEvent}.
     * @param modId          Your mod id.
     */
    public PlantProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(PackOutput.Target.DATA_PACK, ArsMagicaApi.MOD_ID + "/plant", "Plant", Plant.CODEC, output, lookupProvider, modId);
    }

    /**
     * Creates and adds a new {@link PlantBuilder}.
     *
     * @param name       The name of the plant.
     * @param growthType The {@link GrowthType} of the plant.
     * @param allStates  A {@link RuleTest} for all states of the plant.
     * @return The new {@link PlantBuilder}.
     */
    public PlantBuilder builder(String name, GrowthType growthType, RuleTest allStates) {
        PlantBuilder builder = new PlantBuilder(ResourceLocation.fromNamespaceAndPath(modId, name), growthType, allStates);
        add(builder);
        return builder;
    }
}
