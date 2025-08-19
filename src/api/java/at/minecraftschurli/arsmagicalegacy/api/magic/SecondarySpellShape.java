package at.minecraftschurli.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public abstract non-sealed class SecondarySpellShape extends SpellPart {
    @Override
    public final boolean isPrimaryShape() {
        return false;
    }

    @Override
    public final boolean isSecondaryShape() {
        return true;
    }

    @Override
    public final boolean isComponent() {
        return false;
    }

    @Override
    public final boolean isModifier() {
        return false;
    }

    public abstract SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity);
}
