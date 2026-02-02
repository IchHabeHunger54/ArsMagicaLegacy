package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//TODO
public class Contingency extends PrimarySpellShape {
    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster) {
        return spell;
    }
}
