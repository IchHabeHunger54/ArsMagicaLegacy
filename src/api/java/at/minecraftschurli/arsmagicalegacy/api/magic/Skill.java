package at.minecraftschurli.arsmagicalegacy.api.magic;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public record Skill(List<ResourceLocation> parents, Optional<Holder<SkillPoint>> cost, Holder<OcculusTab> tab, int x, int y, boolean hidden) {
    public static final Codec<Skill> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ResourceLocation.CODEC.listOf().fieldOf("parents").forGetter(Skill::parents),
        SkillPoint.CODEC.optionalFieldOf("cost").forGetter(Skill::cost),
        OcculusTab.CODEC.fieldOf("tab").forGetter(Skill::tab),
        Codec.INT.fieldOf("x").forGetter(Skill::x),
        Codec.INT.fieldOf("y").forGetter(Skill::y),
        Codec.BOOL.optionalFieldOf("hidden", false).forGetter(Skill::hidden)
    ).apply(inst, Skill::new));
    public static final Codec<Holder<Skill>> CODEC = RegistryFileCodec.create(AMRegistryKeys.SKILL, DIRECT_CODEC);

    public List<Skill> getParents(RegistryAccess registryAccess) {
        return parents.stream()
            .map(registryAccess.registryOrThrow(AMRegistryKeys.SKILL)::get)
            .toList();
    }

    public static MutableComponent getName(Holder<Skill> holder) {
        return Component.translatable(Util.makeDescriptionId("skill", holder.getKey().location()) + ".name");
    }

    public static MutableComponent getDescription(Holder<Skill> holder) {
        return Component.translatable(Util.makeDescriptionId("skill", holder.getKey().location()) + ".description");
    }
}
