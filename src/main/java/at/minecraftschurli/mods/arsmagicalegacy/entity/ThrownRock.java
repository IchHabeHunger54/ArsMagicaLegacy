package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDamageSources;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class ThrownRock extends Entity implements TraceableEntity {
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> OWNER = SynchedEntityData.defineId(NatureScythe.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    private static final String OWNER_KEY = "owner";

    public ThrownRock(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        entityData.define(OWNER, Optional.empty());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        input.child(ArsMagicaApi.MOD_ID).ifPresent(child -> entityData.set(OWNER, Optional.ofNullable(EntityReference.readWithOldOwnerConversion(child, OWNER_KEY, level()))));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        ValueOutput child = output.child(ArsMagicaApi.MOD_ID);
        EntityReference.store(entityData.get(OWNER).orElse(null), child, OWNER_KEY);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (getOwner() == null || getOwner().isDeadOrDying() || tickCount >= 50) {
            setRemoved(RemovalReason.KILLED);
        }
        Vec3 oldPos = position();
        Vec3 newPos = position().add(getDeltaMovement());
        HitResult hit = AMUtil.getHitResult(oldPos, newPos, this, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE);
        if (hit.getType() != HitResult.Type.MISS) {
            newPos = hit.getLocation();
        }
        if (hit.getType() == HitResult.Type.ENTITY && !level().isClientSide()) {
            if (((EntityHitResult) hit).getEntity() instanceof LivingEntity living && living != getOwner() && level() instanceof ServerLevel level) {
                if (living.isBlocking()) {
                    living.stopUsingItem();
                    ItemStack itemBlockingWith = living.getItemBlockingWith();
                    if (itemBlockingWith != null && living instanceof Player player && random.nextFloat() < 0.25f) {
                        player.getCooldowns().addCooldown(itemBlockingWith, 100);
                    }
                } else {
                    living.hurtServer(level, AMDamageSources.thrownRock(this), 6);
                }
                setRemoved(RemovalReason.KILLED);
            }
        }
        setPos(newPos);
    }

    public void setOwner(@Nullable LivingEntity owner) {
        entityData.set(OWNER, owner == null ? Optional.empty() : Optional.of(EntityReference.of(owner)));
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        return EntityReference.getLivingEntity(entityData.get(OWNER).orElse(null), level());
    }
}
