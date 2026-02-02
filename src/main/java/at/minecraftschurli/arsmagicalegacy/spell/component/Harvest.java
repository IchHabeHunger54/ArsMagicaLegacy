package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

//TODO
public class Harvest extends SpellComponent.CastBlock {
    @Override
    public Spell castBlock(Spell spell, List<SpellModifier> modifiers, Level level, LivingEntity caster, Entity directEntity, BlockHitResult hitResult) {
        return spell;
    }
}
