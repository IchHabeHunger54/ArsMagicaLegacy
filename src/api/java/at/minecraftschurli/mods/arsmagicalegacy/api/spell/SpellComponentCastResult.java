package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

/**
 * Represents the result of an individual {@link SpellPart} being cast. The result is then used to accordingly populate a {@link SpellCastResult}.
 * Get an instance via {@link #success()}, {@link #pass()} or {@link #failure(Component)}.
 */
public sealed interface SpellComponentCastResult {
    /// A [SpellComponentCastResult] marked as successful. A successful result triggers behavior such as mana consumption or affinity awarding.
    SpellComponentCastResult SUCCESS = new Success();

    /// A [SpellComponentCastResult] marked as neither successful nor failing. This should be used e.g. when only running code on one side.
    SpellComponentCastResult PASS = new Pass();

    /// @return Whether the result is considered successful. A successful result triggers behavior such as mana consumption or affinity awarding.
    boolean isSuccess();

    /// @return Whether the result is considered failing.
    boolean isFailure();

    /// @return The error message. This will be a non-null value iff [SpellComponentCastResult#isFailure()] returns true.
    @Nullable
    Component getMessage();

    static SpellComponentCastResult success() {
        return SUCCESS;
    }

    static SpellComponentCastResult pass() {
        return PASS;
    }

    /// @param message The error message to set.
    /// @return A new [SpellComponentCastResult] marked as failing and with the given error message set.
    static SpellComponentCastResult failure(Component message) {
        return new Failure(message);
    }

    final class Success implements SpellComponentCastResult {
        private Success() {}

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public @Nullable Component getMessage() {
            return null;
        }
    }

    final class Pass implements SpellComponentCastResult {
        private Pass() {}

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public @Nullable Component getMessage() {
            return null;
        }
    }

    record Failure(Component message) implements SpellComponentCastResult {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public Component getMessage() {
            return message;
        }
    }
}
