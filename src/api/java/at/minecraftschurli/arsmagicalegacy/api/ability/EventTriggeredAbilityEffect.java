package at.minecraftschurli.arsmagicalegacy.api.ability;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

/**
 * Specialization of {@link AbilityEffect} that is triggered by an {@link Event}.
 *
 * @param <T> The type of the {@link Event}.
 */
public interface EventTriggeredAbilityEffect<T extends Event> extends AbilityEffect {
    /**
     * Called when the effect is triggered from an {@link Event}.
     *
     * @param event   The {@link Event} that triggered the effect.
     * @param player  The {@link Player} the {@link Ability} is triggered on.
     * @param ability The {@link Ability} that is triggered.
     */
    void apply(T event, Player player, Holder<Ability> ability);
}
