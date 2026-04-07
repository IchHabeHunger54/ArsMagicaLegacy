package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class FireRain extends SpellComponent {
    public FireRain() {
        super(SpellStat.COLOR, AMSpells.DAMAGE_STAT, AMSpells.DURATION_STAT, AMSpells.RANGE_STAT);
    }

    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        if (context.isHitResultNullOrMiss()) return SpellComponentCastResult.failure(AMTranslations.SPELL_FAIL_NO_HIT);
        Level level = context.level();
        if (level.isClientSide()) return SpellComponentCastResult.pass();
        LivingEntity caster = context.caster();
        var fireRain = AMEntities.FIRE_RAIN.get().create(level, EntitySpawnReason.MOB_SUMMONED);
        assert fireRain != null;
        assert context.hitResult() != null;
        fireRain.setPos(context.hitResult().getLocation());
        if (caster != null) {
            fireRain.setOwner(caster);
        }
        SpellHelper helper = ArsMagicaApi.spellHelper();
        fireRain.setColor(helper.getColor(modifiers, context.spellData(), -1));
        fireRain.setDuration((int) helper.getModifiedStat(AMServerConfig.FIRE_RAIN_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context));
        fireRain.setFireDuration((int) helper.getModifiedStat(AMServerConfig.FIRE_RAIN_FIRE_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context));
        fireRain.setDamage((float) helper.getModifiedStat(AMServerConfig.FIRE_RAIN_DAMAGE.get(), AMSpells.DAMAGE_STAT, modifiers, context));
        fireRain.setRange((float) helper.getModifiedStat(AMServerConfig.FIRE_RAIN_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context));
        level.addFreshEntity(fireRain);
        return SpellComponentCastResult.success();
    }
}
