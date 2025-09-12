package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.magic.MagicAttachment;
import at.minecraftschurli.arsmagicalegacy.effect.TemporalAnchorEffect;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public interface AMAttachments {
    // formatter:off
    DeferredHolder<AttachmentType<?>, AttachmentType<Double>>          BURNOUT = register("burnout", () -> 0.,                      Codec.DOUBLE,          ByteBufCodecs.DOUBLE);
    DeferredHolder<AttachmentType<?>, AttachmentType<Integer>>         FROST   = register("frost",   () -> 0,                       Codec.INT,             ByteBufCodecs.INT);
    DeferredHolder<AttachmentType<?>, AttachmentType<MagicAttachment>> MAGIC   = register("magic",   () -> MagicAttachment.DEFAULT, MagicAttachment.CODEC, MagicAttachment.STREAM_CODEC);
    DeferredHolder<AttachmentType<?>, AttachmentType<Double>>          MANA    = register("mana",    () -> 0.,                      Codec.DOUBLE,          ByteBufCodecs.DOUBLE);
    DeferredHolder<AttachmentType<?>, AttachmentType<TemporalAnchorEffect.Snapshot>> TEMPORAL_ANCHOR_SNAPSHOT = AMRegistries.ATTACHMENTS.register("temporal_anchor_snapshot", () -> AttachmentType.<TemporalAnchorEffect.Snapshot>builder(() -> null).serialize(TemporalAnchorEffect.Snapshot.CODEC).build());
    // formatter:on

    private static <T> DeferredHolder<AttachmentType<?>, AttachmentType<T>> register(String name, Supplier<T> defaultValueSupplier, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return AMRegistries.ATTACHMENTS.register(name, () -> AttachmentType.builder(defaultValueSupplier).serialize(codec).sync(streamCodec).copyOnDeath().build());
    }

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
