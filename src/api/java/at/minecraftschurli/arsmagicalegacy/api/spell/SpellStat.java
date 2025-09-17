package at.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a stat that can be modified, e.g. by {@link SpellModifier}s. No registration is necessary, equality is checked via {@link SpellStat#equals(Object)}.
 *
 * @param id     The id of the stat.
 * @param global Whether the stat is considered global. Global stats can be applied to any spell part, even if a spell part doesn't specify it. Ars Magica: Legacy uses this for the color stat.
 */
public record SpellStat(ResourceLocation id, boolean global) {
    private static final Set<SpellStat> GLOBALS = new HashSet<>();

    public SpellStat {
        if (global) {
            GLOBALS.add(this);
        }
    }

    /**
     * @param id The id of the stat.
     */
    public SpellStat(ResourceLocation id) {
        this(id, false);
    }

    /**
     * @return An unmodifiable set of all global spell stats.
     */
    public static Set<SpellStat> getGlobals() {
        return Collections.unmodifiableSet(GLOBALS);
    }
}
