package at.minecraftschurli.arsmagicalegacy.entity;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class Zone extends SpellShapeEntity {
    private static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RANGE = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.FLOAT);
    private static final String GRAVITY_KEY = "gravity";
    private static final String RANGE_KEY = "range";

    public Zone(EntityType<?> entityType, Level level) {
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
        setPos(position().add(0, -getGravity(), 0));
        if (cancelTick(AMServerConfig.ZONE_TICK_INTERVAL.get())) return;
        float range = getRange();
        castArea(new AABB(position().add(-range, 0, -range), position().add(range, AMServerConfig.ZONE_HEIGHT.get(), range)), pos -> true, entity -> true, false);
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

    public void setRange(float range) {
        entityData.set(RANGE, range);
    }
}
