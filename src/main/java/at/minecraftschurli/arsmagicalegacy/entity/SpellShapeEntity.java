package at.minecraftschurli.arsmagicalegacy.entity;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
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
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;

import java.util.function.Predicate;

public abstract class SpellShapeEntity extends SpellEntity {
    private static final EntityDataAccessor<Boolean> TARGET_NON_SOLID = SynchedEntityData.defineId(SpellShapeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Spell> SPELL = SynchedEntityData.defineId(SpellShapeEntity.class, AMSpells.DATA_SERIALIZER.get());
    private static final String TARGET_NON_SOLID_KEY = "target_non_solid";
    private static final String SPELL_KEY = "spell";

    public SpellShapeEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TARGET_NON_SOLID, false)
            .define(SPELL, Spell.EMPTY);
    }

    @Override
    protected void readNbt(CompoundTag tag) {
        entityData.set(TARGET_NON_SOLID, tag.getBoolean(TARGET_NON_SOLID_KEY));
        entityData.set(SPELL, Spell.CODEC.decode(NbtOps.INSTANCE, tag.getCompound(SPELL_KEY)).getOrThrow().getFirst());
    }

    @Override
    protected void writeNbt(CompoundTag tag) {
        tag.putBoolean(TARGET_NON_SOLID_KEY, entityData.get(TARGET_NON_SOLID));
        tag.put(SPELL_KEY, Spell.CODEC.encodeStart(NbtOps.INSTANCE, getSpell()).getOrThrow());
    }

    public boolean getTargetNonSolid() {
        return entityData.get(TARGET_NON_SOLID);
    }

    public void setTargetNonSolid(boolean targetNonSolid) {
        entityData.set(TARGET_NON_SOLID, targetNonSolid);
    }

    public Spell getSpell() {
        return entityData.get(SPELL);
    }

    public void setSpell(Spell spell) {
        entityData.set(SPELL, spell);
    }

    protected void spawnParticles(Vec3 position) {
        if (level().isClientSide()) {
            AMClientUtil.spawnSpellShapeEntityParticles(this, getSpell(), position, getColor(), getOwner());
        }
    }

    protected void castEntity(Entity entity, Predicate<Entity> entityPredicate, boolean secondary) {
        while (entity instanceof PartEntity<?> part) {
            entity = part.getParent();
        }
        if (!(entity instanceof SpellEntity) && entityPredicate.test(entity) && tryReflect(entity)) {
            Spell spell = getSpell();
            LivingEntity owner = getOwner();
            EntityHitResult hitResult = new EntityHitResult(entity);
            if (secondary) {
                ArsMagicaApi.spellHelper().castSecondaryOrGrammar(spell, owner, this, hitResult);
            } else {
                ArsMagicaApi.spellHelper().castGrammar(spell, owner, this, hitResult);
            }
        }
    }

    protected void castArea(AABB aabb, Predicate<BlockPos> blockPredicate, Predicate<Entity> entityPredicate, boolean secondary) {
        Spell spell = getSpell();
        LivingEntity owner = getOwner();
        for (Entity entity : level().getEntities(this, aabb)) {
            castEntity(entity, entityPredicate, secondary);
        }
        ClipContext.Block blockContext = getTargetNonSolid() ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER;
        ClipContext.Fluid fluidContext = getTargetNonSolid() ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE;
        BlockPos.betweenClosedStream(aabb).filter(blockPredicate).forEach(pos -> {
            HitResult hitResult = AMUtil.getHitResult(position(), position().add(getDeltaMovement()), this, blockContext, fluidContext);
            if (secondary) {
                ArsMagicaApi.spellHelper().castSecondaryOrGrammar(spell, owner, this, hitResult);
            } else {
                ArsMagicaApi.spellHelper().castGrammar(spell, owner, this, hitResult);
            }
            spawnParticles(pos.getBottomCenter());
        });
    }
}
