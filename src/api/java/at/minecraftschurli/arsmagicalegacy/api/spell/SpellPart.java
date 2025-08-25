package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;

/**
 * Represents a spell part.
 */
public abstract sealed class SpellPart permits PrimarySpellShape, SecondarySpellShape, SpellComponent, SpellModifier {
    /**
     * @return Whether this spell part is a shape (primary or secondary).
     */
    public boolean isShape() {
        return isPrimaryShape() || isSecondaryShape();
    }

    /**
     * @return Whether this spell part is a primary shape.
     */
    public abstract boolean isPrimaryShape();

    /**
     * @return Whether this spell part is a secondary shape.
     */
    public abstract boolean isSecondaryShape();

    /**
     * @return Whether this spell part is a component.
     */
    public abstract boolean isComponent();

    /**
     * @return Whether this spell part is a modifier.
     */
    public abstract boolean isModifier();

    /**
     * @return This spell part's datapack-defined data.
     */
    public SpellPartData getData() {
        return ArsMagicaApi.spellPartData(this);
    }
}
