package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.component.DataComponentType;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Represents a spell part.
 */
public abstract sealed class SpellPart permits PrimarySpellShape, SecondarySpellShape, SpellComponent, SpellModifier {
    /**
     * @return Whether the spell part is a shape (primary or secondary).
     */
    public boolean isShape() {
        return isPrimaryShape() || isSecondaryShape();
    }

    /**
     * @return Whether the spell part is a primary shape.
     */
    public abstract boolean isPrimaryShape();

    /**
     * @return Whether the spell part is a secondary shape.
     */
    public abstract boolean isSecondaryShape();

    /**
     * @return Whether the spell part is a component.
     */
    public abstract boolean isComponent();

    /**
     * @return Whether the spell part is a modifier.
     */
    public abstract boolean isModifier();

    /**
     * If the spell part is a modifier, returns the {@link SpellStat}s the modifier modifies. Otherwise, returns the {@link SpellStat}s the spell part uses.
     *
     * @return A {@link Set} of {@link SpellStat}s.
     */
    public abstract Set<SpellStat> getStats();

    /**
     * @return The spell part's datapack-defined data.
     */
    public SpellPartData getData() {
        return ArsMagicaApi.spellPartData(this);
    }

    /**
     * @return The {@link DataComponentType} the spell part uses for additional data storage, or null if it does not use a {@link DataComponentType}.
     */
    @Nullable
    public DataComponentType<?> getDataComponentType() {
        return null;
    }
}
