package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.data.AbstractDataProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record SpellPartData(double mana, Optional<Double> burnout) {
    public static final SpellPartData DEFAULT = new SpellPartData(0f, Optional.empty());
    public static final Codec<SpellPartData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.DOUBLE.fieldOf("mana").forGetter(SpellPartData::mana),
        Codec.DOUBLE.optionalFieldOf("burnout").forGetter(SpellPartData::burnout)
    ).apply(inst, SpellPartData::new));

    public double burnoutOrGenerated() {
        return burnout.orElse(mana * ArsMagicaApi.getBurnoutHelper().getManaToBurnoutRatio());
    }

    public static class Builder extends AbstractDataProvider.Builder<SpellPartData> {
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

        @Override
        public SpellPartData build() {
            return new SpellPartData(mana, Optional.ofNullable(burnout));
        }
    }
}
