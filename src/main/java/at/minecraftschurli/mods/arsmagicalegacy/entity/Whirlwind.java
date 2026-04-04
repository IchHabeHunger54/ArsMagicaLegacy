package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDamageSources;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Whirlwind extends Entity implements TraceableEntity {
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> OWNER = SynchedEntityData.defineId(NatureScythe.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    private static final String OWNER_KEY = "owner";
    private final Map<Player, Integer> cooldowns = new HashMap<>();

    public Whirlwind(EntityType<?> type, Level level) {
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
        if (!level().isClientSide() && tickCount > 140) {
            remove(RemovalReason.KILLED);
        }
        cooldowns.replaceAll((_, v) -> Math.max(v - 1, 0));
        setPos(position().add(getDeltaMovement()));
    }

    @Override
    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (!(level() instanceof ServerLevel level) || player.isCreative()) return;
        Integer cd = cooldowns.get(player);
        if (cd == null || cd <= 0) {
            if (random.nextInt(100) < 10) {
                int slot = player.getInventory().getNonEquipmentItems().size() + random.nextInt(4);
                ItemStack stack = player.getInventory().getItem(slot).copy();
                player.getInventory().setItem(slot, ItemStack.EMPTY);
                if (!player.getInventory().add(stack)) {
                    ItemEntity item = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), stack);
                    item.setDeltaMovement(random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.2 - 0.1);
                    level.addFreshEntity(item);
                }
            }
            player.hurtServer(level, AMDamageSources.whirlwind(this), 6);
            player.setDeltaMovement(getDeltaMovement().x() + random.nextFloat() * 0.2f, getDeltaMovement().y() + 0.8, getDeltaMovement().z() + random.nextFloat() * 0.2f);
            player.fallDistance = 0f;
            cooldowns.put(player, 20);
        }
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
