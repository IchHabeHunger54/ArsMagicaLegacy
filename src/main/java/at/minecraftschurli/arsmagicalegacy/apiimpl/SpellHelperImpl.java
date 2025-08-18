package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

final class SpellHelperImpl implements SpellHelper {
    @Override
    public SpellCastResult cast(Spell spell, LivingEntity caster, boolean consume) {
        double manaCost = spell.getManaCost();
        ManaHelper manaHelper = ArsMagicaApi.getManaHelper();
        if (consume && !(caster instanceof Player player && player.isCreative())) {
            if (manaHelper.getMana(caster) < manaCost) return SpellCastResult.fail(AMTranslations.SPELL_CAST_NOT_ENOUGH_MANA);
        }
        SpellCastResult result = castPrimary(spell, caster);
        if (consume && !(caster instanceof Player player && player.isCreative())) {
            manaHelper.decreaseMana(caster, manaCost);
        }
        return result;
    }

    @Override
    public SpellCastResult castPrimary(Spell spell, LivingEntity caster) {
        PrimarySpellShape primary = spell.currentShapeGroup().primaryShape();
        return primary == null ? SpellCastResult.fail(AMTranslations.SPELL_CAST_MALFORMED) : primary.cast(spell, spell.currentShapeGroup().primaryModifiers(), caster);
    }

    @Override
    public SpellCastResult castSecondary(Spell spell, LivingEntity caster, Entity directEntity) {
        SecondarySpellShape secondary = spell.currentShapeGroup().secondaryShape();
        return secondary == null ? SpellCastResult.fail(AMTranslations.SPELL_CAST_MALFORMED) : secondary.cast(spell, spell.currentShapeGroup().secondaryModifiers(), caster, directEntity);
    }

    @Override
    public SpellCastResult castGrammar(Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        SpellCastResult result = SpellCastResult.success(spell);
        for (Pair<SpellComponent, List<SpellModifier>> component : spell.grammar().components()) {
            result = component.getFirst().cast(result.spell(), component.getSecond(), caster, directEntity, hitResult);
            if (!result.result().isFalse()) return result;
        }
        return SpellCastResult.success(result.spell());
    }

    @Override
    public SpellCastResult castSecondaryOrGrammar(Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        return spell.currentShapeGroup().secondaryShape() != null ? castSecondary(spell, caster, directEntity) : castGrammar(spell, caster, directEntity, hitResult);
    }
}
