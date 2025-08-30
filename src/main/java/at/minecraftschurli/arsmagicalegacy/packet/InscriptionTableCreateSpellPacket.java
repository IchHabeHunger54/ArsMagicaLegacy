package at.minecraftschurli.arsmagicalegacy.packet;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.block.inscriptiontable.InscriptionTableBlockEntity;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record InscriptionTableCreateSpellPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<InscriptionTableCreateSpellPacket> TYPE = new Type<>(ArsMagicaApi.modLoc("inscription_table_create_spell"));
    public static final StreamCodec<ByteBuf, InscriptionTableCreateSpellPacket> STREAM_CODEC = BlockPos.STREAM_CODEC.map(InscriptionTableCreateSpellPacket::new, InscriptionTableCreateSpellPacket::pos);

    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();
        if (player.isCreative() && level.isLoaded(pos) && level.getBlockEntity(pos) instanceof InscriptionTableBlockEntity blockEntity) {
            Spell spell = blockEntity.getData().toSpell();
            if (!spell.isEmpty()) {
                player.getInventory().add(blockEntity.setSpell(AMItems.SPELL.toStack()));
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
