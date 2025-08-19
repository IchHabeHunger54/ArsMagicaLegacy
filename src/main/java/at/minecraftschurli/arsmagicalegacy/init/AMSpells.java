package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.magic.SpellPart;
import at.minecraftschurli.arsmagicalegacy.spell.component.Heal;
import at.minecraftschurli.arsmagicalegacy.spell.shape.Self;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface AMSpells {
    DeferredHolder<SpellPart, Self> SELF = AMRegistries.SPELL_PARTS.register("self", Self::new);
    DeferredHolder<SpellPart, Heal> HEAL = AMRegistries.SPELL_PARTS.register("heal", Heal::new);

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
