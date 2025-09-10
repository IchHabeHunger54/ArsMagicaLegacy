package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityHelper;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicAttachment;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.google.common.collect.Sets;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class AbilityHelperImpl implements AbilityHelper {
    @Override
    public void onAffinityChange(Player player, MagicAttachment oldData, MagicAttachment newData) {
        Registry<Ability> registry = player.registryAccess().registryOrThrow(AMRegistryKeys.ABILITY);
        Set<Holder<Ability>> oldSet = registry.holders()
            .filter(ability -> ability.value().test(oldData))
            .collect(Collectors.toSet());
        Set<Holder<Ability>> newSet = registry.holders()
            .filter(ability -> ability.value().test(newData))
            .collect(Collectors.toSet());
        Set<Holder<Ability>> oldAbilities = Sets.difference(oldSet, newSet);
        Set<Holder<Ability>> newAbilities = Sets.difference(newSet, oldSet);
        if (!oldAbilities.isEmpty() || !newAbilities.isEmpty()) {
            Component message;
            if (oldAbilities.isEmpty()) {
                message = Component.translatable(newAbilities.size() == 1 ? AMTranslations.ABILITY_INTO_SINGLE_KEY : AMTranslations.ABILITY_INTO_MULTIPLE_KEY, joinAbilities(newAbilities));
            } else if (newAbilities.isEmpty()) {
                message = Component.translatable(oldAbilities.size() == 1 ? AMTranslations.ABILITY_OUT_OF_SINGLE_KEY : AMTranslations.ABILITY_OUT_OF_MULTIPLE_KEY, joinAbilities(oldAbilities));
            } else {
                message = Component.translatable(oldAbilities.size() == 1 && newAbilities.size() == 1 ? AMTranslations.ABILITY_INTO_SINGLE_OUT_OF_SINGLE_KEY
                    : oldAbilities.size() == 1 ? AMTranslations.ABILITY_INTO_MULTIPLE_OUT_OF_SINGLE_KEY
                    : newAbilities.size() == 1 ? AMTranslations.ABILITY_INTO_SINGLE_OUT_OF_MULTIPLE_KEY
                    : AMTranslations.ABILITY_INTO_MULTIPLE_OUT_OF_MULTIPLE_KEY, joinAbilities(newAbilities), joinAbilities(oldAbilities));
            }
            player.displayClientMessage(message, true);
        }
        oldSet.forEach(holder -> holder.value().shiftOutOf(player));
        newSet.forEach(holder -> holder.value().shiftInto(player));
    }

    @Override
    public Stream<? extends Holder<Ability>> getActiveAbilities(Player player) {
        return player.registryAccess()
            .registryOrThrow(AMRegistryKeys.ABILITY)
            .holders()
            .filter(e -> e.value().test(player));
    }

    @Override
    public Stream<AbilityEffect> getActiveEffects(Player player) {
        return getActiveAbilities(player)
            .map(Holder::value)
            .map(Ability::effects)
            .flatMap(List::stream);
    }

    @Override
    public Stream<AbilityEffect> getActiveEffectsOfType(Player player, MapCodec<? extends AbilityEffect> codec) {
        return getActiveEffects(player).filter(e -> e.codec() == codec);
    }

    @Override
    public double getDepthPercent(double affinityDepth, Ability ability) {
        double min = ability.bounds().min().orElse(0.);
        double max = ability.bounds().max().orElse(1.);
        return min == max ? affinityDepth == min ? 1 : 0 : Math.clamp((affinityDepth - min) / (max - min), 0, 1);
    }

    private Component joinAbilities(Set<Holder<Ability>> set) {
        return set.stream()
            .map(holder -> Ability.getName(holder).withColor(holder.value().affinity().value().color()))
            .collect(AMUtil.joiningComponents(AMTranslations.ABILITY_SEPARATOR));
    }
}
