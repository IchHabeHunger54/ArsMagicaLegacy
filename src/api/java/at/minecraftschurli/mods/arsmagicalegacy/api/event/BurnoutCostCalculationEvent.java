package at.minecraftschurli.mods.arsmagicalegacy.api.event;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellFacade;
import net.minecraft.world.entity.LivingEntity;

/**
 * Event that fires when burnout cost is retrieved.
 * <p>
 * This event is not cancelable. This event is fired on the main event bus.
 */
@SuppressWarnings("unused")
public class BurnoutCostCalculationEvent extends SpellEvent {
    private final double originalBurnout;
    private double burnout;

    public BurnoutCostCalculationEvent(LivingEntity entity, SpellFacade spell, double burnout) {
        super(entity, spell);
        originalBurnout = burnout;
        this.burnout = burnout;
    }

    /**
     * @return The original burnout cost.
     */
    public double getOriginalBurnout() {
        return originalBurnout;
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
}
