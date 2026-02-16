package at.minecraftschurli.arsmagicalegacy.api.ritual;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import java.util.function.Function;

public interface RitualEffect {
    Codec<RitualEffect> CODEC = Codec.lazyInitialized(() -> AMRegistries.RITUAL_EFFECTS.byNameCodec().dispatch(RitualEffect::codec, Function.identity()));

    /**
     * @return The registered {@link MapCodec}.
     */
    MapCodec<? extends RitualEffect> codec();
}
