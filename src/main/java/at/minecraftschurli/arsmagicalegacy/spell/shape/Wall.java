package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;

import java.util.List;

public class Wall extends SecondarySpellShape {
    public Wall() {
        super(SpellStat.COLOR, AMSpells.DURATION_STAT, AMSpells.RANGE_STAT, AMSpells.TARGET_NON_SOLID_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        Level level = context.level();
        Entity directEntity = context.directEntity();
        if (level.isClientSide() || directEntity == null) return new SpellCastResult(spell);
        var wall = AMEntities.WALL.get().create(level, EntitySpawnReason.MOB_SUMMONED);
        wall.setPos(directEntity.getEyePosition());
        wall.setXRot(directEntity.getXRot());
        wall.setYRot(directEntity.getYRot());
        wall.setOwner(context.caster());
        wall.setSpell(spell);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        wall.setColor(helper.getColor(modifiers, spell, spell.activeShapeGroup()));
        wall.setTargetNonSolid(helper.getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, context) > 0);
        wall.setDuration((int) helper.getModifiedStat(AMServerConfig.WALL_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context));
        wall.setRange((float) helper.getModifiedStat(AMServerConfig.WALL_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context));
        level.addFreshEntity(wall);
        return new SpellCastResult(spell).setSuccess();
    }
}
