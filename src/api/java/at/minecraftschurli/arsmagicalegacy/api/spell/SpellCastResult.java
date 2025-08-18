package at.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.Nullable;

public record SpellCastResult(TriState result, @Nullable Spell spell, @Nullable Component message) {
    public static SpellCastResult success(Spell spell) {
        return new SpellCastResult(TriState.TRUE, spell, null);
    }

    public static SpellCastResult pass(Spell spell) {
        return new SpellCastResult(TriState.DEFAULT, spell, null);
    }

    public static SpellCastResult fail(String messageKey) {
        return new SpellCastResult(TriState.FALSE, null, Component.translatable(messageKey));
    }
}
