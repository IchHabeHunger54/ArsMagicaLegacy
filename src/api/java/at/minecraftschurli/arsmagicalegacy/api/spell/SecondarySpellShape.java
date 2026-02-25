package at.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * Represents a secondary spell shape. Secondary shapes must be in a {@link SpellShapeGroup}, with a primary shape before it.
 */
public abstract non-sealed class SecondarySpellShape extends SpellPart {
    private final Set<SpellStat> stats;

    /**
     * @param stats A vararg of {@link SpellStat}s used by the shape.
     */
    public SecondarySpellShape(SpellStat... stats) {
        this.stats = Set.of(stats);
    }

    @Override
    public final boolean isPrimaryShape() {
        return false;
    }

    @Override
    public final boolean isSecondaryShape() {
        return true;
    }

    @Override
    public final boolean isComponent() {
        return false;
    }

    @Override
    public final boolean isModifier() {
        return false;
    }

    @Override
    public Set<SpellStat> getStats() {
        return stats;
    }

    /**
     * Casts this part.
     *
     * @param spell        The {@link Spell} being cast.
     * @param modifiers    The {@link SpellModifier}s to consider.
     * @param level        The {@link Level} the {@link Spell} is cast in.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return A {@link SpellCastResult} representing the result of the cast.
     * @see SpellHelper#castSecondary(Spell, Level, LivingEntity, Entity, HitResult)
     */
    public abstract SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult);
}
