package at.minecraftschurli.arsmagicalegacy.api.ability;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

/**
 * Represents an ability effect. One {@link Ability} may have multiple ability effects.
 */
public interface AbilityEffect {
    Codec<AbilityEffect> CODEC = Codec.lazyInitialized(() -> ArsMagicaApi.abilityEffectRegistry().byNameCodec().dispatch(AbilityEffect::codec, Function.identity()));

    /**
     * @return The registered {@link MapCodec} of the ability effect.
     */
    MapCodec<? extends AbilityEffect> codec();

    /**
     * Called when a {@link Player} shifts into an {@link Ability} with this effect.
     *
     * @param player  The {@link Player} shifting into the {@link Ability}.
     * @param ability The {@link Ability} the player is shifting into.
     */
    default void shiftInto(Player player, Ability ability) {
    }

    /**
     * Called when a {@link Player} shifts into an {@link Ability} with this effect.
     *
     * @param player  The {@link Player} shifting into the {@link Ability}.
     * @param ability The {@link Ability} the player is shifting into.
     */
    default void shiftOutOf(Player player, Ability ability) {
    }
}
