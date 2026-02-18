package at.minecraftschurli.arsmagicalegacy.packet;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.compat.jei.HiddenSkills;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateSkillsPacket() implements CustomPacketPayload {
    public static final Type<UpdateSkillsPacket> TYPE = new Type<>(ArsMagicaApi.id("update_skills"));
    public static final StreamCodec<ByteBuf, UpdateSkillsPacket> STREAM_CODEC = StreamCodec.unit(new UpdateSkillsPacket());

    public void handle(IPayloadContext context) {
        if (ModList.get().isLoaded("jei")) {
            HiddenSkills.update();
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
