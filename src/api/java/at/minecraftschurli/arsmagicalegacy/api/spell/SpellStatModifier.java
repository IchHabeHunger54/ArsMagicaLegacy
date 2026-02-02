package at.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

/**
 * Callback for calculating a modified {@link SpellStat} value.
 */
@FunctionalInterface
public interface SpellStatModifier {
    SpellStatModifier NOOP = (base, modified, spell, level, caster, directEntity, hitResult) -> modified;

    /**
     * Calculates a modified value.
     *
     * @param base         The base value being modified.
     * @param modified     The modified value with all previous modifications.
     * @param spell        The {@link Spell} being cast.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return A modified value.
     */
    double modify(double base, double modified, Spell spell, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult);

    /**
     * @param value The value to add.
     * @return A spell stat modifier that adds the given value to the modified value.
     */
    static SpellStatModifier add(double value) {
        return (base, modified, spell, level, caster, directEntity, hitResult) -> modified + value;
    }

    /**
     * @param value The value to multiply with.
     * @return A spell stat modifier that multiplies the given value with the modified value.
     */
    static SpellStatModifier multiply(double value) {
        return (base, modified, spell, level, caster, directEntity, hitResult) -> modified * value;
    }

    /**
     * @param value The value to multiply with.
     * @return A spell stat modifier that adds the base value, multiplied with the given value, to the modified value.
     */
    static SpellStatModifier addMultipliedBase(double value) {
        return (base, modified, spell, level, caster, directEntity, hitResult) -> modified + base * value;
    }

    /**
     * @param value The value to multiply with.
     * @return A spell stat modifier that adds the modified value, multiplied with the given value, to the modified value.
     */
    static SpellStatModifier addMultipliedTotal(double value) {
        return (base, modified, spell, level, caster, directEntity, hitResult) -> modified + modified * value;
    }
}
