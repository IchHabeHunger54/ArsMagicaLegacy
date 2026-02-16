package at.minecraftschurli.arsmagicalegacy.api.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record Ritual(List<RitualRequirement> requirements, RitualTrigger trigger, List<RitualEffect> effects) {
    public static final Codec<Ritual> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        RitualRequirement.CODEC.listOf().fieldOf("requirements").forGetter(Ritual::requirements),
        RitualTrigger.CODEC.fieldOf("trigger").forGetter(Ritual::trigger),
        RitualEffect.CODEC.listOf().fieldOf("effects").forGetter(Ritual::effects)
    ).apply(inst, Ritual::new));
}
