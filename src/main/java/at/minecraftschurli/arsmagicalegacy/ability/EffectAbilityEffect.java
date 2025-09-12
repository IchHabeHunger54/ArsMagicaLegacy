package at.minecraftschurli.arsmagicalegacy.ability;

import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public record EffectAbilityEffect(MobEffectInstance effect) implements AbilityEffect {
    public static final MapCodec<EffectAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        MobEffectInstance.CODEC.fieldOf("effect").forGetter(EffectAbilityEffect::effect)
    ).apply(inst, EffectAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void tick(Player player, Holder<Ability> ability) {
        player.addEffect(new MobEffectInstance(effect));
    }
}
