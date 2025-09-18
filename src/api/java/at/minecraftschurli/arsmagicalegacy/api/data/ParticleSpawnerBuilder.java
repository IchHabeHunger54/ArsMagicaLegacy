package at.minecraftschurli.arsmagicalegacy.api.data;

import at.minecraftschurli.arsmagicalegacy.api.client.ParticleController;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleSpawner;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ParticleSpawnerBuilder extends AbstractDataProvider.Builder<ParticleSpawner> {
    private final Holder<ParticleType<?>> particle;
    private final int count;
    private final int lifetime;
    private final List<ParticleController> controllers = new ArrayList<>();
    private Vec3 offset = Vec3.ZERO;
    private Vec3 randomOffset = Vec3.ZERO;
    private MinMaxBounds.Doubles speedX = ParticleSpawner.DEFAULT_BOUNDS;
    private MinMaxBounds.Doubles speedY = ParticleSpawner.DEFAULT_BOUNDS;
    private MinMaxBounds.Doubles speedZ = ParticleSpawner.DEFAULT_BOUNDS;
    private float gravity = 0f;
    private float scale = 1f;
    private int color = -1;
    private float alpha = 1f;

    public ParticleSpawnerBuilder(ResourceLocation id, Holder<ParticleType<?>> particle, int count, int lifetime) {
        super(id);
        this.particle = particle;
        this.count = count;
        this.lifetime = lifetime;
    }

    public ParticleSpawnerBuilder(ResourceLocation id, ParticleType<?> particle, int count, int lifetime) {
        this(id, BuiltInRegistries.PARTICLE_TYPE.wrapAsHolder(particle), count, lifetime);
    }

    public ParticleSpawnerBuilder offset(Vec3 offset) {
        this.offset = offset;
        return this;
    }

    public ParticleSpawnerBuilder randomOffset(Vec3 randomOffset) {
        this.randomOffset = randomOffset;
        return this;
    }

    public ParticleSpawnerBuilder speed(double minX, double maxX, double minY, double maxY, double minZ, double maxZ) {
        speedX = MinMaxBounds.Doubles.between(minX, maxX);
        speedY = MinMaxBounds.Doubles.between(minY, maxY);
        speedZ = MinMaxBounds.Doubles.between(minZ, maxZ);
        return this;
    }

    public ParticleSpawnerBuilder speed(double minX, double maxX, double y, double minZ, double maxZ) {
        speedX = MinMaxBounds.Doubles.between(minX, maxX);
        speedY = MinMaxBounds.Doubles.exactly(y);
        speedZ = MinMaxBounds.Doubles.between(minZ, maxZ);
        return this;
    }

    public ParticleSpawnerBuilder speed(double x, double y, double z) {
        speedX = MinMaxBounds.Doubles.exactly(x);
        speedY = MinMaxBounds.Doubles.exactly(y);
        speedZ = MinMaxBounds.Doubles.exactly(z);
        return this;
    }

    public ParticleSpawnerBuilder gravity(float gravity) {
        this.gravity = gravity;
        return this;
    }

    public ParticleSpawnerBuilder scale(float scale) {
        this.scale = scale;
        return this;
    }

    public ParticleSpawnerBuilder color(int color) {
        this.color = color;
        return this;
    }

    public ParticleSpawnerBuilder alpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    public ParticleSpawnerBuilder controller(ParticleController controller) {
        this.controllers.add(controller);
        return this;
    }

    @Override
    public ParticleSpawner build() {
        return new ParticleSpawner(particle, count, lifetime, offset, randomOffset, speedX, speedY, speedZ, gravity, scale, color, alpha, controllers);
    }
}
