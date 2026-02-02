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
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wall extends SecondarySpellShape {
    public Wall() {
        super(SpellStat.COLOR, AMSpells.DURATION_STAT, AMSpells.RANGE_STAT, AMSpells.TARGET_NON_SOLID_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, Level level, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        if (level.isClientSide()) return spell;
        var wall = AMEntities.WALL.get().create(level);
        wall.setPos(directEntity.getEyePosition());
        wall.setYRot(directEntity.getYRot());
        wall.setOwner(caster);
        wall.setSpell(spell);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        wall.setColor(helper.getColor(modifiers, spell, spell.activeShapeGroup()));
        wall.setTargetNonSolid(helper.getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, spell, level, caster, caster, hitResult) > 0);
        wall.setDuration((int) helper.getModifiedStat(AMServerConfig.WALL_DURATION.get(), AMSpells.DURATION_STAT, modifiers, spell, level, caster, caster, hitResult));
        wall.setRange((float) helper.getModifiedStat(AMServerConfig.WALL_RANGE.get(), AMSpells.RANGE_STAT, modifiers, spell, level, caster, caster, hitResult));
        level.addFreshEntity(wall);
        return spell;
    }
}
