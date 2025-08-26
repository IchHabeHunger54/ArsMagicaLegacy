package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
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
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;

import java.util.List;

final class SpellHelperImpl implements SpellHelper {
    @Override
    public Either<Spell, Component> cast(Spell spell, LivingEntity caster, boolean consume, boolean awardXp) {
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
        double manaCost = NeoForge.EVENT_BUS.post(new ManaCostCalculationEvent(caster, spell, spell.getManaCost(), burnoutHelper.getBurnout(caster))).getResult();
        double burnoutCost = NeoForge.EVENT_BUS.post(new BurnoutCostCalculationEvent(caster, spell, spell.grammar().getBurnoutCost())).getBurnout();
        SpellCastEvent.Pre event = new SpellCastEvent.Pre(caster, spell, manaCost, burnoutCost, consume, awardXp);
        if (event.isCanceled()) return Either.right(event.getCancellationMessage());
        if (event.isConsume() && !(caster instanceof Player player && player.isCreative())) {
            if (manaHelper.getMana(caster) < manaCost) return Either.right(AMTranslations.SPELL_CAST_NOT_ENOUGH_MANA);
            if (burnoutHelper.getMaxBurnout(caster) - burnoutHelper.getBurnout(caster) < burnoutCost) return Either.right(AMTranslations.SPELL_CAST_BURNED_OUT);
        }
        if (spell.currentShapeGroup().primaryShape() == null || spell.grammar().components().isEmpty()) return Either.right(AMTranslations.SPELL_CAST_MALFORMED);
        spell = castPrimary(spell, caster);
        if (event.isConsume() && !(caster instanceof Player player && player.isCreative())) {
            manaHelper.decreaseMana(caster, manaCost);
            burnoutHelper.increaseBurnout(caster, burnoutCost);
        }
        if (event.isAwardXp() && caster instanceof Player player) {
            ArsMagicaApi.magicHelper().addXp(player, manaCost / 10);
        }
        NeoForge.EVENT_BUS.post(new SpellCastEvent.Post(caster, spell, manaCost, burnoutCost, event.isConsume(), event.isAwardXp()));
        return Either.left(spell);
    }

    @Override
    public Spell castPrimary(Spell spell, LivingEntity caster) {
        PrimarySpellShape primary = spell.currentShapeGroup().primaryShape();
        List<SpellModifier> modifiers = spell.currentShapeGroup().primaryModifiers();
        if (primary != null) {
            spell = primary.cast(spell, modifiers, caster);
            NeoForge.EVENT_BUS.post(new SpellPartCastEvent.PrimaryShape(caster, spell, primary, modifiers));
        }
        return spell;
    }

    @Override
    public Spell castSecondary(Spell spell, LivingEntity caster, Entity directEntity) {
        SecondarySpellShape secondary = spell.currentShapeGroup().secondaryShape();
        List<SpellModifier> modifiers = spell.currentShapeGroup().secondaryModifiers();
        if (secondary != null) {
            spell = secondary.cast(spell, modifiers, caster, directEntity);
            NeoForge.EVENT_BUS.post(new SpellPartCastEvent.SecondaryShape(caster, spell, secondary, modifiers, directEntity));
        }
        return spell;
    }

    @Override
    public Spell castGrammar(Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        for (Pair<SpellComponent, List<SpellModifier>> pair : spell.grammar().components()) {
            SpellComponent component = pair.getFirst();
            List<SpellModifier> modifiers = pair.getSecond();
            spell = component.cast(spell, modifiers, caster, directEntity, hitResult);
            NeoForge.EVENT_BUS.post(new SpellPartCastEvent.Component(caster, spell, component, modifiers, directEntity, hitResult));
        }
        return spell;
    }

    @Override
    public Spell castSecondaryOrGrammar(Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        return spell.currentShapeGroup().secondaryShape() != null ? castSecondary(spell, caster, directEntity) : castGrammar(spell, caster, directEntity, hitResult);
    }

    @Override
    public double getManaToBurnoutRatio() {
        return AMServerConfig.MANA_TO_BURNOUT_RATIO.get();
    }
}
