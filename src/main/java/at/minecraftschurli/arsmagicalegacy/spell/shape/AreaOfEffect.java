package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AreaOfEffect extends SecondarySpellShape {
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        if (directEntity == null || hitResult == null) return spell;
        int range = (int) ArsMagicaApi.spellHelper().getModifiedStat(1, AMSpells.RANGE_STAT, modifiers, spell, level, caster, directEntity, hitResult);
        BlockPos pos = BlockPos.containing(hitResult.getLocation());
        Direction.Axis axis = hitResult instanceof BlockHitResult result ? result.getDirection().getAxis() : Math.abs(directEntity.getXRot()) > 45 ? Direction.Axis.Y : directEntity.getDirection().getAxis();
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                BlockPos currentPos = switch (axis) {
                    case X -> pos.offset(0, i, j);
                    case Y -> pos.offset(i, 0, j);
                    case Z -> pos.offset(i, j, 0);
                };
                Vec3 currentVec = Vec3.atCenterOf(currentPos);
                spell = ArsMagicaApi.spellHelper().castGrammar(spell, level, caster, directEntity, switch (hitResult) {
                    case EntityHitResult result -> new EntityHitResult(result.getEntity(), currentVec);
                    case BlockHitResult result -> new BlockHitResult(currentVec, result.getDirection(), currentPos, result.isInside());
                    default -> hitResult;
                });
            }
        }
        return spell;
    }
}
