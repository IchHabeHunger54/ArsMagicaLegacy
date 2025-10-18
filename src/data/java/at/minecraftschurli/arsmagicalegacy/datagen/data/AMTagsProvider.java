package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMDamageSources;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public final class AMTagsProvider {
    private AMTagsProvider() {
    }

    public static void addProviders(DataGenerator generator, boolean includeServer, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        Block blockTags = generator.addProvider(includeServer, new Block(output, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new Item(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(includeServer, new DamageType(output, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new EntityType(output, lookupProvider, existingFileHelper));
    }

    public static final class Block extends BlockTagsProvider {
        public Block(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, ArsMagicaApi.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(AMTags.Blocks.ORES_CHIMERITE).add(AMBlocks.CHIMERITE_ORE.get(), AMBlocks.DEEPSLATE_CHIMERITE_ORE.get());
            tag(AMTags.Blocks.ORES_TOPAZ).add(AMBlocks.TOPAZ_ORE.get(), AMBlocks.DEEPSLATE_TOPAZ_ORE.get());
            tag(AMTags.Blocks.ORES_VINTEUM).add(AMBlocks.VINTEUM_ORE.get(), AMBlocks.DEEPSLATE_VINTEUM_ORE.get());
            tag(AMTags.Blocks.ORES_MOONSTONE).add(AMBlocks.MOONSTONE_ORE.get(), AMBlocks.DEEPSLATE_MOONSTONE_ORE.get());
            tag(AMTags.Blocks.ORES_SUNSTONE).add(AMBlocks.SUNSTONE_ORE.get());
            tag(AMTags.Blocks.STORAGE_BLOCKS_CHIMERITE).add(AMBlocks.CHIMERITE_BLOCK.get());
            tag(AMTags.Blocks.STORAGE_BLOCKS_TOPAZ).add(AMBlocks.TOPAZ_BLOCK.get());
            tag(AMTags.Blocks.STORAGE_BLOCKS_VINTEUM).add(AMBlocks.VINTEUM_BLOCK.get());
            tag(AMTags.Blocks.STORAGE_BLOCKS_MOONSTONE).add(AMBlocks.MOONSTONE_BLOCK.get());
            tag(AMTags.Blocks.STORAGE_BLOCKS_SUNSTONE).add(AMBlocks.SUNSTONE_BLOCK.get());
            tag(Tags.Blocks.ORES).addTags(AMTags.Blocks.ORES_CHIMERITE, AMTags.Blocks.ORES_TOPAZ, AMTags.Blocks.ORES_VINTEUM, AMTags.Blocks.ORES_MOONSTONE, AMTags.Blocks.ORES_SUNSTONE);
            tag(Tags.Blocks.STORAGE_BLOCKS).addTags(AMTags.Blocks.STORAGE_BLOCKS_CHIMERITE, AMTags.Blocks.STORAGE_BLOCKS_TOPAZ, AMTags.Blocks.STORAGE_BLOCKS_VINTEUM, AMTags.Blocks.STORAGE_BLOCKS_MOONSTONE, AMTags.Blocks.STORAGE_BLOCKS_SUNSTONE);
            tag(AMTags.Blocks.WITCHWOOD_LOGS).add(AMBlocks.WITCHWOOD_LOG.get(), AMBlocks.WITCHWOOD.get(), AMBlocks.STRIPPED_WITCHWOOD_LOG.get(), AMBlocks.STRIPPED_WITCHWOOD.get());
            tag(BlockTags.LOGS).addTag(AMTags.Blocks.WITCHWOOD_LOGS);
            tag(BlockTags.LEAVES).add(AMBlocks.WITCHWOOD_LEAVES.get());
            tag(BlockTags.SAPLINGS).add(AMBlocks.WITCHWOOD_SAPLING.get());
            tag(BlockTags.PLANKS).add(AMBlocks.WITCHWOOD_PLANKS.get());
            tag(BlockTags.WOODEN_SLABS).add(AMBlocks.WITCHWOOD_SLAB.get());
            tag(BlockTags.WOODEN_STAIRS).add(AMBlocks.WITCHWOOD_STAIRS.get());
            tag(BlockTags.WOODEN_FENCES).add(AMBlocks.WITCHWOOD_FENCE.get());
            tag(Tags.Blocks.FENCES_WOODEN).add(AMBlocks.WITCHWOOD_FENCE.get());
            tag(BlockTags.FENCE_GATES).add(AMBlocks.WITCHWOOD_FENCE_GATE.get());
            tag(Tags.Blocks.FENCE_GATES_WOODEN).add(AMBlocks.WITCHWOOD_FENCE_GATE.get());
            tag(BlockTags.WOODEN_DOORS).add(AMBlocks.WITCHWOOD_DOOR.get());
            tag(BlockTags.WOODEN_TRAPDOORS).add(AMBlocks.WITCHWOOD_TRAPDOOR.get());
            tag(BlockTags.WOODEN_BUTTONS).add(AMBlocks.WITCHWOOD_BUTTON.get());
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get());
            tag(BlockTags.STANDING_SIGNS).add(AMBlocks.WITCHWOOD_SIGN.get());
            tag(BlockTags.WALL_SIGNS).add(AMBlocks.WITCHWOOD_WALL_SIGN.get());
            tag(BlockTags.CEILING_HANGING_SIGNS).add(AMBlocks.WITCHWOOD_HANGING_SIGN.get());
            tag(BlockTags.WALL_HANGING_SIGNS).add(AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get());
            tag(AMTags.Blocks.AUM_PLANTABLE_ON).addTag(BlockTags.DIRT);
            tag(AMTags.Blocks.CERUBLOSSOM_PLANTABLE_ON).addTag(BlockTags.DIRT);
            tag(AMTags.Blocks.DESERT_NOVA_PLANTABLE_ON).addTag(BlockTags.SAND);
            tag(AMTags.Blocks.TARMA_ROOT_PLANTABLE_ON).add(Blocks.CLAY, Blocks.GRAVEL).addTags(BlockTags.DIRT, BlockTags.SAND, BlockTags.STONE_ORE_REPLACEABLES, BlockTags.DEEPSLATE_ORE_REPLACEABLES);
            tag(AMTags.Blocks.WIZARDS_AUTUMN_LEAVES).addTag(BlockTags.LEAVES);
            tag(BlockTags.SMALL_FLOWERS).add(AMBlocks.AUM.get(), AMBlocks.CERUBLOSSOM.get(), AMBlocks.DESERT_NOVA.get(), AMBlocks.TARMA_ROOT.get(), AMBlocks.WAKEBLOOM.get());
            tag(BlockTags.FLOWER_POTS).add(AMBlocks.POTTED_WITCHWOOD_SAPLING.get(), AMBlocks.POTTED_AUM.get(), AMBlocks.POTTED_CERUBLOSSOM.get(), AMBlocks.POTTED_DESERT_NOVA.get(), AMBlocks.POTTED_TARMA_ROOT.get(), AMBlocks.POTTED_WAKEBLOOM.get());
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(AMBlocks.OCCULUS.get(), AMBlocks.CHIMERITE_ORE.get(), AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), AMBlocks.CHIMERITE_BLOCK.get(), AMBlocks.TOPAZ_ORE.get(), AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), AMBlocks.TOPAZ_BLOCK.get(), AMBlocks.VINTEUM_ORE.get(), AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), AMBlocks.VINTEUM_BLOCK.get(), AMBlocks.MOONSTONE_ORE.get(), AMBlocks.DEEPSLATE_MOONSTONE_ORE.get(), AMBlocks.MOONSTONE_BLOCK.get(), AMBlocks.SUNSTONE_ORE.get(), AMBlocks.SUNSTONE_BLOCK.get());
            tag(BlockTags.MINEABLE_WITH_AXE).add(AMBlocks.INSCRIPTION_TABLE.get());
            tag(BlockTags.NEEDS_STONE_TOOL).add(AMBlocks.CHIMERITE_BLOCK.get(), AMBlocks.CHIMERITE_ORE.get(), AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), AMBlocks.TOPAZ_BLOCK.get(), AMBlocks.TOPAZ_ORE.get(), AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), AMBlocks.VINTEUM_BLOCK.get(), AMBlocks.VINTEUM_ORE.get(), AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), AMBlocks.MOONSTONE_BLOCK.get(), AMBlocks.SUNSTONE_BLOCK.get());
            tag(BlockTags.NEEDS_IRON_TOOL).add(AMBlocks.MOONSTONE_ORE.get(), AMBlocks.DEEPSLATE_MOONSTONE_ORE.get());
            tag(BlockTags.NEEDS_DIAMOND_TOOL).add(AMBlocks.SUNSTONE_ORE.get());
        }
    }

    public static final class Item extends ItemTagsProvider {
        public Item(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<net.minecraft.world.level.block.Block>> blockTags, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, blockTags, ArsMagicaApi.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            copy(AMTags.Blocks.ORES_CHIMERITE, AMTags.Items.ORES_CHIMERITE);
            copy(AMTags.Blocks.ORES_TOPAZ, AMTags.Items.ORES_TOPAZ);
            copy(AMTags.Blocks.ORES_VINTEUM, AMTags.Items.ORES_VINTEUM);
            copy(AMTags.Blocks.ORES_MOONSTONE, AMTags.Items.ORES_MOONSTONE);
            copy(AMTags.Blocks.ORES_SUNSTONE, AMTags.Items.ORES_SUNSTONE);
            copy(AMTags.Blocks.STORAGE_BLOCKS_CHIMERITE, AMTags.Items.STORAGE_BLOCKS_CHIMERITE);
            copy(AMTags.Blocks.STORAGE_BLOCKS_TOPAZ, AMTags.Items.STORAGE_BLOCKS_TOPAZ);
            copy(AMTags.Blocks.STORAGE_BLOCKS_VINTEUM, AMTags.Items.STORAGE_BLOCKS_VINTEUM);
            copy(AMTags.Blocks.STORAGE_BLOCKS_MOONSTONE, AMTags.Items.STORAGE_BLOCKS_MOONSTONE);
            copy(AMTags.Blocks.STORAGE_BLOCKS_SUNSTONE, AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
            tag(AMTags.Items.GEMS_CHIMERITE).add(AMItems.CHIMERITE.get());
            tag(AMTags.Items.GEMS_TOPAZ).add(AMItems.TOPAZ.get());
            tag(AMTags.Items.DUSTS_VINTEUM).add(AMItems.VINTEUM_DUST.get());
            tag(AMTags.Items.GEMS_MOONSTONE).add(AMItems.MOONSTONE.get());
            tag(AMTags.Items.GEMS_SUNSTONE).add(AMItems.SUNSTONE.get());
            tag(AMTags.Items.DUSTS_ARCANE_COMPOUND).add(AMItems.ARCANE_COMPOUND.get());
            tag(AMTags.Items.DUSTS_ARCANE_ASH).add(AMItems.ARCANE_ASH.get());
            tag(AMTags.Items.DUSTS_PURIFIED_VINTEUM).add(AMItems.PURIFIED_VINTEUM_DUST.get());
            tag(Tags.Items.ORES).addTags(AMTags.Items.ORES_CHIMERITE, AMTags.Items.ORES_TOPAZ, AMTags.Items.ORES_VINTEUM, AMTags.Items.ORES_MOONSTONE, AMTags.Items.ORES_SUNSTONE);
            tag(Tags.Items.STORAGE_BLOCKS).addTags(AMTags.Items.STORAGE_BLOCKS_CHIMERITE, AMTags.Items.STORAGE_BLOCKS_TOPAZ, AMTags.Items.STORAGE_BLOCKS_VINTEUM, AMTags.Items.STORAGE_BLOCKS_MOONSTONE, AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
            tag(Tags.Items.GEMS).addTags(AMTags.Items.GEMS_CHIMERITE, AMTags.Items.GEMS_TOPAZ, AMTags.Items.GEMS_MOONSTONE, AMTags.Items.GEMS_SUNSTONE);
            tag(Tags.Items.DUSTS).addTags(AMTags.Items.DUSTS_VINTEUM, AMTags.Items.DUSTS_ARCANE_COMPOUND, AMTags.Items.DUSTS_ARCANE_ASH, AMTags.Items.DUSTS_PURIFIED_VINTEUM);
            copy(AMTags.Blocks.WITCHWOOD_LOGS, AMTags.Items.WITCHWOOD_LOGS);
            tag(ItemTags.LOGS).addTag(AMTags.Items.WITCHWOOD_LOGS);
            tag(ItemTags.LEAVES).add(AMItems.WITCHWOOD_LEAVES.get());
            tag(ItemTags.SAPLINGS).add(AMItems.WITCHWOOD_SAPLING.get());
            tag(ItemTags.PLANKS).add(AMItems.WITCHWOOD_PLANKS.get());
            tag(ItemTags.WOODEN_SLABS).add(AMItems.WITCHWOOD_SLAB.get());
            tag(ItemTags.WOODEN_STAIRS).add(AMItems.WITCHWOOD_STAIRS.get());
            tag(ItemTags.WOODEN_FENCES).add(AMItems.WITCHWOOD_FENCE.get());
            tag(Tags.Items.FENCES_WOODEN).add(AMItems.WITCHWOOD_FENCE.get());
            tag(ItemTags.FENCE_GATES).add(AMItems.WITCHWOOD_FENCE_GATE.get());
            tag(Tags.Items.FENCE_GATES_WOODEN).add(AMItems.WITCHWOOD_FENCE_GATE.get());
            tag(ItemTags.WOODEN_DOORS).add(AMItems.WITCHWOOD_DOOR.get());
            tag(ItemTags.WOODEN_TRAPDOORS).add(AMItems.WITCHWOOD_TRAPDOOR.get());
            tag(ItemTags.WOODEN_BUTTONS).add(AMItems.WITCHWOOD_BUTTON.get());
            tag(ItemTags.WOODEN_PRESSURE_PLATES).add(AMItems.WITCHWOOD_PRESSURE_PLATE.get());
            tag(ItemTags.SIGNS).add(AMItems.WITCHWOOD_SIGN.get());
            tag(ItemTags.HANGING_SIGNS).add(AMItems.WITCHWOOD_HANGING_SIGN.get());
            tag(ItemTags.SMALL_FLOWERS).add(AMItems.AUM.get(), AMItems.CERUBLOSSOM.get(), AMItems.DESERT_NOVA.get(), AMItems.TARMA_ROOT.get(), AMItems.WAKEBLOOM.get());
            tag(ItemTags.LECTERN_BOOKS).add(AMItems.SPELL_RECIPE.get());
            tag(AMTags.Items.INSCRIPTION_TABLE_BOOKS).add(Items.WRITABLE_BOOK, AMItems.SPELL_RECIPE.get());
            tag(AMTags.Items.OCCULUS_FORGET_ALL).addTag(AMTags.Items.STORAGE_BLOCKS_VINTEUM);
            tag(AMTags.Items.RUNES).add(AMItems.BLANK_RUNE.get(), AMItems.WHITE_RUNE.get(), AMItems.ORANGE_RUNE.get(), AMItems.MAGENTA_RUNE.get(), AMItems.LIGHT_BLUE_RUNE.get(), AMItems.YELLOW_RUNE.get(), AMItems.LIME_RUNE.get(), AMItems.PINK_RUNE.get(), AMItems.GRAY_RUNE.get(), AMItems.LIGHT_GRAY_RUNE.get(), AMItems.CYAN_RUNE.get(), AMItems.PURPLE_RUNE.get(), AMItems.BLUE_RUNE.get(), AMItems.BROWN_RUNE.get(), AMItems.GREEN_RUNE.get(), AMItems.RED_RUNE.get(), AMItems.BLACK_RUNE.get());
            tag(AMTags.Items.SHOWS_SHAPE_GROUPS).add(AMItems.SPELL.get());
            tag(AMTags.Items.SPELLCRAFTING_START).add(AMItems.BLANK_RUNE.get());
            tag(AMTags.Items.SPELLCRAFTING_END).add(AMItems.SPELL_PARCHMENT.get());
        }
    }

    public static final class EntityType extends EntityTypeTagsProvider {
        public EntityType(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper existingFileHelper) {
            super(output, provider, ArsMagicaApi.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(AMTags.EntityTypes.AFFECTED_BY_ENDER_THORNS_ABILITY).add(net.minecraft.world.entity.EntityType.ENDER_DRAGON, net.minecraft.world.entity.EntityType.ENDERMAN, net.minecraft.world.entity.EntityType.ENDERMITE, net.minecraft.world.entity.EntityType.SHULKER);
            tag(AMTags.EntityTypes.AFFECTED_BY_SMITE_ABILITY).addTag(EntityTypeTags.UNDEAD);
            tag(AMTags.EntityTypes.AFFECTED_BY_NAUSEA_ABILITY).addTag(EntityTypeTags.UNDEAD);
        }
    }

    public static final class DamageType extends DamageTypeTagsProvider {
        public DamageType(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, ArsMagicaApi.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH).add(AMDamageSources.SPELL_MAGIC);
            tag(DamageTypeTags.AVOIDS_GUARDIAN_THORNS).add(AMDamageSources.SPELL_MAGIC);
            tag(DamageTypeTags.BYPASSES_ARMOR).add(AMDamageSources.SPELL_DROWNING, AMDamageSources.SPELL_FROST, AMDamageSources.SPELL_MAGIC);
            tag(DamageTypeTags.BYPASSES_INVULNERABILITY).addTag(AMTags.DamageTypes.IS_SPELL);
            tag(DamageTypeTags.IGNITES_ARMOR_STANDS).add(AMDamageSources.SPELL_FIRE);
            tag(DamageTypeTags.IS_DROWNING).add(AMDamageSources.SPELL_DROWNING);
            tag(DamageTypeTags.IS_FIRE).add(AMDamageSources.SPELL_FIRE);
            tag(DamageTypeTags.IS_FREEZING).add(AMDamageSources.SPELL_FROST);
            tag(DamageTypeTags.IS_LIGHTNING).add(AMDamageSources.SPELL_LIGHTNING);
            //tag(DamageTypeTags.IS_PROJECTILE).add(AMDamageSources.NATURE_SCYTHE, AMDamageSources.THROWN_ROCK);
            tag(DamageTypeTags.WITHER_IMMUNE_TO).add(AMDamageSources.SPELL_DROWNING);
            tag(DamageTypeTags.WITCH_RESISTANT_TO).add(AMDamageSources.SPELL_MAGIC);
            tag(Tags.DamageTypes.IS_MAGIC).add(AMDamageSources.SPELL_MAGIC);
            tag(Tags.DamageTypes.IS_PHYSICAL).add(AMDamageSources.SPELL_PHYSICAL, AMDamageSources.SPELL_PHYSICAL_PLAYER);
            tag(AMTags.DamageTypes.AFFECTED_BY_FIRE_RESISTANCE_ABILITY).addTag(DamageTypeTags.IS_FIRE);
            tag(AMTags.DamageTypes.AFFECTED_BY_RESISTANCE_ABILITY).addTag(Tags.DamageTypes.IS_PHYSICAL).remove(DamageTypeTags.IS_FALL);
            tag(AMTags.DamageTypes.AFFECTED_BY_FALL_DAMAGE_ABILITY).addTag(DamageTypeTags.IS_FALL);
            tag(AMTags.DamageTypes.AFFECTED_BY_FEATHER_FALLING_ABILITY).addTag(DamageTypeTags.IS_FALL);
            tag(AMTags.DamageTypes.AFFECTED_BY_MAGIC_DAMAGE_ABILITY).addTag(Tags.DamageTypes.IS_MAGIC).remove(Tags.DamageTypes.IS_POISON);
            tag(AMTags.DamageTypes.IS_SPELL).add(AMDamageSources.SPELL_DROWNING, AMDamageSources.SPELL_FIRE, AMDamageSources.SPELL_FROST, AMDamageSources.SPELL_LIGHTNING, AMDamageSources.SPELL_MAGIC, AMDamageSources.SPELL_PHYSICAL, AMDamageSources.SPELL_PHYSICAL_PLAYER);
        }
    }
}
