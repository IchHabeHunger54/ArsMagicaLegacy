package at.minecraftschurli.arsmagicalegacy.api.spell;

public abstract non-sealed class SpellComponent extends SpellPart {
    @Override
    public final boolean isShape() {
        return false;
    }

    @Override
    public final boolean isComponent() {
        return true;
    }

    @Override
    public final boolean isModifier() {
        return false;
    }
}
