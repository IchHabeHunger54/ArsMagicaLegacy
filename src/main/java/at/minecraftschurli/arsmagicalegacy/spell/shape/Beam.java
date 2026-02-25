package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// TODO 26.1 rendering has changed completely
public class Beam extends PrimarySpellShape {
    @Override
    public SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster) {
        return new SpellCastResult(spell);
    }

    @Override
    public boolean isContinuous() {
        return true;
    }
}
