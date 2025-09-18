package at.minecraftschurli.arsmagicalegacy.api.spell;

import com.mojang.datafixers.util.Either;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
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
     * @param caster  The {@link LivingEntity} casting the {@link Spell}.
     * @param consume Whether to consume mana and burnout or not.
     * @param awardXp Whether to award xp or not.
     * @return An {@link Either} containing either the potentially modified {@link Spell} that was cast, or an error message.
     */
    Either<Spell, Component> cast(Spell spell, LivingEntity caster, boolean consume, boolean awardXp);

    /**
     * Casts the given {@link Spell}'s primary shape.
     *
     * @param spell  The {@link Spell} to cast.
     * @param caster The {@link LivingEntity} casting the {@link Spell}.
     * @return The {@link Spell} that was cast, potentially modified.
     * @see PrimarySpellShape#cast(Spell, List, LivingEntity)
     */
    Spell castPrimary(Spell spell, LivingEntity caster);

    /**
     * Casts the given {@link Spell}'s secondary shape.
     *
     * @param spell        The {@link Spell} to cast.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @return The {@link Spell} that was cast, potentially modified.
     * @see SecondarySpellShape#cast(Spell, List, LivingEntity, Entity)
     */
    Spell castSecondary(Spell spell, LivingEntity caster, Entity directEntity);

    /**
     * Casts the given {@link Spell}'s grammar.
     *
     * @param spell        The {@link Spell} to cast.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return The {@link Spell} that was cast, potentially modified.
     */
    Spell castGrammar(Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);

    /**
     * If present, casts the given {@link Spell}'s secondary shape. Otherwise, casts the given {@link Spell}'s grammar.
     *
     * @param spell        The {@link Spell} to cast.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return The {@link Spell} that was cast, potentially modified.
     */
    Spell castSecondaryOrGrammar(Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);

    /**
     * Calculates the modifier-changed value from the base value.
     *
     * @param base         The base value to use.
     * @param stat         The {@link SpellStat} that is modified.
     * @param modifiers    The {@link SpellModifier}s to check.
     * @param spell        The {@link Spell} to cast.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return A modifier-changed value.
     */
    double getModifiedStat(double base, SpellStat stat, List<SpellModifier> modifiers, Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);

    /**
     * @param modifiers       The {@link SpellModifier}s to check.
     * @param spell           The {@link Spell} to cast.
     * @param shapeGroupIndex The index of the shape group to query the data components for. Pass a negative to use the grammar's data components instead.
     * @return The color of the {@link Spell}'s visual effects.
     */
    int getColor(List<SpellModifier> modifiers, Spell spell, int shapeGroupIndex);

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
}
