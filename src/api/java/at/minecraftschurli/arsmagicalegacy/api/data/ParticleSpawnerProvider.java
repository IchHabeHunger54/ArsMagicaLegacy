package at.minecraftschurli.arsmagicalegacy.api.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.particle.ParticleSpawner;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public abstract class ParticleSpawnerProvider extends AbstractDataProvider<ParticleSpawner, ParticleSpawnerBuilder> {
    /**
     * @param output         The {@link PackOutput} to use. Get this from {@link GatherDataEvent}.
     * @param lookupProvider The lookup {@link CompletableFuture} to use. Get this from {@link GatherDataEvent}.
     * @param modId          Your mod id.
     */
    public ParticleSpawnerProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(PackOutput.Target.RESOURCE_PACK, ArsMagicaApi.MOD_ID + "/particle_spawners", "Particle Spawners", ParticleSpawner.CODEC, output, lookupProvider, modId);
    }

    /**
     * @param id          The id of the {@link ParticleSpawner}.
     * @param particle    The spawned particles' {@link ParticleOptions}.
     * @param count       The spawned particle count.
     * @param minLifetime The min lifetime of the spawned particles.
     * @param maxLifetime The max lifetime of the spawned particles.
     * @return A new {@link ParticleSpawnerBuilder}.
     */
    public ParticleSpawnerBuilder builder(ResourceLocation id, ParticleOptions particle, int count, int minLifetime, int maxLifetime) {
        ParticleSpawnerBuilder builder = new ParticleSpawnerBuilder(id, particle, count, minLifetime, maxLifetime);
        add(builder);
        return builder;
    }

    /**
     * @param id       The id of the {@link ParticleSpawner}.
     * @param particle The spawned particles' {@link ParticleOptions}.
     * @param count    The spawned particle count.
     * @param lifetime The lifetime of the spawned particles.
     * @return A new {@link ParticleSpawnerBuilder}.
     */
    public ParticleSpawnerBuilder builder(ResourceLocation id, ParticleOptions particle, int count, int lifetime) {
        ParticleSpawnerBuilder builder = new ParticleSpawnerBuilder(id, particle, count, lifetime);
        add(builder);
        return builder;
    }
}
