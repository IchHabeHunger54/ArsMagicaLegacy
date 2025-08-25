package at.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents a spell component. Components are part of the {@link SpellGrammar}.
 * <p>
 * Extend this class directly for target-independent effects, e.g. time or weather components. Extend one of the inner subclasses for target-dependent effects instead.
 */
public abstract non-sealed class SpellComponent extends SpellPart {
    @Override
    public final boolean isPrimaryShape() {
        return false;
    }

    @Override
    public final boolean isSecondaryShape() {
        return false;
    }

    @Override
    public final boolean isComponent() {
        return true;
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
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return The {@link Spell} that was cast, potentially modified.
     */
    public abstract Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);

    /**
     * Represents a spell component that only affects blocks.
     */
    public static abstract class CastBlock extends SpellComponent {
        @Override
        public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
            return hitResult instanceof BlockHitResult blockHitResult ? castBlock(spell, modifiers, caster, directEntity, blockHitResult) : spell;
        }

        /**
         * Casts this part on a block.
         *
         * @param spell        The {@link Spell} being cast.
         * @param modifiers    The {@link SpellModifier}s to consider.
         * @param caster       The {@link LivingEntity} casting the {@link Spell}.
         * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
         * @param hitResult    The {@link BlockHitResult} of the spell cast.
         * @return The {@link Spell} that was cast, potentially modified.
         */
        public abstract Spell castBlock(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, BlockHitResult hitResult);
    }

    /**
     * Represents a spell component that only affects entities.
     */
    public static abstract class CastEntity extends SpellComponent {
        @Override
        public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
            return hitResult instanceof EntityHitResult entityHitResult ? castEntity(spell, modifiers, caster, directEntity, entityHitResult) : spell;
        }

        /**
         * Casts this part on an entity.
         *
         * @param spell        The {@link Spell} being cast.
         * @param modifiers    The {@link SpellModifier}s to consider.
         * @param caster       The {@link LivingEntity} casting the {@link Spell}.
         * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
         * @param hitResult    The {@link EntityHitResult} of the spell cast.
         * @return The {@link Spell} that was cast, potentially modified.
         */
        public abstract Spell castEntity(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, EntityHitResult hitResult);
    }

    /**
     * Represents a spell component that affects both blocks and entities, with distinct effects on each.
     */
    public static abstract class CastBoth extends SpellComponent {
        @Override
        public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
            return switch (hitResult) {
                case BlockHitResult blockHitResult -> castBlock(spell, modifiers, caster, directEntity, blockHitResult);
                case EntityHitResult entityHitResult -> castEntity(spell, modifiers, caster, directEntity, entityHitResult);
                case null, default -> spell;
            };
        }

        /**
         * Casts this part on a block.
         *
         * @param spell        The {@link Spell} being cast.
         * @param modifiers    The {@link SpellModifier}s to consider.
         * @param caster       The {@link LivingEntity} casting the {@link Spell}.
         * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
         * @param hitResult    The {@link BlockHitResult} of the spell cast.
         * @return The {@link Spell} that was cast, potentially modified.
         */
        public abstract Spell castBlock(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, BlockHitResult hitResult);

        /**
         * Casts this part on an entity.
         *
         * @param spell        The {@link Spell} being cast.
         * @param modifiers    The {@link SpellModifier}s to consider.
         * @param caster       The {@link LivingEntity} casting the {@link Spell}.
         * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
         * @param hitResult    The {@link EntityHitResult} of the spell cast.
         * @return The {@link Spell} that was cast, potentially modified.
         */
        public abstract Spell castEntity(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, EntityHitResult hitResult);
    }
}
