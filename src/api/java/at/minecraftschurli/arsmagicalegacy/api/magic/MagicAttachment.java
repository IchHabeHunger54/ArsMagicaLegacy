package at.minecraftschurli.arsmagicalegacy.api.magic;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public record MagicAttachment(int level, double xp, Set<Holder<Skill>> skills, Map<Holder<SkillPoint>, Integer> skillPoints) {
    public static final Codec<MagicAttachment> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.INT.fieldOf("level").forGetter(MagicAttachment::level),
        Codec.DOUBLE.fieldOf("xp").forGetter(MagicAttachment::xp),
        Skill.CODEC.listOf().xmap(Set::copyOf, List::copyOf).fieldOf("skills").forGetter(MagicAttachment::skills),
        Codec.unboundedMap(SkillPoint.CODEC, Codec.INT).fieldOf("skill_points").forGetter(MagicAttachment::skillPoints)
    ).apply(inst, MagicAttachment::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, MagicAttachment> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT, MagicAttachment::level,
        ByteBufCodecs.DOUBLE, MagicAttachment::xp,
        ByteBufCodecs.holderRegistry(AMRegistryKeys.SKILL).apply(ByteBufCodecs.collection(HashSet::new)), MagicAttachment::skills,
        ByteBufCodecs.map(HashMap::new, ByteBufCodecs.holderRegistry(AMRegistryKeys.SKILL_POINT), ByteBufCodecs.INT), MagicAttachment::skillPoints,
        MagicAttachment::new);
    public static final MagicAttachment DEFAULT = new MagicAttachment(0, 0, Set.of(), Map.of());

    public MagicAttachment setLevel(int level) {
        return new MagicAttachment(level, xp, skills, skillPoints);
    }

    public MagicAttachment setXp(double xp) {
        return new MagicAttachment(level, xp, skills, skillPoints);
    }

    public MagicAttachment updateSkills(Consumer<Set<Holder<Skill>>> consumer) {
        Set<Holder<Skill>> skills = new HashSet<>(this.skills);
        consumer.accept(skills);
        return new MagicAttachment(level, xp, skills, skillPoints);
    }

    public MagicAttachment updateSkillPoints(Consumer<Map<Holder<SkillPoint>, Integer>> consumer) {
        Map<Holder<SkillPoint>, Integer> skillPoints = new HashMap<>(this.skillPoints);
        consumer.accept(skillPoints);
        return new MagicAttachment(level, xp, skills, skillPoints);
    }
}
