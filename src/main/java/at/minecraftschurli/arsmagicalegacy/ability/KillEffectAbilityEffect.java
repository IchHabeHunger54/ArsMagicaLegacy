package at.minecraftschurli.arsmagicalegacy.ability;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.ability.EventTriggeredAbilityEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.Optional;

public record KillEffectAbilityEffect(double min, double max, MobEffectInstance effect, Optional<HolderSet<EntityType<?>>> entities) implements EventTriggeredAbilityEffect<LivingDeathEvent> {
    public static final MapCodec<KillEffectAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.DOUBLE.fieldOf("min").forGetter(KillEffectAbilityEffect::min),
        Codec.DOUBLE.fieldOf("max").forGetter(KillEffectAbilityEffect::max),
        MobEffectInstance.CODEC.fieldOf("effect").forGetter(KillEffectAbilityEffect::effect),
        HolderSetCodec.create(Registries.ENTITY_TYPE, BuiltInRegistries.ENTITY_TYPE.holderByNameCodec(), false).optionalFieldOf("entities").forGetter(KillEffectAbilityEffect::entities)
    ).apply(inst, KillEffectAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void apply(LivingDeathEvent event, Player player, Holder<Ability> ability) {
        if (entities.isPresent() && entities.get().contains(event.getEntity().getType().builtInRegistryHolder())) return;
        player.addEffect(new MobEffectInstance(effect.getEffect(), (int) ArsMagicaApi.abilityHelper().scaleToDepth(player, ability.value(), min, max), effect.getDuration(), effect.isAmbient(), effect.isVisible(), effect.showIcon()));
    }
}
