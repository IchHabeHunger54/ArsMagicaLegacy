package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class AMMagicProvider {
    public static void addOcculusTabs(BootstrapContext<OcculusTab> bootstrap) {
        bootstrap.register(AMMagic.OFFENSE, new OcculusTab(512, 512, 130, 0, 0, ArsMagicaApi.modLoc("default")));
        bootstrap.register(AMMagic.DEFENSE, new OcculusTab(512, 512, 85, 0, 1, ArsMagicaApi.modLoc("default")));
        bootstrap.register(AMMagic.UTILITY, new OcculusTab(512, 512, 38, 0, 2, ArsMagicaApi.modLoc("default")));
        bootstrap.register(AMMagic.TALENT, new OcculusTab(256, 256, 0, 0, 3, ArsMagicaApi.modLoc("default")));
        bootstrap.register(AMMagic.AFFINITY, new OcculusTab(0, 0, 0, 0, 4, ArsMagicaApi.modLoc("affinity")));
    }

    public static void addSkillPoints(BootstrapContext<SkillPoint> bootstrap) {
        bootstrap.register(AMMagic.BLUE_POINT, new SkillPoint(0x0000ff, 0, 1));
        bootstrap.register(AMMagic.GREEN_POINT, new SkillPoint(0x00ff00, 10, 2));
        bootstrap.register(AMMagic.RED_POINT, new SkillPoint(0xff0000, 20, 3));
    }

    @SuppressWarnings("unused")
    public static void addSkills(BootstrapContext<Skill> bootstrap) {
        // @formatter:off
        Holder<Skill> projectile       = addSkill(bootstrap, AMSpells.PROJECTILE,          AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 210,  30);
        Holder<Skill> bounce           = addSkill(bootstrap, AMSpells.BOUNCE,              AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 255,  75, projectile);
        Holder<Skill> gravity          = addSkill(bootstrap, AMSpells.GRAVITY,             AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 165,  75, projectile);
        Holder<Skill> physicalDamage   = addSkill(bootstrap, AMSpells.PHYSICAL_DAMAGE,     AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 210,  75, projectile);
        Holder<Skill> fireDamage       = addSkill(bootstrap, AMSpells.FIRE_DAMAGE,         AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 165, 120, physicalDamage);
        Holder<Skill> frostDamage      = addSkill(bootstrap, AMSpells.FROST_DAMAGE,        AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 255, 120, physicalDamage);
        Holder<Skill> lightningDamage  = addSkill(bootstrap, AMSpells.LIGHTNING_DAMAGE,    AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 165, 165, physicalDamage);
        Holder<Skill> magicDamage      = addSkill(bootstrap, AMSpells.MAGIC_DAMAGE,        AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 255, 165, physicalDamage);
        Holder<Skill> areaOfEffect     = addSkill(bootstrap, AMSpells.AREA_OF_EFFECT,      AMMagic.GREEN_POINT, AMMagic.OFFENSE, 210, 165, fireDamage, frostDamage, lightningDamage, magicDamage);
        Holder<Skill> beam             = addSkill(bootstrap, AMSpells.BEAM,                AMMagic.RED_POINT,   AMMagic.OFFENSE, 210, 210, areaOfEffect);
        Holder<Skill> chain            = addSkill(bootstrap, AMSpells.CHAIN,               AMMagic.RED_POINT,   AMMagic.OFFENSE, 210, 255, beam);
        Holder<Skill> damage           = addSkill(bootstrap, AMSpells.DAMAGE,              AMMagic.RED_POINT,   AMMagic.OFFENSE, 210, 300, chain);
        Holder<Skill> fury             = addSkill(bootstrap, AMSpells.FURY,                AMMagic.RED_POINT,   AMMagic.OFFENSE, 165, 300, damage);
        Holder<Skill> explosion        = addSkill(bootstrap, AMSpells.EXPLOSION,           AMMagic.RED_POINT,   AMMagic.OFFENSE, 120, 300, fury);
        Holder<Skill> ignition         = addSkill(bootstrap, AMSpells.IGNITION,            AMMagic.GREEN_POINT, AMMagic.OFFENSE, 120, 120, fireDamage);
        Holder<Skill> forge            = addSkill(bootstrap, AMSpells.FORGE,               AMMagic.GREEN_POINT, AMMagic.OFFENSE,  75, 120, ignition);
        Holder<Skill> contingencyFire  = addSkill(bootstrap, AMSpells.CONTINGENCY_FIRE,    AMMagic.RED_POINT,   AMMagic.OFFENSE,  75, 165, forge);
        Holder<Skill> storm            = addSkill(bootstrap, AMSpells.STORM,               AMMagic.RED_POINT,   AMMagic.OFFENSE, 120, 165, lightningDamage);
        Holder<Skill> blindness        = addSkill(bootstrap, AMSpells.BLINDNESS,           AMMagic.GREEN_POINT, AMMagic.OFFENSE, 165, 210, lightningDamage);
        Holder<Skill> solar            = addSkill(bootstrap, AMSpells.SOLAR,               AMMagic.RED_POINT,   AMMagic.OFFENSE, 120, 210, blindness);
        Holder<Skill> frost            = addSkill(bootstrap, AMSpells.FROST,               AMMagic.GREEN_POINT, AMMagic.OFFENSE, 300, 120, frostDamage);
        Holder<Skill> piercing         = addSkill(bootstrap, AMSpells.PIERCING,            AMMagic.RED_POINT,   AMMagic.OFFENSE, 345, 120, frost);
        Holder<Skill> drowningDamage   = addSkill(bootstrap, AMSpells.DROWNING_DAMAGE,     AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 300, 165, magicDamage);
        Holder<Skill> wateryGrave      = addSkill(bootstrap, AMSpells.WATERY_GRAVE,        AMMagic.GREEN_POINT, AMMagic.OFFENSE, 345, 165, drowningDamage);
        Holder<Skill> astralDistortion = addSkill(bootstrap, AMSpells.ASTRAL_DISTORTION,   AMMagic.GREEN_POINT, AMMagic.OFFENSE, 255, 210, magicDamage);
        Holder<Skill> silence          = addSkill(bootstrap, AMSpells.SILENCE,             AMMagic.RED_POINT,   AMMagic.OFFENSE, 255, 255, astralDistortion);
        Holder<Skill> knockback        = addSkill(bootstrap, AMSpells.KNOCKBACK,           AMMagic.GREEN_POINT, AMMagic.OFFENSE, 300, 210, magicDamage);
        Holder<Skill> fling            = addSkill(bootstrap, AMSpells.FLING,               AMMagic.GREEN_POINT, AMMagic.OFFENSE, 300, 255, knockback);
        Holder<Skill> velocity         = addSkill(bootstrap, AMSpells.VELOCITY,            AMMagic.RED_POINT,   AMMagic.OFFENSE, 300, 300, fling);
        Holder<Skill> wave             = addSkill(bootstrap, AMSpells.WAVE,                AMMagic.RED_POINT,   AMMagic.OFFENSE, 255, 300, damage, velocity);

        Holder<Skill> self              = addSkill(bootstrap, AMSpells.SELF,                AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 165,  30);
        Holder<Skill> jumpBoost         = addSkill(bootstrap, AMSpells.JUMP_BOOST,          AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 120,  75, self);
        Holder<Skill> slowFalling       = addSkill(bootstrap, AMSpells.SLOW_FALLING,        AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 120, 120, jumpBoost);
        Holder<Skill> contingencyFall   = addSkill(bootstrap, AMSpells.CONTINGENCY_FALL,    AMMagic.RED_POINT,   AMMagic.DEFENSE,  75, 120, slowFalling);
        Holder<Skill> slowness          = addSkill(bootstrap, AMSpells.SLOWNESS,            AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  75, 165, slowFalling);
        Holder<Skill> gravityWell       = addSkill(bootstrap, AMSpells.GRAVITY_WELL,        AMMagic.GREEN_POINT, AMMagic.DEFENSE, 120, 165, slowFalling);
        Holder<Skill> haste             = addSkill(bootstrap, AMSpells.HASTE,               AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 165, 165, slowFalling);
        Holder<Skill> repel             = addSkill(bootstrap, AMSpells.REPEL,               AMMagic.GREEN_POINT, AMMagic.DEFENSE,  75, 210, slowness);
        Holder<Skill> levitation        = addSkill(bootstrap, AMSpells.LEVITATION,          AMMagic.GREEN_POINT, AMMagic.DEFENSE, 120, 210, gravityWell);
        Holder<Skill> swiftSwim         = addSkill(bootstrap, AMSpells.SWIFT_SWIM,          AMMagic.GREEN_POINT, AMMagic.DEFENSE, 165, 210, haste);
        Holder<Skill> entangle          = addSkill(bootstrap, AMSpells.ENTANGLE,            AMMagic.GREEN_POINT, AMMagic.DEFENSE,  75, 255, repel);
        Holder<Skill> flight            = addSkill(bootstrap, AMSpells.FLIGHT,              AMMagic.RED_POINT,   AMMagic.DEFENSE, 120, 255, levitation);
        Holder<Skill> agility           = addSkill(bootstrap, AMSpells.AGILITY,             AMMagic.GREEN_POINT, AMMagic.DEFENSE, 165, 255, swiftSwim);
        Holder<Skill> wall              = addSkill(bootstrap, AMSpells.WALL,                AMMagic.GREEN_POINT, AMMagic.DEFENSE,  30, 210, repel);
        Holder<Skill> rune              = addSkill(bootstrap, AMSpells.RUNE,                AMMagic.GREEN_POINT, AMMagic.DEFENSE, 120, 300, agility, entangle);
        Holder<Skill> runePower         = addSkill(bootstrap, AMSpells.RUNE_POWER,          AMMagic.GREEN_POINT, AMMagic.DEFENSE, 120, 345, rune);
        Holder<Skill> regeneration      = addSkill(bootstrap, AMSpells.REGENERATION,        AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 255,  75, self);
        Holder<Skill> shrink            = addSkill(bootstrap, AMSpells.SHRINK,              AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 300,  75, regeneration);
        Holder<Skill> heal              = addSkill(bootstrap, AMSpells.HEAL,                AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 255, 120, regeneration);
        Holder<Skill> healing           = addSkill(bootstrap, AMSpells.HEALING,             AMMagic.RED_POINT,   AMMagic.DEFENSE, 300, 120, heal);
        Holder<Skill> lifeTap           = addSkill(bootstrap, AMSpells.LIFE_TAP,            AMMagic.GREEN_POINT, AMMagic.DEFENSE, 210, 120, heal);
        Holder<Skill> lifeDrain         = addSkill(bootstrap, AMSpells.LIFE_DRAIN,          AMMagic.GREEN_POINT, AMMagic.DEFENSE, 210, 165, lifeTap);
        Holder<Skill> manaDrain         = addSkill(bootstrap, AMSpells.MANA_DRAIN,          AMMagic.GREEN_POINT, AMMagic.DEFENSE, 210, 210, lifeDrain);
        Holder<Skill> summon            = addSkill(bootstrap, AMSpells.SUMMON,              AMMagic.GREEN_POINT, AMMagic.DEFENSE, 165, 120, lifeTap);
        Holder<Skill> dispel            = addSkill(bootstrap, AMSpells.DISPEL,              AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 255, 165, heal);
        Holder<Skill> disarm            = addSkill(bootstrap, AMSpells.DISARM,              AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 300, 165, dispel);
        Holder<Skill> zone              = addSkill(bootstrap, AMSpells.ZONE,                AMMagic.RED_POINT,   AMMagic.DEFENSE, 255, 210, dispel);
        Holder<Skill> shield            = addSkill(bootstrap, AMSpells.SHIELD,              AMMagic.RED_POINT,   AMMagic.DEFENSE, 255, 255, zone);
        Holder<Skill> contingencyHealth = addSkill(bootstrap, AMSpells.CONTINGENCY_HEALTH,  AMMagic.RED_POINT,   AMMagic.OFFENSE, 300, 255, shield);
        Holder<Skill> absorption        = addSkill(bootstrap, AMSpells.ABSORPTION,          AMMagic.RED_POINT,   AMMagic.DEFENSE, 210, 255, shield);
        Holder<Skill> reflect           = addSkill(bootstrap, AMSpells.REFLECT,             AMMagic.RED_POINT,   AMMagic.DEFENSE, 255, 300, shield);
        Holder<Skill> contingencyDamage = addSkill(bootstrap, AMSpells.CONTINGENCY_DAMAGE,  AMMagic.RED_POINT,   AMMagic.DEFENSE, 255, 345, reflect);
        Holder<Skill> temporalAnchor    = addSkill(bootstrap, AMSpells.TEMPORAL_ANCHOR,     AMMagic.RED_POINT,   AMMagic.DEFENSE, 210, 300, reflect);
        Holder<Skill> duration          = addSkill(bootstrap, AMSpells.DURATION,            AMMagic.RED_POINT,   AMMagic.DEFENSE, 210, 345, temporalAnchor);

        Holder<Skill> touch              = addSkill(bootstrap, AMSpells.TOUCH,               AMMagic.BLUE_POINT,  AMMagic.UTILITY, 120,  30);
        Holder<Skill> targetNonSolid     = addSkill(bootstrap, AMSpells.TARGET_NON_SOLID,    AMMagic.BLUE_POINT,  AMMagic.UTILITY,  75,  30, touch);
        Holder<Skill> dig                = addSkill(bootstrap, AMSpells.DIG,                 AMMagic.BLUE_POINT,  AMMagic.UTILITY, 120,  75, touch);
        Holder<Skill> placeBlock         = addSkill(bootstrap, AMSpells.PLACE_BLOCK,         AMMagic.BLUE_POINT,  AMMagic.UTILITY, 165,  75, dig);
        Holder<Skill> wizardsAutumn      = addSkill(bootstrap, AMSpells.WIZARDS_AUTUMN,      AMMagic.BLUE_POINT,  AMMagic.UTILITY, 165, 120, dig);
        Holder<Skill> silkTouch          = addSkill(bootstrap, AMSpells.SILK_TOUCH,          AMMagic.BLUE_POINT,  AMMagic.UTILITY,  75,  75, dig);
        Holder<Skill> miningPower        = addSkill(bootstrap, AMSpells.MINING_POWER,        AMMagic.GREEN_POINT, AMMagic.UTILITY,  75, 120, silkTouch);
        Holder<Skill> light              = addSkill(bootstrap, AMSpells.LIGHT,               AMMagic.BLUE_POINT,  AMMagic.UTILITY, 120, 165, dig);
        Holder<Skill> charm              = addSkill(bootstrap, AMSpells.CHARM,               AMMagic.GREEN_POINT, AMMagic.UTILITY, 165, 165, light);
        Holder<Skill> nightVision        = addSkill(bootstrap, AMSpells.NIGHT_VISION,        AMMagic.BLUE_POINT,  AMMagic.UTILITY,  75, 165, light);
        Holder<Skill> lunar              = addSkill(bootstrap, AMSpells.LUNAR,               AMMagic.RED_POINT,   AMMagic.UTILITY,  30, 165, nightVision);
        Holder<Skill> trueSight          = addSkill(bootstrap, AMSpells.TRUE_SIGHT,          AMMagic.BLUE_POINT,  AMMagic.UTILITY,  75, 210, nightVision);
        Holder<Skill> invisibility       = addSkill(bootstrap, AMSpells.INVISIBILITY,        AMMagic.GREEN_POINT, AMMagic.UTILITY,  30, 210, trueSight);
        Holder<Skill> randomTeleport     = addSkill(bootstrap, AMSpells.RANDOM_TELEPORT,     AMMagic.GREEN_POINT, AMMagic.UTILITY,  30, 255, invisibility);
        Holder<Skill> range              = addSkill(bootstrap, AMSpells.RANGE,               AMMagic.RED_POINT,   AMMagic.UTILITY,  75, 255, randomTeleport);
        Holder<Skill> blink              = addSkill(bootstrap, AMSpells.BLINK,               AMMagic.GREEN_POINT, AMMagic.UTILITY,  30, 300, randomTeleport);
        Holder<Skill> transplace         = addSkill(bootstrap, AMSpells.TRANSPLACE,          AMMagic.GREEN_POINT, AMMagic.UTILITY,  75, 300, blink);
        Holder<Skill> recall             = addSkill(bootstrap, AMSpells.RECALL,              AMMagic.GREEN_POINT, AMMagic.UTILITY,  75, 345, transplace);
        Holder<Skill> divineIntervention = addSkill(bootstrap, AMSpells.DIVINE_INTERVENTION, AMMagic.RED_POINT,   AMMagic.UTILITY,  30, 345, recall);
        Holder<Skill> enderIntervention  = addSkill(bootstrap, AMSpells.ENDER_INTERVENTION,  AMMagic.RED_POINT,   AMMagic.UTILITY, 120, 345, recall);
        Holder<Skill> contingencyDeath   = addSkill(bootstrap, AMSpells.CONTINGENCY_DEATH,   AMMagic.RED_POINT,   AMMagic.UTILITY, 165, 345, enderIntervention);
        Holder<Skill> rift               = addSkill(bootstrap, AMSpells.RIFT,                AMMagic.GREEN_POINT, AMMagic.UTILITY, 120, 255, light);
        Holder<Skill> channel            = addSkill(bootstrap, AMSpells.CHANNEL,             AMMagic.GREEN_POINT, AMMagic.UTILITY, 165, 255, rift);
        Holder<Skill> attract            = addSkill(bootstrap, AMSpells.ATTRACT,             AMMagic.GREEN_POINT, AMMagic.UTILITY, 120, 300, rift);
        Holder<Skill> telekinesis        = addSkill(bootstrap, AMSpells.TELEKINESIS,         AMMagic.GREEN_POINT, AMMagic.UTILITY, 165, 300, attract);
        Holder<Skill> plant              = addSkill(bootstrap, AMSpells.PLANT,               AMMagic.BLUE_POINT,  AMMagic.UTILITY, 210, 210, light);
        Holder<Skill> grow               = addSkill(bootstrap, AMSpells.GROW,                AMMagic.GREEN_POINT, AMMagic.UTILITY, 255, 210, plant);
        Holder<Skill> plow               = addSkill(bootstrap, AMSpells.PLOW,                AMMagic.BLUE_POINT,  AMMagic.UTILITY, 210, 165, plant);
        Holder<Skill> harvest            = addSkill(bootstrap, AMSpells.HARVEST,             AMMagic.GREEN_POINT, AMMagic.UTILITY, 210, 120, plow);
        Holder<Skill> createWater        = addSkill(bootstrap, AMSpells.CREATE_WATER,        AMMagic.GREEN_POINT, AMMagic.UTILITY, 210, 255, plant);
        Holder<Skill> waterBreathing     = addSkill(bootstrap, AMSpells.WATER_BREATHING,     AMMagic.GREEN_POINT, AMMagic.UTILITY, 255, 255, createWater);
        Holder<Skill> drought            = addSkill(bootstrap, AMSpells.DROUGHT,             AMMagic.GREEN_POINT, AMMagic.UTILITY, 210, 300, createWater);
        Holder<Skill> banishRain         = addSkill(bootstrap, AMSpells.BANISH_RAIN,         AMMagic.GREEN_POINT, AMMagic.UTILITY, 255, 300, drought);

        Holder<Skill> color                  = addSkill(bootstrap, AMSpells.COLOR,                    AMMagic.BLUE_POINT,  AMMagic.TALENT,  30,  30);
        Holder<Skill> manaRegenerationBoost1 = addSkill(bootstrap, AMMagic.MANA_REGENERATION_BOOST_1, AMMagic.BLUE_POINT,  AMMagic.TALENT,  75,  30);
        Holder<Skill> manaRegenerationBoost2 = addSkill(bootstrap, AMMagic.MANA_REGENERATION_BOOST_2, AMMagic.GREEN_POINT, AMMagic.TALENT,  75,  75, manaRegenerationBoost1);
        Holder<Skill> manaRegenerationBoost3 = addSkill(bootstrap, AMMagic.MANA_REGENERATION_BOOST_3, AMMagic.RED_POINT,   AMMagic.TALENT,  75, 120, manaRegenerationBoost2);
        Holder<Skill> affinityGainsBoost     = addSkill(bootstrap, AMMagic.AFFINITY_GAINS_BOOST,      AMMagic.BLUE_POINT,  AMMagic.TALENT, 120,  30, manaRegenerationBoost1);
        Holder<Skill> spellMotion            = addSkill(bootstrap, AMMagic.SPELL_MOTION,              AMMagic.GREEN_POINT, AMMagic.TALENT,  30,  75, manaRegenerationBoost2);
        Holder<Skill> augmentedCasting       = addSkill(bootstrap, AMMagic.AUGMENTED_CASTING,         AMMagic.RED_POINT,   AMMagic.TALENT,  30, 120, spellMotion);

        addHiddenSkill(bootstrap, AMSpells.BLIZZARD,     AMMagic.OFFENSE, 30,  30);
        addHiddenSkill(bootstrap, AMSpells.DAYLIGHT,     AMMagic.UTILITY, 30,  30);
        addHiddenSkill(bootstrap, AMSpells.FALLING_STAR, AMMagic.OFFENSE, 30,  75);
        addHiddenSkill(bootstrap, AMSpells.FIRE_RAIN,    AMMagic.OFFENSE, 30, 120);
        addHiddenSkill(bootstrap, AMSpells.HEALTH_BOOST, AMMagic.DEFENSE, 30,  30);
        addHiddenSkill(bootstrap, AMSpells.MANA_BLAST,   AMMagic.OFFENSE, 30, 165);
        addHiddenSkill(bootstrap, AMSpells.MOONRISE,     AMMagic.UTILITY, 30,  75);
        addHiddenSkill(bootstrap, AMSpells.DISMEMBERING, AMMagic.OFFENSE, 30, 210);
        addHiddenSkill(bootstrap, AMSpells.EFFECT_POWER, AMMagic.DEFENSE, 30,  75);
        addHiddenSkill(bootstrap, AMSpells.PROSPERITY,   AMMagic.UTILITY, 30, 120);
        // @formatter:on
    }

    private static void addHiddenSkill(BootstrapContext<Skill> bootstrap, DeferredHolder<SpellPart, ?> part, ResourceKey<OcculusTab> tab, int x, int y) {
        bootstrap.register(fromPart(part), new Skill(List.of(), Optional.empty(), bootstrap.lookup(AMRegistryKeys.OCCULUS_TAB).getOrThrow(tab), x, y, true));
    }

    @SafeVarargs
    private static Holder<Skill> addSkill(BootstrapContext<Skill> bootstrap, ResourceKey<Skill> key, ResourceKey<SkillPoint> point, ResourceKey<OcculusTab> tab, int x, int y, Holder<Skill>... parents) {
        return bootstrap.register(key, new Skill(
            Arrays.stream(parents).map(Holder::getKey).map(ResourceKey::location).toList(),
            Optional.of(bootstrap.lookup(AMRegistryKeys.SKILL_POINT).getOrThrow(point)),
            bootstrap.lookup(AMRegistryKeys.OCCULUS_TAB).getOrThrow(tab),
            x,
            y,
            false));
    }

    @SafeVarargs
    private static Holder<Skill> addSkill(BootstrapContext<Skill> bootstrap, DeferredHolder<SpellPart, ?> part, ResourceKey<SkillPoint> point, ResourceKey<OcculusTab> tab, int x, int y, Holder<Skill>... parents) {
        return addSkill(bootstrap, fromPart(part), point, tab, x, y, parents);
    }

    private static ResourceKey<Skill> fromPart(DeferredHolder<SpellPart, ?> part) {
        return ResourceKey.create(AMRegistryKeys.SKILL, ArsMagicaApi.getSpellPartRegistry().getKey(part.get()));
    }
}
