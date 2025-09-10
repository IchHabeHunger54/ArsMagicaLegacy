package at.minecraftschurli.arsmagicalegacy.api.helper;

import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicAttachment;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;

/**
 * Helper for operations related to a {@link Player}'s {@link MagicAttachment}.
 */
public interface MagicHelper {
    /**
     * @param player The {@link Player} to get the level for.
     * @return The given {@link Player}'s level.
     */
    int getLevel(Player player);

    /**
     * @param player The {@link Player} to get the xp for.
     * @return The given {@link Player}'s xp.
     */
    double getXp(Player player);

    /**
     * If a {@link Player} is at the given level, calculate how much xp is required for the next level.
     *
     * @param level The current level.
     * @return The xp required for the next level.
     */
    double getXpForNextLevel(int level);

    /**
     * @param player The {@link Player} to add the levels for.
     * @param level  The levels to add.
     */
    void addLevel(Player player, int level);

    /**
     * @param player The {@link Player} to set the level for.
     * @param level  The level to set.
     */
    void setLevel(Player player, int level);

    /**
     * @param player The {@link Player} to add the xp for.
     * @param xp     The xp to add.
     */
    void addXp(Player player, double xp);

    /**
     * @param player The {@link Player} to set the xp for.
     * @param xp     The xp to set.
     */
    void setXp(Player player, double xp);

    /**
     * @param player The {@link Player} to query.
     * @return Whether the given {@link Player} knows magic and can interact with various systems added by the mod. This is always true if the player is in creative mode.
     */
    boolean knowsMagic(Player player);

    /**
     * Initiates magic on the given {@link Player}, setting them to level 1 and granting some extra skill points.
     * By default, this happens when they take an Arcane Compendium in the inventory for the first time.
     * @param player The {@link Player} to initiate magic on.
     */
    void initiateMagic(Player player);

    /**
     * @param player The {@link Player} to query.
     * @param skill  The {@link Skill} to check for.
     * @return Whether the {@link Player} knows the given {@link Skill}.
     */
    boolean knows(Player player, Holder<Skill> skill);

    /**
     * @param player The {@link Player} to query.
     * @param skill  The {@link Skill} to check for.
     * @return Whether the {@link Player} does not know, but meets the requirements to learn the given {@link Skill}.
     */
    boolean canLearn(Player player, Holder<Skill> skill);

    /**
     * @param player The {@link Player} to query.
     * @return A {@link List} of all {@link Skill}s the {@link Player} currently knows.
     */
    List<? extends Holder<Skill>> getKnown(Player player);

    /**
     * @param player The {@link Player} to query.
     * @return A {@link List} of all {@link Skill}s the {@link Player} does not currently know.
     */
    List<? extends Holder<Skill>> getUnknown(Player player);

    /**
     * Adds the given {@link Skill} to the {@link Player}'s known {@link Skill}s.
     *
     * @param player The {@link Player} to add the {@link Skill} to.
     * @param skill  The {@link Skill} to add.
     */
    void learn(Player player, Holder<Skill> skill);

    /**
     * Removes the given {@link Skill} from the {@link Player}'s known {@link Skill}s.
     *
     * @param player The {@link Player} to remove the {@link Skill} from.
     * @param skill  The {@link Skill} to remove.
     */
    void forget(Player player, Holder<Skill> skill);

    /**
     * Adds all {@link Skill}s to the {@link Player}'s known {@link Skill}s.
     *
     * @param player The {@link Player} to add the {@link Skill}s to.
     */
    void learnAll(Player player);

    /**
     * Removes all {@link Skill}s from the {@link Player}'s known {@link Skill}s.
     *
     * @param player The {@link Player} to remove the {@link Skill}s from.
     */
    void forgetAll(Player player);

    /**
     * @param player     The {@link Player} to query.
     * @param skillPoint The {@link SkillPoint} to check.
     * @return The amount of {@link SkillPoint}s the {@link Player} has.
     */
    int getSkillPoint(Player player, Holder<SkillPoint> skillPoint);

    /**
     * Adds {@link SkillPoint}s to the {@link Player}.
     *
     * @param player     The {@link Player} to add the {@link SkillPoint}s to.
     * @param skillPoint The {@link SkillPoint} to add.
     * @param amount     The amount of {@link SkillPoint}s to add.
     */
    void addSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount);

    /**
     * Adds one {@link SkillPoint} to the {@link Player}.
     *
     * @param player     The {@link Player} to add the {@link SkillPoint} to.
     * @param skillPoint The {@link SkillPoint} to add.
     */
    void addSkillPoint(Player player, Holder<SkillPoint> skillPoint);

    /**
     * Sets {@link SkillPoint}s on the {@link Player}.
     *
     * @param player     The {@link Player} to set the {@link SkillPoint}s on.
     * @param skillPoint The {@link SkillPoint} to set.
     * @param amount     The amount of {@link SkillPoint}s to set.
     */
    void setSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount);

    /**
     * @param player   The {@link Player} to query.
     * @param affinity The {@link Affinity} to query.
     * @return The {@link Affinity} depth of the given {@link Player}, in the range [0, 1].
     */
    double getAffinityDepth(Player player, Holder<Affinity> affinity);

    /**
     * Sets the given {@link Player}'s {@link Affinity} depth. Will not bypass locks.
     *
     * @param player   The {@link Player} to set the {@link Affinity} depth on.
     * @param affinity The {@link Affinity} to set the depth for.
     * @param depth    The depth to set.
     */
    void setAffinityDepth(Player player, Holder<Affinity> affinity, double depth);

    /**
     * Sets the given {@link Player}'s {@link Affinity} depths. Will not bypass locks.
     *
     * @param player     The {@link Player} to set the {@link Affinity} depth on.
     * @param affinities The {@link Affinity} depths to set.
     */
    void setAffinityDepth(Player player, Map<Holder<Affinity>, Double> affinities);

    /**
     * Sets the given {@link Player}'s {@link Affinity} depth.
     *
     * @param player        The {@link Player} to set the {@link Affinity} depth on.
     * @param affinity      The {@link Affinity} to set the depth for.
     * @param depth         The depth to set.
     * @param commandSource Whether the method was called from a command. If true, bypasses locks.
     */
    void setAffinityDepth(Player player, Holder<Affinity> affinity, double depth, boolean commandSource);

    /**
     * Sets the given {@link Player}'s {@link Affinity} depths.
     *
     * @param player        The {@link Player} to set the {@link Affinity} depths on.
     * @param affinities    The {@link Affinity} depths to set.
     * @param commandSource Whether the method was called from a command. If true, bypasses locks.
     */
    void setAffinityDepth(Player player, Map<Holder<Affinity>, Double> affinities, boolean commandSource);

    /**
     * Adds to the given {@link Player}'s {@link Affinity} depth. Will not bypass locks.
     *
     * @param player   The {@link Player} to add the {@link Affinity} depth to.
     * @param affinity The {@link Affinity} to add the depth for.
     * @param depth    The depth to add.
     */
    void addAffinityDepth(Player player, Holder<Affinity> affinity, double depth);

    /**
     * Adds to the given {@link Player}'s {@link Affinity} depth. Will not bypass locks.
     *
     * @param player     The {@link Player} to add the {@link Affinity} depths to.
     * @param affinities The {@link Affinity} depths to add.
     */
    void addAffinityDepth(Player player, Map<Holder<Affinity>, Double> affinities);

    /**
     * Adds to the given {@link Player}'s {@link Affinity} depth.
     *
     * @param player        The {@link Player} to add the {@link Affinity} depth to.
     * @param affinity      The {@link Affinity} to add the depth for.
     * @param depth         The depth to add.
     * @param commandSource Whether the method was called from a command. If true, bypasses locks.
     */
    void addAffinityDepth(Player player, Holder<Affinity> affinity, double depth, boolean commandSource);

    /**
     * Adds to the given {@link Player}'s {@link Affinity} depth.
     *
     * @param player        The {@link Player} to add the {@link Affinity} depths to.
     * @param affinities    The {@link Affinity} depths to add.
     * @param commandSource Whether the method was called from a command. If true, bypasses locks.
     */
    void addAffinityDepth(Player player, Map<Holder<Affinity>, Double> affinities, boolean commandSource);

    /**
     * Applies an {@link Affinity} shift to the given {@link Player}. It sets the {@link Affinity} change itself, as well as modifying the adjacent and opposite {@link Affinity}s. Will not bypass locks.
     *
     * @param player   The {@link Player} to apply the {@link Affinity} shift to.
     * @param affinity The {@link Affinity} to apply the shift for.
     * @param shift    The shift to apply.
     */
    void applyAffinityShift(Player player, Holder<Affinity> affinity, double shift);

    /**
     * Applies {@link Affinity} shifts to the given {@link Player}. It sets the {@link Affinity} changes itself, as well as modifying the adjacent and opposite {@link Affinity}s. Will not bypass locks.
     *
     * @param player         The {@link Player} to apply the {@link Affinity} shifts to.
     * @param affinityShifts The {@link Affinity} shifts to apply.
     */
    void applyAffinityShift(Player player, Map<Holder<Affinity>, Double> affinityShifts);

    /**
     * Applies an {@link Affinity} shift to the given {@link Player}. It sets the {@link Affinity} change itself, as well as modifying the adjacent and opposite {@link Affinity}s.
     *
     * @param player        The {@link Player} to apply the {@link Affinity} shift to.
     * @param affinity      The {@link Affinity} to apply the shift for.
     * @param shift         The shift to apply.
     * @param commandSource Whether the method was called from a command. If true, bypasses locks.
     */
    void applyAffinityShift(Player player, Holder<Affinity> affinity, double shift, boolean commandSource);

    /**
     * Applies {@link Affinity} shifts to the given {@link Player}. It sets the {@link Affinity} changes itself, as well as modifying the adjacent and opposite {@link Affinity}s.
     *
     * @param player         The {@link Player} to apply the {@link Affinity} shifts to.
     * @param affinityShifts The {@link Affinity} shifts to apply.
     * @param commandSource  Whether the method was called from a command. If true, bypasses locks.
     */
    void applyAffinityShift(Player player, Map<Holder<Affinity>, Double> affinityShifts, boolean commandSource);

    /**
     * Locks the {@link Player}'s {@link Affinity} depths.
     *
     * @param player The {@link Player} to lock the {@link Affinity} depths of.
     * @see MagicHelper#unlockAffinities(Player)
     */
    void lockAffinities(Player player);

    /**
     * Unlocks the {@link Player}'s {@link Affinity} depths.
     *
     * @param player The {@link Player} to unlock the {@link Affinity} depths of.
     * @see MagicHelper#lockAffinities(Player)
     */
    void unlockAffinities(Player player);

    /**
     * Sets the {@link Player}'s {@link Affinity} depth lock depending on whether there is an affinity at 100% or not.
     *
     * @param player The {@link Player} to set the {@link Affinity} depth lock for.
     */
    void updateAffinityLock(Player player);
}
