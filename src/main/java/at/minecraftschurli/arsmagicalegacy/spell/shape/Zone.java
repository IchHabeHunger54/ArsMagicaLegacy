package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class Zone extends SecondarySpellShape {
    @Override
    public SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity) {
        return null;
    }
}
