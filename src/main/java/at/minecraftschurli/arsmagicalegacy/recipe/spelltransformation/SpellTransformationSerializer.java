package at.minecraftschurli.arsmagicalegacy.recipe.spelltransformation;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SpellTransformationSerializer implements RecipeSerializer<SpellTransformationRecipe> {
    @Override
    public MapCodec<SpellTransformationRecipe> codec() {
        return SpellTransformationRecipe.CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SpellTransformationRecipe> streamCodec() {
        return SpellTransformationRecipe.STREAM_CODEC;
    }
}
