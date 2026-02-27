package at.minecraftschurli.arsmagicalegacy.spell;

import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastContext;
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
            AMSpells.DAMAGE_STAT, (base, modified, context) -> modified + lunarMultiplier(context),
            AMSpells.DURATION_STAT, (base, modified, context) -> modified + modified * lunarMultiplier(context),
            AMSpells.HEALING_STAT, (base, modified, context) -> modified + modified * lunarMultiplier(context),
            AMSpells.PIERCING_STAT, (base, modified, context) -> modified + lunarMultiplier(context),
            AMSpells.RANGE_STAT, (base, modified, context) -> modified + modified * lunarMultiplier(context),
            AMSpells.SPEED_STAT, (base, modified, context) -> modified + modified * lunarMultiplier(context));
    }

    public static Map<SpellStat, SpellStatModifier> solarStatModifiers() {
        return Map.of(
            AMSpells.DAMAGE_STAT, (base, modified, context) -> modified + solarMultiplier(context),
            AMSpells.DURATION_STAT, (base, modified, context) -> modified + modified * solarMultiplier(context),
            AMSpells.HEALING_STAT, (base, modified, context) -> modified + modified * solarMultiplier(context),
            AMSpells.PIERCING_STAT, (base, modified, context) -> modified + solarMultiplier(context),
            AMSpells.RANGE_STAT, (base, modified, context) -> modified + modified * solarMultiplier(context),
            AMSpells.SPEED_STAT, (base, modified, context) -> modified + modified * solarMultiplier(context));
    }

    private static double lunarMultiplier(SpellCastContext context) {
        Level level = context.level();
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

    private static double solarMultiplier(SpellCastContext context) {
        long time = (context.level().getDayTime() + 1500) % 24000;
        if (time >= 15000) return 0;
        return Math.abs(7500 - time) / 7500f * SOLAR_MULTIPLIER;
    }
}
