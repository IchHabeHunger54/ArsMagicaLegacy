package at.minecraftschurli.arsmagicalegacy.api.helper;

import net.minecraft.world.entity.LivingEntity;

/**
 * Helper for mana-related operations.
 */
@SuppressWarnings("UnusedReturnValue")
public interface ManaHelper {
    /**
     * Returns the base value used for calculating the maximum mana of a {@link LivingEntity}.
     * Mana is calculated as base + {@link ManaHelper#getManaMultiplier()} * (level - 1).
     *
     * @return The base value used for calculating maximum mana.
     */
    double getManaBase();

    /**
     * Returns the multiplier used for calculating the maximum mana of a {@link LivingEntity}.
     * Mana is calculated as {@link ManaHelper#getManaBase()} + multiplier * (level - 1).
     *
     * @return The multiplier used for calculating maximum mana.
     */
    double getManaMultiplier();

    /**
     * Returns the multiplier used for calculating the mana regeneration of a {@link LivingEntity}.
     * Mana is calculated as {@link ManaHelper#getManaBase()} + {@link ManaHelper#getManaMultiplier()} * (level - 1) * regeneration.
     *
     * @return The multiplier used for calculating mana regeneration.
     */
    double getManaRegenerationMultiplier();

    /**
     * @param entity The {@link LivingEntity} to get the mana for.
     * @return The mana value of the given {@link LivingEntity}.
     */
    double getMana(LivingEntity entity);

    /**
     * @param entity The {@link LivingEntity} to get the maximum mana for.
     * @return The maximum mana value of the given {@link LivingEntity}.
     */
    double getMaxMana(LivingEntity entity);

    /**
     * @param entity The {@link LivingEntity} to get the mana regeneration value for.
     * @return The mana regeneration value of the given {@link LivingEntity}.
     */
    double getManaRegeneration(LivingEntity entity);

    /**
     * Sets the {@link LivingEntity}'s mana value.
     *
     * @param entity The {@link LivingEntity} to set the mana value on.
     * @param amount The mana value to set.
     * @return Whether the operation was successful or not.
     */
    boolean setMana(LivingEntity entity, double amount);

    /**
     * Increases the {@link LivingEntity}'s mana value.
     *
     * @param entity The {@link LivingEntity} to increase the mana value on.
     * @param amount The mana amount to increase by.
     * @return Whether the operation was successful or not.
     */
    boolean increaseMana(LivingEntity entity, double amount);

    /**
     * Decreases the {@link LivingEntity}'s mana value.
     *
     * @param entity The {@link LivingEntity} to decrease the mana value on.
     * @param amount The mana amount to decrease by.
     * @return Whether the operation was successful or not.
     */
    boolean decreaseMana(LivingEntity entity, double amount);

    /**
     * Sets the {@link LivingEntity}'s maximum mana value.
     *
     * @param entity The {@link LivingEntity} to set the maximum mana value on.
     * @param amount The maximum mana value to set.
     * @return Whether the operation was successful or not.
     */
    boolean setMaxMana(LivingEntity entity, double amount);

    /**
     * Sets the {@link LivingEntity}'s mana regeneration value.
     *
     * @param entity The {@link LivingEntity} to set the mana regeneration value on.
     * @param amount The mana regeneration value to set.
     * @return Whether the operation was successful or not.
     */
    boolean setManaRegeneration(LivingEntity entity, double amount);
}
