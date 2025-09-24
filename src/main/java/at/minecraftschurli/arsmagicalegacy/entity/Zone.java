package at.minecraftschurli.arsmagicalegacy.entity;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

public class Zone extends AbstractSpellEntity {
    private static final EntityDataAccessor<Boolean> TARGET_NON_SOLID = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RANGE = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Spell> SPELL = SynchedEntityData.defineId(Zone.class, AMSpells.DATA_SERIALIZER.get());
    private static final String TARGET_NON_SOLID_KEY = "target_non_solid";
    private static final String COLOR_KEY = "color";
    private static final String DURATION_KEY = "duration";
    private static final String OWNER_KEY = "owner";
    private static final String GRAVITY_KEY = "gravity";
    private static final String RANGE_KEY = "range";
    private static final String SPELL_KEY = "spell";

    public Zone(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(TARGET_NON_SOLID, false)
            .define(COLOR, -1)
            .define(DURATION, 200)
            .define(OWNER, 0)
            .define(GRAVITY, 0f)
            .define(RANGE, 1f)
            .define(SPELL, Spell.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        CompoundTag tag = compound.getCompound(ArsMagicaApi.MOD_ID);
        entityData.set(TARGET_NON_SOLID, tag.getBoolean(TARGET_NON_SOLID_KEY));
        entityData.set(COLOR, tag.getInt(COLOR_KEY));
        entityData.set(DURATION, tag.getInt(DURATION_KEY));
        entityData.set(OWNER, tag.getInt(OWNER_KEY));
        entityData.set(GRAVITY, tag.getFloat(GRAVITY_KEY));
        entityData.set(RANGE, tag.getFloat(RANGE_KEY));
        entityData.set(SPELL, Spell.CODEC.decode(NbtOps.INSTANCE, tag.getCompound(SPELL_KEY)).getOrThrow().getFirst());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        CompoundTag tag = compound.getCompound(ArsMagicaApi.MOD_ID);
        tag.putBoolean(TARGET_NON_SOLID_KEY, entityData.get(TARGET_NON_SOLID));
        tag.putInt(COLOR_KEY, entityData.get(COLOR));
        tag.putInt(DURATION_KEY, entityData.get(DURATION));
        tag.putInt(OWNER_KEY, entityData.get(OWNER));
        tag.putFloat(GRAVITY_KEY, entityData.get(GRAVITY));
        tag.putFloat(RANGE_KEY, entityData.get(RANGE));
        tag.put(SPELL_KEY, Spell.CODEC.encodeStart(NbtOps.INSTANCE, getSpell()).getOrThrow());
    }

    @Override
    public void tick() {
        super.tick();
        setPos(position().add(0, -getGravity(), 0));
        if (tickCount % AMServerConfig.ZONE_TICK_INTERVAL.get() != 0) return;
        LivingEntity owner = getOwner();
        SpellHelper helper = ArsMagicaApi.spellHelper();
        int color = getColor();
        double range = getRange();
        Spell spell = getSpell();
        AABB aabb = new AABB(position().add(-range, 0, -range), position().add(range, AMServerConfig.ZONE_HEIGHT.get(), range));
        for (Entity entity : level().getEntities(this, aabb)) {
            if (entity instanceof PartEntity<?> part) {
                entity = part.getParent();
            }
            if (entity instanceof AbstractSpellEntity) continue;
            if (tryReflect(entity)) {
                helper.castGrammar(spell, owner, this, new EntityHitResult(entity));
            }
        }
        BlockPos.betweenClosedStream(aabb).forEach(pos -> {
            helper.castGrammar(spell, owner, this, AMUtil.getHitResult(position(), position().add(getDeltaMovement()), this, getTargetNonSolid() ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER, getTargetNonSolid() ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE));
            if (level().isClientSide()) {
                AMClientUtil.spawnSpellEntityParticles(this, pos.getBottomCenter(), color, owner);
            }
        });
    }

    public boolean getTargetNonSolid() {
        return entityData.get(TARGET_NON_SOLID);
    }

    public void setTargetNonSolid() {
        entityData.set(TARGET_NON_SOLID, true);
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

    @Override
    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level().getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity living ? living : null;
    }

    @Override
    public void setOwner(LivingEntity owner) {
        entityData.set(OWNER, owner.getId());
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

    public Spell getSpell() {
        return entityData.get(SPELL);
    }

    public void setSpell(Spell spell) {
        entityData.set(SPELL, spell);
    }
}
