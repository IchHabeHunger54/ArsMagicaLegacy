package at.minecraftschurli.arsmagicalegacy.ability;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public record WaterHealthModifierAbilityEffect(double min, double max) implements AbilityEffect {
    public static final MapCodec<WaterHealthModifierAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.DOUBLE.fieldOf("min").forGetter(WaterHealthModifierAbilityEffect::min),
        Codec.DOUBLE.fieldOf("max").forGetter(WaterHealthModifierAbilityEffect::max)
    ).apply(inst, WaterHealthModifierAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void tick(Player player, Holder<Ability> ability) {
        AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (attribute == null) return;
        ResourceLocation location = ability.getKey().location();
        attribute.removeModifier(location);
        if (!player.isInWaterOrBubble()) return;
        attribute.addTransientModifier(new AttributeModifier(location, ArsMagicaApi.abilityHelper().scaleToDepth(player, ability.value(), min, max), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }
}
