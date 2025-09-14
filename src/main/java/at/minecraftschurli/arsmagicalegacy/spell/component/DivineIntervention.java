package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMMobEffects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class DivineIntervention extends SpellComponent.CastEntity {
    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, EntityHitResult hitResult) {
        if (!(hitResult.getEntity() instanceof LivingEntity entity)) return spell;
        if (caster.hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            caster.sendSystemMessage(AMTranslations.NO_TELEPORT);
        } else if (entity.hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            caster.sendSystemMessage(AMTranslations.NO_TELEPORT_OTHER);
        } else {
            Level level = caster.level();
            ResourceKey<Level> dimension = level.dimension();
            if (dimension == Level.NETHER) {
                caster.sendSystemMessage(AMTranslations.NO_TELEPORT_NETHER);
            } else if (dimension != Level.OVERWORLD && level instanceof ServerLevel server) {
                entity.changeDimension(entity instanceof ServerPlayer player
                    ? player.findRespawnPositionAndUseSpawnBlock(false, DimensionTransition.DO_NOTHING)
                    : new DimensionTransition(server.getServer().overworld(), server.getSharedSpawnPos().getBottomCenter(), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), DimensionTransition.DO_NOTHING));
            }
        }
        return spell;
    }
}
