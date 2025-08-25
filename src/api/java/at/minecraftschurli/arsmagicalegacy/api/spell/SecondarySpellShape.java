package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.helper.SpellHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

/**
 * Represents a secondary spell shape. Secondary shapes must be in a {@link SpellShapeGroup}, with a primary shape before it.
 */
public abstract non-sealed class SecondarySpellShape extends SpellPart {
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

    /**
     * Casts this part.
     *
     * @param spell        The {@link Spell} being cast.
     * @param modifiers    The {@link SpellModifier}s to consider.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @return The {@link Spell} that was cast, potentially modified.
     * @see SpellHelper#castSecondary(Spell, LivingEntity, Entity)
     */
    public abstract Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity);
}
