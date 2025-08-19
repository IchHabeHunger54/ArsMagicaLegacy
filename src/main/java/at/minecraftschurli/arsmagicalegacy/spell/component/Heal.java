package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.magic.Spell;
import at.minecraftschurli.arsmagicalegacy.api.magic.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.magic.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.magic.SpellModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Heal extends SpellComponent.CastEntity {
    @Override
    public SpellCastResult castEntity(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, EntityHitResult hitResult) {
        if (!(hitResult.getEntity() instanceof LivingEntity living)) return SpellCastResult.pass(spell);
        float healing = 2;
        if (living.isInvertedHealAndHarm()) {
            living.hurt(caster.level().damageSources().indirectMagic(caster, caster), healing);
        } else {
            living.heal(healing);
        }
        return SpellCastResult.success(spell);
    }
}
