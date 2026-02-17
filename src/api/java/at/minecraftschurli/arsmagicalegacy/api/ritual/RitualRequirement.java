package at.minecraftschurli.arsmagicalegacy.api.ritual;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

/**
 * Represents a passive ritual requirement. These requirements are checked before the ritual effects are performed.
 */
public interface RitualRequirement {
    Codec<RitualRequirement> CODEC = Codec.lazyInitialized(() -> AMRegistries.RITUAL_REQUIREMENTS.byNameCodec().dispatch(RitualRequirement::codec, Function.identity()));

    /**
     * @return The registered {@link MapCodec}.
     */
    MapCodec<? extends RitualRequirement> codec();

    /**
     * @param player The {@link Player} triggering the ritual.
     * @param level  The {@link Level} the ritual is triggered in.
     * @param vec    The {@link Vec3} the ritual is triggered at.
     * @return Whether the requirement was met or not.
     */
    boolean test(Player player, Level level, Vec3 vec);
}
