package at.minecraftschurli.mods.arsmagicalegacy.api.ability;

import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicAttachment;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

import java.util.List;
import java.util.stream.Stream;

/**
 * Helper for operations related to a {@link Player}'s {@link Ability}s.
 */
public interface AbilityHelper {
    /**
     * Called when the {@link Player}'s {@link MagicAttachment} changes. Updates the {@link Ability}s accordingly.
     *
     * @param player  The {@link Player} whose {@link MagicAttachment} changes.
     * @param oldData The old {@link MagicAttachment}.
     * @param newData The new {@link MagicAttachment}.
     */
    void onMagicChange(Player player, MagicAttachment oldData, MagicAttachment newData);

    /**
     * @param player The {@link Player} to query.
     * @return The active {@link Ability}s of the given {@link Player}.
     */
    Stream<? extends Holder<Ability>> getActiveAbilities(Player player);

    /**
     * @param player      The {@link Player} to query.
     * @param effectCodec The {@link AbilityEffect} type to filter for.
     * @param <T>         The exact {@link AbilityEffect} type.
     * @return A {@link Stream} of {@link Pair}s, each representing a {@link Ability} and its associated {@link AbilityEffect}s.
     */
    <T extends AbilityEffect> Stream<? extends Pair<? extends Holder<Ability>, List<T>>> getActiveAbilitiesWithEffect(Player player, MapCodec<T> effectCodec);

    /**
     * Triggers an {@link EventTriggeredAbilityEffect}.
     *
     * @param event  The {@link Event} to trigger the {@link EventTriggeredAbilityEffect} from.
     * @param player The {@link Player} causing the {@link Event}.
     * @param codec  The {@link AbilityEffect} type.
     * @param <T>    The exact {@link Event} type.
     */
    <T extends Event> void triggerEventEffect(T event, Player player, MapCodec<? extends EventTriggeredAbilityEffect<T>> codec);

    /**
     * Linearly scales the given {@link Ability}'s range to the min and max provided, depending on the {@link Player}'s depth.
     * If the depth is at the minimum bound, returns the min value. If the depth is at the maximum bound, returns the max value.
     * If the ability is somewhere in between, linear interpolation is performed.
     *
     * @param player  The {@link Player} to query.
     * @param ability The {@link Ability} to get the range from.
     * @param min     The min value to use.
     * @param max     The max value to use.
     * @return A scaled value.
     */
    double scaleToDepth(Player player, Ability ability, double min, double max);
}
