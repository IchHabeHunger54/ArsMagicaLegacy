package at.minecraftschurli.arsmagicalegacy.packet;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.util.OwnerSetter;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;

public record SetEntityOwnerPacket(int id, Optional<EntityReference<LivingEntity>> owner) implements CustomPacketPayload {
    public static final Type<SetEntityOwnerPacket> TYPE = new Type<>(ArsMagicaApi.id("set_entity_owner"));
    public static final StreamCodec<ByteBuf, SetEntityOwnerPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT, SetEntityOwnerPacket::id,
        ByteBufCodecs.optional(EntityReference.streamCodec()), SetEntityOwnerPacket::owner,
        SetEntityOwnerPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        if (context.player().level().getEntity(id) instanceof OwnerSetter ownerSetter) {
            ownerSetter.setOwner(owner.orElse(null));
        }
    }
}
