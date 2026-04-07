package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

// TODO rework
/**
 * Represents the result of a {@link Spell} cast. Holds the {@link Spell} itself, whether the {@link Spell} cast was successful, and an error message if one was set.
 */
public final class SpellCastResult {
    private boolean success = false;
    private SpellFacade spell;
    @Nullable
    private Component message = null;

    public SpellCastResult(SpellFacade spell) {
        this.spell = spell;
    }

    public SpellCastResult(Component message) {
        this.spell = SpellFacade.EMPTY;
        this.message = message;
    }

    /**
     * @return Whether the {@link Spell} cast was successful. A successful result triggers behavior such as mana consumption or affinity awarding.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Marks the {@link Spell} cast as successful. A successful result triggers behavior such as mana consumption or affinity awarding. This is not reversible.
     *
     * @return This object, for chaining.
     */
    public SpellCastResult setSuccess() {
        success = true;
        return this;
    }

    public SpellFacade getSpell() {
        return spell;
    }

    public SpellCastResult setSpell(SpellFacade spell) {
        this.spell = spell;
        return this;
    }

    /**
     * @return The error message. May be null, which indicates that no error message was recorded.
     */
    @Nullable
    public Component getMessage() {
        return message;
    }

    /**
     * Sets the given error message if none was recorded yet. This behavior is to ensure that the earliest error message is returned.
     *
     * @param message The error message to set.
     * @return This object, for chaining.
     */
    public SpellCastResult setMessage(Component message) {
        if (this.message == null) {
            this.message = message;
        }
        return this;
    }
}
