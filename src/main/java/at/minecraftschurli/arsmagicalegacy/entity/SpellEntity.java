package at.minecraftschurli.arsmagicalegacy.entity;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMMobEffects;
import at.minecraftschurli.arsmagicalegacy.packet.SetEntityOwnerPacket;
import at.minecraftschurli.arsmagicalegacy.util.OwnerSetter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class SpellEntity extends Entity implements OwnableEntity, OwnerSetter {
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.INT);
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
            .define(OWNER, -1);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        CompoundTag tag = compound.getCompound(ArsMagicaApi.MOD_ID);
        entityData.set(COLOR, tag.getInt(COLOR_KEY));
        entityData.set(DURATION, tag.getInt(DURATION_KEY));
        entityData.set(OWNER, tag.getInt(OWNER_KEY));
        readNbt(tag);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        CompoundTag tag = new CompoundTag();
        tag.putInt(COLOR_KEY, entityData.get(COLOR));
        tag.putInt(DURATION_KEY, entityData.get(DURATION));
        tag.putInt(OWNER_KEY, entityData.get(OWNER));
        writeNbt(tag);
        compound.put(ArsMagicaApi.MOD_ID, tag);
    }

    protected abstract void readNbt(CompoundTag tag);

    protected abstract void writeNbt(CompoundTag tag);

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

    public int getOwnerId() {
        return entityData.get(OWNER);
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level().getEntity(getOwnerId());
        return entity instanceof LivingEntity living ? living : null;
    }

    @Override
    @Nullable
    public UUID getOwnerUUID() {
        LivingEntity owner = getOwner();
        return owner != null ? owner.getUUID() : null;
    }

    public void setOwner(LivingEntity owner) {
        int ownerId = owner.getId();
        setOwner(ownerId);
        if (!level().isClientSide()) {
            PacketDistributor.sendToPlayersTrackingEntity(this, new SetEntityOwnerPacket(getId(), ownerId));
        }
    }

    @Override
    public void setOwner(int id) {
        entityData.set(OWNER, id);
    }

    @Override
    public float getYHeadRot() {
        return getYRot();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    protected boolean cancelTick(int tickInterval) {
        if (tickCount > getDuration() || getOwnerId() < 0) {
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
