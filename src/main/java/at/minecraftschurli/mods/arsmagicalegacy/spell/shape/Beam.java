package at.minecraftschurli.mods.arsmagicalegacy.spell.shape;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class Beam extends PrimarySpellShape {
    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        LivingEntity caster = context.caster();
        if (caster == null) return new SpellCastResult(spell).setMessage(AMTranslations.SPELL_FAIL_NO_CASTER);
        double range = 64;
        boolean targetNonSolid = ArsMagicaApi.spellHelper().getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, context) > 0;
        return ArsMagicaApi.spellHelper().castSecondaryOrGrammar(context.setDirectEntityAndHitResult(caster, AMUtil.getHitResult(caster, range, targetNonSolid)));
    }

    @Override
    public boolean isContinuous() {
        return true;
    }
}
