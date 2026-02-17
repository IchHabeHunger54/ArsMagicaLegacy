package at.minecraftschurli.arsmagicalegacy.api.ritual;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

/**
 * Represents a ritual trigger. The triggers are to be called from code, and may have trigger-specific conditions.
 *
 * @param <T> The object considered the context of the trigger.
 */
public interface RitualTrigger<T> {
    Codec<RitualTrigger<?>> CODEC = Codec.lazyInitialized(() -> AMRegistries.RITUAL_TRIGGERS.byNameCodec().dispatch(RitualTrigger::codec, Function.identity()));

    /**
     * @return The registered {@link MapCodec}.
     */
    MapCodec<? extends RitualTrigger<T>> codec();

    /**
     * @param player  The {@link Player} triggering the ritual.
     * @param level   The {@link Level} the ritual is triggered in.
     * @param vec     The {@link Vec3} the ritual is triggered at.
     * @param context The context object.
     * @return Whether the requirement should actually be triggered or not.
     */
    boolean test(Player player, Level level, Vec3 vec, T context);
}
