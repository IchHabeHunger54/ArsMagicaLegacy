package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMMobEffects;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Light extends SpellComponent.CastBoth {
    public Light() {
        super(AMSpells.DURATION_STAT);
    }

    @Override
    public SpellComponentCastResult castBlock(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, BlockHitResult hitResult) {
        Direction direction = hitResult.getDirection();
        BlockPos pos = hitResult.getBlockPos().offset(direction.getNormal());
        if (level.getBlockState(pos).isAir()) {
            level.setBlockAndUpdate(pos, AMBlocks.SPELL_LIGHT.get().defaultBlockState());
        }
        return SpellComponentCastResult.success(spell);
    }

    @Override
    public SpellComponentCastResult castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        if (!(hitResult.getEntity() instanceof LivingEntity living)) return SpellComponentCastResult.pass(spell);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        living.addEffect(new MobEffectInstance(AMMobEffects.ILLUMINATION, (int) helper.getModifiedStat(AMServerConfig.EFFECT_DURATION.get(), AMSpells.DURATION_STAT, modifiers, spell, level, caster, directEntity, hitResult)));
        return SpellComponentCastResult.success(spell);
    }
}
