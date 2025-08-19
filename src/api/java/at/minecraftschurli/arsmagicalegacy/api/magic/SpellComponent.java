package at.minecraftschurli.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract non-sealed class SpellComponent extends SpellPart {
    @Override
    public final boolean isPrimaryShape() {
        return false;
    }

    @Override
    public final boolean isSecondaryShape() {
        return false;
    }

    @Override
    public final boolean isComponent() {
        return true;
    }

    @Override
    public final boolean isModifier() {
        return false;
    }

    public abstract SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult);

    public static abstract class CastBlock extends SpellComponent {
        @Override
        public SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
            return hitResult instanceof BlockHitResult blockHitResult ? castBlock(spell, modifiers, caster, directEntity, blockHitResult) : SpellCastResult.pass(spell);
        }

        public abstract SpellCastResult castBlock(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, BlockHitResult hitResult);
    }

    public static abstract class CastEntity extends SpellComponent {
        @Override
        public SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
            return hitResult instanceof EntityHitResult entityHitResult ? castEntity(spell, modifiers, caster, directEntity, entityHitResult) : SpellCastResult.pass(spell);
        }

        public abstract SpellCastResult castEntity(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, EntityHitResult hitResult);
    }

    public static abstract class CastBoth extends SpellComponent {
        @Override
        public SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
            return switch (hitResult) {
                case BlockHitResult blockHitResult -> castBlock(spell, modifiers, caster, directEntity, blockHitResult);
                case EntityHitResult entityHitResult -> castEntity(spell, modifiers, caster, directEntity, entityHitResult);
                case null, default -> SpellCastResult.pass(spell);
            };
        }

        public abstract SpellCastResult castBlock(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, BlockHitResult hitResult);

        public abstract SpellCastResult castEntity(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, EntityHitResult hitResult);
    }
}
