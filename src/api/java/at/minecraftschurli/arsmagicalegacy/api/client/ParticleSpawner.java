package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStat;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * Represents a particle spawner. A particle spawner is looked up by id in {@link ArsMagicaClientApi#spawnParticles(ResourceLocation, Vec3, int)} and used to spawn particles.
 *
 * @param particle    The {@link ParticleOptions} to use.
 * @param count       The amount of particles to spawn.
 * @param lifetime    The lifetime of the particles.
 * @param minOffset   The min offset of the particles. Particles will be randomly offset between minOffset and maxOffset.
 * @param maxOffset   The max offset of the particles. Particles will be randomly offset between minOffset and maxOffset.
 * @param minSpeed    The min speed of the particles. Particles will be given a random speed between minSpeed and maxSpeed.
 * @param maxSpeed    The max speed of the particles. Particles will be given a random speed between minSpeed and maxSpeed.
 * @param gravity     The gravity of the particles.
 * @param scale       The scale of the particles.
 * @param color       The color of the particles. May be overridden by {@link SpellStat#COLOR}.
 * @param alpha       The alpha of the particles.
 * @param controllers A list of {@link ParticleController}s to add {@link ParticleControllerInstance}s for to the particles.
 */
public record ParticleSpawner(
    ParticleOptions particle,
    int count,
    int lifetime,
    Vec3 minOffset,
    Vec3 maxOffset,
    Vec3 minSpeed,
    Vec3 maxSpeed,
    float gravity,
    float scale,
    int color,
    float alpha,
    List<ParticleController> controllers
) {
    public static final Codec<ParticleSpawner> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ParticleTypes.CODEC.fieldOf("particle").forGetter(ParticleSpawner::particle),
        Codec.INT.fieldOf("count").forGetter(ParticleSpawner::count),
        Codec.INT.fieldOf("lifetime").forGetter(ParticleSpawner::lifetime),
        Vec3.CODEC.optionalFieldOf("min_offset", Vec3.ZERO).forGetter(ParticleSpawner::minOffset),
        Vec3.CODEC.optionalFieldOf("max_offset", Vec3.ZERO).forGetter(ParticleSpawner::maxOffset),
        Vec3.CODEC.optionalFieldOf("min_speed", Vec3.ZERO).forGetter(ParticleSpawner::minSpeed),
        Vec3.CODEC.optionalFieldOf("max_speed", Vec3.ZERO).forGetter(ParticleSpawner::maxSpeed),
        Codec.FLOAT.optionalFieldOf("gravity", 0f).forGetter(ParticleSpawner::gravity),
        Codec.FLOAT.optionalFieldOf("scale", 1f).forGetter(ParticleSpawner::scale),
        Codec.INT.optionalFieldOf("color", -1).forGetter(ParticleSpawner::color),
        Codec.FLOAT.optionalFieldOf("scale", 1f).forGetter(ParticleSpawner::scale),
        ParticleController.CODEC.listOf().fieldOf("controllers").forGetter(ParticleSpawner::controllers)
    ).apply(inst, ParticleSpawner::new));
}
