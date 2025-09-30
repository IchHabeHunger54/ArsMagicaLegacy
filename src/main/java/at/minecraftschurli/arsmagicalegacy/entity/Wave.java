package at.minecraftschurli.arsmagicalegacy.entity;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class Wave extends SpellShapeEntity {
    private static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(Wave.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RANGE = SynchedEntityData.defineId(Wave.class, EntityDataSerializers.FLOAT);
    private static final String GRAVITY_KEY = "gravity";
    private static final String RANGE_KEY = "range";

    public Wave(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(GRAVITY, 0f)
            .define(RANGE, 1f);
    }

    @Override
    protected void readNbt(CompoundTag tag) {
        super.readNbt(tag);
        entityData.set(GRAVITY, tag.getFloat(GRAVITY_KEY));
        entityData.set(RANGE, tag.getFloat(RANGE_KEY));
    }

    @Override
    protected void writeNbt(CompoundTag tag) {
        super.writeNbt(tag);
        tag.putFloat(GRAVITY_KEY, entityData.get(GRAVITY));
        tag.putFloat(RANGE_KEY, entityData.get(RANGE));
    }

    @Override
    public void tick() {
        setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y - getGravity(), getDeltaMovement().z);
        setPos(position().add(getDeltaMovement()));
        float range = getRange();
        AABB aabb = new AABB(position().add(-range, -range, -range), position().add(range, range, range));
        if (cancelTick(AMServerConfig.WAVE_TICK_INTERVAL.get())) {
            BlockPos.betweenClosedStream(aabb).map(BlockPos::getBottomCenter).forEach(this::spawnParticles);
        } else {
            int owner = getOwnerId();
            castArea(aabb, pos -> true, entity -> entity.getId() != owner, true);
        }
    }

    @Override
    public double getDefaultGravity() {
        return entityData.get(GRAVITY);
    }

    public void setGravity(float gravity) {
        entityData.set(GRAVITY, gravity);
    }

    public float getRange() {
        return entityData.get(RANGE);
    }

    public void setRange(float radius) {
        entityData.set(RANGE, radius);
    }
}
