package at.minecraftschurli.arsmagicalegacy.api.ability;

import at.minecraftschurli.arsmagicalegacy.api.magic.MagicAttachment;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;

import java.util.stream.Stream;

public interface AbilityHelper {
    void onAffinityChange(Player player, MagicAttachment oldData, MagicAttachment newData);

    Stream<? extends Holder<Ability>> getActiveAbilities(Player player);

    Stream<AbilityEffect> getActiveEffects(Player player);

    Stream<AbilityEffect> getActiveEffectsOfType(Player player, MapCodec<? extends AbilityEffect> codec);

    double getDepthPercent(double affinityDepth, Ability ability);
}
