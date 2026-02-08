package at.minecraftschurli.arsmagicalegacy.plant;

import at.minecraftschurli.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.arsmagicalegacy.util.AMDataManager;
import org.slf4j.LoggerFactory;

public final class PlantManager extends AMDataManager<Plant> {
    public static final PlantManager INSTANCE = new PlantManager();

    private PlantManager() {
        super("plant", Plant.CODEC, LoggerFactory.getLogger(PlantManager.class));
    }
}
