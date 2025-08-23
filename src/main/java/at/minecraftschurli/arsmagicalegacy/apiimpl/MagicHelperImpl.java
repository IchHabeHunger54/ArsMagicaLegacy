package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.event.LevelChangeEvent;
import at.minecraftschurli.arsmagicalegacy.api.helper.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.helper.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.helper.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicAttachment;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

final class MagicHelperImpl implements MagicHelper {
    @Override
    public int getLevel(Player player) {
        return player.getData(AMAttachments.MAGIC).level();
    }

    @Override
    public double getXp(Player player) {
        return player.getData(AMAttachments.MAGIC).xp();
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
        MagicAttachment data = player.getData(AMAttachments.MAGIC);
        int oldLevel = data.level();
        NeoForge.EVENT_BUS.post(new LevelChangeEvent(player, oldLevel, level));
        player.setData(AMAttachments.MAGIC, data.setLevel(level));
        List<Holder.Reference<SkillPoint>> skillPoints = player.registryAccess().registryOrThrow(AMRegistryKeys.SKILL_POINT).holders().toList();
        for (int i = oldLevel; i <= level; i++) {
            for (Holder<SkillPoint> holder : skillPoints) {
                SkillPoint skillPoint = holder.value();
                int minEarnLevel = skillPoint.minEarnLevel();
                int levelsForPoint = skillPoint.levelsForPoint();
                if (minEarnLevel >= 0 && levelsForPoint >= 0 && i >= minEarnLevel && (i - minEarnLevel) % levelsForPoint == 0) {
                    addSkillPoint(player, holder);
                }
            }
        }
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
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).setXp(xp));
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
        setLevel(player, Math.max(1, getLevel(player)));
        ManaHelper manaHelper = ArsMagicaApi.getManaHelper();
        manaHelper.setMana(player, manaHelper.getMaxMana(player));
        player.registryAccess().registryOrThrow(AMRegistryKeys.SKILL_POINT).getHolder(AMMagic.BLUE_POINT).ifPresent(skillPoint -> addSkillPoint(player, skillPoint, AMServerConfig.EXTRA_SKILL_POINTS.get()));
    }

    @Override
    public boolean knows(Player player, Holder<Skill> skill) {
        return player.getData(AMAttachments.MAGIC).skills().contains(skill);
    }

    @Override
    public boolean canLearn(Player player, Holder<Skill> skill) {
        if (knows(player, skill)) return false;
        MagicAttachment data = player.getData(AMAttachments.MAGIC);
        boolean hasSkillPoints = skill
            .value()
            .cost()
            .filter(e -> getSkillPoint(player, e) > 0)
            .isPresent();
        boolean hasParents = data.skills()
            .stream()
            .map(Holder::value)
            .collect(Collectors.toSet())
            .containsAll(skill.value().getParents(player.registryAccess()));
        return hasSkillPoints && hasParents;
    }

    @Override
    public void learn(Player player, Holder<Skill> skill) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkills(set -> set.add(skill)));
    }

    @Override
    public void forget(Player player, Holder<Skill> skill) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkills(set -> set.remove(skill)));
    }

    @Override
    public void learnAll(Player player) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkills(set -> set.addAll(player.registryAccess().registryOrThrow(AMRegistryKeys.SKILL).holders().toList())));
    }

    @Override
    public void forgetAll(Player player) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkills(Set::clear));
    }

    @Override
    public int getSkillPoint(Player player, Holder<SkillPoint> skillPoint) {
        return player.getData(AMAttachments.MAGIC).skillPoints().getOrDefault(skillPoint, 0);
    }

    @Override
    public void addSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkillPoints(map -> map.compute(skillPoint, (k, v) -> v == null ? amount : v + amount)));
    }

    @Override
    public void addSkillPoint(Player player, Holder<SkillPoint> skillPoint) {
        addSkillPoint(player, skillPoint, 1);
    }

    @Override
    public void removeSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkillPoints(map -> map.computeIfPresent(skillPoint, (k, v) -> Math.max(0, v - amount))));
    }

    @Override
    public void removeSkillPoint(Player player, Holder<SkillPoint> skillPoint) {
        removeSkillPoint(player, skillPoint, 1);
    }
}
