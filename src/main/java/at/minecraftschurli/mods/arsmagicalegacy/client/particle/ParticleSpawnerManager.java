package at.minecraftschurli.mods.arsmagicalegacy.client.particle;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleSpawner;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMDataManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public final class ParticleSpawnerManager extends AMDataManager<ParticleSpawner> {
    public static final Identifier ID = ArsMagicaApi.id("particle_spawners");
    public static final ParticleSpawnerManager INSTANCE = new ParticleSpawnerManager();

    private ParticleSpawnerManager() {
        super(ID, ParticleSpawner.CODEC);
    }

    @Override
    protected void apply(Map<Identifier, ParticleSpawner> map, ResourceManager resourceManager, ProfilerFiller profiler) {
        ParticleUtil.clearParticleSpawnerCache();
        super.apply(map, resourceManager, profiler);
    }
}
