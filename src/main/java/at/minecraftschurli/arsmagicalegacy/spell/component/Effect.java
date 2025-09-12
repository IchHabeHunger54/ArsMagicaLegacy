package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Effect extends SpellComponent.CastEntity {
    private final Holder<MobEffect> effect;

    public Effect(Holder<MobEffect> effect) {
        this.effect = effect;
    }

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, EntityHitResult hitResult) {
        if (!(hitResult.getEntity() instanceof LivingEntity living)) return spell;
        if (effect.value().isInstantenous()) {
            effect.value().applyInstantenousEffect(directEntity, caster, living, 0, living.getHealth());
        } else {
            living.addEffect(new MobEffectInstance(effect, AMServerConfig.EFFECT_DURATION.get()));
        }
        return spell;
    }
}
