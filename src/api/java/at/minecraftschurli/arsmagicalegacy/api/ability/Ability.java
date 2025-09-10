package at.minecraftschurli.arsmagicalegacy.api.ability;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicAttachment;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.Predicate;

/**
 * Represents an affinity ability.
 *
 * @param affinity The {@link Affinity} to associate the ability with.
 * @param bounds   The {@link MinMaxBounds.Doubles} within which the ability becomes active. Should overlap with the range [0, 1].
 * @param negative Whether the ability should be considered negative or not.
 * @param effects  A list of {@link AbilityEffect}s that this ability applies.
 */
public record Ability(Holder<Affinity> affinity, MinMaxBounds.Doubles bounds, boolean negative, List<AbilityEffect> effects) implements Predicate<Player> {
    public static final Codec<Ability> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Affinity.CODEC.fieldOf("affinity").forGetter(Ability::affinity),
        MinMaxBounds.Doubles.CODEC.fieldOf("bounds").forGetter(Ability::bounds),
        Codec.BOOL.optionalFieldOf("negative", false).forGetter(Ability::negative),
        AbilityEffect.CODEC.listOf().optionalFieldOf("effects", List.of()).forGetter(Ability::effects)
    ).apply(inst, Ability::new));
    public static final Codec<Holder<Ability>> CODEC = RegistryFileCodec.create(AMRegistryKeys.ABILITY, Ability.DIRECT_CODEC);

    /**
     * @param affinity The {@link Affinity} to associate the ability with.
     * @param bounds   The {@link MinMaxBounds.Doubles} within which the ability becomes active. Should overlap with the range [0, 1].
     * @param effects  A list of {@link AbilityEffect}s that this ability applies.
     */
    public Ability(Holder<Affinity> affinity, MinMaxBounds.Doubles bounds, List<AbilityEffect> effects) {
        this(affinity, bounds, false, effects);
    }

    /**
     * @param affinity The {@link Affinity} to associate the ability with.
     * @param bounds   The {@link MinMaxBounds.Doubles} within which the ability becomes active. Should overlap with the range [0, 1].
     * @param negative Whether the ability should be considered negative or not.
     * @param effect   The {@link AbilityEffect} that this ability applies.
     */
    public Ability(Holder<Affinity> affinity, MinMaxBounds.Doubles bounds, boolean negative, AbilityEffect effect) {
        this(affinity, bounds, negative, List.of(effect));
    }

    /**
     * @param affinity The {@link Affinity} to associate the ability with.
     * @param bounds   The {@link MinMaxBounds.Doubles} within which the ability becomes active. Should overlap with the range [0, 1].
     * @param effect   The {@link AbilityEffect} that this ability applies.
     */
    public Ability(Holder<Affinity> affinity, MinMaxBounds.Doubles bounds, AbilityEffect effect) {
        this(affinity, bounds, false, List.of(effect));
    }

    /**
     * @param player The {@link Player} to query.
     * @return Whether the given {@link Player} matches the bounds.
     */
    @Override
    public boolean test(Player player) {
        return bounds.matches(ArsMagicaApi.magicHelper().getAffinityDepth(player, affinity));
    }

    /**
     * @param data The {@link MagicAttachment} to query.
     * @return Whether the given {@link MagicAttachment} matches the bounds.
     */
    public boolean test(MagicAttachment data) {
        return bounds.matches(data.affinityShifts().get(affinity));
    }

    /**
     * Called when the given {@link Player} shifts into the ability's bounds.
     *
     * @param player The {@link Player} shifting into the ability's bounds.
     */
    public void shiftInto(Player player) {
        effects.forEach(effect -> effect.shiftInto(player, this));
    }

    /**
     * Called when the given {@link Player} shifts out of the ability's bounds.
     *
     * @param player The {@link Player} shifting out of the ability's bounds.
     */
    public void shiftOutOf(Player player) {
        effects.forEach(effect -> effect.shiftOutOf(player, this));
    }

    /**
     * @param holder The ability {@link Holder} to query.
     * @return The display name of the given ability.
     */
    @SuppressWarnings("DataFlowIssue")
    public static MutableComponent getName(Holder<Ability> holder) {
        return Component.translatable(Util.makeDescriptionId("ability", holder.getKey().location()));
    }
}
