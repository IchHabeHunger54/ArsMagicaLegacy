package at.minecraftschurli.arsmagicalegacy.packet;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.blockentity.SpellRuneBlockEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public record SetSpellRuneOwnerPacket(BlockPos pos, UUID uuid) implements CustomPacketPayload {
    public static final Type<SetSpellRuneOwnerPacket> TYPE = new Type<>(ArsMagicaApi.id("set_spell_rune_owner"));
    public static final StreamCodec<ByteBuf, SetSpellRuneOwnerPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, SetSpellRuneOwnerPacket::pos,
        UUIDUtil.STREAM_CODEC, SetSpellRuneOwnerPacket::uuid,
        SetSpellRuneOwnerPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        if (context.player().level().getBlockEntity(pos) instanceof SpellRuneBlockEntity spellRune) {
            spellRune.setOwner(uuid);
        }
    }
}
