package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AreaOfEffect extends SecondarySpellShape {
    public AreaOfEffect() {
        super(AMSpells.RANGE_STAT);
    }

    @Override
    public SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        if (directEntity == null || hitResult == null || hitResult.getType() == HitResult.Type.MISS) return null;
        double range = ArsMagicaApi.spellHelper().getModifiedStat(1, AMSpells.RANGE_STAT, modifiers, spell, level, caster, directEntity, hitResult);
        SpellCastResult result = new SpellCastResult(spell);
        if (hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getDirection();
            doBlockAoe(result, level, caster, directEntity, pos, (int) range, direction, blockHitResult.isInside());
            doEntityAoe(result, level, caster, directEntity, Vec3.atCenterOf(pos).add(Vec3.atLowerCornerOf(direction.getNormal()).scale(0.5)), range);
        } else if (hitResult instanceof EntityHitResult entityHitResult) {
            float xRot = directEntity.getXRot();
            Entity entity = entityHitResult.getEntity();
            doBlockAoe(result, level, caster, directEntity, entity.blockPosition(), (int) range, Math.abs(xRot) > 45 ? xRot > 0 ? Direction.DOWN : Direction.UP : directEntity.getDirection(), false);
            doEntityAoe(result, level, caster, directEntity, entity.position(), range);
        }
        return null;
    }

    private void doBlockAoe(SpellCastResult result, Level level, @Nullable LivingEntity caster, Entity directEntity, BlockPos pos, int range, Direction direction, boolean inside) {
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                BlockPos currentPos = switch (direction.getAxis()) {
                    case X -> pos.offset(0, i, j);
                    case Y -> pos.offset(i, 0, j);
                    case Z -> pos.offset(i, j, 0);
                };
                updateResult(result, ArsMagicaApi.spellHelper().castGrammar(result.getSpell(), level, caster, directEntity, new BlockHitResult(Vec3.atCenterOf(currentPos), direction, currentPos, inside)));
            }
        }
    }

    private void doEntityAoe(SpellCastResult result, Level level, @Nullable LivingEntity caster, Entity directEntity, Vec3 location, double range) {
        for (Entity entity : level.getEntities(null, new AABB(location.subtract(range, range, range), location.add(range, range, range)))) {
            int id = entity.getId();
            if (caster != null && id == caster.getId() || id == directEntity.getId()) continue;
            updateResult(result, ArsMagicaApi.spellHelper().castGrammar(result.getSpell(), level, caster, directEntity, new EntityHitResult(entity)));
        }
    }

    private void updateResult(SpellCastResult result, SpellCastResult grammarResult) {
        result.setSpell(grammarResult.getSpell());
        result.setMessage(grammarResult.getMessage());
        if (grammarResult.isSuccess()) {
            result.setSuccess();
        }
    }
}
