package at.minecraftschurli.arsmagicalegacy.api.spell;

import com.mojang.datafixers.util.Either;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
     * @param level   The {@link Level} the {@link Spell} is cast in.
     * @param caster  The {@link LivingEntity} casting the {@link Spell}.
     * @param consume Whether to consume mana and burnout or not.
     * @param awardXp Whether to award xp or not.
     * @return An {@link Either} containing either the potentially modified {@link Spell} that was cast, or an error message.
     */
    Either<Spell, Component> cast(Spell spell, Level level, LivingEntity caster, boolean consume, boolean awardXp);

    /**
     * Casts the given {@link Spell}'s primary shape.
     *
     * @param spell  The {@link Spell} to cast.
     * @param level  The {@link Level} the {@link Spell} is cast in.
     * @param caster The {@link LivingEntity} casting the {@link Spell}.
     * @return The {@link Spell} that was cast, potentially modified.
     * @see PrimarySpellShape#cast(Spell, List, Level, LivingEntity)
     */
    Spell castPrimary(Spell spell, Level level, LivingEntity caster);

    /**
     * Casts the given {@link Spell}'s secondary shape.
     *
     * @param spell        The {@link Spell} to cast.
     * @param level        The {@link Level} the {@link Spell} is cast in.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return The {@link Spell} that was cast, potentially modified.
     * @see SecondarySpellShape#cast(Spell, List, Level, LivingEntity, Entity, HitResult)
     */
    Spell castSecondary(Spell spell, Level level, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);

    /**
     * Casts the given {@link Spell}'s grammar.
     *
     * @param spell        The {@link Spell} to cast.
     * @param level        The {@link Level} the {@link Spell} is cast in.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return The {@link Spell} that was cast, potentially modified.
     */
    Spell castGrammar(Spell spell, Level level, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);

    /**
     * If present, casts the given {@link Spell}'s secondary shape. Otherwise, casts the given {@link Spell}'s grammar.
     *
     * @param spell        The {@link Spell} to cast.
     * @param level        The {@link Level} the {@link Spell} is cast in.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return The {@link Spell} that was cast, potentially modified.
     */
    Spell castSecondaryOrGrammar(Spell spell, Level level, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);

    /**
     * @param part The {@link SpellPart} to get the {@link SpellPartData} for.
     * @return The {@link SpellPartData} for the specified {@link SpellPart}.
     */
    SpellPartData getData(SpellPart part);

    /**
     * Calculates the modifier-changed value from the base value.
     *
     * @param base         The base value to use.
     * @param stat         The {@link SpellStat} that is modified.
     * @param modifiers    The {@link SpellModifier}s to check.
     * @param spell        The {@link Spell} to cast.
     * @param level        The {@link Level} the {@link Spell} is cast in.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return A modifier-changed value.
     */
    double getModifiedStat(double base, SpellStat stat, List<SpellModifier> modifiers, Spell spell, Level level, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);

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
     * @param toolTier The tool tier to get the incorrect block tag for.
     * @return A tag specifying which blocks are not breakable by the given tool tier.
     */
    TagKey<Block> getIncorrectTagForToolTier(int toolTier);

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
     * @param part         The id of the spell part to spawn the particles for.
     * @param spell        The {@link Spell} being cast.
     * @param modifiers    The {@link SpellModifier}s to consider.
     * @param level        The {@link Level} the {@link Spell} is cast in.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     */
    void spawnParticles(ResourceLocation part, Spell spell, List<SpellModifier> modifiers, Level level, LivingEntity caster, Entity directEntity, HitResult hitResult);
}
