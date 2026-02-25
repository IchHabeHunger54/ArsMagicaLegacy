package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaDrain extends SpellComponent.CastEntity {
    @Override
    public SpellComponentCastResult castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        if (caster == null || !(hitResult.getEntity() instanceof LivingEntity entity)) return SpellComponentCastResult.success(spell);
        ManaHelper helper = ArsMagicaApi.manaHelper();
        double mana = Math.min(helper.getMana(entity), AMServerConfig.MANA_DRAIN_MAX.get());
        helper.decreaseMana(entity, mana);
        helper.increaseMana(caster, mana);
        return SpellComponentCastResult.success(spell);
    }
}
