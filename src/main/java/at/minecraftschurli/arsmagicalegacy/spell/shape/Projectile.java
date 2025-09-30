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

public class Projectile extends PrimarySpellShape {
    public Projectile() {
        super(SpellStat.COLOR, AMSpells.BOUNCE_STAT, AMSpells.DURATION_STAT, AMSpells.GRAVITY_STAT, AMSpells.PIERCING_STAT, AMSpells.SPEED_STAT, AMSpells.TARGET_NON_SOLID_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster) {
        Level level = caster.level();
        if (level.isClientSide()) return spell;
        var projectile = AMEntities.PROJECTILE.get().create(level);
        projectile.setPos(caster.getX(), caster.getEyeY(), caster.getZ());
        projectile.setYRot(caster.getYRot());
        projectile.setOwner(caster);
        projectile.setSpell(spell);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        projectile.setDeltaMovement(caster.getLookAngle().scale(helper.getModifiedStat(AMServerConfig.PROJECTILE_SPEED.get(), AMSpells.SPEED_STAT, modifiers, spell, caster, caster, null)));
        projectile.setColor(helper.getColor(modifiers, spell, spell.activeShapeGroup()));
        projectile.setTargetNonSolid(helper.getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, spell, caster, caster, null) > 0);
        projectile.setDuration((int) helper.getModifiedStat(AMServerConfig.PROJECTILE_DURATION.get(), AMSpells.DURATION_STAT, modifiers, spell, caster, caster, null));
        projectile.setBounces((int) helper.getModifiedStat(0, AMSpells.BOUNCE_STAT, modifiers, spell, caster, caster, null));
        projectile.setPierces((int) helper.getModifiedStat(0, AMSpells.PIERCING_STAT, modifiers, spell, caster, caster, null));
        projectile.setGravity((float) (helper.getModifiedStat(0, AMSpells.GRAVITY_STAT, modifiers, spell, caster, caster, null) * AMServerConfig.PROJECTILE_GRAVITY.get()));
        level.addFreshEntity(projectile);
        return spell;
    }
}
