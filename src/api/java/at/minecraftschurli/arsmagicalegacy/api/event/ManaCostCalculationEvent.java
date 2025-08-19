package at.minecraftschurli.arsmagicalegacy.api.event;

import at.minecraftschurli.arsmagicalegacy.api.magic.Spell;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

/**
 * Event that fires when mana cost is retrieved.
 * <p>
 * Mana cost is calculated as (mana + burnout), where mana is retrieved from the spell and burnout is the caster's current burnout value.
 * Modifiers added in this event will then be applied to the result.
 * <p>
 * This event is not cancelable. This event is fired on the main event bus.
 */
@SuppressWarnings("unused")
public class ManaCostCalculationEvent extends SpellEvent {
    private final double originalMana;
    private final double originalBurnout;
    private double mana;
    private double burnout;
    private List<DoubleUnaryOperator> modifiers = new ArrayList<>();

    public ManaCostCalculationEvent(LivingEntity entity, Spell spell, double mana, double burnout) {
        super(entity, spell);
        originalMana = mana;
        originalBurnout = burnout;
        this.mana = mana;
        this.burnout = burnout;
    }

    /**
     * @return The unmodified mana cost.
     */
    public double getOriginalMana() {
        return originalMana;
    }

    /**
     * @return The unmodified burnout cost.
     */
    public double getOriginalBurnout() {
        return originalBurnout;
    }

    /**
     * @return The potentially modified mana cost.
     */
    public double getMana() {
        return mana;
    }

    /**
     * Modifies the mana cost.
     *
     * @param mana The new mana cost to set.
     */
    public void setMana(double mana) {
        this.mana = mana;
    }

    /**
     * @return The potentially modified burnout cost.
     */
    public double getBurnout() {
        return burnout;
    }

    /**
     * Modifies the burnout cost.
     *
     * @param burnout The new burnout cost to set.
     */
    public void setBurnout(double burnout) {
        this.burnout = burnout;
    }

    /**
     * Adds a modifier. Modifiers will be applied after the regular calculation (mana + burnout).
     *
     * @param modifier A {@link DoubleUnaryOperator} to be applied after the regular calculation.
     */
    public void addModifier(DoubleUnaryOperator modifier) {
        modifiers.add(modifier);
    }

    /**
     * @return The final result after applying all modifiers.
     */
    public double getResult() {
        double result = mana + burnout;
        for (DoubleUnaryOperator modifier : modifiers) {
            result = modifier.applyAsDouble(result);
        }
        return result;
    }
}
