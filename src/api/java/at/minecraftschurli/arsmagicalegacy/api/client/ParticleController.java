package at.minecraftschurli.arsmagicalegacy.api.client;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public abstract class ParticleController {
    public static final Codec<ParticleController> CODEC = ResourceLocation.CODEC.comapFlatMap(
        id -> Optional.ofNullable(ArsMagicaClientApi.particleController(id))
            .map(DataResult::success)
            .orElseGet(() -> DataResult.error(() -> "Unknown particle controller: " + id)),
        ParticleController.Type::id
    ).dispatch(ParticleController::type, ParticleController.Type::codec);
    private final ResourceLocation id;
    protected final boolean stopOtherControllers;
    protected final boolean killOnFinish;

    public ParticleController(ResourceLocation id, boolean stopOtherControllers, boolean killOnFinish) {
        this.id = id;
        this.stopOtherControllers = stopOtherControllers;
        this.killOnFinish = killOnFinish;
    }

    public static <T extends ParticleController> Products.P3<RecordCodecBuilder.Mu<T>, ResourceLocation, Boolean, Boolean> baseFields(RecordCodecBuilder.Instance<T> instance) {
        return instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(ParticleController::getId),
            Codec.BOOL.fieldOf("stop_other_controllers").forGetter(ParticleController::stopOtherControllers),
            Codec.BOOL.fieldOf("kill_on_finish").forGetter(ParticleController::killOnFinish));
    }

    protected ResourceLocation getId() {
        return id;
    }

    public boolean stopOtherControllers() {
        return stopOtherControllers;
    }

    public boolean killOnFinish() {
        return killOnFinish;
    }

    public abstract void tick(ControlledParticle particle, int tickCount);

    public abstract Type type();

    public record Type(ResourceLocation id, MapCodec<? extends ParticleController> codec) {
    }
}
