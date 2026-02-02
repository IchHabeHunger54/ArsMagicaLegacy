package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LifeDrain extends SpellComponent.CastEntity {
    public LifeDrain() {
        super(AMSpells.DAMAGE_STAT, AMSpells.HEALING_STAT);
    }

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        if (caster == null || !(hitResult.getEntity() instanceof LivingEntity entity)) return spell;
        float damage = (float) ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.LIFE_DRAIN_DAMAGE.get(), entity.isInvertedHealAndHarm() ? AMSpells.HEALING_STAT : AMSpells.DAMAGE_STAT, modifiers, spell, level, caster, directEntity, hitResult);
        if (entity.hurt(level.damageSources().indirectMagic(caster, directEntity), damage)) {
            caster.heal(damage);
        }
        return spell;
    }
}
