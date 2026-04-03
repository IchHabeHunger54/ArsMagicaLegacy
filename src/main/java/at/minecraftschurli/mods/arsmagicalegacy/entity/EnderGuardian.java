package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class EnderGuardian extends AbstractBoss {
    public EnderGuardian(EntityType<? extends EnderGuardian> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 500)
            .add(Attributes.ARMOR, 20)
            .add(AMAttributes.MAX_MANA, 5000)
            .add(AMAttributes.MAX_BURNOUT, 5000);
    }
}
