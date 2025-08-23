package at.minecraftschurli.arsmagicalegacy.api.helper;

import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;

public interface MagicHelper {
    int getLevel(Player player);

    double getXp(Player player);

    double getXpForNextLevel(int level);

    void awardLevel(Player player, int level);

    void setLevel(Player player, int level);

    void awardXp(Player player, double xp);

    void setXp(Player player, double xp);

    boolean knowsMagic(Player player);

    void initiateMagic(Player player);

    boolean knows(Player player, Holder<Skill> skill);

    boolean canLearn(Player player, Holder<Skill> skill);

    void learn(Player player, Holder<Skill> skill);

    void forget(Player player, Holder<Skill> skill);

    void learnAll(Player player);

    void forgetAll(Player player);

    int getSkillPoint(Player player, Holder<SkillPoint> skillPoint);

    void addSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount);

    void addSkillPoint(Player player, Holder<SkillPoint> skillPoint);

    void removeSkillPoint(Player player, Holder<SkillPoint> skillPoint, int amount);

    void removeSkillPoint(Player player, Holder<SkillPoint> skillPoint);
}
