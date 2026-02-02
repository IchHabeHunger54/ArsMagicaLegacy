package at.minecraftschurli.arsmagicalegacy.spell;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.arsmagicalegacy.init.AMMobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class TeleportComponent extends SpellComponent.CastEntity {
    public TeleportComponent(SpellStat... stats) {
        super(stats);
    }

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        if (caster != null && caster.hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            caster.sendSystemMessage(AMTranslations.NO_TELEPORT);
        } else if (entity instanceof LivingEntity living && living.hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            if (caster != null) {
                caster.sendSystemMessage(AMTranslations.NO_TELEPORT_OTHER);
            }
        } else {
            teleport(spell, modifiers, level, caster, directEntity, hitResult, entity);
        }
        return spell;
    }

    protected abstract void teleport(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult, Entity entity);
}
