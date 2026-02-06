package at.minecraftschurli.arsmagicalegacy.packet;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.util.OwnerSetter;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetBlockEntityOwnerPacket(BlockPos pos, int owner) implements CustomPacketPayload {
    public static final Type<SetBlockEntityOwnerPacket> TYPE = new Type<>(ArsMagicaApi.id("set_block_entity_owner"));
    public static final StreamCodec<ByteBuf, SetBlockEntityOwnerPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, SetBlockEntityOwnerPacket::pos,
        ByteBufCodecs.INT, SetBlockEntityOwnerPacket::owner,
        SetBlockEntityOwnerPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        if (context.player().level().getBlockEntity(pos) instanceof OwnerSetter ownerSetter) {
            ownerSetter.setOwner(owner);
        }
    }
}
