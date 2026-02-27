package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;

import java.util.List;

// TODO 26.1 rendering has changed completely
public class Beam extends PrimarySpellShape {
    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        return new SpellCastResult(context.spell());
    }

    @Override
    public boolean isContinuous() {
        return true;
    }
}
