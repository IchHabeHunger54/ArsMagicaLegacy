package at.minecraftschurli.arsmagicalegacy.api.advancement;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class SkillChangeTrigger extends SimpleCriterionTrigger<SkillChangeTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        trigger(player, t -> t.matches(player));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, List<Holder<Skill>> skills, boolean inverted) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
            Skill.CODEC.listOf().optionalFieldOf("skills", List.of()).forGetter(TriggerInstance::skills),
            Codec.BOOL.optionalFieldOf("inverted", false).forGetter(TriggerInstance::inverted)
        ).apply(inst, TriggerInstance::new));

        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        public TriggerInstance(Optional<ContextAwarePredicate> player, List<Holder<Skill>> skills) {
            this(player, skills, false);
        }

        public TriggerInstance(List<Holder<Skill>> skills, boolean inverted) {
            this(Optional.empty(), skills, inverted);
        }

        public TriggerInstance(List<Holder<Skill>> skills) {
            this(Optional.empty(), skills, false);
        }

        public boolean matches(Player player) {
            Predicate<Holder<Skill>> predicate = skills::contains;
            if (inverted) {
                predicate = predicate.negate();
            }
            return AMRegistries.skills(player.registryAccess())
                .holders()
                .filter(predicate)
                .allMatch(skill -> ArsMagicaApi.magicHelper().knows(player, skill));
        }
    }
}
