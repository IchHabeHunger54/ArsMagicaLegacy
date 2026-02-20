package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
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
    public Spell cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        if (directEntity == null || hitResult == null || hitResult.getType() == HitResult.Type.MISS) return spell;
        SpellHelper helper = ArsMagicaApi.spellHelper();
        if (hitResult instanceof BlockHitResult result) {
            int range = (int) helper.getModifiedStat(1, AMSpells.RANGE_STAT, modifiers, spell, level, caster, directEntity, hitResult);
            BlockPos pos = result.getBlockPos();
            Direction.Axis axis = result.getDirection().getAxis();
            for (int i = -range; i <= range; i++) {
                for (int j = -range; j <= range; j++) {
                    BlockPos currentPos = switch (axis) {
                        case X -> pos.offset(0, i, j);
                        case Y -> pos.offset(i, 0, j);
                        case Z -> pos.offset(i, j, 0);
                    };
                    spell = helper.castGrammar(spell, level, caster, directEntity, new BlockHitResult(Vec3.atCenterOf(currentPos), result.getDirection(), currentPos, result.isInside()));
                }
            }
        } else if (hitResult instanceof EntityHitResult result) {
            double range = helper.getModifiedStat(1, AMSpells.RANGE_STAT, modifiers, spell, level, caster, directEntity, hitResult);
            for (Entity entity : level.getEntities(null, new AABB(result.getLocation().subtract(range, range, range), result.getLocation().add(range, range, range)))) {
                int id = entity.getId();
                if (caster != null && id == caster.getId() || id == directEntity.getId()) continue;
                spell = helper.castGrammar(spell, level, caster, directEntity, new EntityHitResult(entity));
            }
        }
        return spell;
    }
}
