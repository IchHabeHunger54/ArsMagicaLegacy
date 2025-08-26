package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.block.inscriptiontable.InscriptionTableBlock;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class AMRecipeProvider extends RecipeProvider {
    public AMRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AMItems.OCCULUS.get())
            .pattern("SGS")
            .pattern(" S ")
            .pattern("CTC")
            .define('S', Items.STONE_BRICKS)
            .define('G', Tags.Items.GLASS_BLOCKS)
            .define('C', ItemTags.COALS)
            .define('T', AMTags.Items.GEMS_TOPAZ)
            .unlockedBy(getHasName(AMItems.TOPAZ), has(AMTags.Items.GEMS_TOPAZ))
            .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AMItems.INSCRIPTION_TABLE.get())
            .pattern("TPF")
            .pattern("SSS")
            .pattern("W W")
            .define('T', Items.TORCH)
            .define('P', AMItems.SPELL_PARCHMENT.get())
            .define('F', Tags.Items.FEATHERS)
            .define('S', ItemTags.WOODEN_SLABS)
            .define('W', ItemTags.PLANKS)
            .unlockedBy("has_spell_parchment", has(AMItems.SPELL_PARCHMENT.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1.get())
            .requires(Items.BOOK)
            .requires(Tags.Items.DYES_BLACK)
            .requires(Tags.Items.FEATHERS)
            .requires(Tags.Items.STRINGS)
            .unlockedBy("has_book", has(Items.BOOK))
            .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2.get())
            .requires(Items.BOOK)
            .requires(Tags.Items.DYES_BLACK)
            .requires(ItemTags.WOOL_CARPETS)
            //.requires(AMItems.WIZARDS_CHALK.get())
            .unlockedBy("has_book", has(Items.BOOK))
            .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3.get())
            .requires(Items.BOOK)
            .requires(ItemTags.CANDLES)
            .requires(Items.HONEYCOMB)
            .requires(Items.GLASS_BOTTLE)
            .unlockedBy("has_book", has(Items.BOOK))
            .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, new ItemStack(AMItems.INSCRIPTION_TABLE, 1, DataComponentPatch.builder().set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(InscriptionTableBlock.TIER, 1)).build()))
            .requires(DataComponentIngredient.of(false, DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(InscriptionTableBlock.TIER, 0), AMItems.INSCRIPTION_TABLE.get()))
            .requires(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1.get())
            .unlockedBy("has_inscription_table", has(AMItems.INSCRIPTION_TABLE.get()))
            .save(output, ArsMagicaApi.modLoc("inscription_table_tier_1"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, new ItemStack(AMItems.INSCRIPTION_TABLE, 1, DataComponentPatch.builder().set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(InscriptionTableBlock.TIER, 2)).build()))
            .requires(DataComponentIngredient.of(false, DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(InscriptionTableBlock.TIER, 1), AMItems.INSCRIPTION_TABLE.get()))
            .requires(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2.get())
            .unlockedBy("has_inscription_table", has(AMItems.INSCRIPTION_TABLE.get()))
            .save(output, ArsMagicaApi.modLoc("inscription_table_tier_2"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, new ItemStack(AMItems.INSCRIPTION_TABLE, 1, DataComponentPatch.builder().set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(InscriptionTableBlock.TIER, 3)).build()))
            .requires(DataComponentIngredient.of(false, DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(InscriptionTableBlock.TIER, 2), AMItems.INSCRIPTION_TABLE.get()))
            .requires(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3.get())
            .unlockedBy("has_inscription_table", has(AMItems.INSCRIPTION_TABLE.get()))
            .save(output, ArsMagicaApi.modLoc("inscription_table_tier_3"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AMItems.SPELL_PARCHMENT.get())
            .pattern("S")
            .pattern("P")
            .pattern("S")
            .define('S', Tags.Items.RODS_WOODEN)
            .define('P', Items.PAPER)
            .unlockedBy("has_paper", has(Items.PAPER))
            .save(output);
        oreSmelting(output, List.of(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get()), AMItems.CHIMERITE.get(), 0.7f, 200, "chimerite");
        oreBlasting(output, List.of(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get()), AMItems.CHIMERITE.get(), 0.7f, 100, "chimerite");
        nineBlockStorageRecipes(output, AMItems.CHIMERITE, AMTags.Items.GEMS_CHIMERITE, AMItems.CHIMERITE_BLOCK, AMTags.Items.STORAGE_BLOCKS_CHIMERITE);
        oreSmelting(output, List.of(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get()), AMItems.TOPAZ.get(), 0.7f, 200, "topaz");
        oreBlasting(output, List.of(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get()), AMItems.TOPAZ.get(), 0.7f, 100, "topaz");
        nineBlockStorageRecipes(output, AMItems.TOPAZ, AMTags.Items.GEMS_TOPAZ, AMItems.TOPAZ_BLOCK, AMTags.Items.STORAGE_BLOCKS_TOPAZ);
        oreSmelting(output, List.of(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get()), AMItems.VINTEUM_DUST.get(), 0.7f, 200, "vinteum_dust");
        oreBlasting(output, List.of(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get()), AMItems.VINTEUM_DUST.get(), 0.7f, 100, "vinteum_dust");
        nineBlockStorageRecipes(output, AMItems.VINTEUM_DUST, AMTags.Items.DUSTS_VINTEUM, AMItems.VINTEUM_BLOCK, AMTags.Items.STORAGE_BLOCKS_VINTEUM);
        oreSmelting(output, List.of(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get()), AMItems.MOONSTONE.get(), 0.7f, 200, "moonstone");
        oreBlasting(output, List.of(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get()), AMItems.MOONSTONE.get(), 0.7f, 100, "moonstone");
        nineBlockStorageRecipes(output, AMItems.MOONSTONE, AMTags.Items.GEMS_MOONSTONE, AMItems.MOONSTONE_BLOCK, AMTags.Items.STORAGE_BLOCKS_MOONSTONE);
        nineBlockStorageRecipes(output, AMItems.SUNSTONE, AMTags.Items.GEMS_SUNSTONE, AMItems.SUNSTONE_BLOCK, AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItems.ARCANE_COMPOUND.get())
            .requires(Tags.Items.DUSTS_GLOWSTONE)
            .requires(Tags.Items.DUSTS_GLOWSTONE)
            .requires(Tags.Items.DUSTS_REDSTONE)
            .requires(Tags.Items.DUSTS_REDSTONE)
            .requires(Tags.Items.NETHERRACKS)
            .requires(Tags.Items.NETHERRACKS)
            .requires(Tags.Items.STONES)
            .requires(Tags.Items.STONES)
            .unlockedBy(getHasName(Items.GLOWSTONE_DUST), has(Tags.Items.DUSTS_GLOWSTONE))
            .save(output);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AMTags.Items.DUSTS_ARCANE_COMPOUND), RecipeCategory.MISC, AMItems.ARCANE_ASH.get(), 0.2f, 200)
            .unlockedBy(getHasName(AMItems.ARCANE_COMPOUND), has(AMTags.Items.DUSTS_ARCANE_COMPOUND))
            .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItems.PURIFIED_VINTEUM_DUST.get())
            .requires(AMTags.Items.DUSTS_ARCANE_ASH)
            .requires(AMItems.CERUBLOSSOM.get())
            .requires(AMItems.DESERT_NOVA.get())
            .requires(AMTags.Items.DUSTS_VINTEUM)
            .unlockedBy(getHasName(AMItems.VINTEUM_DUST), has(AMTags.Items.DUSTS_VINTEUM))
            .save(output);
        generateRecipes(output, AMBlocks.WITCHWOOD_BLOCK_FAMILY.get(), FeatureFlagSet.of(FeatureFlags.VANILLA));
        hangingSign(output, AMItems.WITCHWOOD_HANGING_SIGN.get(), AMItems.STRIPPED_WITCHWOOD_LOG.get());
        planksFromLogs(output, AMItems.WITCHWOOD_PLANKS.get(), AMTags.Items.WITCHWOOD_LOGS, 4);
        woodFromLogs(output, AMBlocks.WITCHWOOD.get(), AMBlocks.WITCHWOOD_LOG.get());
        woodFromLogs(output, AMBlocks.STRIPPED_WITCHWOOD.get(), AMBlocks.STRIPPED_WITCHWOOD_LOG.get());
        oneToOneConversion(output, Items.PINK_DYE, AMItems.AUM.get(), "pink_dye");
        oneToOneConversion(output, Items.BLUE_DYE, AMItems.CERUBLOSSOM.get(), "blue_dye");
        oneToOneConversion(output, Items.RED_DYE, AMItems.DESERT_NOVA.get(), "red_dye");
        oneToOneConversion(output, Items.BROWN_DYE, AMItems.TARMA_ROOT.get(), "brown_dye");
        oneToOneConversion(output, Items.MAGENTA_DYE, AMItems.WAKEBLOOM.get(), "magenta_dye");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AMItems.VINTEUM_TORCH.get())
            .pattern("V")
            .pattern("S")
            .define('V', AMTags.Items.DUSTS_VINTEUM)
            .define('S', Tags.Items.RODS_WOODEN)
            .unlockedBy(getHasName(AMItems.VINTEUM_DUST), has(AMTags.Items.DUSTS_VINTEUM))
            .save(output);
    }

    /**
     * Adds smelting recipes for the given {@link Ingredient}s.
     *
     * @param output      The {@link RecipeOutput} to use.
     * @param ingredients A list of {@link Ingredient}s.
     * @param result      The result item to use.
     * @param experience  The experience to award for this recipe.
     * @param cookingTime The time this recipe takes.
     * @param group       The crafting book group to use.
     */
    @SuppressWarnings("SameParameterValue")
    private void oreSmelting(RecipeOutput output, List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(output, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, ingredients, result, experience, cookingTime, group, "_from_smelting");
    }

    /**
     * Adds blasting recipes for the given {@link Ingredient}s.
     *
     * @param output      The {@link RecipeOutput} to use.
     * @param ingredients A list of {@link Ingredient}s.
     * @param result      The result item to use.
     * @param experience  The experience to award for this recipe.
     * @param cookingTime The time this recipe takes.
     * @param group       The crafting book group to use.
     */
    @SuppressWarnings("SameParameterValue")
    private void oreBlasting(RecipeOutput output, List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(output, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, ingredients, result, experience, cookingTime, group, "_from_blasting");
    }

    /**
     * Adds generic cooking recipes for the given {@link Ingredient}s.
     *
     * @param output        The {@link RecipeOutput} to use.
     * @param serializer    The {@link RecipeSerializer} to use.
     * @param recipeFactory The {@link AbstractCookingRecipe.Factory} to use.
     * @param ingredients   A list of {@link Ingredient}s.
     * @param result        The result item to use.
     * @param experience    The experience to award for this recipe.
     * @param cookingTime   The time this recipe takes.
     * @param group         The crafting book group to use.
     * @param suffix        The suffix to append to the recipe name.
     */
    private <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput output, RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> recipeFactory, List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime, String group, String suffix) {
        for (ItemLike item : ingredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(item), RecipeCategory.MISC, result, experience, cookingTime, serializer, recipeFactory)
                .group(group)
                .unlockedBy(getHasName(item), has(item))
                .save(output, ArsMagicaApi.modLoc(getItemName(result) + suffix + "_" + getItemName(item)));
        }
    }

    /**
     * Creates a block -> item and an item -> block recipe.
     *
     * @param output      The {@link RecipeOutput} to use.
     * @param unpacked    The item to use.
     * @param unpackedTag The item's associated tag to use.
     * @param packed      The block to use.
     * @param packedTag   The block's associated tag to use.
     */
    private void nineBlockStorageRecipes(RecipeOutput output, ItemLike unpacked, TagKey<Item> unpackedTag, ItemLike packed, TagKey<Item> packedTag) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, unpacked, 9)
            .requires(packedTag)
            .unlockedBy(getHasName(packed), has(packed))
            .save(output, ArsMagicaApi.modLoc(getSimpleRecipeName(unpacked)));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, packed)
            .define('#', unpackedTag)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(getHasName(unpacked), has(unpacked))
            .save(output, ArsMagicaApi.modLoc(getSimpleRecipeName(packed)));
    }

    /**
     * Creates an 1 item -> 1 item recipe.
     *
     * @param output     The {@link RecipeOutput} to use.
     * @param result     The result item to use.
     * @param ingredient The {@link Ingredient} to use.
     * @param group      The crafting book group to use.
     */
    private void oneToOneConversion(RecipeOutput output, ItemLike result, ItemLike ingredient, @Nullable String group) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 1)
            .requires(ingredient)
            .group(group)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(output, ArsMagicaApi.modLoc(getConversionRecipeName(result, ingredient)));
    }
}
