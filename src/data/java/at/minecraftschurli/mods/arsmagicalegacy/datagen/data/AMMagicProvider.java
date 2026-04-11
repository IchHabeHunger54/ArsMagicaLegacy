package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPartData;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEtheriumTypes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMParticles;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.spell.EtheriumSpellIngredient;
import at.minecraftschurli.mods.arsmagicalegacy.spell.ItemSpellIngredient;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class AMMagicProvider {
    public static void addAffinities(BootstrapContext<Affinity> bootstrap) {
        HolderOwner<Affinity> owner = new HolderOwner<>() {
            @Override
            public boolean canSerializeIn(HolderOwner<Affinity> owner) {
                return true;
            }
        };
        bootstrap.register(Affinity.NONE, new Affinity(Holder.Reference.createStandAlone(owner, Affinity.NONE), List.of(), List.of(), List.of(), 0, -1, Optional.of(AMSounds.CAST_NONE), Optional.empty(), AMParticles.NONE_HAND.get()));
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
        bootstrap.register(AMMagic.WATER,     new Affinity(fire,      List.of(lightning, ender), List.of(air, arcane),      List.of(ice, nature),      0x0b5cef,  8, AMSounds.CAST_WATER,     AMSounds.LOOP_WATER,     AMParticles.WATER_HAND.get()));
        bootstrap.register(AMMagic.FIRE,      new Affinity(water,     List.of(ice, nature),      List.of(earth, life),      List.of(lightning, ender), 0xef260b,  3, AMSounds.CAST_FIRE,      AMSounds.LOOP_FIRE,      AMParticles.FIRE_HAND.get()));
        bootstrap.register(AMMagic.EARTH,     new Affinity(air,       List.of(lightning, life),  List.of(fire, nature),     List.of(ice, arcane),      0x61330b, 10, AMSounds.CAST_EARTH,     AMSounds.LOOP_EARTH,     AMParticles.EARTH_HAND.get()));
        bootstrap.register(AMMagic.AIR,       new Affinity(earth,     List.of(ice, arcane),      List.of(water, ender),     List.of(lightning, life),  0x777777,  5, AMSounds.CAST_AIR,       AMSounds.LOOP_AIR,       AMParticles.AIR_HAND.get()));
        bootstrap.register(AMMagic.ICE,       new Affinity(lightning, List.of(fire, air),        List.of(life, ender),      List.of(water, earth),     0xd3e8fc,  9, AMSounds.CAST_ICE,       AMSounds.LOOP_ICE,       AMParticles.ICE_HAND.get()));
        bootstrap.register(AMMagic.LIGHTNING, new Affinity(ice,       List.of(water, earth),     List.of(nature, arcane),   List.of(fire, air),        0xdece19,  4, AMSounds.CAST_LIGHTNING, AMSounds.LOOP_LIGHTNING, AMParticles.LIGHTNING_HAND.get()));
        bootstrap.register(AMMagic.NATURE,    new Affinity(ender,     List.of(fire, arcane),     List.of(earth, lightning), List.of(water, life),      0x228718,  7, AMSounds.CAST_NATURE,    AMSounds.LOOP_NATURE,    AMParticles.NATURE_HAND.get()));
        bootstrap.register(AMMagic.LIFE,      new Affinity(arcane,    List.of(earth, ender),     List.of(fire, ice),        List.of(air, nature),      0x34e122,  6, AMSounds.CAST_LIFE,      AMSounds.LOOP_LIFE,      AMParticles.LIFE_HAND.get()));
        bootstrap.register(AMMagic.ARCANE,    new Affinity(life,      List.of(air, nature),      List.of(water, lightning), List.of(earth, ender),     0xb935cd,  1, AMSounds.CAST_ARCANE,    AMSounds.LOOP_ARCANE,    AMParticles.ARCANE_HAND.get()));
        bootstrap.register(AMMagic.ENDER,     new Affinity(nature,    List.of(water, life),      List.of(air, ice),         List.of(fire, arcane),     0x3f043d,  2, AMSounds.CAST_ENDER,     AMSounds.LOOP_ENDER,     AMParticles.ENDER_HAND.get()));
        // @formatter:on
    }

    public static void addAltarCapMaterials(BootstrapContext<AltarCapMaterial> bootstrap) {
        addAltarCapMaterial(bootstrap, "glass", Blocks.GLASS, 1);
        addAltarCapMaterial(bootstrap, "coal", Blocks.COAL_BLOCK, 2);
        addAltarCapMaterial(bootstrap, "copper", Blocks.COPPER_BLOCK, 3);
        addAltarCapMaterial(bootstrap, "exposed_copper", Blocks.EXPOSED_COPPER, 3);
        addAltarCapMaterial(bootstrap, "weathered_copper", Blocks.WEATHERED_COPPER, 3);
        addAltarCapMaterial(bootstrap, "oxidized_copper", Blocks.OXIDIZED_COPPER, 3);
        addAltarCapMaterial(bootstrap, "waxed_copper", Blocks.WAXED_COPPER_BLOCK, 3);
        addAltarCapMaterial(bootstrap, "waxed_exposed_copper", Blocks.WAXED_EXPOSED_COPPER, 3);
        addAltarCapMaterial(bootstrap, "waxed_weathered_copper", Blocks.WAXED_WEATHERED_COPPER, 3);
        addAltarCapMaterial(bootstrap, "waxed_oxidized_copper", Blocks.WAXED_OXIDIZED_COPPER, 3);
        addAltarCapMaterial(bootstrap, "iron", Blocks.IRON_BLOCK, 4);
        addAltarCapMaterial(bootstrap, "redstone", Blocks.REDSTONE_BLOCK, 5);
        addAltarCapMaterial(bootstrap, "vinteum", AMBlocks.VINTEUM_BLOCK.get(), 6);
        addAltarCapMaterial(bootstrap, "chimerite", AMBlocks.CHIMERITE_BLOCK.get(), 7);
        addAltarCapMaterial(bootstrap, "lapis", Blocks.LAPIS_BLOCK, 8);
        addAltarCapMaterial(bootstrap, "gold", Blocks.GOLD_BLOCK, 9);
        addAltarCapMaterial(bootstrap, "topaz", AMBlocks.TOPAZ_BLOCK.get(), 10);
        addAltarCapMaterial(bootstrap, "diamond", Blocks.DIAMOND_BLOCK, 11);
        addAltarCapMaterial(bootstrap, "emerald", Blocks.EMERALD_BLOCK, 12);
        addAltarCapMaterial(bootstrap, "netherite", Blocks.NETHERITE_BLOCK, 13);
        addAltarCapMaterial(bootstrap, "moonstone", AMBlocks.MOONSTONE_BLOCK.get(), 14);
        addAltarCapMaterial(bootstrap, "sunstone", AMBlocks.SUNSTONE_BLOCK.get(), 15);
    }

    public static void addAltarMaterials(BootstrapContext<AltarMaterial> bootstrap) {
        addAltarMaterial(bootstrap, BlockFamilies.OAK_PLANKS, 1);
        addAltarMaterial(bootstrap, BlockFamilies.SPRUCE_PLANKS, 1);
        addAltarMaterial(bootstrap, BlockFamilies.BIRCH_PLANKS, 1);
        addAltarMaterial(bootstrap, BlockFamilies.JUNGLE_PLANKS, 1);
        addAltarMaterial(bootstrap, BlockFamilies.ACACIA_PLANKS, 1);
        addAltarMaterial(bootstrap, BlockFamilies.DARK_OAK_PLANKS, 1);
        addAltarMaterial(bootstrap, BlockFamilies.MANGROVE_PLANKS, 1);
        addAltarMaterial(bootstrap, BlockFamilies.BAMBOO_PLANKS, 1);
        addAltarMaterial(bootstrap, BlockFamilies.CHERRY_PLANKS, 1);
        addAltarMaterial(bootstrap, BlockFamilies.COBBLESTONE, 2);
        addAltarMaterial(bootstrap, BlockFamilies.STONE, 2);
        addAltarMaterial(bootstrap, BlockFamilies.MUD_BRICKS, 2);
        addAltarMaterial(bootstrap, BlockFamilies.MOSSY_COBBLESTONE, 2);
        addAltarMaterial(bootstrap, BlockFamilies.COBBLED_DEEPSLATE, 2);
        addAltarMaterial(bootstrap, BlockFamilies.ANDESITE, 2);
        addAltarMaterial(bootstrap, BlockFamilies.DIORITE, 2);
        addAltarMaterial(bootstrap, BlockFamilies.GRANITE, 2);
        addAltarMaterial(bootstrap, BlockFamilies.SANDSTONE, 2);
        addAltarMaterial(bootstrap, BlockFamilies.RED_SANDSTONE, 2);
        addAltarMaterial(bootstrap, BlockFamilies.BRICKS, 3);
        addAltarMaterial(bootstrap, BlockFamilies.STONE_BRICK, 3);
        addAltarMaterial(bootstrap, BlockFamilies.MOSSY_STONE_BRICKS, 3);
        addAltarMaterial(bootstrap, BlockFamilies.POLISHED_DEEPSLATE, 3);
        addAltarMaterial(bootstrap, BlockFamilies.DEEPSLATE_BRICKS, 3);
        addAltarMaterial(bootstrap, BlockFamilies.DEEPSLATE_TILES, 3);
        addAltarMaterial(bootstrap, BlockFamilies.POLISHED_ANDESITE, 3);
        addAltarMaterial(bootstrap, BlockFamilies.POLISHED_DIORITE, 3);
        addAltarMaterial(bootstrap, BlockFamilies.POLISHED_GRANITE, 3);
        addAltarMaterial(bootstrap, BlockFamilies.SMOOTH_SANDSTONE, 3);
        addAltarMaterial(bootstrap, BlockFamilies.SMOOTH_RED_SANDSTONE, 3);
        addAltarMaterial(bootstrap, BlockFamilies.CUT_COPPER, 3);
        addAltarMaterial(bootstrap, BlockFamilies.EXPOSED_CUT_COPPER, 3);
        addAltarMaterial(bootstrap, BlockFamilies.WEATHERED_CUT_COPPER, 3);
        addAltarMaterial(bootstrap, BlockFamilies.OXIDIZED_CUT_COPPER, 3);
        addAltarMaterial(bootstrap, BlockFamilies.WAXED_CUT_COPPER, 3);
        addAltarMaterial(bootstrap, BlockFamilies.WAXED_EXPOSED_CUT_COPPER, 3);
        addAltarMaterial(bootstrap, BlockFamilies.WAXED_WEATHERED_CUT_COPPER, 3);
        addAltarMaterial(bootstrap, BlockFamilies.WAXED_OXIDIZED_CUT_COPPER, 3);
        addAltarMaterial(bootstrap, BlockFamilies.PRISMARINE, 4);
        addAltarMaterial(bootstrap, BlockFamilies.PRISMARINE_BRICKS, 4);
        addAltarMaterial(bootstrap, BlockFamilies.DARK_PRISMARINE, 4);
        addAltarMaterial(bootstrap, BlockFamilies.CRIMSON_PLANKS, 4);
        addAltarMaterial(bootstrap, BlockFamilies.WARPED_PLANKS, 4);
        addAltarMaterial(bootstrap, AMBlocks.WITCHWOOD_BLOCK_FAMILY.get(), 4);
        addAltarMaterial(bootstrap, BlockFamilies.BLACKSTONE, 4);
        addAltarMaterial(bootstrap, BlockFamilies.QUARTZ, 4);
        addAltarMaterial(bootstrap, BlockFamilies.NETHER_BRICKS, 5);
        addAltarMaterial(bootstrap, BlockFamilies.RED_NETHER_BRICKS, 5);
        addAltarMaterial(bootstrap, BlockFamilies.POLISHED_BLACKSTONE, 5);
        addAltarMaterial(bootstrap, BlockFamilies.POLISHED_BLACKSTONE_BRICKS, 5);
        addAltarMaterial(bootstrap, BlockFamilies.SMOOTH_QUARTZ, 5);
        addAltarMaterial(bootstrap, BlockFamilies.END_STONE_BRICKS, 6);
        addAltarMaterial(bootstrap, BlockFamilies.PURPUR, 6);
    }

    public static void addOcculusTabs(BootstrapContext<OcculusTab> bootstrap) {
        bootstrap.register(AMMagic.OFFENSE, new OcculusTab(368, 320, 85, 0, 0, ArsMagicaApi.id("skill_tree")));
        bootstrap.register(AMMagic.DEFENSE, new OcculusTab(320, 368, 38, 0, 1, ArsMagicaApi.id("skill_tree")));
        bootstrap.register(AMMagic.UTILITY, new OcculusTab(320, 368, 38, 0, 2, ArsMagicaApi.id("skill_tree")));
        bootstrap.register(AMMagic.TALENT, new OcculusTab(224, 196, 14, 0, 3, ArsMagicaApi.id("skill_tree")));
        bootstrap.register(AMMagic.AFFINITY, new OcculusTab(196, 196, 0, 0, 4, ArsMagicaApi.id("affinity")));
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
        Holder<Skill> resistance        = addSkill(bootstrap, AMSpells.RESISTANCE,         AMMagic.GREEN_POINT, AMMagic.DEFENSE, 216, 216, zone);
        Holder<Skill> contingencyHealth = addSkill(bootstrap, AMSpells.CONTINGENCY_HEALTH, AMMagic.RED_POINT,   AMMagic.DEFENSE, 264, 216, resistance);
        Holder<Skill> absorption        = addSkill(bootstrap, AMSpells.ABSORPTION,         AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168, 216, resistance);
        Holder<Skill> reflect           = addSkill(bootstrap, AMSpells.REFLECT,            AMMagic.GREEN_POINT, AMMagic.DEFENSE, 216, 264, resistance);
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
        Holder<Skill> charm              = addSkill(bootstrap, AMSpells.CHARM,               AMMagic.BLUE_POINT,  AMMagic.UTILITY, 168, 120, light);
        Holder<Skill> attract            = addSkill(bootstrap, AMSpells.ATTRACT,             AMMagic.BLUE_POINT,  AMMagic.UTILITY, 216, 120, charm);
        Holder<Skill> plow               = addSkill(bootstrap, AMSpells.PLOW,                AMMagic.BLUE_POINT,  AMMagic.UTILITY, 168, 168, light);
        Holder<Skill> grow               = addSkill(bootstrap, AMSpells.GROW,                AMMagic.BLUE_POINT,  AMMagic.UTILITY, 216, 168, plow);
        Holder<Skill> harvest            = addSkill(bootstrap, AMSpells.HARVEST,             AMMagic.BLUE_POINT,  AMMagic.UTILITY, 264, 168, grow);
        Holder<Skill> replant            = addSkill(bootstrap, AMSpells.REPLANT,             AMMagic.BLUE_POINT,  AMMagic.UTILITY, 264, 216, harvest);
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

    public static void addSpellPartData(BootstrapContext<SpellPartData> bootstrap) {
        HolderGetter<Affinity> affinities = bootstrap.lookup(AMRegistries.Keys.AFFINITY);
        HolderGetter<EtheriumType> etheriumTypes = bootstrap.lookup(AMRegistries.Keys.ETHERIUM_TYPE);
        HolderGetter<Item> items = bootstrap.lookup(Registries.ITEM);
        addSpellPartData(bootstrap, AMSpells.AREA_OF_EFFECT, 2f,
            new ItemSpellIngredient(Ingredient.of(Items.TNT), 1), 
            new EtheriumSpellIngredient(1));
        addSpellPartData(bootstrap, AMSpells.BEAM, 1f, 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ)), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1), 
            new EtheriumSpellIngredient(etheriumTypes.getOrThrow(AMEtheriumTypes.LIGHT), 2500));
        addSpellPartData(bootstrap, AMSpells.CHAIN, 1f, 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ)), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.STRINGS)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.LEAD), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.TRIPWIRE_HOOK), 1), 
            new EtheriumSpellIngredient(etheriumTypes.getOrThrow(AMEtheriumTypes.LIGHT), 2500));
        addSpellPartData(bootstrap, AMSpells.CHANNEL, 0.5f, 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1));
        addSpellPartData(bootstrap, AMSpells.CONTINGENCY_DAMAGE, 10f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIGHTNING), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_MOONSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1), 
            new EtheriumSpellIngredient(5000));
        addSpellPartData(bootstrap, AMSpells.CONTINGENCY_DEATH, 10f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ENDER), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1), 
            new EtheriumSpellIngredient(etheriumTypes.getOrThrow(AMEtheriumTypes.DARK), 5000));
        addSpellPartData(bootstrap, AMSpells.CONTINGENCY_FALL, 10f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.AIR), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1), 
            new EtheriumSpellIngredient(5000));
        addSpellPartData(bootstrap, AMSpells.CONTINGENCY_FIRE, 10f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.FIRE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_SUNSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1), 
            new EtheriumSpellIngredient(5000));
        addSpellPartData(bootstrap, AMSpells.CONTINGENCY_HEALTH, 10f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIFE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1), 
            new EtheriumSpellIngredient(etheriumTypes.getOrThrow(AMEtheriumTypes.LIGHT), 5000));
        addSpellPartData(bootstrap, AMSpells.PROJECTILE, 1f, 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.ARROW), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.SNOWBALL), 1));
        addSpellPartData(bootstrap, AMSpells.RUNE, 2f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.RED_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1));
        addSpellPartData(bootstrap, AMSpells.SELF, 0.5f, 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1));
        addSpellPartData(bootstrap, AMSpells.TOUCH, 1f, 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.FEATHERS)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.CLAY_BALL), 1));
        addSpellPartData(bootstrap, AMSpells.WALL, 2.5f, 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.FENCES_WOODEN)), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGIC_WALL.get()), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(ItemTags.WALLS)), 1), 
            new EtheriumSpellIngredient(2500));
        addSpellPartData(bootstrap, AMSpells.WAVE, 2.5f, 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGIC_WALL.get()), 1), 
            new EtheriumSpellIngredient(2500));
        addSpellPartData(bootstrap, AMSpells.ZONE, 2.5f, 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_MOONSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_SUNSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1), 
            new EtheriumSpellIngredient(2500));
        addSpellPartData(bootstrap, AMSpells.DROWNING_DAMAGE, 25f, 
            affinities.getOrThrow(AMMagic.WATER), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.WATER_BUCKET), 1));
        addSpellPartData(bootstrap, AMSpells.FIRE_DAMAGE, 25f, 
            affinities.getOrThrow(AMMagic.FIRE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.RED_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.FLINT_AND_STEEL), 1));
        addSpellPartData(bootstrap, AMSpells.FROST_DAMAGE, 25f, 
            affinities.getOrThrow(AMMagic.ICE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.CYAN_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_BLUE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.ICE), 1));
        addSpellPartData(bootstrap, AMSpells.LIGHTNING_DAMAGE, 25f, 
            affinities.getOrThrow(AMMagic.LIGHTNING), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.LIGHTNING_ROD), 1));
        addSpellPartData(bootstrap, AMSpells.MAGIC_DAMAGE, 25f, 
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.BOOK), 1));
        addSpellPartData(bootstrap, AMSpells.PHYSICAL_DAMAGE, 25f, 
            affinities.getOrThrow(AMMagic.EARTH), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.RED_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.INGOTS_IRON)), 1));
        addSpellPartData(bootstrap, AMSpells.ABSORPTION, 50f, 
            affinities.getOrThrow(AMMagic.LIFE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.GOLDEN_APPLE), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.SHIELD), 1));
        addSpellPartData(bootstrap, AMSpells.BLINDNESS, 40f, 
            affinities.getOrThrow(AMMagic.ENDER), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.NIGHT_VISION), Items.POTION), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.WEAKNESS), Items.POTION), 1));
        addSpellPartData(bootstrap, AMSpells.HASTE, 30f, 
            affinities.getOrThrow(AMMagic.LIGHTNING), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_GLOWSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_REDSTONE)), 1));
        addSpellPartData(bootstrap, AMSpells.HEALTH_BOOST, 50f, 
            affinities.getOrThrow(AMMagic.LIFE), 0.001f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIFE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_BLUE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE), 1));
        addSpellPartData(bootstrap, AMSpells.INVISIBILITY, 40f, 
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.INVISIBILITY), Items.POTION), 1));
        addSpellPartData(bootstrap, AMSpells.JUMP_BOOST, 30f, 
            affinities.getOrThrow(AMMagic.AIR), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.LIME_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1));
        addSpellPartData(bootstrap, AMSpells.LEVITATION, 40f, 
            affinities.getOrThrow(AMMagic.AIR), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.POPPED_CHORUS_FRUIT), 1));
        addSpellPartData(bootstrap, AMSpells.NIGHT_VISION, 30f, 
            affinities.getOrThrow(AMMagic.ENDER), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.GOLDEN_CARROT), 1));
        addSpellPartData(bootstrap, AMSpells.NAUSEA, 200f, 
            affinities.getOrThrow(AMMagic.LIFE), 0.0001f);
        addSpellPartData(bootstrap, AMSpells.REGENERATION, 30f, 
            affinities.getOrThrow(AMMagic.LIFE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.PINK_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.GHAST_TEAR), 1));
        addSpellPartData(bootstrap, AMSpells.RESISTANCE, 50f, 
            affinities.getOrThrow(AMMagic.EARTH), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BROWN_RUNE), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.SHIELD), 1));
        addSpellPartData(bootstrap, AMSpells.SLOWNESS, 30f, 
            affinities.getOrThrow(AMMagic.ICE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.SLIME_BALLS)), 1));
        addSpellPartData(bootstrap, AMSpells.SLOW_FALLING, 30f, 
            affinities.getOrThrow(AMMagic.AIR), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.PHANTOM_MEMBRANE), 1));
        addSpellPartData(bootstrap, AMSpells.SWIFTNESS, 40f, 
            affinities.getOrThrow(AMMagic.AIR), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_REDSTONE)), 1));
        addSpellPartData(bootstrap, AMSpells.WATER_BREATHING, 40f, 
            affinities.getOrThrow(AMMagic.WATER), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.WAKEBLOOM.get()), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.PUFFERFISH), 1));
        addSpellPartData(bootstrap, AMSpells.ASTRAL_DISTORTION, 40f, 
            affinities.getOrThrow(AMMagic.ENDER), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.ENDER_PEARLS)), 1));
        addSpellPartData(bootstrap, AMSpells.ENTANGLE, 40f, 
            affinities.getOrThrow(AMMagic.NATURE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.LIME_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.SLIME_BALLS)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.VINE), 1));
        addSpellPartData(bootstrap, AMSpells.FLIGHT, 50f, 
            affinities.getOrThrow(AMMagic.AIR), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.NETHER_STARS)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.GHAST_TEAR), 1));
        addSpellPartData(bootstrap, AMSpells.FROST, 40f, 
            affinities.getOrThrow(AMMagic.ICE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_BLUE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.POWDER_SNOW_BUCKET), 1));
        addSpellPartData(bootstrap, AMSpells.FURY, 50f, 
            affinities.getOrThrow(AMMagic.LIGHTNING), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.RODS_BLAZE)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.TROPICAL_FISH), 1));
        addSpellPartData(bootstrap, AMSpells.GRAVITY_WELL, 40f, 
            affinities.getOrThrow(AMMagic.EARTH), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.GRAY_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.STONES)), 1));
        addSpellPartData(bootstrap, AMSpells.REFLECT, 50f, 
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_GRAY_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.RODS_BLAZE)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.GLASS_BLOCKS)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.WITCHWOOD_LOGS)), 1));
        addSpellPartData(bootstrap, AMSpells.SCRAMBLE_SYNAPSES, 3000f, 
            affinities.getOrThrow(AMMagic.LIGHTNING), 0.0001f);
        addSpellPartData(bootstrap, AMSpells.SHRINK, 30f, 
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.BONES)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.BROWN_MUSHROOM), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.SPIDER_EYE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.STONE_BUTTON), 1));
        addSpellPartData(bootstrap, AMSpells.SILENCE, 50f, 
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BROWN_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(ItemTags.WOOL)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.JUKEBOX), 1));
        addSpellPartData(bootstrap, AMSpells.SWIFT_SWIM, 40f, 
            affinities.getOrThrow(AMMagic.WATER), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(ItemTags.FISHES)), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.FISHING_ROD), 1));
        addSpellPartData(bootstrap, AMSpells.TEMPORAL_ANCHOR, 50f, 
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.NETHER_STARS)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        addSpellPartData(bootstrap, AMSpells.TRUE_SIGHT, 30f, 
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.GLASS_BLOCKS)), 1));
        addSpellPartData(bootstrap, AMSpells.WATERY_GRAVE, 40f, 
            affinities.getOrThrow(AMMagic.WATER), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.STONES)), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.LEATHER_BOOTS), 1));
        addSpellPartData(bootstrap, AMSpells.ATTRACT, 5f, 
            affinities.getOrThrow(AMMagic.NATURE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.INGOTS_IRON)), 1));
        addSpellPartData(bootstrap, AMSpells.BANISH_RAIN, 200f, 
            affinities.getOrThrow(AMMagic.WATER), 0.005f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.COPPER_INGOT), 1));
        addSpellPartData(bootstrap, AMSpells.BLINK, 80f, 
            affinities.getOrThrow(AMMagic.ENDER), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.ENDER_PEARLS)), 1));
        addSpellPartData(bootstrap, AMSpells.BLIZZARD, 1000f, 
            affinities.getOrThrow(AMMagic.ICE), 0.01f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ICE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.ICE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.PACKED_ICE), 1));
        addSpellPartData(bootstrap, AMSpells.CHARM, 60f, 
            affinities.getOrThrow(AMMagic.LIFE), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.RED_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.WHEAT), 1));
        addSpellPartData(bootstrap, AMSpells.CREATE_WATER, 5f, 
            affinities.getOrThrow(AMMagic.WATER), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.WATER_BUCKET), 1));
        addSpellPartData(bootstrap, AMSpells.DAYLIGHT, 2000f, 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_SUNSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        addSpellPartData(bootstrap, AMSpells.DIG, 5f, 
            affinities.getOrThrow(AMMagic.EARTH), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BROWN_RUNE), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_AXE), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_PICKAXE), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_SHOVEL), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_HOE), 1));
        addSpellPartData(bootstrap, AMSpells.DISARM, 60f, 
            affinities.getOrThrow(AMMagic.ARCANE), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_SPEAR), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_SWORD), 1));
        addSpellPartData(bootstrap, AMSpells.DISPEL, 60f, 
            affinities.getOrThrow(AMMagic.ARCANE), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.MILK_BUCKET), 1));
        addSpellPartData(bootstrap, AMSpells.DIVINE_INTERVENTION, 200f, 
            affinities.getOrThrow(AMMagic.ENDER), 0.005f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.LIME_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.ENDER_PEARLS)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(ItemTags.BEDS)), 1));
        addSpellPartData(bootstrap, AMSpells.DROUGHT, 5f, 
            affinities.getOrThrow(AMMagic.FIRE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.DEAD_BUSH), 1));
        addSpellPartData(bootstrap, AMSpells.ENDER_INTERVENTION, 200f, 
            affinities.getOrThrow(AMMagic.ENDER), 0.005f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.ENDER_PEARLS)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.ENDER_EYE), 1));
        addSpellPartData(bootstrap, AMSpells.EXPLOSION, 100f, 
            affinities.getOrThrow(AMMagic.FIRE), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.GRAY_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.FIRE_CHARGE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.TNT), 1));
        addSpellPartData(bootstrap, AMSpells.FALLING_STAR, 1000f, 
            affinities.getOrThrow(AMMagic.ARCANE), 0.01f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ARCANE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_ARCANE_ASH)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.END_STONES)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.LAVA_BUCKET), 1));
        addSpellPartData(bootstrap, AMSpells.FIRE_RAIN, 1000f, 
            affinities.getOrThrow(AMMagic.FIRE), 0.01f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.FIRE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_ARCANE_ASH)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.NETHERRACKS)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.LAVA_BUCKET), 1));
        addSpellPartData(bootstrap, AMSpells.FLING, 80f, 
            affinities.getOrThrow(AMMagic.AIR), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.PISTON), 1));
        addSpellPartData(bootstrap, AMSpells.FORGE, 80f, 
            affinities.getOrThrow(AMMagic.FIRE), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.FURNACE), 1));
        addSpellPartData(bootstrap, AMSpells.GROW, 5f, 
            affinities.getOrThrow(AMMagic.NATURE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.BONE_MEAL), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.WITCHWOOD_LOGS)), 1));
        addSpellPartData(bootstrap, AMSpells.HARVEST, 5f, 
            affinities.getOrThrow(AMMagic.NATURE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.SHEARS), 1));
        addSpellPartData(bootstrap, AMSpells.HEAL, 60f, 
            affinities.getOrThrow(AMMagic.LIFE), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.PINK_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_GLOWSTONE)), 1));
        addSpellPartData(bootstrap, AMSpells.IGNITION, 80f, 
            affinities.getOrThrow(AMMagic.FIRE), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.FLINT_AND_STEEL), 1));
        addSpellPartData(bootstrap, AMSpells.KNOCKBACK, 80f, 
            affinities.getOrThrow(AMMagic.AIR), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.PISTON), 1));
        addSpellPartData(bootstrap, AMSpells.LIFE_DRAIN, 5f, 
            affinities.getOrThrow(AMMagic.LIFE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_SUNSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1));
        addSpellPartData(bootstrap, AMSpells.LIFE_TAP, 5f, 
            affinities.getOrThrow(AMMagic.LIFE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.PINK_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1));
        addSpellPartData(bootstrap, AMSpells.LIGHT, 60f, 
            affinities.getOrThrow(AMMagic.LIGHTNING), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.VINTEUM_TORCH.get()), 1));
        addSpellPartData(bootstrap, AMSpells.MANA_BLAST, 0f, 
            affinities.getOrThrow(AMMagic.ENDER), 0.001f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ENDER), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1));
        addSpellPartData(bootstrap, AMSpells.MANA_DRAIN, 5f, 
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.CYAN_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_MOONSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1));
        addSpellPartData(bootstrap, AMSpells.MELT_ARMOR, 200f, 
            affinities.getOrThrow(AMMagic.FIRE), 0.0001f);
        addSpellPartData(bootstrap, AMSpells.MOONRISE, 2000f, 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_MOONSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        addSpellPartData(bootstrap, AMSpells.PLACE_BLOCK, 5f, 
            affinities.getOrThrow(AMMagic.EARTH), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_GRAY_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.CHESTS_WOODEN)), 1));
        addSpellPartData(bootstrap, AMSpells.REPLANT, 5f, 
            affinities.getOrThrow(AMMagic.NATURE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.WHEAT_SEEDS), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.WITCHWOOD_SAPLING.get()), 1));
        addSpellPartData(bootstrap, AMSpells.PLOW, 5f, 
            affinities.getOrThrow(AMMagic.EARTH), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_HOE), 1));
        addSpellPartData(bootstrap, AMSpells.RANDOM_TELEPORT, 80f, 
            affinities.getOrThrow(AMMagic.ENDER), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.ENDER_PEARLS)), 1));
        addSpellPartData(bootstrap, AMSpells.RECALL, 80f, 
            affinities.getOrThrow(AMMagic.ARCANE), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.LIME_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.MAP), 1));
        addSpellPartData(bootstrap, AMSpells.REPEL, 5f, 
            affinities.getOrThrow(AMMagic.NATURE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_PURIFIED_VINTEUM)), 1));
        addSpellPartData(bootstrap, AMSpells.RIFT, 80f, 
            affinities.getOrThrow(AMMagic.ENDER), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.ENDER_CHEST), 1));
        addSpellPartData(bootstrap, AMSpells.STORM, 200f, 
            affinities.getOrThrow(AMMagic.LIGHTNING), 0.005f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.LIGHTNING_ROD), 1));
        addSpellPartData(bootstrap, AMSpells.SUMMON, 80f, 
            affinities.getOrThrow(AMMagic.LIFE), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.GRAY_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_PURIFIED_VINTEUM)), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1), 
            new EtheriumSpellIngredient(etheriumTypes.getOrThrow(AMEtheriumTypes.DARK), 2500));
        addSpellPartData(bootstrap, AMSpells.TRANSPLACE, 80f, 
            affinities.getOrThrow(AMMagic.ENDER), 0.002f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.ENDER_EYE), 1));
        addSpellPartData(bootstrap, AMSpells.WIZARDS_AUTUMN, 5f, 
            affinities.getOrThrow(AMMagic.NATURE), 0.001f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.RODS_WOODEN)), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.WITCHWOOD_SAPLING.get()), 1));
        addSpellPartData(bootstrap, AMSpells.BOUNCE, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.SLIME_BALLS)), 1));
        addSpellPartData(bootstrap, AMSpells.DAMAGE, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.FIRE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.COPPER_INGOT), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.HARMING), Items.POTION), 1));
        addSpellPartData(bootstrap, AMSpells.DISMEMBERING, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ICE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.BONES)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.WITHER_SKELETON_SKULL), 1));
        addSpellPartData(bootstrap, AMSpells.DURATION, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.AIR), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.SLIME_BALLS)), 1));
        addSpellPartData(bootstrap, AMSpells.EFFECT_POWER, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ARCANE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.CROPS_NETHER_WART)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_GLOWSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_REDSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.GUNPOWDERS)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.GLASS_BOTTLE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.SPIDER_EYE), 1));
        addSpellPartData(bootstrap, AMSpells.GRAVITY, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.EARTH), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.COMPASS), 1));
        addSpellPartData(bootstrap, AMSpells.HEALING, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIFE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.EGG), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.HEALING), Items.POTION), 1));
        addSpellPartData(bootstrap, AMSpells.LUNAR, 1f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.NATURE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_MOONSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        addSpellPartData(bootstrap, AMSpells.MINING_POWER, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.EARTH), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.GEMS_DIAMOND)), 1));
        addSpellPartData(bootstrap, AMSpells.PIERCING, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.ARROW), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.SNOWBALL), 1));
        addSpellPartData(bootstrap, AMSpells.PROSPERITY, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ICE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.INGOTS_GOLD)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.GEMS_EMERALD)), 1));
        addSpellPartData(bootstrap, AMSpells.RANGE, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.AIR), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_REDSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.ARROW), 1));
        addSpellPartData(bootstrap, AMSpells.RUNE_POWER, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ARCANE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.PINK_RUNE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1));
        addSpellPartData(bootstrap, AMSpells.SILK_TOUCH, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.EARTH), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.FEATHERS)), 1));
        addSpellPartData(bootstrap, AMSpells.SOLAR, 1f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.NATURE), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_SUNSTONE)), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        addSpellPartData(bootstrap, AMSpells.TARGET_NON_SOLID, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1), 
            new ItemSpellIngredient(Ingredient.of(Items.POPPY), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER), Items.POTION), 1));
        addSpellPartData(bootstrap, AMSpells.VELOCITY, 1.25f, 
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIGHTNING), AMItems.AFFINITY_ESSENCE), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.FEATHERS)), 1), 
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(ItemTags.BOATS)), 1), 
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.SWIFTNESS), Items.POTION), 1));
        addSpellPartData(bootstrap, AMSpells.COLOR, 1.0f, 
            new ItemSpellIngredient(Ingredient.of(AMItems.CHIMERITE.get()), 1));
    }

    private static void addHiddenSkill(BootstrapContext<Skill> bootstrap, ResourceKey<Skill> key, ResourceKey<OcculusTab> tab, int x, int y) {
        bootstrap.register(key, new Skill(List.of(), Optional.empty(), bootstrap.lookup(AMRegistries.Keys.OCCULUS_TAB).getOrThrow(tab), x, y, true));
    }

    private static void addHiddenSkill(BootstrapContext<Skill> bootstrap, DeferredHolder<SpellPart, ?> part, ResourceKey<OcculusTab> tab, int x, int y) {
        addHiddenSkill(bootstrap, skillFromPart(part), tab, x, y);
    }

    @SafeVarargs
    private static Holder<Skill> addSkill(BootstrapContext<Skill> bootstrap, ResourceKey<Skill> key, ResourceKey<SkillPoint> point, ResourceKey<OcculusTab> tab, int x, int y, Holder<Skill>... parents) {
        return bootstrap.register(key, new Skill(
            Arrays.asList(parents),
            Optional.of(bootstrap.lookup(AMRegistries.Keys.SKILL_POINT).getOrThrow(point)),
            bootstrap.lookup(AMRegistries.Keys.OCCULUS_TAB).getOrThrow(tab),
            x,
            y,
            false));
    }

    @SafeVarargs
    private static Holder<Skill> addSkill(BootstrapContext<Skill> bootstrap, DeferredHolder<SpellPart, ?> part, ResourceKey<SkillPoint> point, ResourceKey<OcculusTab> tab, int x, int y, Holder<Skill>... parents) {
        return addSkill(bootstrap, skillFromPart(part), point, tab, x, y, parents);
    }

    private static void addSpellPartData(BootstrapContext<SpellPartData> bootstrap, DeferredHolder<SpellPart, ?> part, double mana, SpellIngredient... ingredients) {
        bootstrap.register(spellPartDataFromPart(part), new SpellPartData(mana, Optional.empty(), Map.of(), Arrays.asList(ingredients)));
    }

    private static void addSpellPartData(BootstrapContext<SpellPartData> bootstrap, DeferredHolder<SpellPart, ?> part, double mana, Holder<Affinity> affinity, double affinityShift, SpellIngredient... ingredients) {
        bootstrap.register(spellPartDataFromPart(part), new SpellPartData(mana, Optional.empty(), Map.of(affinity, affinityShift), Arrays.asList(ingredients)));
    }

    @SuppressWarnings("DataFlowIssue")
    private static ResourceKey<Skill> skillFromPart(DeferredHolder<SpellPart, ?> part) {
        return ResourceKey.create(AMRegistries.Keys.SKILL, AMRegistries.SPELL_PARTS.getKey(part.get()));
    }

    @SuppressWarnings("DataFlowIssue")
    private static ResourceKey<SpellPartData> spellPartDataFromPart(DeferredHolder<SpellPart, ?> part) {
        return ResourceKey.create(AMRegistries.Keys.SPELL_PART_DATA, AMRegistries.SPELL_PARTS.getKey(part.get()));
    }

    private static void addAltarCapMaterial(BootstrapContext<AltarCapMaterial> bootstrap, String name, Block block, int power) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.ALTAR_CAP_MATERIAL, ArsMagicaApi.id(name)), new AltarCapMaterial(block, power));
    }

    private static void addAltarMaterial(BootstrapContext<AltarMaterial> bootstrap, BlockFamily blockFamily, int power) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.ALTAR_MATERIAL, ArsMagicaApi.id(BuiltInRegistries.BLOCK.getKey(blockFamily.getBaseBlock()).getPath())), new AltarMaterial(blockFamily.getBaseBlock(), (StairBlock) blockFamily.get(BlockFamily.Variant.STAIRS), power));
    }
}
