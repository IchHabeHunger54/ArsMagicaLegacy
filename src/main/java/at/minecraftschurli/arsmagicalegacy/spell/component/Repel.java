package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Repel extends SpellComponent {
    public Repel() {
        super(AMSpells.RANGE_STAT, AMSpells.SPEED_STAT);
    }

    @Override
    public SpellComponentCastResult cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        if (hitResult == null || hitResult.getType() == HitResult.Type.MISS) return SpellComponentCastResult.success(spell);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        double range = helper.getModifiedStat(AMServerConfig.REPEL_RANGE.get(), AMSpells.RANGE_STAT, modifiers, spell, level, caster, directEntity, hitResult);
        double speed = helper.getModifiedStat(AMServerConfig.REPEL_SPEED.get(), AMSpells.SPEED_STAT, modifiers, spell, level, caster, directEntity, hitResult);
        Entity target = hitResult instanceof EntityHitResult result ? result.getEntity() : null;
        Vec3 targetPos = hitResult.getLocation();
        for (Entity entity : level.getEntities(target, target == null ? AABB.ofSize(targetPos, range, range, range) : target.getBoundingBox().inflate(range))) {
            if (entity == caster || entity == directEntity) continue;
            Vec3 vec = entity.position();
            entity.setDeltaMovement(entity.getDeltaMovement().add(vec.subtract(targetPos).scale(speed / (targetPos.distanceTo(vec) * 0.9 + 0.09))));
        }
        return SpellComponentCastResult.success(spell);
    }
}
