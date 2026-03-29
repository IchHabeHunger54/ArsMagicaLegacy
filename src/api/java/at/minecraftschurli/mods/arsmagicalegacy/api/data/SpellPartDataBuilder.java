package at.minecraftschurli.mods.arsmagicalegacy.api.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPartData;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Builder class for {@link SpellPartData}, for use in {@link SpellPartDataProvider}. Get an instance via {@link SpellPartDataProvider#builder(DeferredHolder, double)}.
 */
public class SpellPartDataBuilder extends AbstractDataProvider.Builder<SpellPartData> {
    private final Map<Holder<Affinity>, Double> affinityShifts = new HashMap<>();
    private final List<SpellIngredient> recipe = new ArrayList<>();
    private final double mana;
    @Nullable
    private Double burnout;

    /**
     * @param id   The id of the {@link SpellPart} to generate data for.
     * @param mana The mana cost of the {@link SpellPart}.
     */
    public SpellPartDataBuilder(Identifier id, double mana) {
        super(id);
        this.mana = mana;
    }

    /**
     * @param burnout The burnout value to set.
     * @return This builder, for chaining.
     */
    public SpellPartDataBuilder burnout(double burnout) {
        this.burnout = burnout;
        return this;
    }

    /**
     * @param affinity The {@link Affinity} to add.
     * @param shift    The affinity shift value to use.
     * @return This builder, for chaining.
     */
    public SpellPartDataBuilder affinity(Holder<Affinity> affinity, double shift) {
        affinityShifts.put(affinity, shift);
        return this;
    }

    /**
     * @param ingredient The {@link SpellIngredient} to add.
     * @return This builder, for chaining.
     */
    public SpellPartDataBuilder ingredient(SpellIngredient ingredient) {
        recipe.add(ingredient);
        return this;
    }

    @Override
    public SpellPartData build() {
        return new SpellPartData(mana, Optional.ofNullable(burnout), affinityShifts, recipe);
    }
}
