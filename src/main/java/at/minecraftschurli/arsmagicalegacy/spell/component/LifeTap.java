package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class LifeTap extends SpellComponent.CastEntity {
    public LifeTap() {
        super(AMSpells.DAMAGE_STAT, AMSpells.HEALING_STAT);
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        if (!(hitResult.getEntity() instanceof LivingEntity entity)) return SpellComponentCastResult.pass(spell);
        LivingEntity caster = context.caster();
        if (caster == null) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_CASTER);
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.pass(spell);
        float damage = (float) ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.LIFE_TAP_DAMAGE.get(), entity.isInvertedHealAndHarm() ? AMSpells.HEALING_STAT : AMSpells.DAMAGE_STAT, modifiers, context);
        if (entity.hurtServer(level, level.damageSources().indirectMagic(caster, context.directEntity()), damage)) {
            ManaHelper helper = ArsMagicaApi.manaHelper();
            helper.increaseMana(caster, damage * helper.getMaxMana(caster) * AMServerConfig.LIFE_TAP_FACTOR.get());
        }
        return SpellComponentCastResult.success(spell);
    }
}
