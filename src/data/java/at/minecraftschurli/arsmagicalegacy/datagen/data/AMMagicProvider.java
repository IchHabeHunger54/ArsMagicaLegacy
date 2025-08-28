package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMSounds;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class AMMagicProvider {
    public static void addAffinities(BootstrapContext<Affinity> bootstrap) {
        HolderOwner<Affinity> owner = new HolderOwner<>() {
            @Override
            public boolean canSerializeIn(HolderOwner<Affinity> owner) {
                return true;
            }
        };
        bootstrap.register(Affinity.NONE, new Affinity(Holder.Reference.createStandAlone(owner, Affinity.NONE), Set.of(), Set.of(), Set.of(), 0, Optional.of(AMSounds.CAST_NONE), Optional.empty()));
        // @formatter:off
        Holder.Reference<Affinity> water     = Holder.Reference.createStandAlone(owner, AMMagic.WATER);
        Holder.Reference<Affinity> fire      = Holder.Reference.createStandAlone(owner, AMMagic.FIRE);
        Holder.Reference<Affinity> earth     = Holder.Reference.createStandAlone(owner, AMMagic.EARTH);
        Holder.Reference<Affinity> air       = Holder.Reference.createStandAlone(owner, AMMagic.AIR);
        Holder.Reference<Affinity> ice       = Holder.Reference.createStandAlone(owner, AMMagic.ICE);
        Holder.Reference<Affinity> lightning = Holder.Reference.createStandAlone(owner, AMMagic.LIGHTNING);
        Holder.Reference<Affinity> nature    = Holder.Reference.createStandAlone(owner, AMMagic.NATURE);
        Holder.Reference<Affinity> life      = Holder.Reference.createStandAlone(owner, AMMagic.LIFE);
        Holder.Reference<Affinity> arcane    = Holder.Reference.createStandAlone(owner, AMMagic.ARCANE);
        Holder.Reference<Affinity> ender     = Holder.Reference.createStandAlone(owner, AMMagic.ENDER);
        bootstrap.register(AMMagic.WATER,     new Affinity(fire,      Set.of(lightning, ender), Set.of(air, arcane),      Set.of(ice, nature),      0x0b5cef, AMSounds.CAST_WATER,     AMSounds.LOOP_WATER));
        bootstrap.register(AMMagic.FIRE,      new Affinity(water,     Set.of(ice, nature),      Set.of(earth, life),      Set.of(lightning, ender), 0xef260b, AMSounds.CAST_FIRE,      AMSounds.LOOP_FIRE));
        bootstrap.register(AMMagic.EARTH,     new Affinity(air,       Set.of(lightning, life),  Set.of(fire, nature),     Set.of(ice, arcane),      0x61330b, AMSounds.CAST_EARTH,     AMSounds.LOOP_EARTH));
        bootstrap.register(AMMagic.AIR,       new Affinity(earth,     Set.of(ice, arcane),      Set.of(water, ender),     Set.of(lightning, life),  0x777777, AMSounds.CAST_AIR,       AMSounds.LOOP_AIR));
        bootstrap.register(AMMagic.ICE,       new Affinity(lightning, Set.of(fire, air),        Set.of(life, ender),      Set.of(water, earth),     0xd3e8fc, AMSounds.CAST_ICE,       AMSounds.LOOP_ICE));
        bootstrap.register(AMMagic.LIGHTNING, new Affinity(ice,       Set.of(water, earth),     Set.of(nature, arcane),   Set.of(fire, air),        0xdece19, AMSounds.CAST_LIGHTNING, AMSounds.LOOP_LIGHTNING));
        bootstrap.register(AMMagic.NATURE,    new Affinity(ender,     Set.of(fire, arcane),     Set.of(earth, lightning), Set.of(water, life),      0x228718, AMSounds.CAST_NATURE,    AMSounds.LOOP_NATURE));
        bootstrap.register(AMMagic.LIFE,      new Affinity(arcane,    Set.of(earth, ender),     Set.of(fire, ice),        Set.of(air, nature),      0x34e122, AMSounds.CAST_LIFE,      AMSounds.LOOP_LIFE));
        bootstrap.register(AMMagic.ARCANE,    new Affinity(life,      Set.of(air, nature),      Set.of(water, lightning), Set.of(earth, ender),     0xb935cd, AMSounds.CAST_ARCANE,    AMSounds.LOOP_ARCANE));
        bootstrap.register(AMMagic.ENDER,     new Affinity(nature,    Set.of(water, life),      Set.of(air, ice),         Set.of(fire, arcane),     0x3f043d, AMSounds.CAST_ENDER,     AMSounds.LOOP_ENDER));
        // @formatter:on
    }

    public static void addOcculusTabs(BootstrapContext<OcculusTab> bootstrap) {
        bootstrap.register(AMMagic.OFFENSE, new OcculusTab(368, 320, 85, 0, 0, ArsMagicaApi.modLoc("default")));
        bootstrap.register(AMMagic.DEFENSE, new OcculusTab(320, 368, 38, 0, 1, ArsMagicaApi.modLoc("default")));
        bootstrap.register(AMMagic.UTILITY, new OcculusTab(320, 368, 38, 0, 2, ArsMagicaApi.modLoc("default")));
        bootstrap.register(AMMagic.TALENT, new OcculusTab(224, 196, 14, 0, 3, ArsMagicaApi.modLoc("default")));
        bootstrap.register(AMMagic.AFFINITY, new OcculusTab(196, 196, 0, 0, 4, ArsMagicaApi.modLoc("affinity")));
    }

    public static void addSkillPoints(BootstrapContext<SkillPoint> bootstrap) {
        bootstrap.register(AMMagic.BLUE_POINT, new SkillPoint(0x0000ff, 0, 1));
        bootstrap.register(AMMagic.GREEN_POINT, new SkillPoint(0x00ff00, 10, 2));
        bootstrap.register(AMMagic.RED_POINT, new SkillPoint(0xff0000, 20, 3));
    }

    @SuppressWarnings("unused")
    public static void addSkills(BootstrapContext<Skill> bootstrap) {
        // @formatter:off
        Holder<Skill> projectile       = addSkill(bootstrap, AMSpells.PROJECTILE,        AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 168,  24);
        Holder<Skill> bounce           = addSkill(bootstrap, AMSpells.BOUNCE,            AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 216,  24, projectile);
        Holder<Skill> gravity          = addSkill(bootstrap, AMSpells.GRAVITY,           AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 120,  24, projectile);
        Holder<Skill> physicalDamage   = addSkill(bootstrap, AMSpells.PHYSICAL_DAMAGE,   AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 168,  72, projectile);
        Holder<Skill> fireDamage       = addSkill(bootstrap, AMSpells.FIRE_DAMAGE,       AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 120,  72, physicalDamage);
        Holder<Skill> frostDamage      = addSkill(bootstrap, AMSpells.FROST_DAMAGE,      AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 216,  72, physicalDamage);
        Holder<Skill> lightningDamage  = addSkill(bootstrap, AMSpells.LIGHTNING_DAMAGE,  AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 120, 120, physicalDamage);
        Holder<Skill> magicDamage      = addSkill(bootstrap, AMSpells.MAGIC_DAMAGE,      AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 216, 120, physicalDamage);
        Holder<Skill> areaOfEffect     = addSkill(bootstrap, AMSpells.AREA_OF_EFFECT,    AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 168, 120, fireDamage, frostDamage, lightningDamage, magicDamage);
        Holder<Skill> beam             = addSkill(bootstrap, AMSpells.BEAM,              AMMagic.GREEN_POINT, AMMagic.OFFENSE, 168, 168, areaOfEffect);
        Holder<Skill> chain            = addSkill(bootstrap, AMSpells.CHAIN,             AMMagic.GREEN_POINT, AMMagic.OFFENSE, 168, 216, beam);
        Holder<Skill> damage           = addSkill(bootstrap, AMSpells.DAMAGE,            AMMagic.RED_POINT,   AMMagic.OFFENSE, 168, 264, chain);
        Holder<Skill> fury             = addSkill(bootstrap, AMSpells.FURY,              AMMagic.RED_POINT,   AMMagic.OFFENSE, 120, 264, damage);
        Holder<Skill> explosion        = addSkill(bootstrap, AMSpells.EXPLOSION,         AMMagic.RED_POINT,   AMMagic.OFFENSE,  72, 264, fury);
        Holder<Skill> ignition         = addSkill(bootstrap, AMSpells.IGNITION,          AMMagic.BLUE_POINT,  AMMagic.OFFENSE,  72,  72, fireDamage);
        Holder<Skill> forge            = addSkill(bootstrap, AMSpells.FORGE,             AMMagic.BLUE_POINT,  AMMagic.OFFENSE,  24,  72, ignition);
        Holder<Skill> contingencyFire  = addSkill(bootstrap, AMSpells.CONTINGENCY_FIRE,  AMMagic.RED_POINT,   AMMagic.OFFENSE,  24, 120, forge);
        Holder<Skill> storm            = addSkill(bootstrap, AMSpells.STORM,             AMMagic.RED_POINT,   AMMagic.OFFENSE,  72, 120, lightningDamage);
        Holder<Skill> blindness        = addSkill(bootstrap, AMSpells.BLINDNESS,         AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 120, 168, lightningDamage);
        Holder<Skill> solar            = addSkill(bootstrap, AMSpells.SOLAR,             AMMagic.RED_POINT,   AMMagic.OFFENSE, 120, 216, blindness);
        Holder<Skill> frost            = addSkill(bootstrap, AMSpells.FROST,             AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 264,  72, frostDamage);
        Holder<Skill> piercing         = addSkill(bootstrap, AMSpells.PIERCING,          AMMagic.GREEN_POINT, AMMagic.OFFENSE, 312,  72, frost);
        Holder<Skill> drowningDamage   = addSkill(bootstrap, AMSpells.DROWNING_DAMAGE,   AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 264, 120, magicDamage);
        Holder<Skill> wateryGrave      = addSkill(bootstrap, AMSpells.WATERY_GRAVE,      AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 312, 120, drowningDamage);
        Holder<Skill> astralDistortion = addSkill(bootstrap, AMSpells.ASTRAL_DISTORTION, AMMagic.GREEN_POINT, AMMagic.OFFENSE, 216, 168, magicDamage);
        Holder<Skill> silence          = addSkill(bootstrap, AMSpells.SILENCE,           AMMagic.GREEN_POINT, AMMagic.OFFENSE, 216, 216, astralDistortion);
        Holder<Skill> knockback        = addSkill(bootstrap, AMSpells.KNOCKBACK,         AMMagic.GREEN_POINT, AMMagic.OFFENSE, 264, 168, magicDamage);
        Holder<Skill> fling            = addSkill(bootstrap, AMSpells.FLING,             AMMagic.GREEN_POINT, AMMagic.OFFENSE, 264, 216, knockback);
        Holder<Skill> velocity         = addSkill(bootstrap, AMSpells.VELOCITY,          AMMagic.RED_POINT,   AMMagic.OFFENSE, 264, 264, fling);
        Holder<Skill> wave             = addSkill(bootstrap, AMSpells.WAVE,              AMMagic.RED_POINT,   AMMagic.OFFENSE, 216, 264, damage, velocity);

        Holder<Skill> self              = addSkill(bootstrap, AMSpells.SELF,               AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 120,  24);
        Holder<Skill> jumpBoost         = addSkill(bootstrap, AMSpells.JUMP_BOOST,         AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  72,  24, self);
        Holder<Skill> slowFalling       = addSkill(bootstrap, AMSpells.SLOW_FALLING,       AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  72,  72, jumpBoost);
        Holder<Skill> contingencyFall   = addSkill(bootstrap, AMSpells.CONTINGENCY_FALL,   AMMagic.RED_POINT,   AMMagic.DEFENSE,  24,  72, slowFalling);
        Holder<Skill> slowness          = addSkill(bootstrap, AMSpells.SLOWNESS,           AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  24, 120, slowFalling);
        Holder<Skill> gravityWell       = addSkill(bootstrap, AMSpells.GRAVITY_WELL,       AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  72, 120, slowFalling);
        Holder<Skill> swiftness         = addSkill(bootstrap, AMSpells.SWIFTNESS,          AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 120, 120, slowFalling);
        Holder<Skill> repel             = addSkill(bootstrap, AMSpells.REPEL,              AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  24, 168, slowness);
        Holder<Skill> levitation        = addSkill(bootstrap, AMSpells.LEVITATION,         AMMagic.GREEN_POINT, AMMagic.DEFENSE,  72, 168, gravityWell);
        Holder<Skill> swiftSwim         = addSkill(bootstrap, AMSpells.SWIFT_SWIM,         AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 120, 168, swiftness);
        Holder<Skill> entangle          = addSkill(bootstrap, AMSpells.ENTANGLE,           AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  24, 216, repel);
        Holder<Skill> flight            = addSkill(bootstrap, AMSpells.FLIGHT,             AMMagic.RED_POINT,   AMMagic.DEFENSE,  72, 216, levitation);
        Holder<Skill> haste             = addSkill(bootstrap, AMSpells.HASTE,              AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 120, 216, swiftSwim);
        Holder<Skill> wall              = addSkill(bootstrap, AMSpells.WALL,               AMMagic.GREEN_POINT, AMMagic.DEFENSE,  24, 264, entangle);
        Holder<Skill> rune              = addSkill(bootstrap, AMSpells.RUNE,               AMMagic.GREEN_POINT, AMMagic.DEFENSE,  72, 264, haste, entangle);
        Holder<Skill> runePower         = addSkill(bootstrap, AMSpells.RUNE_POWER,         AMMagic.GREEN_POINT, AMMagic.DEFENSE, 120, 264, rune);
        Holder<Skill> regeneration      = addSkill(bootstrap, AMSpells.REGENERATION,       AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 168,  24, self);
        Holder<Skill> shrink            = addSkill(bootstrap, AMSpells.SHRINK,             AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 216,  24, regeneration);
        Holder<Skill> heal              = addSkill(bootstrap, AMSpells.HEAL,               AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 216,  72, regeneration);
        Holder<Skill> healing           = addSkill(bootstrap, AMSpells.HEALING,            AMMagic.GREEN_POINT, AMMagic.DEFENSE, 264,  72, heal);
        Holder<Skill> lifeTap           = addSkill(bootstrap, AMSpells.LIFE_TAP,           AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168,  72, heal);
        Holder<Skill> lifeDrain         = addSkill(bootstrap, AMSpells.LIFE_DRAIN,         AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168, 120, lifeTap);
        Holder<Skill> manaDrain         = addSkill(bootstrap, AMSpells.MANA_DRAIN,         AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168, 168, lifeDrain);
        Holder<Skill> summon            = addSkill(bootstrap, AMSpells.SUMMON,             AMMagic.GREEN_POINT, AMMagic.DEFENSE, 120,  72, lifeTap);
        Holder<Skill> dispel            = addSkill(bootstrap, AMSpells.DISPEL,             AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 216, 120, heal);
        Holder<Skill> disarm            = addSkill(bootstrap, AMSpells.DISARM,             AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 264, 120, dispel);
        Holder<Skill> zone              = addSkill(bootstrap, AMSpells.ZONE,               AMMagic.GREEN_POINT, AMMagic.DEFENSE, 216, 168, dispel);
        Holder<Skill> shield            = addSkill(bootstrap, AMSpells.SHIELD,             AMMagic.GREEN_POINT, AMMagic.DEFENSE, 216, 216, zone);
        Holder<Skill> contingencyHealth = addSkill(bootstrap, AMSpells.CONTINGENCY_HEALTH, AMMagic.RED_POINT,   AMMagic.DEFENSE, 264, 216, shield);
        Holder<Skill> absorption        = addSkill(bootstrap, AMSpells.ABSORPTION,         AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168, 216, shield);
        Holder<Skill> reflect           = addSkill(bootstrap, AMSpells.REFLECT,            AMMagic.GREEN_POINT, AMMagic.DEFENSE, 216, 264, shield);
        Holder<Skill> contingencyDamage = addSkill(bootstrap, AMSpells.CONTINGENCY_DAMAGE, AMMagic.RED_POINT,   AMMagic.DEFENSE, 216, 312, reflect);
        Holder<Skill> temporalAnchor    = addSkill(bootstrap, AMSpells.TEMPORAL_ANCHOR,    AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168, 264, reflect);
        Holder<Skill> duration          = addSkill(bootstrap, AMSpells.DURATION,           AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168, 312, temporalAnchor);

        Holder<Skill> touch              = addSkill(bootstrap, AMSpells.TOUCH,               AMMagic.BLUE_POINT,  AMMagic.UTILITY, 120,  24);
        Holder<Skill> targetNonSolid     = addSkill(bootstrap, AMSpells.TARGET_NON_SOLID,    AMMagic.BLUE_POINT,  AMMagic.UTILITY,  72,  24, touch);
        Holder<Skill> dig                = addSkill(bootstrap, AMSpells.DIG,                 AMMagic.BLUE_POINT,  AMMagic.UTILITY, 120,  72, touch);
        Holder<Skill> placeBlock         = addSkill(bootstrap, AMSpells.PLACE_BLOCK,         AMMagic.BLUE_POINT,  AMMagic.UTILITY, 168,  24, dig);
        Holder<Skill> wizardsAutumn      = addSkill(bootstrap, AMSpells.WIZARDS_AUTUMN,      AMMagic.BLUE_POINT,  AMMagic.UTILITY, 168,  72, dig);
        Holder<Skill> silkTouch          = addSkill(bootstrap, AMSpells.SILK_TOUCH,          AMMagic.BLUE_POINT,  AMMagic.UTILITY,  72,  72, dig);
        Holder<Skill> miningPower        = addSkill(bootstrap, AMSpells.MINING_POWER,        AMMagic.BLUE_POINT,  AMMagic.UTILITY,  24,  72, silkTouch);
        Holder<Skill> light              = addSkill(bootstrap, AMSpells.LIGHT,               AMMagic.BLUE_POINT,  AMMagic.UTILITY, 120, 120, dig);
        Holder<Skill> rift               = addSkill(bootstrap, AMSpells.RIFT,                AMMagic.GREEN_POINT, AMMagic.UTILITY, 120, 168, light);
        Holder<Skill> channel            = addSkill(bootstrap, AMSpells.CHANNEL,             AMMagic.GREEN_POINT, AMMagic.UTILITY, 120, 216, rift);
        Holder<Skill> nightVision        = addSkill(bootstrap, AMSpells.NIGHT_VISION,        AMMagic.BLUE_POINT,  AMMagic.UTILITY,  72, 120, light);
        Holder<Skill> lunar              = addSkill(bootstrap, AMSpells.LUNAR,               AMMagic.RED_POINT,   AMMagic.UTILITY,  24, 120, nightVision);
        Holder<Skill> trueSight          = addSkill(bootstrap, AMSpells.TRUE_SIGHT,          AMMagic.BLUE_POINT,  AMMagic.UTILITY,  72, 168, nightVision);
        Holder<Skill> invisibility       = addSkill(bootstrap, AMSpells.INVISIBILITY,        AMMagic.BLUE_POINT,  AMMagic.UTILITY,  24, 168, trueSight);
        Holder<Skill> randomTeleport     = addSkill(bootstrap, AMSpells.RANDOM_TELEPORT,     AMMagic.GREEN_POINT, AMMagic.UTILITY,  24, 216, invisibility);
        Holder<Skill> range              = addSkill(bootstrap, AMSpells.RANGE,               AMMagic.GREEN_POINT, AMMagic.UTILITY,  72, 216, randomTeleport);
        Holder<Skill> blink              = addSkill(bootstrap, AMSpells.BLINK,               AMMagic.GREEN_POINT, AMMagic.UTILITY,  24, 264, randomTeleport);
        Holder<Skill> transplace         = addSkill(bootstrap, AMSpells.TRANSPLACE,          AMMagic.GREEN_POINT, AMMagic.UTILITY,  72, 264, blink);
        Holder<Skill> recall             = addSkill(bootstrap, AMSpells.RECALL,              AMMagic.GREEN_POINT, AMMagic.UTILITY,  72, 312, transplace);
        Holder<Skill> divineIntervention = addSkill(bootstrap, AMSpells.DIVINE_INTERVENTION, AMMagic.RED_POINT,   AMMagic.UTILITY,  24, 312, recall);
        Holder<Skill> enderIntervention  = addSkill(bootstrap, AMSpells.ENDER_INTERVENTION,  AMMagic.RED_POINT,   AMMagic.UTILITY, 120, 312, recall);
        Holder<Skill> contingencyDeath   = addSkill(bootstrap, AMSpells.CONTINGENCY_DEATH,   AMMagic.RED_POINT,   AMMagic.UTILITY, 168, 312, enderIntervention);
        Holder<Skill> plant              = addSkill(bootstrap, AMSpells.PLANT,               AMMagic.BLUE_POINT,  AMMagic.UTILITY, 168, 120, light);
        Holder<Skill> grow               = addSkill(bootstrap, AMSpells.GROW,                AMMagic.BLUE_POINT,  AMMagic.UTILITY, 216, 120, plant);
        Holder<Skill> harvest            = addSkill(bootstrap, AMSpells.HARVEST,             AMMagic.BLUE_POINT,  AMMagic.UTILITY, 216, 168, grow);
        Holder<Skill> charm              = addSkill(bootstrap, AMSpells.CHARM,               AMMagic.BLUE_POINT,  AMMagic.UTILITY, 264, 120, light);
        Holder<Skill> attract            = addSkill(bootstrap, AMSpells.ATTRACT,             AMMagic.BLUE_POINT,  AMMagic.UTILITY, 264, 168, charm);
        //Holder<Skill> telekinesis        = addSkill(bootstrap, AMSpells.TELEKINESIS,         AMMagic.BLUE_POINT,  AMMagic.UTILITY, 264, 216, attract);
        Holder<Skill> plow               = addSkill(bootstrap, AMSpells.PLOW,                AMMagic.BLUE_POINT,  AMMagic.UTILITY, 168, 168, plant);
        Holder<Skill> createWater        = addSkill(bootstrap, AMSpells.CREATE_WATER,        AMMagic.BLUE_POINT,  AMMagic.UTILITY, 168, 216, plow);
        Holder<Skill> waterBreathing     = addSkill(bootstrap, AMSpells.WATER_BREATHING,     AMMagic.BLUE_POINT,  AMMagic.UTILITY, 216, 216, createWater);
        Holder<Skill> drought            = addSkill(bootstrap, AMSpells.DROUGHT,             AMMagic.GREEN_POINT, AMMagic.UTILITY, 168, 264, createWater);
        Holder<Skill> banishRain         = addSkill(bootstrap, AMSpells.BANISH_RAIN,         AMMagic.GREEN_POINT, AMMagic.UTILITY, 216, 264, drought);

        Holder<Skill> color                  = addSkill(bootstrap, AMSpells.COLOR,                    AMMagic.BLUE_POINT,  AMMagic.TALENT,  24,  24);
        Holder<Skill> manaRegenerationBoost1 = addSkill(bootstrap, AMMagic.MANA_REGENERATION_BOOST_1, AMMagic.BLUE_POINT,  AMMagic.TALENT, 120,  24);
        Holder<Skill> manaRegenerationBoost2 = addSkill(bootstrap, AMMagic.MANA_REGENERATION_BOOST_2, AMMagic.GREEN_POINT, AMMagic.TALENT, 120,  72, manaRegenerationBoost1);
        Holder<Skill> manaRegenerationBoost3 = addSkill(bootstrap, AMMagic.MANA_REGENERATION_BOOST_3, AMMagic.RED_POINT,   AMMagic.TALENT, 120, 120, manaRegenerationBoost2);
        Holder<Skill> affinityGainsBoost     = addSkill(bootstrap, AMMagic.AFFINITY_GAINS_BOOST,      AMMagic.BLUE_POINT,  AMMagic.TALENT, 168,  24, manaRegenerationBoost1);
        //Holder<Skill> mageBand1              = addSkill(bootstrap, AMMagic.MAGE_BAND_1,               AMMagic.GREEN_POINT, AMMagic.TALENT, 168,  72, manaRegenerationBoost2);
        //Holder<Skill> mageBand2              = addSkill(bootstrap, AMMagic.MAGE_BAND_2,               AMMagic.RED_POINT,   AMMagic.TALENT, 168, 120, mageBand1);
        Holder<Skill> spellMotion            = addSkill(bootstrap, AMMagic.SPELL_MOTION,              AMMagic.BLUE_POINT,  AMMagic.TALENT,  72,  24, manaRegenerationBoost1);
        Holder<Skill> augmentedCasting       = addSkill(bootstrap, AMMagic.AUGMENTED_CASTING,         AMMagic.GREEN_POINT, AMMagic.TALENT,  72,  72, spellMotion);
        Holder<Skill> extraSummons           = addSkill(bootstrap, AMMagic.EXTRA_SUMMONS,             AMMagic.RED_POINT,   AMMagic.TALENT,  72, 120, augmentedCasting);

        addHiddenSkill(bootstrap, AMSpells.BLIZZARD,       AMMagic.OFFENSE,  24, 168);
        addHiddenSkill(bootstrap, AMSpells.DAYLIGHT,       AMMagic.UTILITY, 216,  24);
        addHiddenSkill(bootstrap, AMSpells.FALLING_STAR,   AMMagic.OFFENSE,  72, 168);
        addHiddenSkill(bootstrap, AMSpells.FIRE_RAIN,      AMMagic.OFFENSE,  24, 216);
        addHiddenSkill(bootstrap, AMSpells.HEALTH_BOOST,   AMMagic.DEFENSE,  24,  24);
        addHiddenSkill(bootstrap, AMSpells.MANA_BLAST,     AMMagic.OFFENSE,  72, 216);
        addHiddenSkill(bootstrap, AMSpells.MOONRISE,       AMMagic.UTILITY, 264,  24);
        addHiddenSkill(bootstrap, AMSpells.DISMEMBERING,   AMMagic.OFFENSE,  24, 264);
        addHiddenSkill(bootstrap, AMSpells.EFFECT_POWER,   AMMagic.DEFENSE, 264,  24);
        addHiddenSkill(bootstrap, AMSpells.PROSPERITY,     AMMagic.UTILITY,  24,  24);
        addHiddenSkill(bootstrap, AMMagic.SHIELD_OVERLOAD, AMMagic.TALENT,   24,  72);
        // @formatter:on
    }

    private static void addHiddenSkill(BootstrapContext<Skill> bootstrap, ResourceKey<Skill> key, ResourceKey<OcculusTab> tab, int x, int y) {
        bootstrap.register(key, new Skill(List.of(), Optional.empty(), bootstrap.lookup(AMRegistryKeys.OCCULUS_TAB).getOrThrow(tab), x, y, true));
    }

    private static void addHiddenSkill(BootstrapContext<Skill> bootstrap, DeferredHolder<SpellPart, ?> part, ResourceKey<OcculusTab> tab, int x, int y) {
        addHiddenSkill(bootstrap, fromPart(part), tab, x, y);
    }

    @SafeVarargs
    private static Holder<Skill> addSkill(BootstrapContext<Skill> bootstrap, ResourceKey<Skill> key, ResourceKey<SkillPoint> point, ResourceKey<OcculusTab> tab, int x, int y, Holder<Skill>... parents) {
        return bootstrap.register(key, new Skill(
            Arrays.asList(parents),
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

    @SuppressWarnings("DataFlowIssue")
    private static ResourceKey<Skill> fromPart(DeferredHolder<SpellPart, ?> part) {
        return ResourceKey.create(AMRegistryKeys.SKILL, ArsMagicaApi.spellPartRegistry().getKey(part.get()));
    }
}
