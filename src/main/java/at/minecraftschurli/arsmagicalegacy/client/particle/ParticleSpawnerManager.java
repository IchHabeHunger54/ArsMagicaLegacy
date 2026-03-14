package at.minecraftschurli.arsmagicalegacy.client.particle;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.particle.ParticleSpawner;
import at.minecraftschurli.arsmagicalegacy.util.AMDataManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public final class ParticleSpawnerManager extends AMDataManager<ParticleSpawner> {
    public static final ParticleSpawnerManager INSTANCE = new ParticleSpawnerManager();
    public static final Identifier ID = ArsMagicaApi.id("particle_spawners");

    private ParticleSpawnerManager() {
        super(ID, ParticleSpawner.CODEC);
    }

    @Override
    protected void apply(Map<Identifier, ParticleSpawner> map, ResourceManager resourceManager, ProfilerFiller profiler) {
        ParticleUtil.clearParticleSpawnerCache();
        super.apply(map, resourceManager, profiler);
    }
}
