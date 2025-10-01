package at.minecraftschurli.arsmagicalegacy.client.particle.controller;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.ControlledParticle;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleController;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleControllerInstance;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

public record FadeOutController(boolean stopOtherControllers, boolean killOnFinish, float speed) implements ParticleController {
    public static final ResourceLocation ID = ArsMagicaApi.modLoc("fade_out");
    public static final MapCodec<FadeOutController> CODEC = RecordCodecBuilder.mapCodec(inst -> ParticleController.baseFields(inst)
        .and(ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("speed", 0.05f).forGetter(FadeOutController::speed))
        .apply(inst, FadeOutController::new));

    public FadeOutController(float speed) {
        this(false, false, speed);
    }

    public FadeOutController() {
        this(0.05f);
    }

    @Override
    public void tick(ParticleControllerInstance instance) {
        ControlledParticle particle = instance.particle;
        particle.setAlpha(particle.getAlpha() - speed);
        if (particle.getAlpha() <= 0) {
            instance.finish();
        }
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
