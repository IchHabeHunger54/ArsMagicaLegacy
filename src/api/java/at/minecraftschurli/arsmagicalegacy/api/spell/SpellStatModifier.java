package at.minecraftschurli.arsmagicalegacy.api.spell;

/**
 * Callback for calculating a modified {@link SpellStat} value.
 */
@FunctionalInterface
public interface SpellStatModifier {
    SpellStatModifier NOOP = (base, modified, context) -> modified;

    /**
     * Calculates a modified value.
     *
     * @param base     The base value being modified.
     * @param modified The modified value with all previous modifications.
     * @param context  The {@link SpellCastContext} to use.
     * @return A modified value.
     */
    double modify(double base, double modified, SpellCastContext context);

    /**
     * @param value The value to add.
     * @return A spell stat modifier that adds the given value to the modified value.
     */
    static SpellStatModifier add(double value) {
        return (base, modified, context) -> modified + value;
    }

    /**
     * @param value The value to multiply with.
     * @return A spell stat modifier that multiplies the given value with the modified value.
     */
    static SpellStatModifier multiply(double value) {
        return (base, modified, context) -> modified * value;
    }

    /**
     * @param value The value to multiply with.
     * @return A spell stat modifier that adds the base value, multiplied with the given value, to the modified value.
     */
    static SpellStatModifier addMultipliedBase(double value) {
        return (base, modified, context) -> modified + base * value;
    }

    /**
     * @param value The value to multiply with.
     * @return A spell stat modifier that adds the modified value, multiplied with the given value, to the modified value.
     */
    static SpellStatModifier addMultipliedTotal(double value) {
        return (base, modified, context) -> modified + modified * value;
    }
}
