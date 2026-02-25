package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import com.google.common.collect.Sets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * Represents a spell component. Components are part of the {@link SpellGrammar}.
 * <p>
 * Extend this class directly for target-independent effects, e.g. time or weather components. Extend one of the inner subclasses for target-dependent effects instead.
 */
public abstract non-sealed class SpellComponent extends SpellPart {
    private final Set<SpellStat> stats;

    /**
     * @param stats A vararg of {@link SpellStat}s used by the component.
     */
    public SpellComponent(SpellStat... stats) {
        this.stats = Sets.newHashSet(stats);
        this.stats.add(SpellStat.COLOR);
    }

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

    @Override
    public Set<SpellStat> getStats() {
        return stats;
    }

    /**
     * Casts the part.
     *
     * @param spell        The {@link Spell} being cast.
     * @param modifiers    The {@link SpellModifier}s to consider.
     * @param level        The {@link Level} the {@link Spell} is cast in.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     * @return A {@link SpellComponentCastResult} representing the result of the cast.
     */
    public abstract SpellComponentCastResult cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult);

    /**
     * Spawns particles for the part. May only be called on the client.
     *
     * @param spell        The {@link Spell} being cast.
     * @param modifiers    The {@link SpellModifier}s to consider.
     * @param level        The {@link Level} the {@link Spell} is cast in.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
     * @param hitResult    The {@link HitResult} of the spell cast.
     */
    @SuppressWarnings("DataFlowIssue")
    public void spawnParticles(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
            ArsMagicaApi.spellHelper().spawnParticles(AMRegistries.SPELL_PARTS.wrapAsHolder(this).getKey().location(), spell, modifiers, level, caster, directEntity, hitResult);
        }
    }

    /**
     * Represents a spell component that only affects blocks.
     */
    public static abstract class CastBlock extends SpellComponent {
        /**
         * @param stats A vararg of {@link SpellStat}s used by the component.
         */
        public CastBlock(SpellStat... stats) {
            super(stats);
        }

        @Override
        public SpellComponentCastResult cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
            return hitResult instanceof BlockHitResult blockHitResult ? castBlock(spell, modifiers, level, caster, directEntity, blockHitResult) : SpellComponentCastResult.pass(spell);
        }

        @Override
        public void spawnParticles(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
            if (hitResult instanceof BlockHitResult) {
                super.spawnParticles(spell, modifiers, level, caster, directEntity, hitResult);
            }
        }

        /**
         * Casts this part on a block.
         *
         * @param spell        The {@link Spell} being cast.
         * @param modifiers    The {@link SpellModifier}s to consider.
         * @param level        The {@link Level} the {@link Spell} is cast in.
         * @param caster       The {@link LivingEntity} casting the {@link Spell}.
         * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
         * @param hitResult    The {@link BlockHitResult} of the spell cast.
         * @return A {@link SpellComponentCastResult} representing the result of the cast.
         */
        public abstract SpellComponentCastResult castBlock(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, BlockHitResult hitResult);
    }

    /**
     * Represents a spell component that only affects entities.
     */
    public static abstract class CastEntity extends SpellComponent {
        /**
         * @param stats A vararg of {@link SpellStat}s used by the component.
         */
        public CastEntity(SpellStat... stats) {
            super(stats);
        }

        @Override
        public SpellComponentCastResult cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
            return hitResult instanceof EntityHitResult entityHitResult ? castEntity(spell, modifiers, level, caster, directEntity, entityHitResult) : SpellComponentCastResult.pass(spell);
        }

        @Override
        public void spawnParticles(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
            if (hitResult instanceof EntityHitResult) {
                super.spawnParticles(spell, modifiers, level, caster, directEntity, hitResult);
            }
        }

        /**
         * Casts this part on an entity.
         *
         * @param spell        The {@link Spell} being cast.
         * @param modifiers    The {@link SpellModifier}s to consider.
         * @param level        The {@link Level} the {@link Spell} is cast in.
         * @param caster       The {@link LivingEntity} casting the {@link Spell}.
         * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
         * @param hitResult    The {@link EntityHitResult} of the spell cast.
         * @return A {@link SpellComponentCastResult} representing the result of the cast.
         */
        public abstract SpellComponentCastResult castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult);
    }

    /**
     * Represents a spell component that affects both blocks and entities, with distinct effects on each.
     */
    public static abstract class CastBoth extends SpellComponent {
        /**
         * @param stats A vararg of {@link SpellStat}s used by the component.
         */
        public CastBoth(SpellStat... stats) {
            super(stats);
        }

        @Override
        public SpellComponentCastResult cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
            return switch (hitResult) {
                case BlockHitResult blockHitResult -> castBlock(spell, modifiers, level, caster, directEntity, blockHitResult);
                case EntityHitResult entityHitResult -> castEntity(spell, modifiers, level, caster, directEntity, entityHitResult);
                case null, default -> SpellComponentCastResult.pass(spell);
            };
        }

        /**
         * Casts this part on a block.
         *
         * @param spell        The {@link Spell} being cast.
         * @param modifiers    The {@link SpellModifier}s to consider.
         * @param level        The {@link Level} the {@link Spell} is cast in.
         * @param caster       The {@link LivingEntity} casting the {@link Spell}.
         * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
         * @param hitResult    The {@link BlockHitResult} of the spell cast.
         * @return A {@link SpellComponentCastResult} representing the result of the cast.
         */
        public abstract SpellComponentCastResult castBlock(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, BlockHitResult hitResult);

        /**
         * Casts this part on an entity.
         *
         * @param spell        The {@link Spell} being cast.
         * @param modifiers    The {@link SpellModifier}s to consider.
         * @param level        The {@link Level} the {@link Spell} is cast in.
         * @param caster       The {@link LivingEntity} casting the {@link Spell}.
         * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster.
         * @param hitResult    The {@link EntityHitResult} of the spell cast.
         * @return A {@link SpellComponentCastResult} representing the result of the cast.
         */
        public abstract SpellComponentCastResult castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult);
    }
}
