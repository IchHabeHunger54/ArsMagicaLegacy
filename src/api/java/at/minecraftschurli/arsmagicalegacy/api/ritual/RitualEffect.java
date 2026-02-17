package at.minecraftschurli.arsmagicalegacy.api.ritual;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

/**
 * Represents a ritual effect that does something when the ritual is successfully performed.
 */
public interface RitualEffect {
    Codec<RitualEffect> CODEC = Codec.lazyInitialized(() -> AMRegistries.RITUAL_EFFECTS.byNameCodec().dispatch(RitualEffect::codec, Function.identity()));

    /**
     * @return The registered {@link MapCodec}.
     */
    MapCodec<? extends RitualEffect> codec();

    /**
     * Performs the effect.
     *
     * @param player The {@link Player} that triggered the ritual.
     * @param level  The {@link Level} the ritual was triggered in.
     * @param vec    The {@link Vec3} the ritual was triggered at.
     */
    void perform(Player player, Level level, Vec3 vec);
}
