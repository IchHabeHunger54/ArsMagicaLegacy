package at.minecraftschurli.arsmagicalegacy.init;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public interface AMAttachments {
    DeferredHolder<AttachmentType<?>, AttachmentType<Double>> MANA = register("mana", () -> 0d, Codec.DOUBLE, ByteBufCodecs.DOUBLE);

    private static <T> DeferredHolder<AttachmentType<?>, AttachmentType<T>> register(String name, Supplier<T> defaultValueSupplier, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return AMRegistries.ATTACHMENTS.register(name, () -> AttachmentType.builder(defaultValueSupplier).serialize(codec).sync(streamCodec).copyOnDeath().build());
    }

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
