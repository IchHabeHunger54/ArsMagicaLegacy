package at.minecraftschurli.arsmagicalegacy.api.magic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;

import java.util.List;
import java.util.Map;

public record Skill(List<Skill> parents, Map<Holder<SkillPoint>, Integer> cost, OcculusTab tab, int x, int y, boolean hidden) {
    public static final Codec<Skill> CODEC = Codec.recursive(Skill.class.getSimpleName(), recursive -> RecordCodecBuilder.create(inst -> inst.group(
        recursive.listOf().fieldOf("parents").forGetter(Skill::parents),
        Codec.unboundedMap(SkillPoint.CODEC, Codec.INT).fieldOf("cost").forGetter(Skill::cost),
        OcculusTab.CODEC.fieldOf("tab").forGetter(Skill::tab),
        Codec.INT.fieldOf("x").forGetter(Skill::x),
        Codec.INT.fieldOf("y").forGetter(Skill::y),
        Codec.BOOL.optionalFieldOf("hidden", false).forGetter(Skill::hidden)
    ).apply(inst, Skill::new)));
}
