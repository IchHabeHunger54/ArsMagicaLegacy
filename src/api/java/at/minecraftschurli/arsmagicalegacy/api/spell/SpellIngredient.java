package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

/**
 * Represents a spell ingredient.
 */
public interface SpellIngredient {
    Codec<SpellIngredient> CODEC = Codec.lazyInitialized(() -> ArsMagicaApi.spellIngredientRegistry().byNameCodec().dispatch(SpellIngredient::codec, Function.identity()));

    /**
     * @return The registered {@link MapCodec} of the spell ingredient.
     */
    MapCodec<? extends SpellIngredient> codec();

    /**
     * @return The count of the spell ingredient.
     */
    int count();

    /**
     * @return A list of tooltip {@link Component}s for the spell ingredient.
     */
    List<Component> tooltip();

    /**
     * @param other The other ingredient to combine with.
     * @return Whether the spell ingredient and the other spell ingredient can be combined.
     * @see SpellIngredient#combine(SpellIngredient)
     */
    boolean canCombine(SpellIngredient other);

    /**
     * @param other The other ingredient to combine with.
     * @return The combination of the spell ingredient with the other spell ingredient, or null if they cannot be combined.
     * @see SpellIngredient#canCombine(SpellIngredient)
     */
    @Nullable
    SpellIngredient combine(SpellIngredient other);

    /**
     * Attempts to consume the spell ingredient.
     *
     * @param level The {@link Level} in which the spell ingredient is consumed.
     * @param pos   The {@link BlockPos} at which the spell ingredient is consumed.
     * @return Whether the spell ingredient was consumed or not.
     */
    boolean consume(Level level, BlockPos pos);

    /**
     * @return A representation of the spell ingredients as item stacks, for use in GUI rendering.
     */
    List<ItemStack> asItemStacks();
}
