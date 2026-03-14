package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class BanishRain extends SpellComponent {
    public BanishRain() {
        super(AMSpells.DURATION_STAT);
    }

    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        if (context.level() instanceof ServerLevel level) {
            MinecraftServer server = level.getServer();
            if (server != null && level.isRaining()) {
                server.setWeatherParameters((int) ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.BANISH_RAIN_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context), 0, false, false);
                return SpellComponentCastResult.success(spell);
            }
            return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_BANISH_RAIN);
        }
        return SpellComponentCastResult.pass(spell);
    }
}
