package at.minecraftschurli.arsmagicalegacy.api.helper;

import net.minecraft.world.entity.LivingEntity;

public interface BurnoutHelper {
    double getBurnoutBase();

    double getBurnoutMultiplier();

    double getBurnoutRegenerationMultiplier();

    double getBurnout(LivingEntity entity);

    double getMaxBurnout(LivingEntity entity);

    double getBurnoutRegeneration(LivingEntity entity);

    boolean setBurnout(LivingEntity entity, double amount);

    boolean increaseBurnout(LivingEntity entity, double amount);

    boolean decreaseBurnout(LivingEntity entity, double amount);

    boolean setMaxBurnout(LivingEntity entity, double amount);

    boolean setBurnoutRegeneration(LivingEntity entity, double amount);

    double getManaToBurnoutRatio();
}
