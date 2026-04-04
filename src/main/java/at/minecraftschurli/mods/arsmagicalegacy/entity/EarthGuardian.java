package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class EarthGuardian extends AbstractBoss {
    public EarthGuardian(EntityType<? extends EarthGuardian> type, Level level) {
        super(type, level, AMTags.DamageTypes.EARTH_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.EARTH_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.EARTH_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 120)
            .add(Attributes.ARMOR, 10)
            .add(AMAttributes.MAX_MANA, 1000)
            .add(AMAttributes.MAX_BURNOUT, 1000);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.EARTH_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.EARTH_GUARDIAN_ATTACK.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.EARTH_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AMSounds.EARTH_GUARDIAN_HURT.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }
}
