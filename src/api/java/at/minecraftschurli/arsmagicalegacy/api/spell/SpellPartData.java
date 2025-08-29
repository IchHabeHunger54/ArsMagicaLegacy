package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.data.AbstractDataProvider;
import at.minecraftschurli.arsmagicalegacy.api.data.SpellPartDataProvider;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the datapack-supplied properties of a spell part.
 *
 * @param mana           The mana cost of the spell part.
 * @param burnout        The burnout cost of the spell part. If empty, will be calculated from the mana cost.
 * @param affinityShifts A {@link Map} of {@link Affinity}s to doubles, representing the affinity shifts when casting the spell part.
 */
public record SpellPartData(double mana, Optional<Double> burnout, Map<Holder<Affinity>, Double> affinityShifts, List<SpellIngredient> recipe) {
    public static final SpellPartData DEFAULT = new SpellPartData(0f, Optional.empty(), Map.of(), List.of());
    public static final Codec<SpellPartData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.DOUBLE.fieldOf("mana").forGetter(SpellPartData::mana),
        Codec.DOUBLE.optionalFieldOf("burnout").forGetter(SpellPartData::burnout),
        Codec.unboundedMap(Affinity.CODEC, Codec.DOUBLE).fieldOf("affinity_shifts").forGetter(SpellPartData::affinityShifts),
        SpellIngredient.CODEC.listOf().fieldOf("recipe").forGetter(SpellPartData::recipe)
    ).apply(inst, SpellPartData::new));

    /**
     * @return The burnout value. Will use {@link SpellPartData#burnout} or, if that is empty, calculate the value from {@link SpellPartData#mana}.
     */
    public double burnoutOrGenerated() {
        return burnout.orElse(mana * ArsMagicaApi.spellHelper().getManaToBurnoutRatio());
    }

    /**
     * Builder class for {@link SpellPartData}, for use in {@link SpellPartDataProvider}. Get an instance via {@link SpellPartDataProvider#builder(DeferredHolder, double)}.
     */
    public static class Builder extends AbstractDataProvider.Builder<SpellPartData> {
        private final Map<Holder<Affinity>, Double> affinityShifts = new HashMap<>();
        private final List<SpellIngredient> recipe = new ArrayList<>();
        private final double mana;
        private Double burnout;

        public Builder(ResourceLocation id, double mana) {
            super(id);
            this.mana = mana;
        }

        public Builder burnout(double burnout) {
            this.burnout = burnout;
            return this;
        }

        public Builder affinity(Holder<Affinity> affinity, double shift) {
            affinityShifts.put(affinity, shift);
            return this;
        }

        public Builder ingredient(SpellIngredient ingredient) {
            recipe.add(ingredient);
            return this;
        }

        @Override
        public SpellPartData build() {
            return new SpellPartData(mana, Optional.ofNullable(burnout), affinityShifts, recipe);
        }
    }
}
