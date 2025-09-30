package at.minecraftschurli.arsmagicalegacy.entity;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.init.AMMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class AbstractSpellEntity extends Entity implements OwnableEntity {
    public AbstractSpellEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public abstract void setOwner(LivingEntity owner);

    public abstract int getOwnerId();

    public abstract int getDuration();

    public abstract int getColor();

    public abstract Spell getSpell();

    @Override
    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level().getEntity(getOwnerId());
        return entity instanceof LivingEntity living ? living : null;
    }

    @Override
    @Nullable
    public UUID getOwnerUUID() {
        return getOwner() instanceof Player player ? player.getUUID() : null;
    }

    @Override
    public void tick() {
        if (tickCount > getDuration() || getOwnerId() < 0) {
            remove(RemovalReason.KILLED);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @SuppressWarnings("DataFlowIssue")
    protected static boolean tryReflect(Entity e) {
        if (!(e instanceof LivingEntity living)) return true;
        if (living.isDeadOrDying()) return false;
        if (!living.hasEffect(AMMobEffects.REFLECT)) return true;
        MobEffectInstance reflect = living.getEffect(AMMobEffects.REFLECT);
        if (reflect.getAmplifier() == 0) {
            living.removeEffect(AMMobEffects.REFLECT);
        } else {
            MobEffectInstance effect = new MobEffectInstance(reflect.getEffect(), reflect.getDuration(), reflect.getAmplifier(), reflect.isAmbient(), reflect.isVisible(), reflect.showIcon());
            living.removeEffect(AMMobEffects.REFLECT);
            living.addEffect(effect);
        }
        return false;
    }
}
