package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class IceGuardian extends AbstractBoss {
    public IceGuardian(EntityType<? extends IceGuardian> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 300)
            .add(Attributes.ARMOR, 20)
            .add(AMAttributes.MAX_MANA, 3000)
            .add(AMAttributes.MAX_BURNOUT, 3000);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.ICE_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.ICE_GUARDIAN_DEATH.get();
    }
}
