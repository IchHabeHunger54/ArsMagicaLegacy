package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.spell.TeleportComponent;
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

public class Transplace extends TeleportComponent {
    public static final ResourceLocation CASTER_PARTICLES = ArsMagicaApi.id("transplace_caster");

    @Override
    protected void teleport(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult, Entity entity) {
        if (!level.isClientSide() && caster != null) {
            Vec3 targetPos = entity.position();
            Vec3 casterPos = caster.position();
            entity.teleportTo(casterPos.x(), casterPos.y(), casterPos.z());
            caster.teleportTo(targetPos.x(), targetPos.y(), targetPos.z());
        }
    }

    @Override
    public void spawnParticles(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        if (caster == null || !(hitResult instanceof EntityHitResult entityHitResult)) return;
        super.spawnParticles(spell, modifiers, level, caster, directEntity, hitResult);
        if (entityHitResult.getEntity() instanceof LivingEntity living) {
            AMClientUtil.spawnParticles(CASTER_PARTICLES, living.position(), ArsMagicaApi.spellHelper().getColor(modifiers, spell, -1), living, living, new EntityHitResult(caster));
        }
    }
}
