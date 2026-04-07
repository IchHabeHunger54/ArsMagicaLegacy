package at.minecraftschurli.mods.arsmagicalegacy.api.event;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellFacade;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

/**
 * The base class for all events involving a spell.
 */
@SuppressWarnings("unused")
public abstract class SpellEvent extends LivingEvent {
    private final SpellFacade spell;

    public SpellEvent(LivingEntity entity, SpellFacade spell) {
        super(entity);
        this.spell = spell;
    }

    /**
     * @return The involved spell.
     */
    public SpellFacade getSpell() {
        return spell;
    }
}
