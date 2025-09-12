package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMAttachments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Frost extends SpellComponent.CastEntity {
    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        entity.setData(AMAttachments.FROST, Math.max(entity.getData(AMAttachments.FROST), 600));
        return spell;
    }
}
