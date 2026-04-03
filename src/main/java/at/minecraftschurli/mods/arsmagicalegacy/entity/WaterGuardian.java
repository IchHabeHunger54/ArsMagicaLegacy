package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class WaterGuardian extends AbstractBoss {
    public WaterGuardian(EntityType<? extends WaterGuardian> type, Level level) {
        super(type, level, AMTags.DamageTypes.WATER_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.WATER_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.WATER_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 80)
            .add(Attributes.ARMOR, 10)
            .add(AMAttributes.MAX_MANA, 500)
            .add(AMAttributes.MAX_BURNOUT, 500);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.WATER_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.WATER_GUARDIAN_DEATH.get();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return super.hurtServer(level, source, damage);
    }
}
