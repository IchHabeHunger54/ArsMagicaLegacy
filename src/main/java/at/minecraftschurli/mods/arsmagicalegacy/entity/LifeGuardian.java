package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class LifeGuardian extends AbstractBoss {
    public LifeGuardian(EntityType<? extends LifeGuardian> type, Level level) {
        super(type, level, AMTags.DamageTypes.LIFE_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.LIFE_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.LIFE_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 400)
            .add(Attributes.ARMOR, 10)
            .add(AMAttributes.MAX_MANA, 2500)
            .add(AMAttributes.MAX_BURNOUT, 2500);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.LIFE_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.LIFE_GUARDIAN_ATTACK.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.LIFE_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AMSounds.LIFE_GUARDIAN_HURT.get();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return source.is(DamageTypes.FELL_OUT_OF_WORLD) && super.hurtServer(level, source, damage);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }
}
