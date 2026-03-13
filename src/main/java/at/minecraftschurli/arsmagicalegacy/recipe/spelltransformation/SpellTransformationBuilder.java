package at.minecraftschurli.arsmagicalegacy.recipe.spelltransformation;

import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import org.jetbrains.annotations.Nullable;

public class SpellTransformationBuilder implements RecipeBuilder {
    private final SpellTransformationRecipe recipe;

    public SpellTransformationBuilder(RuleTest ruleTest, Holder<SpellPart> spellPart, BlockState result) {
        recipe = new SpellTransformationRecipe(ruleTest, spellPart, result);
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(RecipeOutput recipeOutput, Identifier id) {
        recipeOutput.accept(id, recipe, null);
    }
}
