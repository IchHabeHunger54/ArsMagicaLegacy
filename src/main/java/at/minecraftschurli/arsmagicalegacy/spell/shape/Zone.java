package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class Zone extends SecondarySpellShape {
    public Zone() {
        super(SpellStat.COLOR, AMSpells.DURATION_STAT, AMSpells.GRAVITY_STAT, AMSpells.RANGE_STAT, AMSpells.TARGET_NON_SOLID_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity) {
        Level level = caster.level();
        if (level.isClientSide()) return spell;
        var zone = AMEntities.ZONE.get().create(level);
        zone.setPos(directEntity.getX(), directEntity.getEyeY(), directEntity.getZ());
        zone.setYRot(directEntity.getYRot());
        zone.setOwner(caster);
        zone.setSpell(spell);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        zone.setColor(helper.getColor(modifiers, spell, spell.activeShapeGroup()));
        zone.setTargetNonSolid(helper.getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, spell, caster, caster, null) > 0);
        zone.setDuration((int) helper.getModifiedStat(AMServerConfig.ZONE_DURATION.get(), AMSpells.DURATION_STAT, modifiers, spell, caster, caster, null));
        zone.setGravity((float) (helper.getModifiedStat(0, AMSpells.GRAVITY_STAT, modifiers, spell, caster, caster, null) * AMServerConfig.ZONE_GRAVITY.get()));
        zone.setRange((float) helper.getModifiedStat(AMServerConfig.ZONE_RANGE.get(), AMSpells.RANGE_STAT, modifiers, spell, caster, caster, null));
        level.addFreshEntity(zone);
        return spell;
    }
}
