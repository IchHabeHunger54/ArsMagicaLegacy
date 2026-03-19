package at.minecraftschurli.arsmagicalegacy.entity;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMMobEffects;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public abstract class SpellEntity extends Entity implements TraceableEntity {
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> OWNER = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    private static final String COLOR_KEY = "color";
    private static final String DURATION_KEY = "duration";
    private static final String OWNER_KEY = "owner";

    public SpellEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(COLOR, -1)
            .define(DURATION, 72000)
            .define(OWNER, Optional.empty());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        input.child(ArsMagicaApi.MOD_ID).ifPresent(tag -> {
            entityData.set(COLOR, tag.getIntOr(COLOR_KEY, -1));
            entityData.set(DURATION, tag.getIntOr(DURATION_KEY, 72000));
            entityData.set(OWNER, Optional.ofNullable(EntityReference.readWithOldOwnerConversion(input, OWNER_KEY, this.level())));
            readData(tag);
        });
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        ValueOutput tag = output.child(ArsMagicaApi.MOD_ID);
        tag.putInt(COLOR_KEY, entityData.get(COLOR));
        tag.putInt(DURATION_KEY, entityData.get(DURATION));
        EntityReference.store(entityData.get(OWNER).orElse(null), tag, OWNER_KEY);
        writeData(tag);
    }

    protected abstract void readData(ValueInput tag);

    protected abstract void writeData(ValueOutput tag);

    public int getColor() {
        return entityData.get(COLOR);
    }

    public void setColor(int color) {
        entityData.set(COLOR, color);
    }

    public int getDuration() {
        return entityData.get(DURATION);
    }

    public void setDuration(int duration) {
        entityData.set(DURATION, duration);
    }

    public void setOwner(@Nullable LivingEntity owner) {
        entityData.set(OWNER, owner == null ? Optional.empty() : Optional.of(EntityReference.of(owner)));
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        return EntityReference.getLivingEntity(entityData.get(OWNER).orElse(null), level());
    }

    @Override
    public float getYHeadRot() {
        return getYRot();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return false;
    }

    protected boolean cancelTick(int tickInterval) {
        if (tickCount > getDuration() || getOwner() == null) {
            remove(RemovalReason.KILLED);
            return true;
        }
        return tickCount % tickInterval != 0 || getOwner() == null;
    }

    @SuppressWarnings("DataFlowIssue")
    protected boolean tryReflect(Entity e) {
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
