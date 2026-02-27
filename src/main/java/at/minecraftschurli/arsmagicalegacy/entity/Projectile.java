package at.minecraftschurli.arsmagicalegacy.entity;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class Projectile extends SpellShapeEntity {
    private static final EntityDataAccessor<Integer> BOUNCES = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PIERCES = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.FLOAT);
    private static final String BOUNCES_KEY = "bounces";
    private static final String PIERCES_KEY = "pierces";
    private static final String GRAVITY_KEY = "gravity";

    public Projectile(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BOUNCES, 0)
            .define(PIERCES, 0)
            .define(GRAVITY, 0f);
    }

    @Override
    protected void readNbt(CompoundTag tag) {
        super.readNbt(tag);
        entityData.set(BOUNCES, tag.getInt(BOUNCES_KEY));
        entityData.set(PIERCES, tag.getInt(PIERCES_KEY));
        entityData.set(GRAVITY, tag.getFloat(GRAVITY_KEY));
    }

    @Override
    protected void writeNbt(CompoundTag tag) {
        super.writeNbt(tag);
        tag.putInt(BOUNCES_KEY, entityData.get(BOUNCES));
        tag.putInt(PIERCES_KEY, entityData.get(PIERCES));
        tag.putFloat(GRAVITY_KEY, entityData.get(GRAVITY));
    }

    @Override
    public void tick() {
        setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y - getGravity(), getDeltaMovement().z);
        setPos(position().add(getDeltaMovement()));
        if (cancelTick(1)) return;
        spawnParticles(position());
        HitResult result = AMUtil.getHitResult(position(), position().add(getDeltaMovement()), this, getTargetNonSolid() ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER, getTargetNonSolid() ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE);
        if (result.getType() == HitResult.Type.MISS) return;
        LivingEntity owner = getOwner();
        if (result instanceof BlockHitResult hitResult) {
            Level level = level();
            BlockPos pos = hitResult.getBlockPos();
            level.getBlockState(pos).entityInside(level, pos, this);
            if (getBounces() > 0) {
                Direction direction = hitResult.getDirection();
                double newX = getDeltaMovement().x();
                double newY = getDeltaMovement().y();
                double newZ = getDeltaMovement().z();
                switch (direction.getAxis()) {
                    case X -> newX = -newX;
                    case Y -> newY = -newY;
                    case Z -> newZ = -newZ;
                }
                setDeltaMovement(newX, newY, newZ);
                setBounces(getBounces() - 1);
            } else {
                ArsMagicaApi.spellHelper().castSecondaryOrGrammar(new SpellCastContext(getSpell(), level, owner, this, result, getConsume(), getAwardXp()));
                decreasePierces();
            }
        } else if (result instanceof EntityHitResult hitResult) {
            castEntity(hitResult.getEntity(), entity -> entity != owner, true);
        }
    }

    public int getBounces() {
        return entityData.get(BOUNCES);
    }

    public void setBounces(int bounces) {
        entityData.set(BOUNCES, bounces);
    }

    public int getPierces() {
        return entityData.get(PIERCES);
    }

    public void setPierces(int pierces) {
        entityData.set(PIERCES, pierces);
    }

    @Override
    public double getDefaultGravity() {
        return entityData.get(GRAVITY);
    }

    public void setGravity(float gravity) {
        entityData.set(GRAVITY, gravity);
    }

    @Override
    protected boolean tryReflect(Entity e) {
        boolean reflect = super.tryReflect(e);
        if (reflect) {
            decreasePierces();
        }
        return reflect;
    }

    private void decreasePierces() {
        if (getPierces() == 0) {
            remove(RemovalReason.KILLED);
        } else {
            setPierces(getPierces() - 1);
        }
    }
}
