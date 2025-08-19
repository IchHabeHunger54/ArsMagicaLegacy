package at.minecraftschurli.arsmagicalegacy.api.event;

import at.minecraftschurli.arsmagicalegacy.api.magic.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.magic.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.magic.Spell;
import at.minecraftschurli.arsmagicalegacy.api.magic.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.magic.SpellModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Event that is fired when a particular spell part is cast. Has type-specific sub events.
 * <p>
 * In order to perform additional functionality when the spell as a whole is cast, use {@link SpellCastEvent}.
 * <p>
 * This event is not cancelable. This event is fired on the main event bus.
 */
@SuppressWarnings("unused")
public abstract class SpellPartCastEvent extends SpellEvent {
    private final List<SpellModifier> modifiers;

    public SpellPartCastEvent(LivingEntity entity, Spell spell, List<SpellModifier> modifiers) {
        super(entity, spell);
        this.modifiers = modifiers;
    }

    /**
     * @return The list of {@link SpellModifier}s used by this spell part.
     */
    public List<SpellModifier> getModifiers() {
        return modifiers;
    }

    /**
     * Event that is fired when a {@link PrimarySpellShape} is cast.
     */
    public static class PrimaryShape extends SpellPartCastEvent {
        private final PrimarySpellShape shape;

        public PrimaryShape(LivingEntity entity, Spell spell, PrimarySpellShape shape, List<SpellModifier> modifiers) {
            super(entity, spell, modifiers);
            this.shape = shape;
        }

        /**
         * @return The {@link PrimarySpellShape} being cast.
         */
        public PrimarySpellShape getShape() {
            return shape;
        }
    }

    /**
     * Event that is fired when a {@link SecondarySpellShape} is cast.
     */
    public static class SecondaryShape extends SpellPartCastEvent {
        private final SecondarySpellShape shape;
        private final Entity directEntity;

        public SecondaryShape(LivingEntity entity, Spell spell, SecondarySpellShape shape, List<SpellModifier> modifiers, Entity directEntity) {
            super(entity, spell, modifiers);
            this.shape = shape;
            this.directEntity = directEntity;
        }

        /**
         * @return The {@link SecondarySpellShape} being cast.
         */
        public SecondarySpellShape getShape() {
            return shape;
        }

        /**
         * @return The direct entity casting the spell, e.g. a projectile.
         */
        public Entity getDirectEntity() {
            return directEntity;
        }
    }

    /**
     * Event that is fired when a {@link SpellComponent} is cast.
     */
    public static class Component extends SpellPartCastEvent {
        private final SpellComponent component;
        private final Entity directEntity;
        @Nullable
        private final HitResult hitResult;

        public Component(LivingEntity entity, Spell spell, SpellComponent component, List<SpellModifier> modifiers, Entity directEntity, @Nullable HitResult hitResult) {
            super(entity, spell, modifiers);
            this.component = component;
            this.directEntity = directEntity;
            this.hitResult = hitResult;
        }

        /**
         * @return The {@link SpellComponent} being cast.
         */
        public SpellComponent getComponent() {
            return component;
        }

        /**
         * @return The direct entity casting the spell, e.g. a projectile.
         */
        public Entity getDirectEntity() {
            return directEntity;
        }

        /**
         * @return The hit result of the cast component.
         */
        @Nullable
        public HitResult getHitResult() {
            return hitResult;
        }
    }
}
