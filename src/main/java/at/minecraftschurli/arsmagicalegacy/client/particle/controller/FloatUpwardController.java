package at.minecraftschurli.arsmagicalegacy.client.particle.controller;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.ControlledParticle;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleController;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleControllerInstance;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public record FloatUpwardController(boolean stopOtherControllers, boolean killOnFinish, double jitter, double minSpeed, double maxSpeed) implements ParticleController {
    public static final ResourceLocation ID = ArsMagicaApi.modLoc("float_upward");
    public static final MapCodec<FloatUpwardController> CODEC = RecordCodecBuilder.mapCodec(inst -> ParticleController.baseFields(inst)
        .and(AMUtil.NON_NEGATIVE_DOUBLE_CODEC.fieldOf("jitter").forGetter(FloatUpwardController::jitter))
        .and(Codec.DOUBLE.fieldOf("min_speed").forGetter(FloatUpwardController::minSpeed))
        .and(Codec.DOUBLE.fieldOf("max_speed").forGetter(FloatUpwardController::maxSpeed))
        .apply(inst, FloatUpwardController::new));
    private static final String SPEED_KEY = "speed";

    public FloatUpwardController(double jitter, double minSpeed, double maxSpeed) {
        this(false, false, jitter, minSpeed, maxSpeed);
    }

    public FloatUpwardController(double jitter, double speed) {
        this(false, false, jitter, speed, speed);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void tick(ParticleControllerInstance instance) {
        ControlledParticle particle = instance.particle;
        if (particle.y() > particle.level().getMaxBuildHeight()) {
            instance.finish();
        } else {
            particle.move(particle.random().nextDouble() * jitter - jitter / 2, instance.getContext(SPEED_KEY), particle.random().nextDouble() * jitter - jitter / 2);
        }
    }

    @Override
    public void tickFirst(ParticleControllerInstance instance) {
        instance.setContext(SPEED_KEY, Mth.lerp(instance.particle.random().nextDouble(), minSpeed, maxSpeed));
        tick(instance);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
