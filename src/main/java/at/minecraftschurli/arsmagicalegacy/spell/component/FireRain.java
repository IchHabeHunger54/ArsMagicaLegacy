package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
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

public class FireRain extends SpellComponent {
    public FireRain() {
        super(SpellStat.COLOR, AMSpells.DAMAGE_STAT, AMSpells.DURATION_STAT, AMSpells.RANGE_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        Level level = caster.level();
        if (level.isClientSide()) return spell;
        var fireRain = AMEntities.FIRE_RAIN.get().create(level);
        fireRain.setPos(directEntity.getEyePosition());
        fireRain.setOwner(caster);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        fireRain.setColor(helper.getColor(modifiers, spell, spell.activeShapeGroup()));
        fireRain.setDuration((int) helper.getModifiedStat(AMServerConfig.FIRE_RAIN_DURATION.get(), AMSpells.DURATION_STAT, modifiers, spell, caster, caster, null));
        fireRain.setFireDuration((int) helper.getModifiedStat(AMServerConfig.FIRE_RAIN_FIRE_DURATION.get(), AMSpells.DURATION_STAT, modifiers, spell, caster, caster, null));
        fireRain.setDamage((float) helper.getModifiedStat(AMServerConfig.FIRE_RAIN_DAMAGE.get(), AMSpells.DAMAGE_STAT, modifiers, spell, caster, caster, null));
        fireRain.setRange((float) helper.getModifiedStat(AMServerConfig.FIRE_RAIN_RANGE.get(), AMSpells.RANGE_STAT, modifiers, spell, caster, caster, null));
        level.addFreshEntity(fireRain);
        return spell;
    }
}
