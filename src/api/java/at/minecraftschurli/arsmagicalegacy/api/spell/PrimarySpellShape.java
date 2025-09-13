package at.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.Set;

/**
 * Represents a primary spell shape. Primary shapes must be at the start of a {@link SpellShapeGroup}.
 */
public abstract non-sealed class PrimarySpellShape extends SpellPart {
    private final Set<SpellStat> stats;

    /**
     * @param stats A vararg of {@link SpellStat}s used by the shape.
     */
    public PrimarySpellShape(SpellStat... stats) {
        this.stats = Set.of(stats);
    }

    @Override
    public final boolean isPrimaryShape() {
        return true;
    }

    @Override
    public final boolean isSecondaryShape() {
        return false;
    }

    @Override
    public final boolean isComponent() {
        return false;
    }

    @Override
    public final boolean isModifier() {
        return false;
    }

    /**
     * @return A {@link Set} of {@link SpellStat}s used by the shape.
     */
    public Set<SpellStat> getStats() {
        return stats;
    }

    /**
     * @return Whether this part is continuous, i.e., can be cast by holding down the spell.
     */
    public boolean isContinuous() {
        return false;
    }

    /**
     * Casts this part.
     *
     * @param spell     The {@link Spell} being cast.
     * @param modifiers The {@link SpellModifier}s to consider.
     * @param caster    The {@link LivingEntity} casting the {@link Spell}.
     * @return The {@link Spell} that was cast, potentially modified.
     * @see SpellHelper#castPrimary(Spell, LivingEntity)
     */
    public abstract Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster);
}
