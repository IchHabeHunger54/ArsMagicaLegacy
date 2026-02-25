package at.minecraftschurli.arsmagicalegacy.ritual.trigger;

import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualTrigger;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public record GameEventRitualTrigger(Holder<GameEvent> gameEvent) implements RitualTrigger<Holder<GameEvent>> {
    public static final MapCodec<GameEventRitualTrigger> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        GameEvent.CODEC.fieldOf("game_event").forGetter(GameEventRitualTrigger::gameEvent)
    ).apply(inst, GameEventRitualTrigger::new));

    @Override
    public MapCodec<? extends RitualTrigger<Holder<GameEvent>>> codec() {
        return CODEC;
    }

    @Override
    public boolean test(Player player, Level level, Vec3 vec, Holder<GameEvent> context) {
        return context.getKey() == gameEvent.getKey();
    }
}
