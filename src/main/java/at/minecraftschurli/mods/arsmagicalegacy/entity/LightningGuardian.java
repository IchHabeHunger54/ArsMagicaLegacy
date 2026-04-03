package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class LightningGuardian extends AbstractBoss {
    public LightningGuardian(EntityType<? extends LightningGuardian> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 250)
            .add(Attributes.ARMOR, 20)
            .add(AMAttributes.MAX_MANA, 4000)
            .add(AMAttributes.MAX_BURNOUT, 4000);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.LIGHTNING_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.LIGHTNING_GUARDIAN_ATTACK.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.LIGHTNING_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AMSounds.LIGHTNING_GUARDIAN_HURT.get();
    }
}
