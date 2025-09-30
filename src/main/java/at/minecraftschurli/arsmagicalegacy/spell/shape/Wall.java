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

public class Wall extends SecondarySpellShape {
    public Wall() {
        super(SpellStat.COLOR, AMSpells.DURATION_STAT, AMSpells.RANGE_STAT, AMSpells.TARGET_NON_SOLID_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity) {
        Level level = caster.level();
        if (level.isClientSide()) return spell;
        var wall = AMEntities.WALL.get().create(level);
        wall.setPos(directEntity.getX(), directEntity.getEyeY(), directEntity.getZ());
        wall.setYRot(directEntity.getYRot());
        wall.setOwner(caster);
        wall.setSpell(spell);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        wall.setColor(helper.getColor(modifiers, spell, spell.activeShapeGroup()));
        wall.setTargetNonSolid(helper.getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, spell, caster, caster, null) > 0);
        wall.setDuration((int) helper.getModifiedStat(AMServerConfig.WALL_DURATION.get(), AMSpells.DURATION_STAT, modifiers, spell, caster, caster, null));
        wall.setRange((float) helper.getModifiedStat(AMServerConfig.WALL_RANGE.get(), AMSpells.RANGE_STAT, modifiers, spell, caster, caster, null));
        level.addFreshEntity(wall);
        return spell;
    }
}
