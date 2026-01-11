package at.minecraftschurli.arsmagicalegacy.spell.data;

import at.minecraftschurli.arsmagicalegacy.util.AMExtraCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record RecallPosition(ResourceKey<Level> dimension, Vec3 position) {
    public static final Codec<RecallPosition> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Level.RESOURCE_KEY_CODEC.fieldOf("dimension").forGetter(RecallPosition::dimension),
        Vec3.CODEC.fieldOf("position").forGetter(RecallPosition::position)
    ).apply(inst, RecallPosition::new));
    public static final StreamCodec<ByteBuf, RecallPosition> STREAM_CODEC = StreamCodec.composite(
        ResourceKey.streamCodec(Registries.DIMENSION), RecallPosition::dimension,
        AMExtraCodecs.VEC3_STREAM_CODEC, RecallPosition::position,
        RecallPosition::new);
}
