package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDamageSources;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Shockwave extends Entity implements TraceableEntity {
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> OWNER = SynchedEntityData.defineId(NatureScythe.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    private static final String OWNER_KEY = "owner";
    private static final ParticleOptions PARTICLE = new DustParticleOptions(0xffffff, 1);
    private final Map<LivingEntity, Integer> cooldowns = new HashMap<>();

    public Shockwave(EntityType<?> type, Level level) {
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
        Level level = level();
        if (!level.isClientSide() && tickCount > 60) {
            remove(RemovalReason.KILLED);
        }
        cooldowns.replaceAll((_, v) -> Math.max(v - 1, 0));
        if (level instanceof ServerLevel serverLevel) {
            for (Entity e : serverLevel.getEntities(this, getBoundingBox(), EntitySelector.pushableBy(this))) {
                if (!(e instanceof LivingEntity living) || living instanceof Player player && player.isCreative())
                    continue;
                Integer cooldown = cooldowns.get(living);
                if (cooldown == null || cooldown <= 0) {
                    living.hurtServer(serverLevel, AMDamageSources.shockwave(this), 2);
                    cooldowns.put(living, 20);
                }
            }
        } else {
            for (float f = -1f; f <= 1f; f += 0.1f) {
                level.addParticle(PARTICLE, position().x() + f * getDeltaMovement().x() + random.nextDouble() / 2, position().y(), position().z() + f * getDeltaMovement().z() + random.nextDouble() / 2, 0, 0, 0);
            }
        }
        setPos(position().add(getDeltaMovement()));
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
