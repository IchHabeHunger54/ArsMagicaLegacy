package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class NatureGuardian extends AbstractBoss {
    public NatureGuardian(EntityType<? extends NatureGuardian> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 500)
            .add(Attributes.ARMOR, 20)
            .add(AMAttributes.MAX_MANA, 3500)
            .add(AMAttributes.MAX_BURNOUT, 3500);
    }
}
