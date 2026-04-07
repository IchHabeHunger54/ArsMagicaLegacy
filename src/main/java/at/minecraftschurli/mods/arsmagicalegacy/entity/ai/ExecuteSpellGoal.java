package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCasterEntity;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellFacade;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPrefab;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class ExecuteSpellGoal<T extends Mob & SpellCasterEntity> extends Goal {
    protected final T caster;
    @Nullable
    public final SpellFacade spell;
    public final int duration;
    protected int ticks = 0;

    public ExecuteSpellGoal(T caster, @Nullable SpellFacade spell, int duration) {
        this.caster = caster;
        this.spell = spell;
        this.duration = duration;
    }

    public ExecuteSpellGoal(T caster, int duration) {
        this(caster, (SpellFacade) null, duration);
    }

    public ExecuteSpellGoal(T caster, ResourceKey<SpellPrefab> prefabSpell, int duration) {
        this(caster, getSpellPrefab(caster, prefabSpell), duration);
    }

    public ExecuteSpellGoal(T caster, Identifier prefabSpellId, int duration) {
        this(caster, ResourceKey.create(AMRegistries.Keys.SPELL_PREFAB, prefabSpellId), duration);
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        return caster.canCastSpell() && caster.getTarget() != null && !caster.getTarget().isDeadOrDying() && caster.getRandom().nextBoolean();
    }

    @Override
    public boolean canContinueToUse() {
        return caster.isCastingSpell() && caster.getTarget() != null && !caster.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        caster.setIsCastingSpell(false);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = caster.getTarget();
        if (target == null) return;
        caster.lookAt(target, 30, 30);
        if (caster.distanceToSqr(target) > 64) {
            double angle = -Math.atan2(target.getZ() - caster.getZ(), target.getX() - caster.getX());
            caster.getNavigation().moveTo(target.getX() + (Math.cos(angle) * 6), target.getY(), target.getZ() + (Math.sin(angle) * 6), 0.5f);
        } else if (!caster.canAttack(target)) {
            caster.getNavigation().moveTo(target, 0.5f);
        } else {
            caster.setIsCastingSpell(true);
            ticks++;
            if (ticks >= duration) {
                Level level = caster.level();
                SoundEvent sound = getAttackSound();
                if (sound != null) {
                    level.playSound(null, caster, sound, SoundSource.HOSTILE, 1f, 0.5f + level.getRandom().nextFloat());
                }
                SpellFacade spell = getSpell(caster);
                if (spell != null) {
                    ArsMagicaApi.spellHelper().cast(spell, level, caster, false, false);
                    ticks = 0;
                    stop();
                }
            }
        }
    }

    /**
     * @return The attack sound to play for this goal.
     */
    @Nullable
    protected SoundEvent getAttackSound() {
        return null;
    }

    /**
     * @param caster The entity casting the spell.
     * @return The spell to be cast.
     */
    @Nullable
    protected SpellFacade getSpell(T caster) {
        return spell;
    }

    public static <E extends Mob & SpellCasterEntity> @Nullable SpellFacade getSpellPrefab(E caster, Identifier prefabSpell) {
        return getSpellPrefab(caster, ResourceKey.create(AMRegistries.Keys.SPELL_PREFAB, prefabSpell));
    }

    public static <E extends Mob & SpellCasterEntity> @Nullable SpellFacade getSpellPrefab(E caster, ResourceKey<SpellPrefab> prefabSpell) {
        return caster.registryAccess()
            .get(prefabSpell)
            .map(Holder::value)
            .map(SpellFacade.Immutable::of)
            .orElse(null);
    }
}
