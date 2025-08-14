package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface AMDataComponents {
    DeferredHolder<DataComponentType<?>, DataComponentType<Spell>> SPELL = AMRegistries.DATA_COMPONENTS.registerComponentType("spell", builder -> builder.persistent(Spell.CODEC).networkSynchronized(Spell.STREAM_CODEC));

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
