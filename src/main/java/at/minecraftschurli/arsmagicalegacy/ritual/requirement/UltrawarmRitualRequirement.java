package at.minecraftschurli.arsmagicalegacy.ritual.requirement;

import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record UltrawarmRitualRequirement() implements RitualRequirement {
    public static final MapCodec<UltrawarmRitualRequirement> CODEC = MapCodec.unit(UltrawarmRitualRequirement::new);

    @Override
    public MapCodec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec) {
        return level.dimensionType().ultraWarm();
    }
}
