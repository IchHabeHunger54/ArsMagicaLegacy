package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
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

public class Blizzard extends SpellComponent {
    public Blizzard() {
        super(SpellStat.COLOR, AMSpells.DAMAGE_STAT, AMSpells.DURATION_STAT, AMSpells.RANGE_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public SpellComponentCastResult cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        if (level.isClientSide() || hitResult == null || hitResult.getType() == HitResult.Type.MISS) return SpellComponentCastResult.success(spell);
        var blizzard = AMEntities.BLIZZARD.get().create(level);
        blizzard.setPos(hitResult.getLocation());
        if (caster != null) {
            blizzard.setOwner(caster);
        }
        SpellHelper helper = ArsMagicaApi.spellHelper();
        blizzard.setColor(helper.getColor(modifiers, spell, -1));
        blizzard.setDuration((int) helper.getModifiedStat(AMServerConfig.BLIZZARD_DURATION.get(), AMSpells.DURATION_STAT, modifiers, spell, level, caster, directEntity, hitResult));
        blizzard.setFrostDuration((int) helper.getModifiedStat(AMServerConfig.BLIZZARD_FROST_DURATION.get(), AMSpells.DURATION_STAT, modifiers, spell, level, caster, directEntity, hitResult));
        blizzard.setDamage((float) helper.getModifiedStat(AMServerConfig.BLIZZARD_DAMAGE.get(), AMSpells.DAMAGE_STAT, modifiers, spell, level, caster, directEntity, hitResult));
        blizzard.setRange((float) helper.getModifiedStat(AMServerConfig.BLIZZARD_RANGE.get(), AMSpells.RANGE_STAT, modifiers, spell, level, caster, directEntity, hitResult));
        level.addFreshEntity(blizzard);
        return SpellComponentCastResult.success(spell);
    }
}
