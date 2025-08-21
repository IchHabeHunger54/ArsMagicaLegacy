package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Fling extends SpellComponent {
    @Override
    public SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        return null;
    }
}
