package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.arsmagicalegacy.init.AMAttributes;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("DataFlowIssue")
final class ManaHelperImpl implements ManaHelper {
    @Override
    public double getManaBase() {
        return AMServerConfig.MANA_BASE.get();
    }

    @Override
    public double getManaMultiplier() {
        return AMServerConfig.MANA_MULTIPLIER.get();
    }

    @Override
    public double getManaRegenerationMultiplier() {
        return AMServerConfig.MANA_REGENERATION.get();
    }

    @Override
    public double getMana(LivingEntity entity) {
        return entity.isDeadOrDying() ? 0 : entity.getData(AMAttachments.MANA);
    }

    @Override
    public double getMaxMana(LivingEntity entity) {
        return entity.isDeadOrDying() || !entity.getAttributes().hasAttribute(AMAttributes.MAX_MANA) ? 0 : entity.getAttributeValue(AMAttributes.MAX_MANA);
    }

    @Override
    public double getManaRegeneration(LivingEntity entity) {
        return entity.isDeadOrDying() || !entity.getAttributes().hasAttribute(AMAttributes.MANA_REGENERATION) ? 0 : entity.getAttributeValue(AMAttributes.MANA_REGENERATION);
    }

    @Override
    public boolean setMana(LivingEntity entity, double amount) {
        if (amount < 0) return false;
        double max = getMaxMana(entity);
        if (max <= 0) return false;
        entity.setData(AMAttachments.MANA, Math.min(amount, max));
        return true;
    }

    @Override
    public boolean increaseMana(LivingEntity entity, double amount) {
        if (amount < 0) return false;
        double max = getMaxMana(entity);
        if (max <= 0) return false;
        entity.setData(AMAttachments.MANA, Math.min(entity.getData(AMAttachments.MANA) + amount, max));
        return true;
    }

    @Override
    public boolean decreaseMana(LivingEntity entity, double amount) {
        if (amount < 0) return false;
        double max = getMaxMana(entity);
        if (max <= 0) return false;
        entity.setData(AMAttachments.MANA, Math.max(entity.getData(AMAttachments.MANA) - amount, 0));
        return true;
    }

    @Override
    public boolean setMaxMana(LivingEntity entity, double amount) {
        if (!entity.getAttributes().hasAttribute(AMAttributes.MAX_MANA)) return false;
        entity.getAttribute(AMAttributes.MAX_MANA).setBaseValue(amount);
        return true;
    }

    @Override
    public boolean setManaRegeneration(LivingEntity entity, double amount) {
        if (!entity.getAttributes().hasAttribute(AMAttributes.MANA_REGENERATION)) return false;
        entity.getAttribute(AMAttributes.MANA_REGENERATION).setBaseValue(amount);
        return true;
    }
}
