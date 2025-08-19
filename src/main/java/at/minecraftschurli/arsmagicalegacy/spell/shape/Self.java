package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.magic.Spell;
import at.minecraftschurli.arsmagicalegacy.api.magic.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.magic.SpellModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Self extends PrimarySpellShape {
    @Override
    public SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster) {
        return ArsMagicaApi.getSpellHelper().castSecondaryOrGrammar(spell, caster, caster, new EntityHitResult(caster));
    }
}
