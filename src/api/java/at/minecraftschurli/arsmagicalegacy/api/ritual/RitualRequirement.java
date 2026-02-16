package at.minecraftschurli.arsmagicalegacy.api.ritual;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import java.util.function.Function;

public interface RitualRequirement {
    Codec<RitualRequirement> CODEC = Codec.lazyInitialized(() -> AMRegistries.RITUAL_REQUIREMENTS.byNameCodec().dispatch(RitualRequirement::codec, Function.identity()));

    /**
     * @return The registered {@link MapCodec}.
     */
    MapCodec<? extends RitualRequirement> codec();
}
