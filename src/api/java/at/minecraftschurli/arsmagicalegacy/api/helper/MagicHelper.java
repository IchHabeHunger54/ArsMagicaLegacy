package at.minecraftschurli.arsmagicalegacy.api.helper;

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
}
