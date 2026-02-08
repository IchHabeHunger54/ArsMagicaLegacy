package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RandomTeleport extends SpellComponent.CastEntity {
    public RandomTeleport() {
        super(AMSpells.RANGE_STAT);
    }

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        if (AMUtil.cancelTeleport(entity, caster) || !(level instanceof ServerLevel serverLevel)) return spell;
        RandomSource random = serverLevel.getRandom();
        double range = ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.RANDOM_TELEPORT_RANGE.get(), AMSpells.RANGE_STAT, modifiers, spell, level, caster, directEntity, hitResult);
        for (int i = 0; i < AMServerConfig.RANDOM_TELEPORT_MAX_TRIES.get(); i++) {
            Vec3 vec = entity.position().add(random.nextDouble() * range - range / 2, random.nextDouble() * range - range / 2, random.nextDouble() * range - range / 2);
            BlockPos pos = BlockPos.containing(vec);
            if (serverLevel.getBlockState(pos).isAir() && serverLevel.getBlockState(pos.above()).isAir() && serverLevel.getBlockState(pos.below()).canOcclude()) {
                entity.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                break;
            }
        }
        return spell;
    }
}
