package at.minecraftschurli.arsmagicalegacy.api.spell;

public abstract sealed class SpellPart permits PrimarySpellShape, SecondarySpellShape, SpellComponent, SpellModifier {
    public final boolean isShape() {
        return isPrimaryShape() || isSecondaryShape();
    }

    public abstract boolean isPrimaryShape();

    public abstract boolean isSecondaryShape();

    public abstract boolean isComponent();

    public abstract boolean isModifier();
}
