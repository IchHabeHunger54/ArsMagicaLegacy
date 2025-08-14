package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class AMRecipeProvider extends RecipeProvider {
    public AMRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        generateRecipes(output, AMBlocks.WITCHWOOD_BLOCK_FAMILY.get(), FeatureFlagSet.of(FeatureFlags.VANILLA));
        hangingSign(output, AMItems.WITCHWOOD_HANGING_SIGN.get(), AMItems.STRIPPED_WITCHWOOD_LOG.get());
        planksFromLogs(output, AMItems.WITCHWOOD_PLANKS.get(), AMTags.Items.WITCHWOOD_LOGS, 4);
        woodFromLogs(output, AMBlocks.WITCHWOOD.get(), AMBlocks.WITCHWOOD_LOG.get());
        woodFromLogs(output, AMBlocks.STRIPPED_WITCHWOOD.get(), AMBlocks.STRIPPED_WITCHWOOD_LOG.get());
        nineBlockStorageRecipes(output, AMItems.VINTEUM_DUST, AMTags.Items.DUSTS_VINTEUM, AMItems.VINTEUM_BLOCK, AMTags.Items.STORAGE_BLOCKS_VINTEUM);
        nineBlockStorageRecipes(output, AMItems.CHIMERITE, AMTags.Items.GEMS_CHIMERITE, AMItems.CHIMERITE_BLOCK, AMTags.Items.STORAGE_BLOCKS_CHIMERITE);
        nineBlockStorageRecipes(output, AMItems.TOPAZ, AMTags.Items.GEMS_TOPAZ, AMItems.TOPAZ_BLOCK, AMTags.Items.STORAGE_BLOCKS_TOPAZ);
        nineBlockStorageRecipes(output, AMItems.MOONSTONE, AMTags.Items.GEMS_MOONSTONE, AMItems.MOONSTONE_BLOCK, AMTags.Items.STORAGE_BLOCKS_MOONSTONE);
        nineBlockStorageRecipes(output, AMItems.SUNSTONE, AMTags.Items.GEMS_SUNSTONE, AMItems.SUNSTONE_BLOCK, AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
        oreSmelting(output, List.of(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get()), AMItems.CHIMERITE.get(), 0.7f, 200, "chimerite");
        oreSmelting(output, List.of(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get()), AMItems.TOPAZ.get(), 0.7f, 200, "topaz");
        oreSmelting(output, List.of(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get()), AMItems.VINTEUM_DUST.get(), 0.7f, 200, "vinteum_dust");
        oreSmelting(output, List.of(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get()), AMItems.MOONSTONE.get(), 0.7f, 200, "moonstone");
        oreBlasting(output, List.of(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get()), AMItems.CHIMERITE.get(), 0.7f, 100, "chimerite");
        oreBlasting(output, List.of(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get()), AMItems.TOPAZ.get(), 0.7f, 100, "topaz");
        oreBlasting(output, List.of(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get()), AMItems.VINTEUM_DUST.get(), 0.7f, 100, "vinteum_dust");
        oreBlasting(output, List.of(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get()), AMItems.MOONSTONE.get(), 0.7f, 100, "moonstone");
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

    private void oreSmelting(RecipeOutput output, List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(output, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, ingredients, result, experience, cookingTime, group, "_from_smelting");
    }

    private void oreBlasting(RecipeOutput output, List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(output, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, ingredients, result, experience, cookingTime, group, "_from_blasting");
    }

    private <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput output, RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> recipeFactory, List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime, String group, String suffix) {
        for (ItemLike item : ingredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(item), RecipeCategory.MISC, result, experience, cookingTime, serializer, recipeFactory)
                    .group(group)
                    .unlockedBy(getHasName(item), has(item))
                    .save(output, ArsMagicaApi.modLoc(getItemName(result) + suffix + "_" + getItemName(item)));
        }
    }

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

    private void oneToOneConversion(RecipeOutput output, ItemLike result, ItemLike ingredient, @Nullable String group) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 1)
                .requires(ingredient)
                .group(group)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output, ArsMagicaApi.modLoc(getConversionRecipeName(result, ingredient)));
    }
}
