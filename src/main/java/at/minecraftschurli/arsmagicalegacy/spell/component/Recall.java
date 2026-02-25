package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.util.GlobalVec3;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Recall extends SpellComponent.CastEntity {
    @Override
    public SpellComponentCastResult castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        GlobalVec3 position = spell.dataComponents().grammar().get(AMDataComponents.SPELL_RECALL_POSITION.get());
        Entity entity = hitResult.getEntity();
        if (position == null && caster instanceof Player player) {
            player.displayClientMessage(AMTranslations.RECALL_POSITION_NOT_SET, true);
        } else if (position != null && position.dimension() == level.dimension()) {
            Vec3 vec3 = position.position();
            entity.teleportTo(vec3.x(), vec3.y(), vec3.z());
        }
        return SpellComponentCastResult.success(spell);
    }

    @Override
    public DataComponentType<?> getDataComponentType() {
        return AMDataComponents.SPELL_RECALL_POSITION.get();
    }
}
