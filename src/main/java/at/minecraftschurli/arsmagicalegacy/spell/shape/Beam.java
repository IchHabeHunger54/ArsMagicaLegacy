package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

//TODO
public class Beam extends PrimarySpellShape {
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, Level level, LivingEntity caster) {
        return spell;
    }

    @Override
    public boolean isContinuous() {
        return true;
    }
}
