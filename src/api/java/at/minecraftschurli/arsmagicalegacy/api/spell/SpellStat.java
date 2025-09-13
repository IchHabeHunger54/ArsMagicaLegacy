package at.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.resources.ResourceLocation;

/**
 * Represents a stat that can be modified, e.g. by {@link SpellModifier}s. No registration is necessary, equality is checked via {@link SpellStat#equals(Object)}.
 *
 * @param id The id of the stat.
 */
public record SpellStat(ResourceLocation id) {
}
