package at.minecraftschurli.arsmagicalegacy.ritual.effect;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualEffect;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record LearnSkillRitualEffect(Holder<Skill> skill) implements RitualEffect {
    public static final MapCodec<LearnSkillRitualEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Skill.CODEC.fieldOf("skill").forGetter(LearnSkillRitualEffect::skill)
    ).apply(inst, LearnSkillRitualEffect::new));

    @Override
    public MapCodec<? extends RitualEffect> codec() {
        return CODEC;
    }

    @Override
    public void perform(Player player, Level level, Vec3 vec) {
        ArsMagicaApi.magicHelper().learn(player, skill);
    }
}
