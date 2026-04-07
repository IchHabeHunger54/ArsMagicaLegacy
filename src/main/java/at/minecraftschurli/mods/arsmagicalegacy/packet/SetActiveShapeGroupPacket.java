package at.minecraftschurli.mods.arsmagicalegacy.packet;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMCapabilities;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.MutableSpellFacade;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetActiveShapeGroupPacket(byte activeShapeGroup) implements CustomPacketPayload {
    public static final Type<SetActiveShapeGroupPacket> TYPE = new Type<>(ArsMagicaApi.id("set_active_shape_group"));
    public static final StreamCodec<ByteBuf, SetActiveShapeGroupPacket> STREAM_CODEC = ByteBufCodecs.BYTE.map(SetActiveShapeGroupPacket::new, SetActiveShapeGroupPacket::activeShapeGroup);

    public void handle(IPayloadContext context) {
        Player player = context.player();
        MutableSpellFacade capability = player.getMainHandItem().getCapability(AMCapabilities.SPELL);
        if (capability == null) {
            capability = player.getOffhandItem().getCapability(AMCapabilities.SPELL);
        }
        if (capability != null) {
            capability.setActiveShapeGroup(activeShapeGroup);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
