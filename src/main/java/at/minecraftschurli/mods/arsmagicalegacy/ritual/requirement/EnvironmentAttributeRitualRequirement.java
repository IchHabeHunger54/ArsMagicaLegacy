package at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement;

import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record EnvironmentAttributeRitualRequirement<T>(EnvironmentAttribute<T> type, T value) implements RitualRequirement {
    public static final MapCodec<EnvironmentAttributeRitualRequirement<?>> CODEC = BuiltInRegistries.ENVIRONMENT_ATTRIBUTE.byNameCodec().dispatchMap(EnvironmentAttributeRitualRequirement::type, EnvironmentAttributeRitualRequirement::codec);

    @Override
    public MapCodec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec) {
        return Objects.equals(level.environmentAttributes().getValue(type, vec), value);
    }

    private static <T> MapCodec<EnvironmentAttributeRitualRequirement<T>> codec(EnvironmentAttribute<T> type) {
        return type.valueCodec().fieldOf("value").xmap(value -> new EnvironmentAttributeRitualRequirement<>(type, value), EnvironmentAttributeRitualRequirement::value);
    }
}
