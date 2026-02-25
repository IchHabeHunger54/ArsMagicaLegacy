package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BanishRain extends SpellComponent {
    public BanishRain() {
        super(AMSpells.DURATION_STAT);
    }

    @Override
    public SpellComponentCastResult cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        if (level instanceof ServerLevel serverLevel) {
            if (serverLevel.isRaining()) {
                serverLevel.setWeatherParameters((int) ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.BANISH_RAIN_DURATION.get(), AMSpells.DURATION_STAT, modifiers, spell, level, caster, directEntity, hitResult), 0, false, false);
                return SpellComponentCastResult.success(spell);
            }
            return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_BANISH_RAIN);
        }
        return SpellComponentCastResult.pass(spell);
    }
}
