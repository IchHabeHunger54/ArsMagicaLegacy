package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.spell.SpellDamage;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMDataComponents {
    // @formatter:off
    DeferredHolder<DataComponentType<?>, DataComponentType<Holder<Affinity>>>   AFFINITY    = register(AMRegistries.DATA_COMPONENTS, "affinity",    Affinity.CODEC,   ByteBufCodecs.holderRegistry(AMRegistryKeys.AFFINITY));
    DeferredHolder<DataComponentType<?>, DataComponentType<Holder<SkillPoint>>> SKILL_POINT = register(AMRegistries.DATA_COMPONENTS, "skill_point", SkillPoint.CODEC, ByteBufCodecs.holderRegistry(AMRegistryKeys.SKILL_POINT));
    DeferredHolder<DataComponentType<?>, DataComponentType<Spell>>              SPELL       = register(AMRegistries.DATA_COMPONENTS, "spell",       Spell.CODEC,      Spell.STREAM_CODEC);
    DeferredHolder<DataComponentType<?>, DataComponentType<Integer>>            TIER        = register(AMRegistries.DATA_COMPONENTS, "tier",        Codec.INT,        ByteBufCodecs.INT);

    DeferredHolder<DataComponentType<?>, DataComponentType<Integer>>     SPELL_COLOR  = register(AMRegistries.DATA_COMPONENTS, "color",  Codec.INT, ByteBufCodecs.INT);
    DeferredHolder<DataComponentType<?>, DataComponentType<SpellDamage>> SPELL_DAMAGE = register(AMRegistries.DATA_COMPONENTS, "damage", SpellDamage.CODEC);
    // @formatter:on

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(DeferredRegister.DataComponents registry, String name, Codec<T> codec) {
        return registry.registerComponentType(name, builder -> builder.persistent(codec));
    }

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(DeferredRegister.DataComponents registry, String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return registry.registerComponentType(name, builder -> builder.persistent(codec).networkSynchronized(streamCodec));
    }
}
