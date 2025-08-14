package at.minecraftschurli.arsmagicalegacy.api.spell;

public abstract non-sealed class SpellShape extends SpellPart {
    @Override
    public final boolean isShape() {
        return true;
    }

    @Override
    public final boolean isComponent() {
        return false;
    }

    @Override
    public final boolean isModifier() {
        return false;
    }

    public boolean mustBeFirst() {
        return false;
    }

    public boolean mustNotBeFirst() {
        return false;
    }

    public boolean mustBeLast() {
        return false;
    }
}
