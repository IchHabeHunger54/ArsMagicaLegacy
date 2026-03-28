package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.clock.ServerClockManager;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;

import java.util.List;

public class Moonrise extends SpellComponent {
    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.pass(spell);
        ServerClockManager clockManager = level.clockManager();
        Timeline dayTimeline = level.registryAccess().getOrThrow(Timelines.OVERWORLD_DAY).value();
        // TODO use time markers
        long time = dayTimeline.getCurrentTicks(clockManager);
        if (time % 24000 < 12000) {
            clockManager.setTotalTicks(level.registryAccess().getOrThrow(WorldClocks.OVERWORLD), time + 12000);
            return SpellComponentCastResult.success(spell);
        }
        return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_MOONRISE);
    }
}
