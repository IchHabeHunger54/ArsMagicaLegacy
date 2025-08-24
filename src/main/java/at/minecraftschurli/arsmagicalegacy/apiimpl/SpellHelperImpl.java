package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.event.BurnoutCostCalculationEvent;
import at.minecraftschurli.arsmagicalegacy.api.event.ManaCostCalculationEvent;
import at.minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import at.minecraftschurli.arsmagicalegacy.api.event.SpellPartCastEvent;
import at.minecraftschurli.arsmagicalegacy.api.helper.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.helper.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.helper.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;

import java.util.List;

final class SpellHelperImpl implements SpellHelper {
    @Override
    public SpellCastResult cast(Spell spell, LivingEntity caster, boolean consume, boolean awardXp) {
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
        double manaCost = NeoForge.EVENT_BUS.post(new ManaCostCalculationEvent(caster, spell, spell.getManaCost(), burnoutHelper.getBurnout(caster))).getResult();
        double burnoutCost = NeoForge.EVENT_BUS.post(new BurnoutCostCalculationEvent(caster, spell, spell.grammar().getBurnoutCost())).getBurnout();
        SpellCastEvent.Pre event = new SpellCastEvent.Pre(caster, spell, manaCost, burnoutCost, consume, awardXp);
        if (event.isCanceled()) return SpellCastResult.fail(event.getCancellationMessage());
        if (event.isConsume() && !(caster instanceof Player player && player.isCreative())) {
            if (manaHelper.getMana(caster) < manaCost)
                return SpellCastResult.fail(AMTranslations.SPELL_CAST_NOT_ENOUGH_MANA);
            if (burnoutHelper.getMaxBurnout(caster) - burnoutHelper.getBurnout(caster) < burnoutCost)
                return SpellCastResult.fail(AMTranslations.SPELL_CAST_BURNED_OUT);
        }
        SpellCastResult result = castPrimary(spell, caster);
        if (event.isConsume() && !(caster instanceof Player player && player.isCreative())) {
            manaHelper.decreaseMana(caster, manaCost);
            burnoutHelper.increaseBurnout(caster, burnoutCost);
        }
        if (event.isAwardXp() && !result.result().isFalse() && caster instanceof Player player) {
            ArsMagicaApi.magicHelper().awardXp(player, manaCost / 10);
        }
        NeoForge.EVENT_BUS.post(new SpellCastEvent.Post(caster, spell, manaCost, burnoutCost, event.isConsume(), event.isAwardXp()));
        return result;
    }

    @Override
    public SpellCastResult castPrimary(Spell spell, LivingEntity caster) {
        PrimarySpellShape primary = spell.currentShapeGroup().primaryShape();
        if (primary == null) return SpellCastResult.fail(AMTranslations.SPELL_CAST_MALFORMED);
        List<SpellModifier> modifiers = spell.currentShapeGroup().primaryModifiers();
        SpellCastResult result = primary.cast(spell, modifiers, caster);
        NeoForge.EVENT_BUS.post(new SpellPartCastEvent.PrimaryShape(caster, result.spell(), primary, modifiers));
        return result;
    }

    @Override
    public SpellCastResult castSecondary(Spell spell, LivingEntity caster, Entity directEntity) {
        SecondarySpellShape secondary = spell.currentShapeGroup().secondaryShape();
        if (secondary == null) return SpellCastResult.fail(AMTranslations.SPELL_CAST_MALFORMED);
        List<SpellModifier> modifiers = spell.currentShapeGroup().secondaryModifiers();
        SpellCastResult result = secondary.cast(spell, modifiers, caster, directEntity);
        NeoForge.EVENT_BUS.post(new SpellPartCastEvent.SecondaryShape(caster, result.spell(), secondary, modifiers, directEntity));
        return result;
    }

    @Override
    public SpellCastResult castGrammar(Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        SpellCastResult result = SpellCastResult.success(spell);
        for (Pair<SpellComponent, List<SpellModifier>> pair : spell.grammar().components()) {
            SpellComponent component = pair.getFirst();
            List<SpellModifier> modifiers = pair.getSecond();
            result = component.cast(result.spell(), modifiers, caster, directEntity, hitResult);
            NeoForge.EVENT_BUS.post(new SpellPartCastEvent.Component(caster, result.spell(), component, modifiers, directEntity, hitResult));
            if (!result.result().isFalse()) return result;
        }
        return SpellCastResult.success(result.spell());
    }

    @Override
    public SpellCastResult castSecondaryOrGrammar(Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        return spell.currentShapeGroup().secondaryShape() != null ? castSecondary(spell, caster, directEntity) : castGrammar(spell, caster, directEntity, hitResult);
    }
}
