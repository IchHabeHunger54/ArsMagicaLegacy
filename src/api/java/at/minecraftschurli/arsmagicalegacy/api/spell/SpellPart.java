package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;

public abstract sealed class SpellPart permits PrimarySpellShape, SecondarySpellShape, SpellComponent, SpellModifier {
    public boolean isShape() {
        return isPrimaryShape() || isSecondaryShape();
    }

    public abstract boolean isPrimaryShape();

    public abstract boolean isSecondaryShape();

    public abstract boolean isComponent();

    public abstract boolean isModifier();

    public SpellPartData getData() {
        return ArsMagicaApi.getSpellPartData(this);
    }
}
