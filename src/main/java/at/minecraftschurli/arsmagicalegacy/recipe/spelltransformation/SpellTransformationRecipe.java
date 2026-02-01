package at.minecraftschurli.arsmagicalegacy.recipe.spelltransformation;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.init.AMRecipes;
import at.minecraftschurli.arsmagicalegacy.util.AMExtraCodecs;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public record SpellTransformationRecipe(RuleTest ruleTest, Holder<SpellPart> spellPart, BlockState result) implements Recipe<SpellTransformationInput> {
    private static final RandomSource RANDOM = RandomSource.create(42);
    public static final MapCodec<SpellTransformationRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        RuleTest.CODEC.fieldOf("predicate").forGetter(SpellTransformationRecipe::ruleTest),
        ArsMagicaApi.spellPartRegistry().holderByNameCodec().fieldOf("spell_part").forGetter(SpellTransformationRecipe::spellPart),
        AMExtraCodecs.BLOCK_STATE_CODEC.fieldOf("result").forGetter(SpellTransformationRecipe::result)
    ).apply(inst, SpellTransformationRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellTransformationRecipe> STREAM_CODEC = StreamCodec.composite(
        AMExtraCodecs.toStreamCodec(RuleTest.CODEC), SpellTransformationRecipe::ruleTest,
        ByteBufCodecs.holderRegistry(AMRegistries.SPELL_PART), SpellTransformationRecipe::spellPart,
        AMExtraCodecs.BLOCK_STATE_STREAM_CODEC, SpellTransformationRecipe::result,
        SpellTransformationRecipe::new);

    @SuppressWarnings("DataFlowIssue")
    @Override
    public boolean matches(SpellTransformationInput input, Level level) {
        RANDOM.setSeed(42);
        return ruleTest.test(input.state(), RANDOM) && input.spellPart().is(spellPart.getKey());
    }

    @Override
    public ItemStack assemble(SpellTransformationInput input, HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AMRecipes.SPELL_TRANSFORMATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return AMRecipes.SPELL_TRANSFORMATION_TYPE.get();
    }
}
