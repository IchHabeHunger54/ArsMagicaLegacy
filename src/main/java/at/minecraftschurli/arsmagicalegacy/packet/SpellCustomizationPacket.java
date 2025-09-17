package at.minecraftschurli.arsmagicalegacy.packet;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SpellCustomizationPacket(Spell spell) implements CustomPacketPayload {
    public static final Type<SpellCustomizationPacket> TYPE = new Type<>(ArsMagicaApi.modLoc("spell_customization"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellCustomizationPacket> STREAM_CODEC = Spell.STREAM_CODEC.map(SpellCustomizationPacket::new, SpellCustomizationPacket::spell);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        ItemStack stack = context.player().getMainHandItem();
        if (stack.has(AMDataComponents.SPELL)) {
            stack.set(AMDataComponents.SPELL, spell);
        }
    }
}
