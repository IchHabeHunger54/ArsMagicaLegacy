package at.minecraftschurli.arsmagicalegacy.ability;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.ability.EventTriggeredAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.event.ManaCostCalculationEvent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;

public record ManaCostModifierAbilityEffect(double min, double max) implements EventTriggeredAbilityEffect<ManaCostCalculationEvent> {
    public static final MapCodec<ManaCostModifierAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.DOUBLE.fieldOf("min").forGetter(ManaCostModifierAbilityEffect::min),
        Codec.DOUBLE.fieldOf("max").forGetter(ManaCostModifierAbilityEffect::max)
    ).apply(inst, ManaCostModifierAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void apply(ManaCostCalculationEvent event, Player player, Holder<Ability> ability) {
        event.addModifier(mana -> mana * ArsMagicaApi.abilityHelper().scaleToDepth(player, ability.value(), min, max));
    }
}
