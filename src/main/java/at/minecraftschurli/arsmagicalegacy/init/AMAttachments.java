package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicAttachment;
import at.minecraftschurli.arsmagicalegacy.attachment.RiftAttachment;
import at.minecraftschurli.arsmagicalegacy.attachment.TemporalAnchorAttachment;
import at.minecraftschurli.arsmagicalegacy.attachment.ContingencyAttachment;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public interface AMAttachments {
    DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<AttachmentType<?>, AttachmentType<Double>>                BURNOUT     = register("burnout",     () -> 0.,                            Codec.DOUBLE,                ByteBufCodecs.DOUBLE);
    DeferredHolder<AttachmentType<?>, AttachmentType<ContingencyAttachment>> CONTINGENCY = register("contingency", () -> ContingencyAttachment.DEFAULT, ContingencyAttachment.CODEC, ContingencyAttachment.STREAM_CODEC);
    DeferredHolder<AttachmentType<?>, AttachmentType<Integer>>               FROST       = register("frost",       () -> 0,                             Codec.INT,                   ByteBufCodecs.INT);
    DeferredHolder<AttachmentType<?>, AttachmentType<MagicAttachment>>       MAGIC       = register("magic",       () -> MagicAttachment.DEFAULT,       MagicAttachment.CODEC,       MagicAttachment.STREAM_CODEC);
    DeferredHolder<AttachmentType<?>, AttachmentType<Double>>                MANA        = register("mana",        () -> 0.,                            Codec.DOUBLE,                ByteBufCodecs.DOUBLE);
    DeferredHolder<AttachmentType<?>, AttachmentType<RiftAttachment>>        RIFT        = register("rift",        () -> RiftAttachment.DEFAULT,        RiftAttachment.CODEC,        RiftAttachment.STREAM_CODEC);
    DeferredHolder<AttachmentType<?>, AttachmentType<TemporalAnchorAttachment>> TEMPORAL_ANCHOR_SNAPSHOT = ATTACHMENTS.register("temporal_anchor_snapshot", () -> AttachmentType.<TemporalAnchorAttachment>builder(() -> null).serialize(TemporalAnchorAttachment.CODEC).build());
    // @formatter:on

    private static <T> DeferredHolder<AttachmentType<?>, AttachmentType<T>> register(String name, Supplier<T> defaultValueSupplier, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return ATTACHMENTS.register(name, () -> AttachmentType.builder(defaultValueSupplier).serialize(codec).sync(streamCodec).copyOnDeath().build());
    }
}
