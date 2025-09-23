package at.minecraftschurli.arsmagicalegacy.client.particle.controller;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleController;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleControllerInstance;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;

public record ChangeSizeController(boolean stopOtherControllers, boolean killOnFinish, float from, float to, int duration) implements ParticleController {
    public static final ResourceLocation ID = ArsMagicaApi.modLoc("change_size");
    public static final MapCodec<ChangeSizeController> CODEC = RecordCodecBuilder.mapCodec(inst -> ParticleController.baseFields(inst)
        .and(ExtraCodecs.POSITIVE_FLOAT.fieldOf("from").forGetter(ChangeSizeController::from))
        .and(ExtraCodecs.POSITIVE_FLOAT.fieldOf("to").forGetter(ChangeSizeController::to))
        .and(ExtraCodecs.POSITIVE_INT.fieldOf("duration").forGetter(ChangeSizeController::duration))
        .apply(inst, ChangeSizeController::new));

    public ChangeSizeController(float from, float to, int duration) {
        this(false, false, from, to, duration);
    }

    @Override
    public void tick(ParticleControllerInstance instance) {
        instance.particle.scale(Mth.lerp(Math.clamp(instance.getTickCount() / duration, 0, 1), from, to));
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
