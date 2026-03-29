package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.resources.Identifier;

/**
 * Represents a stat that can be modified, e.g. by {@link SpellModifier}s. No registration is necessary, equality is checked via {@link SpellStat#equals(Object)}.
 *
 * @param id The id of the stat.
 */
public record SpellStat(Identifier id) {
    public static final SpellStat COLOR = new SpellStat(ArsMagicaApi.id("color"));
}
