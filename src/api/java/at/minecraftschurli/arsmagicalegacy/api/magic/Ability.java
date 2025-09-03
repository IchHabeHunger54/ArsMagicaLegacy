package at.minecraftschurli.arsmagicalegacy.api.magic;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

public record Ability(Holder<Affinity> affinity, MinMaxBounds.Doubles bounds) implements Predicate<Player> {
    public static final Codec<Ability> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Affinity.CODEC.fieldOf("affinity").forGetter(Ability::affinity),
        MinMaxBounds.Doubles.CODEC.fieldOf("bounds").forGetter(Ability::bounds)
    ).apply(inst, Ability::new));
    public static final Codec<Holder<Ability>> CODEC = RegistryFileCodec.create(AMRegistryKeys.ABILITY, Ability.DIRECT_CODEC);

    @Override
    public boolean test(Player player) {
        return bounds.matches(ArsMagicaApi.magicHelper().getAffinityDepth(player, affinity));
    }
}
