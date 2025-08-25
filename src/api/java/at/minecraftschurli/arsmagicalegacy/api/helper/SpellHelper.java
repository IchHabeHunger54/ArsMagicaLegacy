package at.minecraftschurli.arsmagicalegacy.api.helper;

import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SpellHelper {
    /**
     * Casts the given {@link Spell}.
     *
     * @param spell   The {@link Spell} to cast.
     * @param caster  The {@link LivingEntity} casting the {@link Spell}.
     * @param consume Whether to consume mana and burnout or not.
     * @param awardXp Whether to award xp or not.
     * @return A {@link SpellCastResult} representing the outcome of the spell cast.
     */
    SpellCastResult cast(Spell spell, LivingEntity caster, boolean consume, boolean awardXp);

    /**
     * Casts the given {@link Spell}'s primary shape.
     *
     * @param spell  The {@link Spell} to cast.
     * @param caster The {@link LivingEntity} casting the {@link Spell}.
     * @return A {@link SpellCastResult} representing the outcome of the spell cast.
     * @see PrimarySpellShape#cast(Spell, List, LivingEntity)
     */
    SpellCastResult castPrimary(Spell spell, LivingEntity caster);

    /**
     * Casts the given {@link Spell}'s secondary shape.
     *
     * @param spell        The {@link Spell} to cast.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @return A {@link SpellCastResult} representing the outcome of the spell cast.
     * @see SecondarySpellShape#cast(Spell, List, LivingEntity, Entity)
     */
    SpellCastResult castSecondary(Spell spell, LivingEntity caster, Entity directEntity);

    /**
     * Casts the given {@link Spell}'s grammar.
     *
     * @param spell        The {@link Spell} to cast.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return A {@link SpellCastResult} representing the outcome of the spell cast.
     */
    SpellCastResult castGrammar(Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);

    /**
     * If present, casts the given {@link Spell}'s secondary shape. Otherwise, casts the given {@link Spell}'s grammar.
     *
     * @param spell        The {@link Spell} to cast.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return A {@link SpellCastResult} representing the outcome of the spell cast.
     */
    SpellCastResult castSecondaryOrGrammar(Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);

    /**
     * @return The mana to burnout conversion ratio, used in spell cost calculation.
     */
    double getManaToBurnoutRatio();
}
