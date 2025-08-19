package at.minecraftschurli.arsmagicalegacy.api;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

public interface SpellHelper {
    SpellCastResult cast(Spell spell, LivingEntity caster, boolean consume, boolean awardXp);

    SpellCastResult castPrimary(Spell spell, LivingEntity caster);

    SpellCastResult castSecondary(Spell spell, LivingEntity caster, Entity directEntity);

    SpellCastResult castGrammar(Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);

    SpellCastResult castSecondaryOrGrammar(Spell spell, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);
}
