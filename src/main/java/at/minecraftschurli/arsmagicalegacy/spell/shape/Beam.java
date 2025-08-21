package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class Beam extends PrimarySpellShape {
    @Override
    public SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster) {
        return null;
    }
}
