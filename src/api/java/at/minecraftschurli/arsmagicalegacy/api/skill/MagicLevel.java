package at.minecraftschurli.arsmagicalegacy.api.skill;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record MagicLevel(int level, double xp) {
    public static final Codec<MagicLevel> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.INT.fieldOf("level").forGetter(MagicLevel::level),
        Codec.DOUBLE.fieldOf("xp").forGetter(MagicLevel::xp)
    ).apply(inst, MagicLevel::new));
    public static final StreamCodec<ByteBuf, MagicLevel> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT, MagicLevel::level,
        ByteBufCodecs.DOUBLE, MagicLevel::xp,
        MagicLevel::new);
    public static final MagicLevel DEFAULT = new MagicLevel(0, 0);

    public MagicLevel setLevel(int level) {
        return new MagicLevel(level, xp);
    }

    public MagicLevel setXp(double xp) {
        return new MagicLevel(level, xp);
    }
}
