package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

//TODO
public class Chain extends PrimarySpellShape {
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster) {
        return spell;
    }

    @Override
    public boolean isContinuous() {
        return true;
    }
}
