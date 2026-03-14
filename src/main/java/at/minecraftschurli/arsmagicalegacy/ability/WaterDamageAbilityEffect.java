package at.minecraftschurli.arsmagicalegacy.ability;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

public record WaterDamageAbilityEffect(double min, double max) implements AbilityEffect {
    public static final MapCodec<WaterDamageAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.DOUBLE.fieldOf("min").forGetter(WaterDamageAbilityEffect::min),
        Codec.DOUBLE.fieldOf("max").forGetter(WaterDamageAbilityEffect::max)
    ).apply(inst, WaterDamageAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void tick(Player player, Holder<Ability> ability) {
        if (player.tickCount % 20 == 0 && player.isInWaterOrRain() && player.getHealth() / player.getMaxHealth() > 1 - ArsMagicaApi.abilityHelper().scaleToDepth(player, ability.value(), min, max)) {
            player.hurt(new DamageSource(player.damageSources().drown().typeHolder()), 1);
        }
    }
}
