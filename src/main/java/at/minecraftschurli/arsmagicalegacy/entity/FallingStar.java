package at.minecraftschurli.arsmagicalegacy.entity;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMDamageSources;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.stream.IntStream;

@SuppressWarnings("deprecation")
public class FallingStar extends SpellEntity {
    public static final ResourceLocation FALL_PARTICLES = ArsMagicaApi.modLoc("falling_star_fall");
    public static final ResourceLocation GROUND_PARTICLES = ArsMagicaApi.modLoc("falling_star_ground");
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(FallingStar.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RANGE = SynchedEntityData.defineId(FallingStar.class, EntityDataSerializers.FLOAT);
    private static final String DAMAGE_KEY = "damage";
    private static final String RANGE_KEY = "range";
    private static final String DAMAGED_KEY = "damaged";
    private final IntSet damaged = new IntOpenHashSet();
    private final DamageSource damageSource = AMDamageSources.fallingStar(this);
    private int timeSinceImpact = -1;

    public FallingStar(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DAMAGE, 0f)
            .define(RANGE, 1f);
    }

    @Override
    protected void readNbt(CompoundTag tag) {
        entityData.set(DAMAGE, tag.getFloat(DAMAGE_KEY));
        entityData.set(RANGE, tag.getFloat(RANGE_KEY));
        ListTag list = tag.getList(DAMAGED_KEY, Tag.TAG_INT);
        IntStream.range(0, list.size()).mapToObj(list::getInt).forEach(damaged::add);
    }

    @Override
    protected void writeNbt(CompoundTag tag) {
        tag.putFloat(DAMAGE_KEY, entityData.get(DAMAGE));
        tag.putFloat(RANGE_KEY, entityData.get(RANGE));
        ListTag list = new ListTag(damaged.size());
        damaged.intStream().mapToObj(IntTag::valueOf).forEach(list::add);
        tag.put(DAMAGED_KEY, list);
    }

    @Override
    public void tick() {
        Level level = level();
        float damage = getDamage();
        if (timeSinceImpact == -1) {
            setPos(position().add(getDeltaMovement()));
            if (level.isClientSide()) {
                AMClientUtil.spawnFallingStarParticles(this, false);
            }
            HitResult result = AMUtil.getHitResult(position(), position().add(0, 0.01, 0), this, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE);
            if (result.getType() == HitResult.Type.MISS) return;
            if (result.getType() == HitResult.Type.BLOCK) {
                Vec3 vec = result.getLocation();
                do {
                    vec = vec.add(0, 1, 0);
                } while (level.getBlockState(BlockPos.containing(vec)).isSolid());
                setPos(vec.x(), (int) vec.y(), vec.z());
                if (level.isClientSide()) {
                    AMClientUtil.spawnFallingStarParticles(this, true);
                }
            }
        }
        timeSinceImpact++;
        for (Entity entity : level.getEntities(this, getBoundingBox().inflate(timeSinceImpact, AMServerConfig.FALLING_STAR_HEIGHT.get(), timeSinceImpact))) {
            int id = entity.getId();
            if (damaged.contains(id) || entity instanceof Player player && player.isCreative() || distanceTo(entity) > timeSinceImpact) continue;
            entity.hurt(damageSource, damage);
            damaged.add(id);
        }
        if (timeSinceImpact > getRange()) {
            remove(RemovalReason.KILLED);
        }
    }

    public float getDamage() {
        return entityData.get(DAMAGE);
    }

    public void setDamage(float damage) {
        entityData.set(DAMAGE, damage);
    }

    public float getRange() {
        return entityData.get(RANGE);
    }

    public void setRange(float radius) {
        entityData.set(RANGE, radius);
    }
}
