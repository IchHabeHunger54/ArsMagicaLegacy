package at.minecraftschurli.arsmagicalegacy.client.particle;

import at.minecraftschurli.arsmagicalegacy.api.client.ParticleSpawner;
import at.minecraftschurli.arsmagicalegacy.util.AMDataManager;
import org.slf4j.LoggerFactory;

public class ParticleSpawnerManager extends AMDataManager<ParticleSpawner> {
    public static final ParticleSpawnerManager INSTANCE = new ParticleSpawnerManager();

    private ParticleSpawnerManager() {
        super("particle_spawners", ParticleSpawner.CODEC, LoggerFactory.getLogger(ParticleSpawnerManager.class));
    }
}
