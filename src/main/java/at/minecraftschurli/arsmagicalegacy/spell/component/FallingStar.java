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

public class FallingStar extends SpellComponent {
    public FallingStar() {
        super(SpellStat.COLOR, AMSpells.DAMAGE_STAT, AMSpells.RANGE_STAT, AMSpells.SPEED_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        Level level = caster.level();
        if (level.isClientSide() || level.dimensionType().hasCeiling() || hitResult == null || hitResult.getType() == HitResult.Type.MISS) return spell;
        var fallingStar = AMEntities.FALLING_STAR.get().create(level);
        fallingStar.setPos(hitResult.getLocation().add(0, AMServerConfig.FALLING_STAR_SPAWN_HEIGHT.get(), 0));
        fallingStar.setOwner(caster);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        fallingStar.setColor(helper.getColor(modifiers, spell, -1));
        fallingStar.setDeltaMovement(0, -helper.getModifiedStat(AMServerConfig.FALLING_STAR_SPEED.get(), AMSpells.SPEED_STAT, modifiers, spell, caster, directEntity, hitResult), 0);
        fallingStar.setDamage((float) helper.getModifiedStat(AMServerConfig.FALLING_STAR_DAMAGE.get(), AMSpells.DAMAGE_STAT, modifiers, spell, caster, directEntity, hitResult));
        fallingStar.setRange((float) helper.getModifiedStat(AMServerConfig.FALLING_STAR_RANGE.get(), AMSpells.RANGE_STAT, modifiers, spell, caster, directEntity, hitResult));
        level.addFreshEntity(fallingStar);
        return spell;
    }
}
