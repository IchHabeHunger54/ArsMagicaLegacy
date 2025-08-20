package at.minecraftschurli.arsmagicalegacy.api.spell;

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
