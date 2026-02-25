package at.minecraftschurli.arsmagicalegacy.ritual.requirement;

import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record MoonPhaseRitualRequirement(int moonPhase) implements RitualRequirement {
    public static final MapCodec<MoonPhaseRitualRequirement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        ExtraCodecs.intRange(0, 7).fieldOf("moon_phase").forGetter(MoonPhaseRitualRequirement::moonPhase)
    ).apply(inst, MoonPhaseRitualRequirement::new));

    @Override
    public MapCodec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec) {
        return level.getMoonPhase() == moonPhase;
    }
}
