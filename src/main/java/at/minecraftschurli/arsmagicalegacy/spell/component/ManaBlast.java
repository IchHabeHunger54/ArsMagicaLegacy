package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaBlast extends SpellComponent.CastEntity {
    public ManaBlast() {
        super(AMSpells.DAMAGE_STAT);
    }

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        if (caster == null || !(hitResult.getEntity() instanceof LivingEntity entity)) return spell;
        ManaHelper helper = ArsMagicaApi.manaHelper();
        double mana = helper.getMana(caster);
        entity.hurt(level.damageSources().indirectMagic(caster, directEntity), (float) ArsMagicaApi.spellHelper().getModifiedStat(mana * AMServerConfig.MANA_BLAST_FACTOR.get(), AMSpells.DAMAGE_STAT, modifiers, spell, level, caster, directEntity, hitResult));
        helper.decreaseMana(caster, mana);
        return spell;
    }
}
