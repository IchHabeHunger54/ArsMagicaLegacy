package at.minecraftschurli.arsmagicalegacy.api.ability;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;

/**
 * Represents an ability effect. One {@link Ability} may have multiple ability effects.
 */
public class AbilityEffect {
    public static final Codec<AbilityEffect> CODEC = Codec.lazyInitialized(() -> ArsMagicaApi.abilityEffectRegistry().byNameCodec().dispatch(AbilityEffect::type, AbilityEffect.Type::codec));
    private final Type<? extends AbilityEffect> type;

    /**
     * @param type The registered type of the ability effect.
     */
    public AbilityEffect(Type<? extends AbilityEffect> type) {
        this.type = type;
    }

    /**
     * @return The registered {@link Type} of the ability effect.
     */
    public Type<? extends AbilityEffect> type() {
        return type;
    }

    /**
     * Called when a {@link Player} shifts into an {@link Ability} with this effect.
     *
     * @param player  The {@link Player} shifting into the {@link Ability}.
     * @param ability The {@link Ability} the player is shifting into.
     */
    public void shiftInto(Player player, Ability ability) {
    }

    /**
     * Called when a {@link Player} shifts into an {@link Ability} with this effect.
     *
     * @param player  The {@link Player} shifting into the {@link Ability}.
     * @param ability The {@link Ability} the player is shifting into.
     */
    public void shiftOutOf(Player player, Ability ability) {
    }

    /**
     * Represents the registered type of a {@link AbilityEffect}.
     *
     * @param codec The {@link MapCodec} to use.
     * @param <T> The type of the {@link AbilityEffect}.
     */
    public record Type<T extends AbilityEffect>(MapCodec<T> codec) {
    }
}
