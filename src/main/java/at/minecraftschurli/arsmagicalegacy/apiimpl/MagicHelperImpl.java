package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.helper.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.helper.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.helper.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.init.AMAttachments;
import net.minecraft.world.entity.player.Player;

final class MagicHelperImpl implements MagicHelper {
    @Override
    public int getLevel(Player player) {
        return player.getData(AMAttachments.MAGIC_LEVEL).level();
    }

    @Override
    public double getXp(Player player) {
        return player.getData(AMAttachments.MAGIC_LEVEL).xp();
    }

    @Override
    public double getXpForNextLevel(int level) {
        return level <= 0 ? 0 : AMServerConfig.LEVEL_MULTIPLIER.get() * Math.pow(AMServerConfig.LEVEL_BASE.get(), level - 1);
    }

    @Override
    public void awardLevel(Player player, int level) {
        setLevel(player, getLevel(player) + level);
    }

    @Override
    public void setLevel(Player player, int level) {
        player.setData(AMAttachments.MAGIC_LEVEL, player.getData(AMAttachments.MAGIC_LEVEL).setLevel(level));
        //TODO add skill points
        ManaHelper manaHelper = ArsMagicaApi.getManaHelper();
        double oldMaxMana = manaHelper.getMaxMana(player);
        double newMaxMana = manaHelper.getManaBase() + manaHelper.getManaMultiplier() * (level - 1);
        manaHelper.setMaxMana(player, newMaxMana);
        manaHelper.increaseMana(player, newMaxMana - oldMaxMana);
        manaHelper.setManaRegeneration(player, newMaxMana * manaHelper.getManaRegenerationMultiplier());
        BurnoutHelper burnoutHelper = ArsMagicaApi.getBurnoutHelper();
        double oldMaxBurnout = burnoutHelper.getMaxBurnout(player);
        double newMaxBurnout = burnoutHelper.getBurnoutBase() + burnoutHelper.getBurnoutMultiplier() * (level - 1);
        burnoutHelper.setMaxBurnout(player, newMaxBurnout);
        burnoutHelper.decreaseBurnout(player, newMaxBurnout - oldMaxBurnout);
        burnoutHelper.setBurnoutRegeneration(player, burnoutHelper.getBurnoutBase() * burnoutHelper.getBurnoutRegenerationMultiplier());
    }

    @Override
    public void awardXp(Player player, double xp) {
        setXp(player, getXp(player) + xp);
    }

    @Override
    public void setXp(Player player, double xp) {
        int level = getLevel(player);
        double xpForNextLevel = getXpForNextLevel(level);
        while (xp >= xpForNextLevel) {
            xp -= xpForNextLevel;
            level++;
            xpForNextLevel = getXpForNextLevel(level);
        }
        player.setData(AMAttachments.MAGIC_LEVEL, player.getData(AMAttachments.MAGIC_LEVEL).setXp(xp));
        if (level > getLevel(player)) {
            setLevel(player, level);
        }
    }

    @Override
    public boolean knowsMagic(Player player) {
        return getLevel(player) > 0 || AMServerConfig.MAGIC_ADVANCEMENT.get().isEmpty() || player.isCreative() || player.isSpectator();
    }

    @Override
    public void initiateMagic(Player player) {
        if (getLevel(player) <= 0) {
            setLevel(player, 1);
            ManaHelper manaHelper = ArsMagicaApi.getManaHelper();
            manaHelper.setMana(player, manaHelper.getMaxMana(player));
            //TODO add skill points
        }
    }
}
