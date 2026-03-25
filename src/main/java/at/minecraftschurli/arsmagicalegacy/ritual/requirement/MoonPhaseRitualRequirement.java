package at.minecraftschurli.arsmagicalegacy.ritual.requirement;

import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.clock.ClockManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;
import org.jetbrains.annotations.Nullable;

public record MoonPhaseRitualRequirement(MoonPhase moonPhase) implements RitualRequirement {
    public static final MapCodec<MoonPhaseRitualRequirement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        MoonPhase.CODEC.fieldOf("moon_phase").forGetter(MoonPhaseRitualRequirement::moonPhase)
    ).apply(inst, MoonPhaseRitualRequirement::new));

    @Override
    public MapCodec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec) {
        ClockManager clockManager = level.clockManager();
        Timeline dayTimeline = level.registryAccess().getOrThrow(Timelines.OVERWORLD_DAY).value();
        Timeline moonTimeline = level.registryAccess().getOrThrow(Timelines.MOON).value();
        MoonPhase[] moonPhases = MoonPhase.values();
        // TODO use time markers
        return dayTimeline.getCurrentTicks(clockManager) >= 12000 && moonPhases[moonTimeline.getPeriodCount(clockManager) % moonPhases.length] == moonPhase;
    }
}
