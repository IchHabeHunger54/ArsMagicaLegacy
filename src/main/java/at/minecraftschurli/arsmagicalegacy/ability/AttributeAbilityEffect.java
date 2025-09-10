/*
package at.minecraftschurli.arsmagicalegacy.ability;

import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.List;

public class AttributeAbilityEffect extends AbilityEffect {
    public static final MapCodec<AttributeAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        AttributeModifier.CODEC.listOf().fieldOf("modifiers").forGetter(e -> e.modifiers)
    ).apply(inst, AttributeAbilityEffect::new));
    private final List<AttributeModifier> modifiers;

    public AttributeAbilityEffect(Type<? extends AbilityEffect> type, List<AttributeModifier> modifiers) {
        super(type);
        this.modifiers = modifiers;
    }
}
*/
