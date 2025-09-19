package at.minecraftschurli.arsmagicalegacy.client.particle;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.ControlledParticle;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleController;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleControllerInstance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record FloatUpwardController(ResourceLocation id, boolean stopOtherControllers, boolean killOnFinish, double jitter, double speed) implements ParticleController {
    public static final ResourceLocation ID = ArsMagicaApi.modLoc("float_upward");
    public static final MapCodec<FloatUpwardController> CODEC = RecordCodecBuilder.mapCodec(inst -> ParticleController.baseFields(inst)
        .and(Codec.DOUBLE.fieldOf("jitter").forGetter(FloatUpwardController::jitter))
        .and(Codec.DOUBLE.fieldOf("speed").forGetter(FloatUpwardController::speed))
        .apply(inst, FloatUpwardController::new));

    public FloatUpwardController(double jitter, double speed) {
        this(ID, false, false, jitter, speed);
    }

    @Override
    public void tick(ParticleControllerInstance instance) {
        ControlledParticle particle = instance.particle;
        if (particle.y() > particle.level().getMaxBuildHeight()) {
            instance.finish();
        } else {
            particle.move(particle.random().nextDouble() * jitter - jitter / 2, speed, particle.random().nextDouble() * jitter - jitter / 2);
        }
    }
}
