package at.minecraftschurli.arsmagicalegacy.api.magic;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

public record SkillPoint(int color, int minEarnLevel, int levelsForPoint) {
    public static final Codec<SkillPoint> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.INT.fieldOf("color").forGetter(SkillPoint::color),
        Codec.INT.fieldOf("min_earn_level").forGetter(SkillPoint::minEarnLevel),
        Codec.INT.fieldOf("levels_for_point").forGetter(SkillPoint::levelsForPoint)
    ).apply(inst, SkillPoint::new));
    public static final Codec<Holder<SkillPoint>> CODEC = RegistryFileCodec.create(AMRegistryKeys.SKILL_POINT, DIRECT_CODEC);
}
