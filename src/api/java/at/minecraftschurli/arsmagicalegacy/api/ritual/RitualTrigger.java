package at.minecraftschurli.arsmagicalegacy.api.ritual;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import java.util.function.Function;

public interface RitualTrigger {
    Codec<RitualTrigger> CODEC = Codec.lazyInitialized(() -> AMRegistries.RITUAL_TRIGGERS.byNameCodec().dispatch(RitualTrigger::codec, Function.identity()));

    /**
     * @return The registered {@link MapCodec}.
     */
    MapCodec<? extends RitualTrigger> codec();
}
