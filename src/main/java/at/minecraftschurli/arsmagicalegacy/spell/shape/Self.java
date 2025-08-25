package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Self extends PrimarySpellShape {
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster) {
        return ArsMagicaApi.spellHelper().castSecondaryOrGrammar(spell, caster, caster, new EntityHitResult(caster));
    }
}
