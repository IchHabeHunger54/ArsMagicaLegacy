package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartDataProvider;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.spell.ItemSpellIngredient;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.concurrent.CompletableFuture;

public final class AMSpellPartDataProvider extends SpellPartDataProvider {
    public AMSpellPartDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        HolderLookup.RegistryLookup<Affinity> affinities = provider.lookupOrThrow(AMRegistryKeys.AFFINITY);
        builder(AMSpells.AREA_OF_EFFECT, 2f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.TNT), 1));
            //.ingredient(new EtheriumSpellIngredient(EtheriumType.ANY, 1));
        builder(AMSpells.BEAM, 1f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1));
            //.ingredient(new EtheriumSpellIngredient(EtheriumType.LIGHT, 2500));
        builder(AMSpells.CHAIN, 1f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.STRINGS), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.LEAD), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.TRIPWIRE_HOOK), 1));
            //.ingredient(new EtheriumSpellIngredient(EtheriumType.LIGHT, 2500));
        builder(AMSpells.CHANNEL, 0.5f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1));
        builder(AMSpells.CONTINGENCY_DAMAGE, 10f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIGHTNING), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1));
            //.ingredient(new EtheriumSpellIngredient(EtheriumType.ANY, 5000));
        builder(AMSpells.CONTINGENCY_DEATH, 10f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ENDER), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1));
            //.ingredient(new EtheriumSpellIngredient(EtheriumType.DARK, 5000));
        builder(AMSpells.CONTINGENCY_FALL, 10f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.AIR), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1));
            //.ingredient(new EtheriumSpellIngredient(EtheriumType.ANY, 5000));
        builder(AMSpells.CONTINGENCY_FIRE, 10f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.FIRE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1));
            //.ingredient(new EtheriumSpellIngredient(EtheriumType.ANY, 5000));
        builder(AMSpells.CONTINGENCY_HEALTH, 10f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIFE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1));
            //.ingredient(new EtheriumSpellIngredient(EtheriumType.LIGHT, 5000));
        builder(AMSpells.PROJECTILE, 1f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.ARROW), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.SNOWBALL), 1));
        builder(AMSpells.RUNE, 2f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.RED_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1));
        builder(AMSpells.SELF, 0.5f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1));
        builder(AMSpells.TOUCH, 1f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.CLAY_BALL), 1));
        builder(AMSpells.WALL, 2.5f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.FENCES_WOODEN), 1))
            //.ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.MAGIC_WALL.get()), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(ItemTags.WALLS), 1));
            //.ingredient(new EtheriumSpellIngredient(EtheriumType.ANY, 2500));
        builder(AMSpells.WAVE, 2.5f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1));
            //.ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.MAGIC_WALL.get()), 1))
            //.ingredient(new EtheriumSpellIngredient(EtheriumType.ANY, 2500));
        builder(AMSpells.ZONE, 2.5f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1));
            //.ingredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 2500));
        builder(AMSpells.DROWNING_DAMAGE, 25f)
            .affinity(affinities.getOrThrow(AMMagic.WATER), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.WATER_BUCKET), 1));
        builder(AMSpells.FIRE_DAMAGE, 25f)
            .affinity(affinities.getOrThrow(AMMagic.FIRE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.RED_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, new ItemStack(Items.FLINT_AND_STEEL)), 1));
        builder(AMSpells.FROST_DAMAGE, 25f)
            .affinity(affinities.getOrThrow(AMMagic.ICE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.CYAN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_BLUE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.ICE), 1));
        builder(AMSpells.LIGHTNING_DAMAGE, 25f)
            .affinity(affinities.getOrThrow(AMMagic.LIGHTNING), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.LIGHTNING_ROD), 1));
        builder(AMSpells.MAGIC_DAMAGE, 25f)
            .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.BOOK), 1));
        builder(AMSpells.PHYSICAL_DAMAGE, 25f)
            .affinity(affinities.getOrThrow(AMMagic.EARTH), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.RED_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.INGOTS_IRON), 1));
        builder(AMSpells.ABSORPTION, 50f)
            .affinity(affinities.getOrThrow(AMMagic.LIFE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.GOLDEN_APPLE), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, new ItemStack(Items.SHIELD)), 1));
        builder(AMSpells.BLINDNESS, 40f)
            .affinity(affinities.getOrThrow(AMMagic.ENDER), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, DataComponents.POTION_CONTENTS, new PotionContents(Potions.NIGHT_VISION), Items.POTION), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, DataComponents.POTION_CONTENTS, new PotionContents(Potions.WEAKNESS), Items.POTION), 1));
        builder(AMSpells.HASTE, 30f)
            .affinity(affinities.getOrThrow(AMMagic.LIGHTNING), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1));
        builder(AMSpells.HEALTH_BOOST, 50f)
            .affinity(affinities.getOrThrow(AMMagic.LIFE), 0.001f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIFE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_BLUE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE), 1));
        builder(AMSpells.INVISIBILITY, 40f)
            .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, DataComponents.POTION_CONTENTS, new PotionContents(Potions.INVISIBILITY), Items.POTION), 1));
        builder(AMSpells.JUMP_BOOST, 30f)
            .affinity(affinities.getOrThrow(AMMagic.AIR), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.LIME_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1));
        builder(AMSpells.LEVITATION, 40f)
            .affinity(affinities.getOrThrow(AMMagic.AIR), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.POPPED_CHORUS_FRUIT), 1));
        builder(AMSpells.NIGHT_VISION, 30f)
            .affinity(affinities.getOrThrow(AMMagic.ENDER), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.GOLDEN_CARROT), 1));
        builder(AMSpells.NAUSEA, 200f)
            .affinity(affinities.getOrThrow(AMMagic.LIFE), 0.0001f);
        builder(AMSpells.REGENERATION, 30f)
            .affinity(affinities.getOrThrow(AMMagic.LIFE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.PINK_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.GHAST_TEAR), 1));
        builder(AMSpells.SLOWNESS, 30f)
            .affinity(affinities.getOrThrow(AMMagic.ICE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.SLIME_BALLS), 1));
        builder(AMSpells.SLOW_FALLING, 30f)
            .affinity(affinities.getOrThrow(AMMagic.AIR), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.PHANTOM_MEMBRANE), 1));
        builder(AMSpells.SWIFTNESS, 40f)
            .affinity(affinities.getOrThrow(AMMagic.AIR), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1));
        builder(AMSpells.WATER_BREATHING, 40f)
            .affinity(affinities.getOrThrow(AMMagic.WATER), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.WAKEBLOOM.get()), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.PUFFERFISH), 1));
        builder(AMSpells.ASTRAL_DISTORTION, 40f)
            .affinity(affinities.getOrThrow(AMMagic.ENDER), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1));
        builder(AMSpells.ENTANGLE, 40f)
            .affinity(affinities.getOrThrow(AMMagic.NATURE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.LIME_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.SLIME_BALLS), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.VINE), 1));
        builder(AMSpells.FLIGHT, 50f)
            .affinity(affinities.getOrThrow(AMMagic.AIR), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.NETHER_STARS), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.GHAST_TEAR), 1));
        builder(AMSpells.FROST, 40f)
            .affinity(affinities.getOrThrow(AMMagic.ICE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_BLUE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.POWDER_SNOW_BUCKET), 1));
        builder(AMSpells.FURY, 50f)
            .affinity(affinities.getOrThrow(AMMagic.LIGHTNING), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.RODS_BLAZE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.TROPICAL_FISH), 1));
        builder(AMSpells.GRAVITY_WELL, 40f)
            .affinity(affinities.getOrThrow(AMMagic.EARTH), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.GRAY_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.STONES), 1));
        builder(AMSpells.REFLECT, 50f)
            .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_GRAY_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.RODS_BLAZE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.GLASS_BLOCKS), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.WITCHWOOD_LOGS), 1));
        builder(AMSpells.SCRAMBLE_SYNAPSES, 3000f)
            .affinity(affinities.getOrThrow(AMMagic.LIGHTNING), 0.0001f);
        builder(AMSpells.SHIELD, 50f)
            .affinity(affinities.getOrThrow(AMMagic.EARTH), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BROWN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, new ItemStack(Items.SHIELD)), 1));
        builder(AMSpells.SHRINK, 30f)
            .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.BONES), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.BROWN_MUSHROOM), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.SPIDER_EYE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.STONE_BUTTON), 1));
        builder(AMSpells.SILENCE, 50f)
            .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BROWN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(ItemTags.WOOL), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.JUKEBOX), 1));
        builder(AMSpells.SWIFT_SWIM, 40f)
            .affinity(affinities.getOrThrow(AMMagic.WATER), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(ItemTags.FISHES), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, new ItemStack(Items.FISHING_ROD)), 1));
        builder(AMSpells.TEMPORAL_ANCHOR, 50f)
            .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.NETHER_STARS), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        builder(AMSpells.TRUE_SIGHT, 30f)
            .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.GLASS_BLOCKS), 1));
        builder(AMSpells.WATERY_GRAVE, 40f)
            .affinity(affinities.getOrThrow(AMMagic.WATER), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.STONES), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, new ItemStack(Items.LEATHER_BOOTS)), 1));
        builder(AMSpells.ATTRACT, 5f)
            .affinity(affinities.getOrThrow(AMMagic.NATURE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.INGOTS_IRON), 1));
        builder(AMSpells.BANISH_RAIN, 200f)
            .affinity(affinities.getOrThrow(AMMagic.WATER), 0.005f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.COPPER_INGOT), 1));
        builder(AMSpells.BLINK, 80f)
            .affinity(affinities.getOrThrow(AMMagic.ENDER), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1));
        builder(AMSpells.BLIZZARD, 1000f)
            .affinity(affinities.getOrThrow(AMMagic.ICE), 0.01f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ICE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.ICE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.PACKED_ICE), 1));
        builder(AMSpells.CHARM, 60f)
            .affinity(affinities.getOrThrow(AMMagic.LIFE), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.RED_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.WHEAT), 1));
        builder(AMSpells.CREATE_WATER, 5f)
            .affinity(affinities.getOrThrow(AMMagic.WATER), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.WATER_BUCKET), 1));
        builder(AMSpells.DAYLIGHT, 2000f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        builder(AMSpells.DIG, 5f)
            .affinity(affinities.getOrThrow(AMMagic.EARTH), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BROWN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, new ItemStack(Items.IRON_AXE)), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, new ItemStack(Items.IRON_PICKAXE)), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, new ItemStack(Items.IRON_SHOVEL)), 1));
        builder(AMSpells.DISARM, 60f)
            .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, new ItemStack(Items.IRON_SWORD)), 1));
        builder(AMSpells.DISPEL, 60f)
            .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.MILK_BUCKET), 1));
        builder(AMSpells.DIVINE_INTERVENTION, 200f)
            .affinity(affinities.getOrThrow(AMMagic.ENDER), 0.005f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.LIME_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(ItemTags.BEDS), 1));
        builder(AMSpells.DROUGHT, 5f)
            .affinity(affinities.getOrThrow(AMMagic.FIRE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.DEAD_BUSH), 1));
        builder(AMSpells.ENDER_INTERVENTION, 200f)
            .affinity(affinities.getOrThrow(AMMagic.ENDER), 0.005f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.ENDER_EYE), 1));
        builder(AMSpells.EXPLOSION, 100f)
            .affinity(affinities.getOrThrow(AMMagic.FIRE), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.GRAY_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.FIRE_CHARGE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.TNT), 1));
        builder(AMSpells.FALLING_STAR, 1000f)
            .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.01f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ARCANE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_ARCANE_ASH), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.END_STONES), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.LAVA_BUCKET), 1));
        builder(AMSpells.FIRE_RAIN, 1000f)
            .affinity(affinities.getOrThrow(AMMagic.FIRE), 0.01f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.FIRE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_ARCANE_ASH), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.NETHERRACKS), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.LAVA_BUCKET), 1));
        builder(AMSpells.FLING, 80f)
            .affinity(affinities.getOrThrow(AMMagic.AIR), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.PISTON), 1));
        builder(AMSpells.FORGE, 80f)
            .affinity(affinities.getOrThrow(AMMagic.FIRE), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.FURNACE), 1));
        builder(AMSpells.GROW, 5f)
            .affinity(affinities.getOrThrow(AMMagic.NATURE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.BONE_MEAL), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.WITCHWOOD_LOGS), 1));
        builder(AMSpells.HARVEST, 5f)
            .affinity(affinities.getOrThrow(AMMagic.NATURE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, new ItemStack(Items.SHEARS)), 1));
        builder(AMSpells.HEAL, 60f)
            .affinity(affinities.getOrThrow(AMMagic.LIFE), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.PINK_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), 1));
        builder(AMSpells.IGNITION, 80f)
            .affinity(affinities.getOrThrow(AMMagic.FIRE), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, new ItemStack(Items.FLINT_AND_STEEL)), 1));
        builder(AMSpells.KNOCKBACK, 80f)
            .affinity(affinities.getOrThrow(AMMagic.WATER), 0.002f)
            .affinity(affinities.getOrThrow(AMMagic.EARTH), 0.002f)
            .affinity(affinities.getOrThrow(AMMagic.AIR), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.PISTON), 1));
        builder(AMSpells.LIFE_DRAIN, 5f)
            .affinity(affinities.getOrThrow(AMMagic.LIFE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1));
        builder(AMSpells.LIFE_TAP, 5f)
            .affinity(affinities.getOrThrow(AMMagic.LIFE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.PINK_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1));
        builder(AMSpells.LIGHT, 60f)
            .affinity(affinities.getOrThrow(AMMagic.LIGHTNING), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.VINTEUM_TORCH.get()), 1));
        builder(AMSpells.MANA_BLAST, 0f)
            .affinity(affinities.getOrThrow(AMMagic.ENDER), 0.001f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ENDER), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1));
        builder(AMSpells.MANA_DRAIN, 5f)
            .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.CYAN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1));
        builder(AMSpells.MELT_ARMOR, 200f)
            .affinity(affinities.getOrThrow(AMMagic.FIRE), 0.0001f);
        builder(AMSpells.MOONRISE, 2000f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        builder(AMSpells.PLACE_BLOCK, 5f)
            .affinity(affinities.getOrThrow(AMMagic.EARTH), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_GRAY_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.CHESTS_WOODEN), 1));
        builder(AMSpells.PLANT, 5f)
            .affinity(affinities.getOrThrow(AMMagic.NATURE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.WHEAT_SEEDS), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.WITCHWOOD_SAPLING.get()), 1));
        builder(AMSpells.PLOW, 5f)
            .affinity(affinities.getOrThrow(AMMagic.EARTH), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, new ItemStack(Items.IRON_HOE)), 1));
        builder(AMSpells.RANDOM_TELEPORT, 80f)
            .affinity(affinities.getOrThrow(AMMagic.ENDER), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1));
        builder(AMSpells.RECALL, 80f)
            .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.LIME_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.MAP), 1));
        builder(AMSpells.REPEL, 5f)
            .affinity(affinities.getOrThrow(AMMagic.NATURE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_PURIFIED_VINTEUM), 1));
        builder(AMSpells.RIFT, 80f)
            .affinity(affinities.getOrThrow(AMMagic.ENDER), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.ENDER_CHEST), 1));
        builder(AMSpells.STORM, 200f)
            .affinity(affinities.getOrThrow(AMMagic.LIGHTNING), 0.005f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.LIGHTNING_ROD), 1));
        builder(AMSpells.SUMMON, 80f)
            .affinity(affinities.getOrThrow(AMMagic.LIFE), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.GRAY_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_PURIFIED_VINTEUM), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1));
            //.ingredient(new EtheriumSpellIngredient(EtheriumType.DARK, 2500));
        //builder(AMSpells.TELEKINESIS, 5f)
        //    .affinity(affinities.getOrThrow(AMMagic.ARCANE), 0.001f)
        //    .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1))
        //    .ingredient(new ItemSpellIngredient(Ingredient.of(Items.STICKY_PISTON), 1));
        builder(AMSpells.TRANSPLACE, 80f)
            .affinity(affinities.getOrThrow(AMMagic.ENDER), 0.002f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.ENDER_EYE), 1));
        builder(AMSpells.WIZARDS_AUTUMN, 5f)
            .affinity(affinities.getOrThrow(AMMagic.NATURE), 0.001f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.RODS_WOODEN), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.WITCHWOOD_SAPLING.get()), 1));
        builder(AMSpells.BOUNCE, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.SLIME_BALLS), 1));
        builder(AMSpells.DAMAGE, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.FIRE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.COPPER_INGOT), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, DataComponents.POTION_CONTENTS, new PotionContents(Potions.HARMING), Items.POTION), 1));
        builder(AMSpells.DISMEMBERING, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ICE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.BONES), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.WITHER_SKELETON_SKULL), 1));
        builder(AMSpells.DURATION, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.AIR), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.SLIME_BALLS), 1));
        builder(AMSpells.EFFECT_POWER, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ARCANE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.CROPS_NETHER_WART), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.GUNPOWDERS), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.GLASS_BOTTLE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.SPIDER_EYE), 1));
        builder(AMSpells.GRAVITY, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.EARTH), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.COMPASS), 1));
        builder(AMSpells.HEALING, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIFE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.EGG), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, DataComponents.POTION_CONTENTS, new PotionContents(Potions.HEALING), Items.POTION), 1));
        builder(AMSpells.LUNAR, 1f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.NATURE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        builder(AMSpells.MINING_POWER, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.EARTH), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.GEMS_DIAMOND), 1));
        builder(AMSpells.PIERCING, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.ARROW), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.SNOWBALL), 1));
        builder(AMSpells.PROSPERITY, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ICE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.INGOTS_GOLD), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.GEMS_EMERALD), 1));
        builder(AMSpells.RANGE, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.AIR), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.ARROW), 1));
        builder(AMSpells.RUNE_POWER, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ARCANE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.PINK_RUNE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1));
        builder(AMSpells.SILK_TOUCH, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.EARTH), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1));
        builder(AMSpells.SOLAR, 1f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.NATURE), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        builder(AMSpells.TARGET_NON_SOLID, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Items.POPPY), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER), Items.POTION), 1));
        builder(AMSpells.VELOCITY, 1.25f)
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIGHTNING), AMItems.AFFINITY_ESSENCE), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
            .ingredient(new ItemSpellIngredient(Ingredient.of(ItemTags.BOATS), 1))
            .ingredient(new ItemSpellIngredient(DataComponentIngredient.of(true, DataComponents.POTION_CONTENTS, new PotionContents(Potions.SWIFTNESS), Items.POTION), 1));
        builder(AMSpells.COLOR, 1.0f)
            .ingredient(new ItemSpellIngredient(Ingredient.of(AMItems.CHIMERITE.get()), 1));
    }
}
