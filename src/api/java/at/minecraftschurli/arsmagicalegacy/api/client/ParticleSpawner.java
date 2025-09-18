package at.minecraftschurli.arsmagicalegacy.api.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public record ParticleSpawner(
    ParticleOptions particle,
    int count,
    int lifetime,
    Vec3 offset,
    Vec3 randomOffset,
    MinMaxBounds.Doubles speedX,
    MinMaxBounds.Doubles speedY,
    MinMaxBounds.Doubles speedZ,
    float gravity,
    float scale,
    int color,
    float alpha,
    List<ParticleController> controllers
) {
    public static final MinMaxBounds.Doubles DEFAULT_BOUNDS = MinMaxBounds.Doubles.exactly(0);
    public static final Codec<ParticleSpawner> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ParticleTypes.CODEC.fieldOf("particle").forGetter(ParticleSpawner::particle),
        Codec.INT.fieldOf("count").forGetter(ParticleSpawner::count),
        Codec.INT.fieldOf("lifetime").forGetter(ParticleSpawner::lifetime),
        Vec3.CODEC.optionalFieldOf("offset", Vec3.ZERO).forGetter(ParticleSpawner::offset),
        Vec3.CODEC.optionalFieldOf("random_offset", Vec3.ZERO).forGetter(ParticleSpawner::randomOffset),
        MinMaxBounds.Doubles.CODEC.optionalFieldOf("speed_x", DEFAULT_BOUNDS).forGetter(ParticleSpawner::speedX),
        MinMaxBounds.Doubles.CODEC.optionalFieldOf("speed_y", DEFAULT_BOUNDS).forGetter(ParticleSpawner::speedY),
        MinMaxBounds.Doubles.CODEC.optionalFieldOf("speed_z", DEFAULT_BOUNDS).forGetter(ParticleSpawner::speedZ),
        Codec.FLOAT.optionalFieldOf("gravity", 0f).forGetter(ParticleSpawner::gravity),
        Codec.FLOAT.optionalFieldOf("scale", 1f).forGetter(ParticleSpawner::scale),
        Codec.INT.optionalFieldOf("color", -1).forGetter(ParticleSpawner::color),
        Codec.FLOAT.optionalFieldOf("scale", 1f).forGetter(ParticleSpawner::scale),
        ParticleController.CODEC.listOf().fieldOf("controllers").forGetter(ParticleSpawner::controllers)
    ).apply(inst, ParticleSpawner::new));
}
