package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Blink extends SpellComponent.CastEntity {
    public Blink() {
        super(AMSpells.RANGE_STAT);
    }

    @Override
    public SpellComponentCastResult castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        if (AMUtil.cancelTeleport(entity, caster)) return SpellComponentCastResult.success(spell);
        for (int i = (int) Math.round(ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.BLINK_RANGE.get(), AMSpells.RANGE_STAT, modifiers, spell, level, caster, directEntity, hitResult)); i > 0; i--) {
            Vec3 angle = entity.getLookAngle().normalize();
            double x = entity.getX() + angle.x() * i;
            double y = entity.getY() + angle.y() * i;
            double z = entity.getZ() + angle.z() * i;
            if (y >= level.getMinBuildHeight() && y < level.getMaxBuildHeight() && level.getBlockState(BlockPos.containing(x, y, z)).isAir() && level.getBlockState(BlockPos.containing(x, y + 1, z)).isAir()) {
                entity.teleportTo(x, y, z);
                break;
            }
        }
        return SpellComponentCastResult.success(spell);
    }
}
