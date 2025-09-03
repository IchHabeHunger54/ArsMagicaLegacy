package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.event.LevelChangeEvent;
import at.minecraftschurli.arsmagicalegacy.api.helper.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.helper.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.helper.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicAttachment;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMSounds;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public void addLevel(Player player, int level) {
        setLevel(player, getLevel(player) + level);
    }

    @Override
    public void setLevel(Player player, int level) {
        level = Math.max(0, level);
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
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        double oldMaxMana = manaHelper.getMaxMana(player);
        double newMaxMana = manaHelper.getManaBase() + manaHelper.getManaMultiplier() * (level - 1);
        manaHelper.setMaxMana(player, newMaxMana);
        manaHelper.increaseMana(player, newMaxMana - oldMaxMana);
        manaHelper.setManaRegeneration(player, newMaxMana * manaHelper.getManaRegenerationMultiplier());
        BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
        double oldMaxBurnout = burnoutHelper.getMaxBurnout(player);
        double newMaxBurnout = burnoutHelper.getBurnoutBase() + burnoutHelper.getBurnoutMultiplier() * (level - 1);
        burnoutHelper.setMaxBurnout(player, newMaxBurnout);
        burnoutHelper.decreaseBurnout(player, newMaxBurnout - oldMaxBurnout);
        burnoutHelper.setBurnoutRegeneration(player, burnoutHelper.getBurnoutBase() * burnoutHelper.getBurnoutRegenerationMultiplier());
        player.level().playSound(null, player, AMSounds.LEVEL_UP.get(), SoundSource.PLAYERS, 1, 1);
    }

    @Override
    public void addXp(Player player, double xp) {
        setXp(player, getXp(player) + xp);
    }

    @Override
    public void setXp(Player player, double xp) {
        xp = Math.max(0, xp);
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
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
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
        boolean hasParents = data.skills().containsAll(skill.value().parents());
        return hasSkillPoints && hasParents;
    }

    @Override
    public List<? extends Holder<Skill>> getKnown(Player player) {
        return player.registryAccess().registryOrThrow(AMRegistryKeys.SKILL).holders().filter(holder -> knows(player, holder)).toList();
    }

    @Override
    public List<? extends Holder<Skill>> getUnknown(Player player) {
        return player.registryAccess().registryOrThrow(AMRegistryKeys.SKILL).holders().filter(holder -> !knows(player, holder)).toList();
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
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkillPoints(map -> map.compute(skillPoint, (k, v) -> v == null ? Math.max(0, amount) : Math.max(0, v + amount))));
    }

    @Override
    public void addSkillPoint(Player player, Holder<SkillPoint> skillPoint) {
        addSkillPoint(player, skillPoint, 1);
    }

    @Override
    public void setSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).updateSkillPoints(map -> map.put(skillPoint, Math.max(0, amount))));
    }

    @Override
    public double getAffinityDepth(Player player, Holder<Affinity> affinity) {
        return affinity.is(Affinity.NONE) ? 0 : player.getData(AMAttachments.MAGIC).affinityShifts().get(affinity);
    }

    @Override
    public void setAffinityDepth(Player player, Holder<Affinity> affinity, double depth) {
        if (affinity.is(Affinity.NONE)) return;
        MagicAttachment data = player.getData(AMAttachments.MAGIC);
        if (data.affinityLocked()) return;
        player.setData(AMAttachments.MAGIC, data.updateAffinityShifts(map -> map.put(affinity, Math.clamp(depth, 0, 1))));
    }

    @Override
    public void addAffinityDepth(Player player, Holder<Affinity> affinity, double depth) {
        setAffinityDepth(player, affinity, player.getData(AMAttachments.MAGIC).affinityShifts().getOrDefault(affinity, 0.) + depth);
    }

    @Override
    public void applyAffinityShift(Player player, Holder<Affinity> affinity, double shift) {
        if (affinity.is(Affinity.NONE)) return;
        MagicAttachment data = player.getData(AMAttachments.MAGIC);
        if (data.affinityLocked()) return;
        Affinity value = affinity.value();
        double direct = shift * AMServerConfig.DIRECT_OPPOSITE_MULTIPLIER.get();
        double major = shift * AMServerConfig.MAJOR_OPPOSITE_MULTIPLIER.get();
        double minor = shift * AMServerConfig.MINOR_OPPOSITE_MULTIPLIER.get();
        double adjacent = shift * AMServerConfig.ADJACENT_MULTIPLIER.get();
        addAffinityDepth(player, value.directOpposite(), -direct);
        for (Holder<Affinity> holder : value.majorOpposites()) {
            addAffinityDepth(player, holder, -major);
        }
        for (Holder<Affinity> holder : value.minorOpposites()) {
            addAffinityDepth(player, holder, -minor);
        }
        for (Holder<Affinity> holder : value.adjacents()) {
            addAffinityDepth(player, holder, adjacent);
        }
        updateAffinityLock(player);
    }

    @Override
    public void applyAffinityShift(Player player, Map<Holder<Affinity>, Double> affinityShifts) {
        affinityShifts.forEach((k, v) -> applyAffinityShift(player, k, v));
    }

    @Override
    public void lockAffinities(Player player) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).setAffinityLocked(true));
    }

    @Override
    public void unlockAffinities(Player player) {
        player.setData(AMAttachments.MAGIC, player.getData(AMAttachments.MAGIC).setAffinityLocked(false));
    }

    @Override
    public void updateAffinityLock(Player player) {
        MagicAttachment data = player.getData(AMAttachments.MAGIC);
        player.setData(AMAttachments.MAGIC, data.setAffinityLocked(data.affinityShifts().values().stream().anyMatch(e -> e >= 1)));
    }
}
