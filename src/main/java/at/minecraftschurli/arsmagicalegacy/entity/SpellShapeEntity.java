package at.minecraftschurli.arsmagicalegacy.entity;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
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
    private static final EntityDataAccessor<Boolean> CONSUME = SynchedEntityData.defineId(SpellShapeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> AWARD_XP = SynchedEntityData.defineId(SpellShapeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final String TARGET_NON_SOLID_KEY = "target_non_solid";
    private static final String SPELL_KEY = "spell";
    private static final String CONSUME_KEY = "consume";
    private static final String AWARD_XP_KEY = "award_xp";

    public SpellShapeEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TARGET_NON_SOLID, false)
            .define(SPELL, Spell.EMPTY)
            .define(CONSUME, true)
            .define(AWARD_XP, true);
    }

    @Override
    protected void readNbt(CompoundTag tag) {
        entityData.set(TARGET_NON_SOLID, tag.getBoolean(TARGET_NON_SOLID_KEY));
        entityData.set(SPELL, Spell.CODEC.decode(NbtOps.INSTANCE, tag.getCompound(SPELL_KEY)).getOrThrow().getFirst());
        entityData.set(CONSUME, tag.getBoolean(CONSUME_KEY));
        entityData.set(AWARD_XP, tag.getBoolean(AWARD_XP_KEY));
    }

    @Override
    protected void writeNbt(CompoundTag tag) {
        tag.putBoolean(TARGET_NON_SOLID_KEY, entityData.get(TARGET_NON_SOLID));
        tag.put(SPELL_KEY, Spell.CODEC.encodeStart(NbtOps.INSTANCE, getSpell()).getOrThrow());
        tag.putBoolean(CONSUME_KEY, entityData.get(CONSUME));
        tag.putBoolean(AWARD_XP_KEY, entityData.get(AWARD_XP));
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
        LivingEntity owner = getOwner();
        if (owner != null) {
            owner.getItemInHand(InteractionHand.MAIN_HAND);
        }
    }

    public boolean getConsume() {
        return entityData.get(CONSUME);
    }

    public void setConsume(boolean consume) {
        entityData.set(CONSUME, consume);
    }

    public boolean getAwardXp() {
        return entityData.get(AWARD_XP);
    }

    public void setAwardXp(boolean awardXp) {
        entityData.set(AWARD_XP, awardXp);
    }

    protected void spawnParticles(Vec3 position) {
        if (level().isClientSide()) {
            AMClientUtil.spawnSpellEntityParticles(this, getSpell(), position, getColor(), getOwner());
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
            SpellCastResult result = secondary
                ? ArsMagicaApi.spellHelper().castSecondaryOrGrammar(new SpellCastContext(spell, level(), owner, this, hitResult, getConsume(), getAwardXp()))
                : ArsMagicaApi.spellHelper().castGrammar(new SpellCastContext(spell, level(), owner, this, hitResult, getConsume(), getAwardXp()));
            setSpell(result.getSpell());
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
            SpellCastResult result = secondary
                ? ArsMagicaApi.spellHelper().castSecondaryOrGrammar(new SpellCastContext(spell, level(), owner, this, hitResult, getConsume(), getAwardXp()))
                : ArsMagicaApi.spellHelper().castGrammar(new SpellCastContext(spell, level(), owner, this, hitResult, getConsume(), getAwardXp()));
            setSpell(result.getSpell());
            spawnParticles(pos.getBottomCenter());
        });
        setSpell(spell);
    }
}
