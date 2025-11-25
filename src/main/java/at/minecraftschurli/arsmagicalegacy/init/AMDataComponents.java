package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.spell.data.RecallPosition;
import at.minecraftschurli.arsmagicalegacy.spell.data.SpellDamage;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMDataComponents {
    DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<DataComponentType<?>, DataComponentType<Holder<Affinity>>>     AFFINITY      = register("affinity",      Affinity.CODEC,     ByteBufCodecs.holderRegistry(AMRegistries.AFFINITY));
    DeferredHolder<DataComponentType<?>, DataComponentType<Holder<EtheriumType>>> ETHERIUM_TYPE = register("etherium_type", EtheriumType.CODEC, ByteBufCodecs.holderRegistry(AMRegistries.ETHERIUM_TYPE));
    DeferredHolder<DataComponentType<?>, DataComponentType<Holder<SkillPoint>>>   SKILL_POINT   = register("skill_point",   SkillPoint.CODEC,   ByteBufCodecs.holderRegistry(AMRegistries.SKILL_POINT));
    DeferredHolder<DataComponentType<?>, DataComponentType<Spell>>                SPELL         = register("spell",         Spell.CODEC,        Spell.STREAM_CODEC);
    DeferredHolder<DataComponentType<?>, DataComponentType<Integer>>              TIER          = register("tier",          Codec.INT,          ByteBufCodecs.INT);

    DeferredHolder<DataComponentType<?>, DataComponentType<Integer>>        SPELL_COLOR           = register("spell_color",           Codec.INT,            ByteBufCodecs.INT);
    DeferredHolder<DataComponentType<?>, DataComponentType<SpellDamage>>    SPELL_DAMAGE          = register("spell_damage",          SpellDamage.CODEC,    SpellDamage.STREAM_CODEC);
    DeferredHolder<DataComponentType<?>, DataComponentType<RecallPosition>> SPELL_RECALL_POSITION = register("spell_recall_position", RecallPosition.CODEC, RecallPosition.STREAM_CODEC);
    // @formatter:on

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Codec<T> codec) {
        return DATA_COMPONENTS.registerComponentType(name, builder -> builder.persistent(codec));
    }

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return DATA_COMPONENTS.registerComponentType(name, builder -> builder.persistent(codec).networkSynchronized(streamCodec));
    }
}
