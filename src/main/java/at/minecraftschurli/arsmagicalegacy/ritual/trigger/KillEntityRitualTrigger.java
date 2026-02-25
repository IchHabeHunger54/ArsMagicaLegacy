package at.minecraftschurli.arsmagicalegacy.ritual.trigger;

import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualTrigger;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record KillEntityRitualTrigger(EntityPredicate predicate) implements RitualTrigger<Entity> {
    public static final MapCodec<KillEntityRitualTrigger> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        EntityPredicate.CODEC.fieldOf("predicate").forGetter(KillEntityRitualTrigger::predicate)
    ).apply(inst, KillEntityRitualTrigger::new));

    @Override
    public MapCodec<? extends RitualTrigger<Entity>> codec() {
        return CODEC;
    }

    @Override
    public boolean test(Player player, Level level, Vec3 vec, Entity context) {
        return level instanceof ServerLevel serverLevel && predicate.matches(serverLevel, vec, context);
    }
}
