package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Effect extends SpellComponent.CastEntity {
    private final Holder<MobEffect> effect;

    public Effect(Holder<MobEffect> effect) {
        super(AMSpells.DURATION_STAT, AMSpells.EFFECT_POWER_STAT);
        this.effect = effect;
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        if (!(hitResult.getEntity() instanceof LivingEntity living)) return SpellComponentCastResult.pass(spell);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        int amplifier = (int) helper.getModifiedStat(0, AMSpells.EFFECT_POWER_STAT, modifiers, context);
        if (!effect.value().isInstantenous()) {
            living.addEffect(new MobEffectInstance(effect, (int) helper.getModifiedStat(AMServerConfig.EFFECT_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context), amplifier));
        } else if (context.level() instanceof ServerLevel level) {
            effect.value().applyInstantenousEffect(level, context.directEntity(), context.caster(), living, amplifier, living.getHealth());
        }
        return SpellComponentCastResult.success(spell);
    }
}
