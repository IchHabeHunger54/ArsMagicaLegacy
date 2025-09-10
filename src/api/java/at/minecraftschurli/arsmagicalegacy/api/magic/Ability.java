package at.minecraftschurli.arsmagicalegacy.api.magic;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

/**
 * Represents an affinity ability.
 *
 * @param affinity The {@link Affinity} to associate the ability with.
 * @param bounds   The {@link MinMaxBounds.Doubles} within which the ability becomes active. Should overlap with the range [0, 1].
 * @param negative Whether the ability should be considered negative or not.
 */
public record Ability(Holder<Affinity> affinity, MinMaxBounds.Doubles bounds, boolean negative) implements Predicate<Player> {
    public static final Codec<Ability> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Affinity.CODEC.fieldOf("affinity").forGetter(Ability::affinity),
        MinMaxBounds.Doubles.CODEC.fieldOf("bounds").forGetter(Ability::bounds),
        Codec.BOOL.optionalFieldOf("negative", false).forGetter(Ability::negative)
    ).apply(inst, Ability::new));
    public static final Codec<Holder<Ability>> CODEC = RegistryFileCodec.create(AMRegistryKeys.ABILITY, Ability.DIRECT_CODEC);

    /**
     * @param affinity The {@link Affinity} to associate the ability with.
     * @param bounds   The {@link MinMaxBounds.Doubles} within which the ability becomes active. Should overlap with the range [0, 1].
     */
    public Ability(Holder<Affinity> affinity, MinMaxBounds.Doubles bounds) {
        this(affinity, bounds, false);
    }

    @Override
    public boolean test(Player player) {
        return bounds.matches(ArsMagicaApi.magicHelper().getAffinityDepth(player, affinity));
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
