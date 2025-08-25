package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface AMDataComponents {
    // @formatter:off
    DeferredHolder<DataComponentType<?>, DataComponentType<Holder<SkillPoint>>> SKILL_POINT = register("skill_point", SkillPoint.CODEC, SkillPoint.STREAM_CODEC);
    DeferredHolder<DataComponentType<?>, DataComponentType<Spell>>              SPELL       = register("spell",       Spell.CODEC,      Spell.STREAM_CODEC);
    DeferredHolder<DataComponentType<?>, DataComponentType<Integer>>            TIER        = register("tier",        Codec.INT,        ByteBufCodecs.INT);
    // @formatter:on

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return AMRegistries.DATA_COMPONENTS.registerComponentType(name, builder -> builder.persistent(codec).networkSynchronized(streamCodec));
    }
}
