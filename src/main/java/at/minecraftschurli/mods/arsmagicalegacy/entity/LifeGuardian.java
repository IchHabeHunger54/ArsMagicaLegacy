package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class LifeGuardian extends AbstractBoss {
    public LifeGuardian(EntityType<? extends LifeGuardian> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 400)
            .add(Attributes.ARMOR, 10)
            .add(AMAttributes.MAX_MANA, 2500)
            .add(AMAttributes.MAX_BURNOUT, 2500);
    }
}
