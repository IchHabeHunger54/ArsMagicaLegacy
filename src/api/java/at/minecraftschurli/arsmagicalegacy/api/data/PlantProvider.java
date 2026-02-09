package at.minecraftschurli.arsmagicalegacy.api.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.api.plant.Plant;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public abstract class PlantProvider extends AbstractDataProvider<Plant, PlantBuilder> {
    /**
     * @param output         The {@link PackOutput} to use. Get this from {@link GatherDataEvent}.
     * @param lookupProvider The lookup {@link CompletableFuture} to use. Get this from {@link GatherDataEvent}.
     * @param modId          Your mod id.
     */
    public PlantProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(PackOutput.Target.DATA_PACK, ArsMagicaApi.MOD_ID + "/plant", "Plant", Plant.CODEC, output, lookupProvider, modId);
    }

    public PlantBuilder builder(String name, GrowthType growthType, ItemStack seed, ItemStack crop, RuleTest soil, RuleTest allStates, Direction... directions) {
        PlantBuilder builder = new PlantBuilder(ResourceLocation.fromNamespaceAndPath(modId, name), growthType, seed, crop, soil, allStates, directions);
        add(builder);
        return builder;
    }
}
