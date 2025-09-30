package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class Wave extends PrimarySpellShape {
    public Wave() {
        super(SpellStat.COLOR, AMSpells.DURATION_STAT, AMSpells.GRAVITY_STAT, AMSpells.RANGE_STAT, AMSpells.SPEED_STAT, AMSpells.TARGET_NON_SOLID_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster) {
        Level level = caster.level();
        if (level.isClientSide()) return spell;
        var wave = AMEntities.WAVE.get().create(level);
        wave.setPos(caster.getEyePosition());
        wave.setYRot(caster.getYRot());
        wave.setOwner(caster);
        wave.setSpell(spell);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        wave.setDeltaMovement(caster.getLookAngle().scale(helper.getModifiedStat(AMServerConfig.WAVE_SPEED.get(), AMSpells.SPEED_STAT, modifiers, spell, caster, caster, null)));
        wave.setColor(helper.getColor(modifiers, spell, spell.activeShapeGroup()));
        wave.setTargetNonSolid(helper.getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, spell, caster, caster, null) > 0);
        wave.setDuration((int) helper.getModifiedStat(AMServerConfig.WAVE_DURATION.get(), AMSpells.DURATION_STAT, modifiers, spell, caster, caster, null));
        wave.setGravity((float) (helper.getModifiedStat(0, AMSpells.GRAVITY_STAT, modifiers, spell, caster, caster, null) * AMServerConfig.WAVE_GRAVITY.get()));
        wave.setRange((float) helper.getModifiedStat(AMServerConfig.WAVE_RANGE.get(), AMSpells.RANGE_STAT, modifiers, spell, caster, caster, null));
        level.addFreshEntity(wave);
        return spell;
    }
}
