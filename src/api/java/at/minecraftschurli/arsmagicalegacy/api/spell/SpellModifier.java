package at.minecraftschurli.arsmagicalegacy.api.spell;

/**
 * Represents a spell modifier. Spell modifiers cannot be cast, instead they are queried for their presence by other spell parts.
 */
public abstract non-sealed class SpellModifier extends SpellPart {
    @Override
    public final boolean isPrimaryShape() {
        return false;
    }

    @Override
    public final boolean isSecondaryShape() {
        return false;
    }

    @Override
    public final boolean isComponent() {
        return false;
    }

    @Override
    public final boolean isModifier() {
        return true;
    }
}
