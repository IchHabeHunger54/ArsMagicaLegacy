package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.data.AbstractDataProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record SpellPartData(float mana, Optional<Float> burnout) {
    public static final SpellPartData DEFAULT = new SpellPartData(0f, Optional.empty());
    public static final Codec<SpellPartData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.FLOAT.fieldOf("mana").forGetter(SpellPartData::mana),
        Codec.FLOAT.optionalFieldOf("burnout").forGetter(SpellPartData::burnout)
    ).apply(inst, SpellPartData::new));

    public static class Builder extends AbstractDataProvider.Builder<SpellPartData> {
        private final float mana;
        private Float burnout;

        public Builder(ResourceLocation id, float mana) {
            super(id);
            this.mana = mana;
        }

        public Builder burnout(float burnout) {
            this.burnout = burnout;
            return this;
        }

        @Override
        public SpellPartData build() {
            return new SpellPartData(mana, Optional.ofNullable(burnout));
        }
    }
}
