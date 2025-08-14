package at.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public abstract non-sealed class PrimarySpellShape extends SpellPart {
    @Override
    public final boolean isPrimaryShape() {
        return true;
    }

    @Override
    public final boolean isSecondaryShape() {
        return false;
    }

    @Override
    public final boolean isComponent() {
        return false;
    }

    @Override
    public final boolean isModifier() {
        return false;
    }

    public abstract SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster);
}
