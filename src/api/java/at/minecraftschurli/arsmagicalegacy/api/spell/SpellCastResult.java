package at.minecraftschurli.arsmagicalegacy.api.spell;

import net.neoforged.neoforge.common.util.TriState;

public record SpellCastResult(TriState result, Spell spell) {
    public static SpellCastResult success(Spell spell) {
        return new SpellCastResult(TriState.TRUE, spell);
    }

    public static SpellCastResult pass(Spell spell) {
        return new SpellCastResult(TriState.DEFAULT, spell);
    }

    public static SpellCastResult fail(Spell spell) {
        return new SpellCastResult(TriState.FALSE, spell);
    }
}
