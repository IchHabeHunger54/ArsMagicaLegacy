package at.minecraftschurli.arsmagicalegacy.api;

import net.minecraft.world.entity.LivingEntity;

public interface ManaHelper {
    double getManaBase();

    double getManaMultiplier();

    double getManaRegenerationMultiplier();

    double getMana(LivingEntity entity);

    double getMaxMana(LivingEntity entity);

    double getManaRegeneration(LivingEntity entity);

    boolean setMana(LivingEntity entity, double amount);

    boolean increaseMana(LivingEntity entity, double amount);

    boolean decreaseMana(LivingEntity entity, double amount);

    boolean setMaxMana(LivingEntity entity, double amount);

    boolean setManaRegeneration(LivingEntity entity, double amount);
}
