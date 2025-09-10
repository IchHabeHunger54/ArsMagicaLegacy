package at.minecraftschurli.arsmagicalegacy.ability;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.init.AMAbilities;
import at.minecraftschurli.arsmagicalegacy.util.LinearAttributeModifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public record AttributeAbilityEffect(Map<Holder<Attribute>, LinearAttributeModifier> modifiers) implements AbilityEffect {
    public static final MapCodec<AttributeAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.unboundedMap(Attribute.CODEC, LinearAttributeModifier.CODEC).fieldOf("modifiers").forGetter(AttributeAbilityEffect::modifiers)
    ).apply(inst, AttributeAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return AMAbilities.ATTRIBUTE_EFFECT.get();
    }

    @Override
    public void shiftInto(Player player, Ability ability) {
        AbilityHelper abilityHelper = ArsMagicaApi.abilityHelper();
        MagicHelper magicHelper = ArsMagicaApi.magicHelper();
        for (Map.Entry<Holder<Attribute>, LinearAttributeModifier> entry : this.modifiers.entrySet()) {
            AttributeInstance attribute = player.getAttribute(entry.getKey());
            if (attribute != null) {
                attribute.addOrUpdateTransientModifier(entry.getValue().toAttributeModifier(abilityHelper.getDepthPercent(magicHelper.getAffinityDepth(player, ability.affinity()), ability)));
            }
        }
    }

    @Override
    public void shiftOutOf(Player player, Ability ability) {
        for (Map.Entry<Holder<Attribute>, LinearAttributeModifier> entry : this.modifiers.entrySet()) {
            AttributeInstance attribute = player.getAttribute(entry.getKey());
            if (attribute != null) {
                attribute.removeModifier(entry.getValue().id());
            }
        }
    }
}
