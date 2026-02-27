package at.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Helper for spell-related operations.
 */
public interface SpellHelper {
    /**
     * Casts the given {@link Spell}.
     *
     * @param spell   The {@link Spell} to cast.
     * @param level   The {@link Level} the {@link Spell} is cast in.
     * @param caster  The {@link LivingEntity} casting the {@link Spell}.
     * @param consume Whether to consume mana and burnout or not.
     * @param awardXp Whether to award xp or not.
     * @return {@code null} if the cast was successful, or an error message if not.
     */
    SpellCastResult cast(Spell spell, Level level, @Nullable LivingEntity caster, boolean consume, boolean awardXp);

    /**
     * Casts the given {@link Spell}'s primary shape. Note that {@link SpellCastContext#directEntity()} and {@link SpellCastContext#hitResult()} are guaranteed to return null here.
     *
     * @param context The {@link SpellCastContext} to use.
     * @return A {@link SpellCastResult} representing the result of the cast.
     * @see PrimarySpellShape#cast(List, SpellCastContext)
     */
    SpellCastResult castPrimary(SpellCastContext context);

    /**
     * Casts the given {@link Spell}'s secondary shape.
     *
     * @param context The {@link SpellCastContext} to use.
     * @return A {@link SpellCastResult} representing the result of the cast.
     * @see SecondarySpellShape#cast(List, SpellCastContext)
     */
    SpellCastResult castSecondary(SpellCastContext context);

    /**
     * Casts the given {@link Spell}'s grammar.
     *
     * @param context The {@link SpellCastContext} to use.
     * @return A {@link SpellCastResult} representing the result of the cast.
     */
    SpellCastResult castGrammar(SpellCastContext context);

    /**
     * If present, casts the given {@link Spell}'s secondary shape. Otherwise, casts the given {@link Spell}'s grammar.
     *
     * @param context The {@link SpellCastContext} to use.
     * @return A {@link SpellCastResult} representing the result of the cast.
     */
    SpellCastResult castSecondaryOrGrammar(SpellCastContext context);

    /**
     * @param part The {@link SpellPart} to get the {@link SpellPartData} for.
     * @return The {@link SpellPartData} for the specified {@link SpellPart}.
     */
    SpellPartData getData(SpellPart part);

    /**
     * Calculates the modifier-changed value from the base value.
     *
     * @param base      The base value to use.
     * @param stat      The {@link SpellStat} that is modified.
     * @param modifiers The {@link SpellModifier}s to check.
     * @param context   The {@link SpellCastContext} to use.
     * @return A modifier-changed value.
     */
    double getModifiedStat(double base, SpellStat stat, List<SpellModifier> modifiers, SpellCastContext context);

    /**
     * @param modifiers       The {@link SpellModifier}s to check.
     * @param spell           The {@link Spell} to cast.
     * @param shapeGroupIndex The index of the shape group to query the data components for. Pass a negative to use the grammar's data components instead.
     * @return The color of the {@link Spell}'s visual effects.
     */
    int getColor(List<SpellModifier> modifiers, Spell spell, int shapeGroupIndex);

    /**
     * @param part The {@link SpellPart} to query.
     * @return A list of {@link SpellModifier} that can modify the given part.
     */
    List<SpellModifier> getModifiers(SpellPart part);

    /**
     * Sets a contingency {@link Spell}.
     *
     * @param entity      The {@link LivingEntity} to set the contingency {@link Spell} on.
     * @param contingency The name of the contingency to trigger the contingency {@link Spell} for.
     * @param spell       The {@link Spell} to cast when the contingency is triggered.
     */
    void setContingency(LivingEntity entity, ResourceLocation contingency, Spell spell);

    /**
     * Triggers a contingency.
     *
     * @param entity      The {@link LivingEntity} to trigger the contingency for.
     * @param contingency The name of the contingency to trigger.
     */
    void triggerContingency(LivingEntity entity, ResourceLocation contingency);

    /**
     * @param toolTier The tool tier to get the incorrect block tag for.
     * @return A tag specifying which blocks are not breakable by the given tool tier.
     */
    TagKey<Block> getIncorrectTagForToolTier(int toolTier);

    /**
     * @param entity The {@link LivingEntity} to get the max summons for.
     * @return The maximum amount of summoned minions for the given {@link LivingEntity}.
     */
    int getMaxSummons(LivingEntity entity);

    /**
     * @return The mana to burnout conversion ratio, used in spell cost calculation.
     */
    double getManaToBurnoutRatio();

    /**
     * Calculates a {@link Spell}'s recipe.
     *
     * @param spell The {@link Spell} to calculate the recipe for.
     * @return The recipe for the {@link Spell}.
     */
    List<SpellIngredient> getRecipe(Spell spell);

    /**
     * Calculates a {@link Spell}'s recipe and combines the ingredients where possible.
     *
     * @param spell The {@link Spell} to calculate the recipe for.
     * @return The recipe for the {@link Spell}.
     */
    List<SpellIngredient> getFlatRecipe(Spell spell);

    /**
     * On the client, spawns particles for the given {@link SpellPart}. On the server, does nothing.
     *
     * @param part      The id of the spell part to spawn the particles for.
     * @param modifiers The {@link SpellModifier}s to consider.
     * @param context   The {@link SpellCastContext} to use.
     */
    void spawnParticles(ResourceLocation part, List<SpellModifier> modifiers, SpellCastContext context);
}
