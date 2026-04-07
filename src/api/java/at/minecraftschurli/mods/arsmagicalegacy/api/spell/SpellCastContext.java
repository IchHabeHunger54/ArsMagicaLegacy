package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * The context of a spell cast, passed into many spell casting methods in {@link SpellHelper} and related places.
 *
 * @param delegate     The delegate for the spell.
 * @param level        The {@link Level} the {@link Spell} is cast in.
 * @param caster       The {@link LivingEntity} casting the {@link Spell}. May be null.
 * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster. May be null.
 * @param hitResult    The {@link HitResult} of the spell cast. May be null.
 * @param consume      Whether to consume mana and burnout or not.
 * @param awardXp      Whether to award xp or not.
 */
public record SpellCastContext(MutableHolder delegate, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult, boolean consume, boolean awardXp) implements MutableSpellFacade {
    public SpellCastContext(SpellFacade delegate, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult, boolean consume, boolean awardXp) {
        this(delegate.asMutable(), level, caster, directEntity, hitResult, consume, awardXp);
    }

    /**
     * The context of a spell cast, passed into many spell casting methods in {@link SpellHelper} and related places.
     * <p>
     * This constructor is called when beginning a spell cast, as a {@linkplain SpellCastContext#directEntity direct entity} and a {@linkplain SpellCastContext#hitResult hit result} will never be present at that stage.
     *
     * @param spell   The {@link MutableSpellFacade} that is cast.
     * @param level   The {@link Level} the {@link MutableSpellFacade} is cast in.
     * @param caster  The {@link LivingEntity} casting the {@link MutableSpellFacade}. May be null.
     * @param consume Whether to consume mana and burnout or not.
     * @param awardXp Whether to award xp or not.
     */
    public SpellCastContext(SpellFacade spell, Level level, @Nullable LivingEntity caster, boolean consume, boolean awardXp) {
        this(spell, level, caster, null, null, consume, awardXp);
    }

    public SpellCastContext setSpell(SpellFacade spell) {
        return new SpellCastContext(spell, level, caster, directEntity, hitResult, consume, awardXp);
    }

    /**
     * @param directEntity The direct {@link Entity} to set.
     * @return A new context object with the direct {@link Entity} set.
     */
    public SpellCastContext setDirectEntity(Entity directEntity) {
        return new SpellCastContext(delegate, level, caster, directEntity, hitResult, consume, awardXp);
    }

    /**
     * @param hitResult The {@link HitResult} to set.
     * @return A new context object with the {@link HitResult} set.
     */
    public SpellCastContext setHitResult(HitResult hitResult) {
        return new SpellCastContext(delegate, level, caster, directEntity, hitResult, consume, awardXp);
    }

    /**
     * @param directEntity The direct {@link Entity} to set.
     * @param hitResult    The {@link HitResult} to set.
     * @return A new context object with the direct {@link Entity} and {@link HitResult} set.
     */
    public SpellCastContext setDirectEntityAndHitResult(Entity directEntity, HitResult hitResult) {
        return new SpellCastContext(delegate, level, caster, directEntity, hitResult, consume, awardXp);
    }

    /**
     * @return Whether the context's {@link HitResult} is null or a miss, effectively meaning it should not be used.
     */
    public boolean isHitResultNullOrMiss() {
        return hitResult == null || hitResult.getType() == HitResult.Type.MISS;
    }

    @Override
    public Spell spell() {
        return delegate.spell();
    }

    @Override
    public Optional<Component> name() {
        return delegate.name();
    }

    @Override
    public void setName(@Nullable Component name) {
        delegate.setName(name);
    }

    @Override
    public void clearName() {
        delegate.clearName();
    }

    @Override
    public Optional<Identifier> icon() {
        return delegate.icon();
    }

    @Override
    public void setIcon(@Nullable Identifier icon) {
        delegate.setIcon(icon);
    }

    @Override
    public void clearIcon() {
        delegate.clearIcon();
    }

    @Override
    public SpellDataComponentMap spellData() {
        return delegate.spellData();
    }

    @Override
    public void updateSpellData(UnaryOperator<SpellDataComponentMap> update) {
        delegate.updateSpellData(update);
    }

    @Override
    public void setSpellData(SpellDataComponentMap data) {
        delegate.setSpellData(data);
    }

    @Override
    public byte activeShapeGroup() {
        return delegate.activeShapeGroup();
    }

    @Override
    public void setActiveShapeGroup(byte activeShapeGroup) {
        delegate.setActiveShapeGroup(activeShapeGroup);
    }

    @Override
    public void setFrom(SpellFacade spell) {
        delegate.setFrom(spell);
    }
}
