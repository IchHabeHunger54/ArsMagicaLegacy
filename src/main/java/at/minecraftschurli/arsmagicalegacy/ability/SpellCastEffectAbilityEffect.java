package at.minecraftschurli.arsmagicalegacy.ability;

import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.ability.EventTriggeredAbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public record SpellCastEffectAbilityEffect(MobEffectInstance effect, double chance) implements EventTriggeredAbilityEffect<SpellCastEvent.Post> {
    public static final MapCodec<SpellCastEffectAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        MobEffectInstance.CODEC.fieldOf("effect").forGetter(SpellCastEffectAbilityEffect::effect),
        Codec.DOUBLE.fieldOf("chance").forGetter(SpellCastEffectAbilityEffect::chance)
    ).apply(inst, SpellCastEffectAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void apply(SpellCastEvent.Post event, Player player, Holder<Ability> ability) {
        if (player instanceof ServerPlayer serverPlayer && serverPlayer.getRandom().nextDouble() < chance) {
            player.addEffect(new MobEffectInstance(effect));
        }
    }
}
