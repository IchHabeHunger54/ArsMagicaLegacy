package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

//TODO
public class Rune extends SecondarySpellShape {
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity) {
        return spell;
    }
}
