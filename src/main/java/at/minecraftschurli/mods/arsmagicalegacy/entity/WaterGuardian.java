package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class WaterGuardian extends AbstractBoss {
    public WaterGuardian(EntityType<? extends WaterGuardian> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 80)
            .add(Attributes.ARMOR, 10)
            .add(AMAttributes.MAX_MANA, 500)
            .add(AMAttributes.MAX_BURNOUT, 500);
    }
}
