package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Heal extends SpellComponent.CastEntity {
    public static final ResourceLocation UNDEAD_PARTICLES = ArsMagicaApi.id("heal_undead");

    public Heal() {
        super(AMSpells.HEALING_STAT);
    }

    @Override
    public SpellComponentCastResult castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        if (!(hitResult.getEntity() instanceof LivingEntity living)) return SpellComponentCastResult.pass(spell);
        float healing = (float) ArsMagicaApi.spellHelper().getModifiedStat(2, AMSpells.HEALING_STAT, modifiers, spell, level, caster, directEntity, hitResult);
        if (living.isInvertedHealAndHarm()) {
            living.hurt(caster != null ? level.damageSources().indirectMagic(caster, directEntity) : level.damageSources().magic(), healing);
        } else {
            living.heal(healing);
        }
        return SpellComponentCastResult.success(spell);
    }

    @Override
    public void spawnParticles(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        if (directEntity == null || !(hitResult instanceof EntityHitResult entityHitResult) || !(entityHitResult.getEntity() instanceof LivingEntity living)) return;
        if (living.isInvertedHealAndHarm()) {
            AMClientUtil.spawnParticles(UNDEAD_PARTICLES, directEntity.position(), ArsMagicaApi.spellHelper().getColor(modifiers, spell, -1), caster, directEntity, hitResult);
        } else {
            super.spawnParticles(spell, modifiers, level, caster, directEntity, hitResult);
        }
    }
}
