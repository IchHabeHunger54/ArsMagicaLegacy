package at.minecraftschurli.arsmagicalegacy.api.helper;

import net.minecraft.world.entity.LivingEntity;

/**
 * Helper for burnout-related operations.
 */
public interface BurnoutHelper {
    /**
     * Returns the base value used for calculating the maximum burnout of a {@link LivingEntity}.
     * Burnout is calculated as base + {@link BurnoutHelper#getBurnoutMultiplier()} * (level - 1).
     *
     * @return The base value used for calculating maximum burnout.
     */
    double getBurnoutBase();

    /**
     * Returns the multiplier used for calculating the maximum burnout of a {@link LivingEntity}.
     * Burnout is calculated as {@link BurnoutHelper#getBurnoutBase()} + multiplier * (level - 1).
     *
     * @return The multiplier used for calculating maximum burnout.
     */
    double getBurnoutMultiplier();

    /**
     * Returns the multiplier used for calculating the burnout regeneration of a {@link LivingEntity}.
     * Burnout is calculated as {@link BurnoutHelper#getBurnoutBase()} + {@link BurnoutHelper#getBurnoutMultiplier()} * (level - 1) * regeneration.
     *
     * @return The multiplier used for calculating burnout regeneration.
     */
    double getBurnoutRegenerationMultiplier();

    /**
     * @param entity The {@link LivingEntity} to get the burnout for.
     * @return The burnout value of the given {@link LivingEntity}.
     */
    double getBurnout(LivingEntity entity);

    /**
     * @param entity The {@link LivingEntity} to get the maximum burnout for.
     * @return The maximum burnout value of the given {@link LivingEntity}.
     */
    double getMaxBurnout(LivingEntity entity);

    /**
     * @param entity The {@link LivingEntity} to get the burnout regeneration value for.
     * @return The burnout regeneration value of the given {@link LivingEntity}.
     */
    double getBurnoutRegeneration(LivingEntity entity);

    /**
     * Sets the {@link LivingEntity}'s burnout value.
     *
     * @param entity The {@link LivingEntity} to set the burnout value on.
     * @param amount The burnout value to set.
     * @return Whether the operation was successful or not.
     */
    boolean setBurnout(LivingEntity entity, double amount);

    /**
     * Increases the {@link LivingEntity}'s burnout value.
     *
     * @param entity The {@link LivingEntity} to increase the burnout value on.
     * @param amount The burnout amount to increase by.
     * @return Whether the operation was successful or not.
     */
    boolean increaseBurnout(LivingEntity entity, double amount);

    /**
     * Decreases the {@link LivingEntity}'s burnout value.
     *
     * @param entity The {@link LivingEntity} to decrease the burnout value on.
     * @param amount The burnout amount to decrease by.
     * @return Whether the operation was successful or not.
     */
    boolean decreaseBurnout(LivingEntity entity, double amount);

    /**
     * Sets the {@link LivingEntity}'s maximum burnout value.
     *
     * @param entity The {@link LivingEntity} to set the maximum burnout value on.
     * @param amount The maximum burnout value to set.
     * @return Whether the operation was successful or not.
     */
    boolean setMaxBurnout(LivingEntity entity, double amount);

    /**
     * Sets the {@link LivingEntity}'s burnout regeneration value.
     *
     * @param entity The {@link LivingEntity} to set the burnout regeneration value on.
     * @param amount The burnout regeneration value to set.
     * @return Whether the operation was successful or not.
     */
    boolean setBurnoutRegeneration(LivingEntity entity, double amount);
}
