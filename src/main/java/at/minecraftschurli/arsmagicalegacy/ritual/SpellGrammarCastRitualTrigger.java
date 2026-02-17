package at.minecraftschurli.arsmagicalegacy.ritual;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualTrigger;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellGrammar;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;

public record SpellGrammarCastRitualTrigger(List<SpellPart> parts) implements RitualTrigger<SpellGrammar> {
    public static final MapCodec<SpellGrammarCastRitualTrigger> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        AMRegistries.SPELL_PARTS.byNameCodec().listOf().fieldOf("parts").forGetter(SpellGrammarCastRitualTrigger::parts)
    ).apply(inst, SpellGrammarCastRitualTrigger::new));

    @Override
    public MapCodec<? extends RitualTrigger<SpellGrammar>> codec() {
        return CODEC;
    }

    @Override
    public boolean test(Player player, Level level, Vec3 vec, SpellGrammar context) {
        return new HashSet<>(context.parts()).containsAll(parts);
    }
}
