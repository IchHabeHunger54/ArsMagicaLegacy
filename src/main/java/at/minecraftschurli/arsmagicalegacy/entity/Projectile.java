package at.minecraftschurli.arsmagicalegacy.entity;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
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
import net.neoforged.neoforge.entity.PartEntity;

public class Projectile extends AbstractSpellEntity {
    private static final EntityDataAccessor<Boolean> TARGET_NON_SOLID = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> BOUNCES = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PIERCES = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Spell> SPELL = SynchedEntityData.defineId(Projectile.class, AMSpells.DATA_SERIALIZER.get());
    private static final String TARGET_NON_SOLID_KEY = "target_non_solid";
    private static final String BOUNCES_KEY = "bounces";
    private static final String COLOR_KEY = "color";
    private static final String DURATION_KEY = "duration";
    private static final String PIERCES_KEY = "pierces";
    private static final String OWNER_KEY = "owner";
    private static final String GRAVITY_KEY = "gravity";
    private static final String SPEED_KEY = "speed";
    private static final String SPELL_KEY = "spell";

    public Projectile(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(TARGET_NON_SOLID, false)
            .define(BOUNCES, 0)
            .define(COLOR, -1)
            .define(DURATION, 200)
            .define(PIERCES, 0)
            .define(OWNER, -1)
            .define(GRAVITY, 0f)
            .define(SPEED, 1f)
            .define(SPELL, Spell.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        CompoundTag tag = compound.getCompound(ArsMagicaApi.MOD_ID);
        entityData.set(TARGET_NON_SOLID, tag.getBoolean(TARGET_NON_SOLID_KEY));
        entityData.set(BOUNCES, tag.getInt(BOUNCES_KEY));
        entityData.set(COLOR, tag.getInt(COLOR_KEY));
        entityData.set(DURATION, tag.getInt(DURATION_KEY));
        entityData.set(PIERCES, tag.getInt(PIERCES_KEY));
        entityData.set(OWNER, tag.getInt(OWNER_KEY));
        entityData.set(GRAVITY, tag.getFloat(GRAVITY_KEY));
        entityData.set(SPEED, tag.getFloat(SPEED_KEY));
        entityData.set(SPELL, Spell.CODEC.decode(NbtOps.INSTANCE, tag.getCompound(SPELL_KEY)).getOrThrow().getFirst());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean(TARGET_NON_SOLID_KEY, entityData.get(TARGET_NON_SOLID));
        tag.putInt(BOUNCES_KEY, entityData.get(BOUNCES));
        tag.putInt(COLOR_KEY, entityData.get(COLOR));
        tag.putInt(DURATION_KEY, entityData.get(DURATION));
        tag.putInt(PIERCES_KEY, entityData.get(PIERCES));
        tag.putInt(OWNER_KEY, entityData.get(OWNER));
        tag.putFloat(GRAVITY_KEY, entityData.get(GRAVITY));
        tag.putFloat(SPEED_KEY, entityData.get(SPEED));
        tag.put(SPELL_KEY, Spell.CODEC.encodeStart(NbtOps.INSTANCE, getSpell()).getOrThrow());
        compound.put(ArsMagicaApi.MOD_ID, tag);
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y - getGravity(), getDeltaMovement().z);
        setPos(position().add(getDeltaMovement()));
        LivingEntity owner = getOwner();
        if (owner == null) return;
        if (level().isClientSide()) {
            AMClientUtil.spawnSpellEntityParticles(this, getColor(), owner);
        }
        HitResult result = AMUtil.getHitResult(position(), position().add(getDeltaMovement()), this, getTargetNonSolid() ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER, getTargetNonSolid() ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE);
        if (result.getType() == HitResult.Type.MISS) return;
        if (result instanceof BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            level.getBlockState(pos).entityInside(level, pos, this);
            if (getBounces() > 0) {
                Direction direction = hitResult.getDirection();
                double speed = getSpeed();
                double newX = getDeltaMovement().x();
                double newY = getDeltaMovement().y();
                double newZ = getDeltaMovement().z();
                switch (direction.getAxis()) {
                    case X -> newX = -newX;
                    case Y -> newY = -newY;
                    case Z -> newZ = -newZ;
                }
                setDeltaMovement(newX * speed, newY * speed, newZ * speed);
                setBounces(getBounces() - 1);
            } else {
                ArsMagicaApi.spellHelper().castSecondaryOrGrammar(getSpell(), owner, this, result);
                decreasePierces();
            }
        } else if (result instanceof EntityHitResult hitResult) {
            Entity entity = hitResult.getEntity();
            while (entity instanceof PartEntity<?> part) {
                entity = part.getParent();
            }
            if (entity != owner && tryReflect(entity)) {
                ArsMagicaApi.spellHelper().castSecondaryOrGrammar(getSpell(), owner, this, result);
                decreasePierces();
            }
        }
    }

    @Override
    public double getDefaultGravity() {
        return entityData.get(GRAVITY);
    }

    public void setGravity(float gravity) {
        entityData.set(GRAVITY, gravity);
    }

    public boolean getTargetNonSolid() {
        return entityData.get(TARGET_NON_SOLID);
    }

    public void setTargetNonSolid() {
        entityData.set(TARGET_NON_SOLID, true);
    }

    public int getBounces() {
        return entityData.get(BOUNCES);
    }

    public void setBounces(int bounces) {
        entityData.set(BOUNCES, bounces);
    }

    @Override
    public int getColor() {
        return entityData.get(COLOR);
    }

    public void setColor(int color) {
        entityData.set(COLOR, color);
    }

    @Override
    public int getDuration() {
        return entityData.get(DURATION);
    }

    public void setDuration(int duration) {
        entityData.set(DURATION, duration);
    }

    public int getPierces() {
        return entityData.get(PIERCES);
    }

    public void setPierces(int pierces) {
        entityData.set(PIERCES, pierces);
    }

    @Override
    public int getOwnerId() {
        return entityData.get(OWNER);
    }

    @Override
    public void setOwner(LivingEntity owner) {
        entityData.set(OWNER, owner.getId());
    }

    public float getSpeed() {
        return entityData.get(SPEED);
    }

    public void setSpeed(float speed) {
        entityData.set(SPEED, speed);
    }

    public Spell getSpell() {
        return entityData.get(SPELL);
    }

    public void setSpell(Spell spell) {
        entityData.set(SPELL, spell);
    }

    private void decreasePierces() {
        if (getPierces() == 0) {
            remove(RemovalReason.KILLED);
        } else {
            setPierces(getPierces() - 1);
        }
    }
}
