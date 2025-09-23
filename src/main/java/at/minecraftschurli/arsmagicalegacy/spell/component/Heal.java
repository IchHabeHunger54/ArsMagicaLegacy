package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Heal extends SpellComponent.CastEntity {
    public Heal() {
        super(AMSpells.HEALING_STAT);
    }

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, EntityHitResult hitResult) {
        if (!(hitResult.getEntity() instanceof LivingEntity living)) return spell;
        float healing = (float) ArsMagicaApi.spellHelper().getModifiedStat(2, AMSpells.HEALING_STAT, modifiers, spell, caster, directEntity, hitResult);
        if (living.isInvertedHealAndHarm()) {
            living.hurt(caster.level().damageSources().indirectMagic(caster, directEntity), healing);
        } else {
            living.heal(healing);
        }
        return spell;
    }

    @Override
    public void spawnParticles(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        if (!(hitResult instanceof EntityHitResult entityHitResult) || !(entityHitResult.getEntity() instanceof LivingEntity living)) return;
        if (living.isInvertedHealAndHarm()) {
            ComponentParticleSpawner.spawnParticles(ArsMagicaApi.modLoc("heal_undead"), spell, modifiers, caster, directEntity, hitResult);
        } else {
            super.spawnParticles(spell, modifiers, caster, directEntity, hitResult);
        }
    }
}
