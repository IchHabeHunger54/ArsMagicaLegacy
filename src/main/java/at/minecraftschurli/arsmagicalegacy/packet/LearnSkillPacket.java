package at.minecraftschurli.arsmagicalegacy.packet;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record LearnSkillPacket(Holder<Skill> skill) implements CustomPacketPayload {
    public static final Type<LearnSkillPacket> TYPE = new Type<>(ArsMagicaApi.modLoc("learn_skill"));
    public static final StreamCodec<RegistryFriendlyByteBuf, LearnSkillPacket> STREAM_CODEC = ByteBufCodecs.holderRegistry(AMRegistryKeys.SKILL).map(LearnSkillPacket::new, LearnSkillPacket::skill);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        MagicHelper helper = ArsMagicaApi.magicHelper();
        Player player = context.player();
        if (helper.canLearn(player, skill)) {
            helper.learn(player, skill);
            if (!player.isCreative()) {
                skill.value().cost().ifPresent(cost -> helper.addSkillPoint(player, cost, -1));
            }
        }
    }
}
