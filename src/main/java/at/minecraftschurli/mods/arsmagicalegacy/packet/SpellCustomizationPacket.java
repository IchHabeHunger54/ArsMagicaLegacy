package at.minecraftschurli.mods.arsmagicalegacy.packet;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMCapabilities;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.MutableSpellFacade;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellFacade;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellDataComponentMap;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMExtraCodecs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;

public record SpellCustomizationPacket(Optional<Component> name, Optional<Identifier> icon, SpellDataComponentMap map, InteractionHand hand) implements CustomPacketPayload {
    public static final Type<SpellCustomizationPacket> TYPE = new Type<>(ArsMagicaApi.id("spell_customization"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellCustomizationPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.optional(ComponentSerialization.STREAM_CODEC), SpellCustomizationPacket::name,
        ByteBufCodecs.optional(Identifier.STREAM_CODEC), SpellCustomizationPacket::icon,
        SpellDataComponentMap.STREAM_CODEC, SpellCustomizationPacket::map,
        AMExtraCodecs.INTERACTION_HAND_STREAM_CODEC, SpellCustomizationPacket::hand,
        SpellCustomizationPacket::new);

    public SpellCustomizationPacket(SpellFacade spell, InteractionHand hand) {
        this(spell.name(), spell.icon(), spell.spellData(), hand);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        ItemStack stack = context.player().getItemInHand(hand);
        MutableSpellFacade capability = stack.getCapability(AMCapabilities.SPELL);
        if (capability == null) return;
        capability.setName(name.orElse(null));
        capability.setIcon(icon.orElse(null));
        capability.setSpellData(map);
    }
}
