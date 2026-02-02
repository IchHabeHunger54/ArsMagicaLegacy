package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMMobEffects;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Transplace extends SpellComponent.CastEntity {
    public static final ResourceLocation CASTER_PARTICLES = ArsMagicaApi.modLoc("transplace_caster");

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, Level level, LivingEntity caster, Entity directEntity, EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        if (caster.hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            caster.sendSystemMessage(AMTranslations.NO_TELEPORT);
        } else if (entity instanceof LivingEntity living && living.hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            caster.sendSystemMessage(AMTranslations.NO_TELEPORT_OTHER);
        } else if (!level.isClientSide()) {
            Vec3 targetPos = entity.position();
            Vec3 casterPos = caster.position();
            entity.teleportTo(casterPos.x(), casterPos.y(), casterPos.z());
            caster.teleportTo(targetPos.x(), targetPos.y(), targetPos.z());
        }
        return spell;
    }

    @Override
    public void spawnParticles(Spell spell, List<SpellModifier> modifiers, Level level, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        if (!(hitResult instanceof EntityHitResult entityHitResult)) return;
        super.spawnParticles(spell, modifiers, level, caster, directEntity, hitResult);
        if (entityHitResult.getEntity() instanceof LivingEntity living) {
            AMClientUtil.spawnParticles(CASTER_PARTICLES, living.position(), ArsMagicaApi.spellHelper().getColor(modifiers, spell, -1), living, living, new EntityHitResult(caster));
        }
    }
}
