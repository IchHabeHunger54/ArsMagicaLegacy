package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DivineIntervention extends SpellComponent.CastEntity {
    @Override
    public SpellComponentCastResult castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        if (AMUtil.cancelTeleport(entity, caster)) return SpellComponentCastResult.success(spell);
        ResourceKey<Level> dimension = level.dimension();
        if (dimension == Level.NETHER) {
            if (caster != null) {
                caster.sendSystemMessage(AMTranslations.NO_TELEPORT_NETHER);
            }
        } else if (dimension != Level.OVERWORLD && level instanceof ServerLevel server) {
            entity.changeDimension(entity instanceof ServerPlayer player
                ? player.findRespawnPositionAndUseSpawnBlock(false, DimensionTransition.DO_NOTHING)
                : new DimensionTransition(server.getServer().overworld(), server.getSharedSpawnPos().getBottomCenter(), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), DimensionTransition.DO_NOTHING));
        }
        return SpellComponentCastResult.success(spell);
    }
}
