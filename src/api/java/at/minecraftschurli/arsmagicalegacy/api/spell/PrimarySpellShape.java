package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.helper.SpellHelper;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

/**
 * Represents a primary spell shape. Primary shapes must be at the start of a {@link SpellShapeGroup}.
 */
public abstract non-sealed class PrimarySpellShape extends SpellPart {
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
     * Casts this part.
     *
     * @param spell     The {@link Spell} being cast.
     * @param modifiers The {@link SpellModifier}s to consider.
     * @param caster    The {@link LivingEntity} casting the {@link Spell}.
     * @return A {@link SpellCastResult} representing the outcome of the spell cast.
     * @see SpellHelper#castPrimary(Spell, LivingEntity)
     */
    public abstract SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster);
}
