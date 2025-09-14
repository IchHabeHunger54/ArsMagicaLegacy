package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Knockback extends SpellComponent.CastEntity {
    public Knockback() {
        super(AMSpells.SPEED_STAT);
    }

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        if (entity == caster) return spell;
        double velocity = ArsMagicaApi.spellHelper().getModifiedStat(1, AMSpells.SPEED_STAT, modifiers, spell, caster, directEntity, hitResult);
        entity.setDeltaMovement(entity.getDeltaMovement().add(velocity * Math.cos(Math.atan2(entity.getZ() - caster.getZ(), entity.getX() - caster.getX())), velocity * 0.325f, velocity * Math.sin(Math.atan2(entity.getZ() - caster.getZ(), entity.getX() - caster.getX()))));
        return spell;
    }
}
