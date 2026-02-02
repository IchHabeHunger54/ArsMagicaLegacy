package at.minecraftschurli.arsmagicalegacy.spell;

import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStatModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.level.Level;

import java.util.Map;

public final class SpellModifiers {
    private static final double LUNAR_MULTIPLIER = 1.625;
    private static final double SOLAR_MULTIPLIER = 1.375;

    private SpellModifiers() {
    }

    public static Map<SpellStat, SpellStatModifier> lunarStatModifiers() {
        return Map.of(
            AMSpells.DAMAGE_STAT, (base, modified, spell, level, caster, directEntity, hitResult) -> modified + lunarMultiplier(level),
            AMSpells.DURATION_STAT, (base, modified, spell, level, caster, directEntity, hitResult) -> modified + modified * lunarMultiplier(level),
            AMSpells.HEALING_STAT, (base, modified, spell, level, caster, directEntity, hitResult) -> modified + modified * lunarMultiplier(level),
            AMSpells.PIERCING_STAT, (base, modified, spell, level, caster, directEntity, hitResult) -> modified + lunarMultiplier(level),
            AMSpells.RANGE_STAT, (base, modified, spell, level, caster, directEntity, hitResult) -> modified + modified * lunarMultiplier(level),
            AMSpells.SPEED_STAT, (base, modified, spell, level, caster, directEntity, hitResult) -> modified + modified * lunarMultiplier(level));
    }

    public static Map<SpellStat, SpellStatModifier> solarStatModifiers() {
        return Map.of(
            AMSpells.DAMAGE_STAT, (base, modified, spell, level, caster, directEntity, hitResult) -> modified + solarMultiplier(level),
            AMSpells.DURATION_STAT, (base, modified, spell, level, caster, directEntity, hitResult) -> modified + modified * solarMultiplier(level),
            AMSpells.HEALING_STAT, (base, modified, spell, level, caster, directEntity, hitResult) -> modified + modified * solarMultiplier(level),
            AMSpells.PIERCING_STAT, (base, modified, spell, level, caster, directEntity, hitResult) -> modified + solarMultiplier(level),
            AMSpells.RANGE_STAT, (base, modified, spell, level, caster, directEntity, hitResult) -> modified + modified * solarMultiplier(level),
            AMSpells.SPEED_STAT, (base, modified, spell, level, caster, directEntity, hitResult) -> modified + modified * solarMultiplier(level));
    }

    private static double lunarMultiplier(Level level) {
        long time = (level.getDayTime() + 13500) % 24000;
        if (time >= 9000) return 0;
        return Math.abs(4500 - time) / 4500f * LUNAR_MULTIPLIER * switch (level.getMoonPhase()) {
            case 0 -> 2;
            case 1, 7 -> 1.5;
            case 2, 6 -> 1;
            case 3, 5 -> 0.5;
            default -> 0;
        };
    }

    private static double solarMultiplier(Level level) {
        long time = (level.getDayTime() + 1500) % 24000;
        if (time >= 15000) return 0;
        return Math.abs(7500 - time) / 7500f * SOLAR_MULTIPLIER;
    }
}
