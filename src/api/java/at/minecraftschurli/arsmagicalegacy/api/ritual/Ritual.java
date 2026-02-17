package at.minecraftschurli.arsmagicalegacy.api.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * Represents a ritual. A ritual can be triggered, in which case it performs the ritual effect.
 *
 * @param requirements The passive requirements of the ritual.
 * @param trigger      The active trigger of the ritual. May itself contain requirements.
 * @param effects      The effects to perform when the ritual is successfully triggered.
 * @param <T>          The trigger context type.
 */
public record Ritual<T>(List<RitualRequirement> requirements, RitualTrigger<T> trigger, List<RitualEffect> effects) {
    public static final Codec<Ritual<?>> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        RitualRequirement.CODEC.listOf().fieldOf("requirements").forGetter(Ritual::requirements),
        RitualTrigger.CODEC.fieldOf("trigger").forGetter(Ritual::trigger),
        RitualEffect.CODEC.listOf().fieldOf("effects").forGetter(Ritual::effects)
    ).apply(inst, Ritual::new));

    /**
     * Triggers the ritual.
     *
     * @param player  The {@link Player} triggering the ritual.
     * @param level   The {@link Level} the ritual is triggered in.
     * @param vec     The {@link Vec3} the ritual is triggered at.
     * @param context The trigger context to use.
     */
    public void perform(Player player, Level level, Vec3 vec, T context) {
        if (!trigger.test(player, level, vec, context)) return;
        if (requirements.stream().allMatch(e -> e.test(player, level, vec))) {
            effects.forEach(e -> e.perform(player, level, vec));
        }
    }
}
