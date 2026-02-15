package at.minecraftschurli.arsmagicalegacy.api.ability;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

/**
 * Represents an ability effect. One {@link Ability} may have multiple ability effects.
 */
public interface AbilityEffect {
    Codec<AbilityEffect> CODEC = Codec.lazyInitialized(() -> AMRegistries.ABILITY_EFFECTS.byNameCodec().dispatch(AbilityEffect::codec, Function.identity()));

    /**
     * @return The registered {@link MapCodec} of the ability effect.
     */
    MapCodec<? extends AbilityEffect> codec();

    /**
     * Called when a {@link Player} shifts into an {@link Ability} with the effect.
     *
     * @param player  The {@link Player} shifting into the {@link Ability}.
     * @param ability The {@link Ability} the player is shifting into.
     */
    default void shiftInto(Player player, Holder<Ability> ability) {
    }

    /**
     * Called when a {@link Player} shifts into an {@link Ability} with the effect.
     *
     * @param player  The {@link Player} shifting into the {@link Ability}.
     * @param ability The {@link Ability} the player is shifting into.
     */
    default void shiftOutOf(Player player, Holder<Ability> ability) {
    }

    /**
     * Called every tick when an {@link Ability} with the effect is active on the given {@link Player}.
     *
     * @param player  The {@link Player} the {@link Ability} is active on.
     * @param ability The {@link Ability} that is active.
     */
    default void tick(Player player, Holder<Ability> ability) {
    }
}
